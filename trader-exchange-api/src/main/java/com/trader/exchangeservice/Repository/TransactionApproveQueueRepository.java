package com.trader.exchangeservice.Repository;

import com.trader.exchangeservice.Model.TransactionApproveQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionApproveQueueRepository extends JpaRepository<TransactionApproveQueue,Long> {


    TransactionApproveQueue findByApproveQueueId(Long id);


    @Override
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    <S extends TransactionApproveQueue> S save(S entity);

    @Override
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    void deleteById(Long aLong);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM transaction_approve_queue \n" +
            "WHERE queue_timeout  < now()- interval '10 minutes' ",nativeQuery = true)
    void deleteTimoutQueueElements();

}
