package com.trader.exchangeservice.Dto;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ExchangeDto (@NotNull Long srcAccount, @NotNull Long dstAccount,@NotNull  BigDecimal amount){
}
