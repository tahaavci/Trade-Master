package com.trader.api.Security.UserDetail;

import com.trader.api.Service.CustomerService;
import com.trader.api.Model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthUserDetailService implements UserDetailsService {

    private final CustomerService customerService;

    private static final Logger logger =  LoggerFactory.getLogger(AuthUserDetailService.class);

    public AuthUserDetailService(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerService.GetCustomerByEmail(username)
                .orElseThrow(()->
                {
                    logger.error("Logger --->Email Not Found!");
                    throw new UsernameNotFoundException("Email not found !!!");
                });

        return AuthUserDetail.build(customer);
    }







}
