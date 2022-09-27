package com.trader.exchangeservice.Dto;

import com.trader.exchangeservice.Model.Currency;

import java.math.BigDecimal;

public record AccountResponseDto(Long accountId, Currency.Type accountType,
                                 BigDecimal accountBalance,Long customerId) {
}
