package com.trader.exchangeservice.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyDto(int currencyId,
                          String srcCurrency,
                          String dstCurrency,
                          BigDecimal sell,
                          BigDecimal buy,
                          @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")LocalDateTime date) {



}
