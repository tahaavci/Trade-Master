package com.trader.exchangeservice.Service;

import com.trader.exchangeservice.Model.TransactionApproveQueue;
import com.trader.exchangeservice.Repository.TransactionApproveQueueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionApproveQueueService {


    private final TransactionApproveQueueRepository transactionApproveQueueRepository;


    public TransactionApproveQueueService(TransactionApproveQueueRepository transactionApproveQueueRepository) {
        this.transactionApproveQueueRepository = transactionApproveQueueRepository;
    }


    @Transactional
    protected TransactionApproveQueue SaveToQueue(TransactionApproveQueue queue){
        return transactionApproveQueueRepository.save(queue);
    }


    protected TransactionApproveQueue GetQueueElement(Long queueId){

        return transactionApproveQueueRepository.findByApproveQueueId(queueId);
    }


    @Transactional
    protected void DeleteFromQueue(Long queueId){

        transactionApproveQueueRepository.deleteById(queueId);
    }










}
