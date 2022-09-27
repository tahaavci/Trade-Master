package com.trader.exchangeservice.Security.UserDetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUserDetail implements UserDetails {

    private final long customerId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private static final Logger logger =  LoggerFactory.getLogger(AuthUserDetail.class);


    private AuthUserDetail(long customerId, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }



    public static AuthUserDetail build(Customer customer){


        List<GrantedAuthority> authorities = customer.customerRoles().stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        logger.info("ROLE Info --->"+customer.customerId()+" HAS "+authorities.stream().toList());


        return new AuthUserDetail(customer.customerId(), customer.customerEmail(),customer.customerPassword(),authorities);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
