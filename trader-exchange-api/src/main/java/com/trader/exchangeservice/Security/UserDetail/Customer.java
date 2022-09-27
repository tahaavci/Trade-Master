package com.trader.exchangeservice.Security.UserDetail;


import java.util.HashSet;
import java.util.Set;

public record Customer(Long customerId,
                       String customerName,
                       String customerSurname,
                       String customerEmail,
                       String customerPassword,
                       Set<String> customerRoles) {

}
