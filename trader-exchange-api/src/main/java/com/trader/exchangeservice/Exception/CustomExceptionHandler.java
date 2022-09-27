package com.trader.exchangeservice.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

/*
    @ExceptionHandler(TransactionFailException.class)
    public ResponseEntity<?> TransactionFailExceptionHandler(TransactionFailException ex){

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<?> SessionExpiredExceptionHandler(SessionExpiredException ex){

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
    }

*/
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> InsufficientBalanceExceptionHandler(InsufficientBalanceException ex){

        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
