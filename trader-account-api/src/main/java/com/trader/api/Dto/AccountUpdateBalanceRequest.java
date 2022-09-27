package com.trader.api.Dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AccountUpdateBalanceRequest (@NotNull Long accountId,@NotNull BigDecimal amount){
}
