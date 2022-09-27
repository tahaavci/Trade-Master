package com.trader.exchangeservice.Dto;

import com.trader.exchangeservice.Model.Currency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record Account(Long accountId,
                      @Enumerated(EnumType.STRING) Currency.Type accountType,
                      @DecimalMin(value ="0.0") BigDecimal accountBalance,
                      Long customerId) {


}
