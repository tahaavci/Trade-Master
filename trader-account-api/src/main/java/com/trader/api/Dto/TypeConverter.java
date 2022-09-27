package com.trader.api.Dto;

import com.trader.api.Model.Account;
import com.trader.api.Model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeConverter {



    public static AccountDto AccountToDto(Account account){

        return new AccountDto(account.getAccountId(),account.getAccountType(),account.getAccountBalance(),account.getCustomerId().getCustomerId());
    }


    public static List<AccountDto> AccountListToDtoList(List<Account> accounts){

        if(accounts==null)
            return null;

        List<AccountDto> dtoList = new ArrayList<>(accounts.size());

        for (Account item:accounts)
            dtoList.add(AccountToDto(item));

        return dtoList;
    }


    public static CustomerDto CustomerToDto(Customer customer){

        if(customer==null)
            return null;
        
        return new CustomerDto(customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getCustomerEmail(),
                customer.getCustomerPassword(),
                customer.getCustomerRoles().stream().map(x-> x.getRoleId().getRole().name()).collect(Collectors.toSet())
        );
    }

}
