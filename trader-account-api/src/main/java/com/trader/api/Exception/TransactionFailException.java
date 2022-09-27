package com.trader.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionFailException extends RuntimeException{

    public TransactionFailException(){
        super("Transaction Failed. Rollback Applied.");
    }


}
