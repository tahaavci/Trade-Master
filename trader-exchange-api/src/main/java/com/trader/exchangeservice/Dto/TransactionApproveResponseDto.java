package com.trader.exchangeservice.Dto;

import java.math.BigDecimal;
public record TransactionApproveResponseDto(Long queueId, BigDecimal exchangeRate, BigDecimal totalAmount) {


}
