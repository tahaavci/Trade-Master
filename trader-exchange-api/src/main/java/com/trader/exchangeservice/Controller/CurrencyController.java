package com.trader.exchangeservice.Controller;

import com.trader.exchangeservice.Dto.CurrencyDto;
import com.trader.exchangeservice.Service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }




    @GetMapping("currency")
    public ResponseEntity<List<CurrencyDto>> GetRates(){


        return ResponseEntity.ok(currencyService.GetRates());
    }








}
