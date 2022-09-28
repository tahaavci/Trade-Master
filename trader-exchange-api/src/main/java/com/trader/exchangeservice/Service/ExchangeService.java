package com.trader.exchangeservice.Service;

import com.trader.exchangeservice.Dto.Account;
import com.trader.exchangeservice.Dto.ExchangeDto;
import com.trader.exchangeservice.Exception.InsufficientBalanceException;
import com.trader.exchangeservice.Dto.TransactionApproveResponseDto;
import com.trader.exchangeservice.Model.Transaction;
import com.trader.exchangeservice.Model.TransactionApproveQueue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

@Service
@EnableTransactionManagement
public class ExchangeService {

    private final TransactionService transactionService;
    private final TransactionApproveQueueService transactionApproveQueueService;
    private final CurrencyService currencyService;
    private final Clock clock;
    private final int queueTimeout =1;//minutes

    private final CustomerAccountService customerAccountService;

    public ExchangeService(TransactionService transactionService, TransactionApproveQueueService transactionApproveQueueService, CurrencyService currencyService, Clock clock, CustomerAccountService customerAccountService) {
        this.transactionService = transactionService;
        this.transactionApproveQueueService = transactionApproveQueueService;
        this.currencyService = currencyService;
        this.clock = clock;
        this.customerAccountService = customerAccountService;
    }



    @Transactional
    public TransactionApproveResponseDto BeginExchange(ExchangeDto dto) {

        Account src = customerAccountService.retrieveAccount(dto.srcAccount());
        Account dst = customerAccountService.retrieveAccount(dto.dstAccount());


        if (src.accountType() == dst.accountType()){

            if(src.accountBalance().compareTo(dto.amount()) < 0)
                throw new InsufficientBalanceException();

            TransactionApproveQueue queue = transactionApproveQueueService.SaveToQueue(
                    new TransactionApproveQueue(dto.srcAccount(),
                            dto.dstAccount(),
                            dto.amount(),
                            dto.amount(),
                            BigDecimal.ONE,
                            LocalDateTime.now(clock).plusMinutes(queueTimeout)));

            return sendConfirmationMessage(queue.getApproveQueueId(),BigDecimal.ONE, dto.amount());

        }
        else{

                BigDecimal sell = currencyService.GetSellPrice(src.accountType(),dst.accountType());
                if(!sell.equals(BigDecimal.ZERO)){

                    BigDecimal removedFromSrc = dto.amount().divide(sell,7, RoundingMode.HALF_UP);

                    if(src.accountBalance().compareTo(removedFromSrc) < 0)
                       throw new InsufficientBalanceException();

                    TransactionApproveQueue queue = transactionApproveQueueService.SaveToQueue(
                            new TransactionApproveQueue(dto.srcAccount(),
                                                        dto.dstAccount(),
                                                        removedFromSrc,
                                                        dto.amount(),
                                                        sell,
                                                        LocalDateTime.now(clock).plusMinutes(queueTimeout)));


                    return sendConfirmationMessage(queue.getApproveQueueId(),sell,removedFromSrc);
                }


                BigDecimal buy = currencyService.GetBuyPrice(dst.accountType(),src.accountType());
                if(!buy.equals(BigDecimal.ZERO)){


                    BigDecimal removedFromSrc = dto.amount().multiply(buy);

                    if(src.accountBalance().compareTo(removedFromSrc) < 0)
                        throw new InsufficientBalanceException();

                    TransactionApproveQueue queue = transactionApproveQueueService.SaveToQueue(
                            new TransactionApproveQueue(dto.srcAccount(),
                                    dto.dstAccount(),
                                    removedFromSrc,
                                    dto.amount(),
                                    buy,
                                    LocalDateTime.now(clock).plusMinutes(queueTimeout)));

                    return sendConfirmationMessage(queue.getApproveQueueId(),buy,removedFromSrc);
                }

             return sendConfirmationMessage(0L,BigDecimal.ZERO,BigDecimal.ZERO);
        }

    }


    public TransactionApproveResponseDto sendConfirmationMessage(Long queueId,BigDecimal exchangeRate,BigDecimal amount){


        return new TransactionApproveResponseDto(queueId,exchangeRate,amount);
    }


    @Transactional
    public String CompleteTransaction(Long queueId,Boolean isApproved) throws TimeoutException {

        if(!isApproved) {
            transactionApproveQueueService.DeleteFromQueue(queueId);

            return "Transaction Deleted From Queue.";
        }

        TransactionApproveQueue queue = transactionApproveQueueService.GetQueueElement(queueId);

        if(queue.getQueueTimeout().isBefore(LocalDateTime.now(clock))){
            transactionApproveQueueService.DeleteFromQueue(queueId);
            throw new TimeoutException("Queue Timeout Exceeded.");
        }


        Account src = customerAccountService.retrieveAccount(queue.getQueueSrcAccountId());
        if(src.accountBalance().compareTo(queue.getQueueSrcSubtractAmount()) < 0){
            transactionApproveQueueService.DeleteFromQueue(queueId);
            throw new InsufficientBalanceException();
        }


        // Source Account Update
        customerAccountService.updateAccountBalance(queue.getQueueSrcAccountId(),
                src.accountBalance().subtract(queue.getQueueSrcSubtractAmount()));


        // Destination Account Update
        Account dst = customerAccountService.retrieveAccount(queue.getQueueDstAccountId());
        customerAccountService.updateAccountBalance(queue.getQueueDstAccountId(),
                dst.accountBalance().add(queue.getQueueDstAddAmount()));



        //Save as Buy Transaction
        transactionService.saveTransaction(new Transaction(queue.getQueueDstAddAmount(),
                LocalDateTime.now(clock),
                "Sell "+src.accountType()+" from "+queue.getQueueExchangeRate(),
                queue.getQueueDstAccountId(),
                true));


        //Save as Sell Transaction
        transactionService.saveTransaction(new Transaction(queue.getQueueSrcSubtractAmount(),
                LocalDateTime.now(clock),
                "Sell from "+queue.getQueueExchangeRate(),
                queue.getQueueSrcAccountId(),
                false));


        return "Transaction Completed.";
    }



}
