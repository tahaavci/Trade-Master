package com.trader.exchangeservice.Controller;

import com.trader.exchangeservice.Dto.TransactionDto;
import com.trader.exchangeservice.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/transaction")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasAnyAuthority('Customer')")
public class TransactionController {


    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/account")
    public ResponseEntity<List<TransactionDto>> GetAccountTransactions(@RequestBody Map<String,Long> body){



        return ResponseEntity.ok(transactionService.GetAccountTransaction(body.get("accountId")));
    }





}
