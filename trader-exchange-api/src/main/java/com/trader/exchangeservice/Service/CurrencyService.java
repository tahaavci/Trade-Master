package com.trader.exchangeservice.Service;

import com.trader.exchangeservice.Dto.CurrencyDto;
import com.trader.exchangeservice.Dto.TypeConvertion;
import com.trader.exchangeservice.Model.Currency;
import com.trader.exchangeservice.Repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {


    private final CurrencyRepository currencyRepository;


    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }



    public List<CurrencyDto> GetRates(){

        List<Currency> currencies = currencyRepository.findAll();


        return TypeConvertion.PrepareCurrencyList(currencies);
    }

    public BigDecimal GetBuyPrice(Currency.Type src,Currency.Type dst){

       Optional<Currency> currency = currencyRepository.GetCurrency(src.name(),dst.name());

       if(currency.isPresent())
           return currency.get().getCurrencyBuyPrice();

       return BigDecimal.ZERO;
    }

    public BigDecimal GetSellPrice(Currency.Type src,Currency.Type dst){

        Optional<Currency> currency = currencyRepository.GetCurrency(src.name(),dst.name());

        if(currency.isPresent())
            return currency.get().getCurrencySellPrice();

        return BigDecimal.ZERO;
    }




}
