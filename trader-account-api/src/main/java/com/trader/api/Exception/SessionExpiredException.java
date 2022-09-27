package com.trader.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends RuntimeException{

    public SessionExpiredException(){
        super("Session Expired.");
    }

}
