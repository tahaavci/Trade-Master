package com.trader.exchangeservice.Dto;

import com.trader.exchangeservice.Model.Currency;
import com.trader.exchangeservice.Model.Transaction;

import java.math.BigDecimal;
import java.util.*;

public class TypeConvertion {


    public static TransactionDto TransactionToDto(Transaction transaction){


        return new TransactionDto(transaction.getTransactionId(),transaction.getTransactionAmount(),
                transaction.getTransactionDate(),transaction.getTransactionDesc(),
                transaction.getTransactionAccountId(), transaction.isTransactionInComing());
    }


    public static List<TransactionDto> TransactionToDtoList(List<Transaction> transactionList){


        return transactionList.stream().map(TypeConvertion::TransactionToDto).toList();
    }

    private static CurrencyDto CurrencyToDto(Currency currency){
        return new CurrencyDto(currency.getCurrencyRateId(),
                                currency.getCurrencySrcType().name(),
                                currency.getCurrencyDstType().name(),
                                currency.getCurrencySellPrice(),
                                currency.getCurrencyBuyPrice(),
                currency.getCurrencyLastUpdated());
    }

    public static List<CurrencyDto> PrepareCurrencyList(List<Currency> currencies){


        Currency usd_try = currencies.stream().filter(x -> x.getCurrencySrcType()== Currency.Type.USD && x.getCurrencyDstType()== Currency.Type.TRY)
                .findFirst().orElse(null);

        Currency eur_try = currencies.stream().filter(x -> x.getCurrencySrcType()== Currency.Type.EUR && x.getCurrencyDstType()== Currency.Type.TRY)
                .findFirst().orElse(null);


        Currency gold_try = currencies.stream().filter(x -> x.getCurrencySrcType()== Currency.Type.GOLD && x.getCurrencyDstType()== Currency.Type.TRY)
                .findFirst().orElse(null);


        Currency usd_eur = currencies.stream().filter(x -> x.getCurrencySrcType()== Currency.Type.EUR && x.getCurrencyDstType()== Currency.Type.USD)
                .findFirst().orElse(null);


        List<CurrencyDto> dtoList = new ArrayList<>();
        dtoList.add(CurrencyToDto(usd_try));
        dtoList.add(CurrencyToDto(eur_try));
        dtoList.add(CurrencyToDto(gold_try));
        dtoList.add(CurrencyToDto(usd_eur));


        return dtoList;
    }






}
