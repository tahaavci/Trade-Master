package com.trader.exchangeservice.Security.UserDetail;

import com.trader.exchangeservice.Service.CustomerAccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailService implements UserDetailsService {

    private final CustomerAccountService customerAccountService;

    private static final Logger logger =  LoggerFactory.getLogger(AuthUserDetailService.class);


    public AuthUserDetailService(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerAccountService.retrieveCustomer(username); //.GetCustomerByEmail(username)

        if(customer==null){
            logger.error("Logger --->Email Not Found!");
            throw new UsernameNotFoundException("Email not found !!!");
        }

        return AuthUserDetail.build(customer);
    }







}
