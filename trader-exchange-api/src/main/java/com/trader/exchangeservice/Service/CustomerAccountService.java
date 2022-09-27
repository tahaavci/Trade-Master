package com.trader.exchangeservice.Service;

import com.trader.exchangeservice.Dto.Account;
import com.trader.exchangeservice.Dto.BalanceUpdateRequestDto;
import com.trader.exchangeservice.Security.UserDetail.AuthUserDetail;
import com.trader.exchangeservice.Security.UserDetail.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerAccountService {


    private final RestTemplate restTemplate;
    private static final Logger logger =  LoggerFactory.getLogger(CustomerAccountService.class);


    @Value("${trader.api.customerServiceUrl}")
    private String customerServiceUrl;

    @Value("${trader.api.accountServiceUrl}")
    private String accountServiceUrl;

    @Value("${trader.api.updateAccountServiceUrl}")
    private String updateAccountServiceUrl;


    public CustomerAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Customer retrieveCustomer(String email){

        Map<String,String> credentials = new HashMap<>();
        credentials.put("email",email);

        logger.info("Exchange Service : Retrieving customer from ->"+customerServiceUrl);
        return restTemplate.postForObject(customerServiceUrl,credentials,Customer.class);
    }


    public Account retrieveAccount(Long accountId){

        Map<String,Long> credentials = new HashMap<>();
        credentials.put("accountId",accountId);


        return restTemplate.postForObject(accountServiceUrl,credentials, Account.class);
    }



    public Integer updateAccountBalance(Long accountId, BigDecimal amount){


        return restTemplate.postForObject(updateAccountServiceUrl,
                new BalanceUpdateRequestDto(accountId,amount), Integer.class);
    }



}
