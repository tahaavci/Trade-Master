package com.trader.exchangeservice.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record TransactionDto(String transactionId,
                             BigDecimal transactionAmount,
                             @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime transactionDate,
                             String transactionDesc,
                             Long transactionAccountId,
                             boolean transactionInComing)
        implements Comparable<TransactionDto>{

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, transactionAmount, transactionDate, transactionDesc, transactionAccountId, transactionInComing);
    }

    @Override
    public LocalDateTime transactionDate() {
        return transactionDate;
    }

    @Override
    public int compareTo(TransactionDto o) {
        if(transactionDate() == null || o.transactionDate()==null)
            return 0;

        return transactionDate().compareTo(o.transactionDate());
    }
}
