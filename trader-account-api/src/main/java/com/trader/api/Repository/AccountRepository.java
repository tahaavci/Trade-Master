package com.trader.api.Repository;

import com.trader.api.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {


    @Query(value = "select * from account a where customer_id = ?1",nativeQuery = true)
    List<Account> getAccounts(long customerId);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update account set account_balance =?1 where account_id = ?2",nativeQuery = true)
    int UpdateAccountBalance(BigDecimal amount,long accountId);

    }

