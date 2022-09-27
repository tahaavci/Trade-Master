package com.trader.api.Controller;

import com.trader.api.Service.AccountService;
import com.trader.api.Dto.AccountDto;
import com.trader.api.Dto.AccountUpdateBalanceRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/account")
public class AccountController {


    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/accounts")
    @PreAuthorize("hasAnyAuthority('Customer')")
    public ResponseEntity<List<AccountDto>> GetCustomerAccounts(@Valid @RequestBody Map<String,Long> body){


        return ResponseEntity.ok(accountService.GetCustomerAccounts(body.get("id")));
    }



    @PostMapping("/update-account")
    public ResponseEntity<Integer> UpdateBalance(@Valid@RequestBody AccountUpdateBalanceRequest request){


        return ResponseEntity.ok(accountService.UpdateAccountBalance(request.accountId(),request.amount()));
    }




    @PostMapping("/retrieve-account")
    public ResponseEntity<AccountDto> retrieveAccount(@Valid @RequestBody Map<String,Long> credentials){



        return ResponseEntity.ok(accountService.GetAccountById(credentials.get("accountId")));
    }





}
