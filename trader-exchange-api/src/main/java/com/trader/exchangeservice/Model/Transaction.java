package com.trader.exchangeservice.Model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String transactionId;


    @NotNull
    private BigDecimal transactionAmount;


    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    private String transactionDesc;

    @NotNull
    private Long transactionAccountId;

    @NotNull
    private boolean transactionInComing;

    public Transaction() {
    }

    public Transaction(BigDecimal transactionAmount, LocalDateTime transactionDate, String transactionDesc, Long transactionAccountId, boolean transactionInComing) {
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionDesc = transactionDesc;
        this.transactionAccountId = transactionAccountId;
        this.transactionInComing = transactionInComing;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public Long getTransactionAccountId() {
        return transactionAccountId;
    }

    public boolean isTransactionInComing() {
        return transactionInComing;
    }
}
