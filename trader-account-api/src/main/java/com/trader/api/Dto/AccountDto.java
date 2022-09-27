package com.trader.api.Dto;

import com.trader.api.Model.Account;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record AccountDto(Long accountId,
                         @Enumerated(EnumType.STRING) Account.Currency accountType,
                         @DecimalMin(value ="0.0") BigDecimal accountBalance,
                         Long customerId) {


}
