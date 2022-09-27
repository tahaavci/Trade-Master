package com.trader.exchangeservice.Dto;

import java.math.BigDecimal;

public record BalanceUpdateRequestDto(Long accountId, BigDecimal amount) {
}
