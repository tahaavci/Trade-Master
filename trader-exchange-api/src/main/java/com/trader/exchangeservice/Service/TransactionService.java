package com.trader.exchangeservice.Service;

import com.trader.exchangeservice.Dto.TransactionDto;
import com.trader.exchangeservice.Dto.TypeConvertion;
import com.trader.exchangeservice.Model.Transaction;
import com.trader.exchangeservice.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;


    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public TransactionDto saveTransaction(Transaction transaction){


        return TypeConvertion.TransactionToDto(transactionRepository.save(transaction));
    }

    public List<TransactionDto> GetAccountTransaction(Long accountId){



       return TypeConvertion.TransactionToDtoList(transactionRepository.GetCustomerTransactions(accountId));
    }


}
