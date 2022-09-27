package com.trader.api.Service;

import com.trader.api.Dto.AccountDto;
import com.trader.api.Dto.TypeConverter;
import com.trader.api.Repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    public List<AccountDto> GetCustomerAccounts(long customerId){


        return TypeConverter.AccountListToDtoList(accountRepository.getAccounts(customerId));
    }

    public AccountDto GetAccountById(long accountId){


        return TypeConverter.AccountToDto(accountRepository.findById(accountId).
                orElseThrow(()->new NotFoundException("Account Not Found!!!")));
    }

    @Transactional
    public Integer UpdateAccountBalance(long accountId,BigDecimal amount){


         return accountRepository.UpdateAccountBalance(amount,accountId);
    }


}
