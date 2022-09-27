package com.trader.exchangeservice.Repository;

import com.trader.exchangeservice.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String> {


    @Query(value = "select * from transaction where transaction_account_id=?1 order by transaction_date desc limit 5",nativeQuery = true)
    List<Transaction> GetCustomerTransactions(Long accountId);


}
