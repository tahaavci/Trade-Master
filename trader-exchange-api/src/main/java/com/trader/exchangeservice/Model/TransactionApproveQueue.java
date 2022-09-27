package com.trader.exchangeservice.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransactionApproveQueue {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long approveQueueId;


    private Long queueSrcAccountId;


    private Long queueDstAccountId;


    private BigDecimal queueSrcSubtractAmount;


    private BigDecimal queueDstAddAmount;


    private BigDecimal queueExchangeRate;


    private LocalDateTime queueTimeout;

    public TransactionApproveQueue() {
    }


    public TransactionApproveQueue(Long queueSrcAccountId, Long queueDstAccountId, BigDecimal queueSrcSubtractAmount, BigDecimal queueDstAddAmount, BigDecimal queueExchangeRate, LocalDateTime queueTimeout) {
        this.queueSrcAccountId = queueSrcAccountId;
        this.queueDstAccountId = queueDstAccountId;
        this.queueSrcSubtractAmount = queueSrcSubtractAmount;
        this.queueDstAddAmount = queueDstAddAmount;
        this.queueExchangeRate = queueExchangeRate;
        this.queueTimeout = queueTimeout;
    }

    public Long getApproveQueueId() {
        return approveQueueId;
    }

    public Long getQueueSrcAccountId() {
        return queueSrcAccountId;
    }

    public BigDecimal getQueueSrcSubtractAmount() {
        return queueSrcSubtractAmount;
    }
    public BigDecimal getQueueDstAddAmount() {
        return queueDstAddAmount;
    }

    public LocalDateTime getQueueTimeout() {
        return queueTimeout;
    }

    public Long getQueueDstAccountId() {
        return queueDstAccountId;
    }

    public BigDecimal getQueueExchangeRate() {
        return queueExchangeRate;
    }
}
