package com.trader.exchangeservice.Controller;


import com.trader.exchangeservice.Dto.ExchangeDto;
import com.trader.exchangeservice.Dto.TransactionApproveRequestDto;
import com.trader.exchangeservice.Dto.TransactionApproveResponseDto;
import com.trader.exchangeservice.Service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasAnyAuthority('Customer')")
public class ExchangeController {


    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @PostMapping("/exchange")
    public ResponseEntity<TransactionApproveResponseDto> StartExchange(@Valid @RequestBody ExchangeDto dto){



        return ResponseEntity.ok(exchangeService.BeginExchange(dto));
    }


    @PostMapping("/approve-transaction")
    public ResponseEntity<String> CompleteTransaction(@Valid @RequestBody TransactionApproveRequestDto approveRequest) throws TimeoutException {


        return ResponseEntity.ok(exchangeService.CompleteTransaction(approveRequest.queueId(),approveRequest.isApproved()));
    }







}
