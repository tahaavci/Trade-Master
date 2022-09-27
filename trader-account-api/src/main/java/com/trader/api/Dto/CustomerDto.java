package com.trader.api.Dto;

import java.util.Set;

public record CustomerDto(Long customerId,
                          String customerName,
                          String customerSurname,
                          String customerEmail,
                          String customerPassword,
                          Set<String> customerRoles) {
}
