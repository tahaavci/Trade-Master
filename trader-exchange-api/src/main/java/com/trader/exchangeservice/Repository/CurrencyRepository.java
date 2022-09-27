package com.trader.exchangeservice.Repository;

import com.trader.exchangeservice.Model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {


    @Transactional
    @Modifying
    @Query(value ="CREATE SEQUENCE IF NOT EXISTS queue_sequence increment 1 start 1 owned by public.currency.currency_rate_id; " +
            "alter sequence queue_sequence restart 1 owned by currency.currency_rate_id;",nativeQuery = true)
    void tableConfig();

    //--------------------


    @Query(value = "select *  from currency c where currency_src_type =?1 and currency_dst_type =?2",nativeQuery = true )
    Optional<Currency> GetCurrency(String src, String dst);





}
