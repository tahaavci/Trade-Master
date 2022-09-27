package com.trader.exchangeservice.Model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Currency {

    @Id
    @SequenceGenerator(name="queue_sequence",sequenceName="queue_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="queue_sequence")
    private int currencyRateId;

    @Enumerated(EnumType.STRING)
    private Type currencySrcType;

    @Enumerated(EnumType.STRING)
    private Type currencyDstType;
    @NotNull
    @Column(precision = 19, scale = 7)
    private BigDecimal currencyBuyPrice;

    @NotNull
    @Column(precision = 19, scale = 7)
    private BigDecimal currencySellPrice;


    @NotNull
    private LocalDateTime currencyLastUpdated;


    public Currency() {

    }

    public Currency(Type currencySrcType, Type currencyDstType, BigDecimal currencyBuyPrice, BigDecimal currencySellPrice, LocalDateTime currencyLastUpdated) {
        this.currencySrcType = currencySrcType;
        this.currencyDstType = currencyDstType;
        this.currencyBuyPrice = currencyBuyPrice;
        this.currencySellPrice = currencySellPrice;
        this.currencyLastUpdated = currencyLastUpdated;
    }

    public int getCurrencyRateId() {
        return currencyRateId;
    }

    public Type getCurrencySrcType() {
        return currencySrcType;
    }

    public Type getCurrencyDstType() {
        return currencyDstType;
    }

    public BigDecimal getCurrencyBuyPrice() {
        return currencyBuyPrice;
    }

    public BigDecimal getCurrencySellPrice() {
        return currencySellPrice;
    }

    public LocalDateTime getCurrencyLastUpdated() {
        return currencyLastUpdated;
    }

    public enum Type{

        TRY,
        EUR,
        USD,
        GOLD

    }

}
