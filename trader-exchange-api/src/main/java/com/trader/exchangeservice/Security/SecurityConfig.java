package com.trader.exchangeservice.Security;

import com.trader.exchangeservice.Security.Jwt.AuthEntryPoint;
import com.trader.exchangeservice.Security.Jwt.CustomAccessDeniedHandler;
import com.trader.exchangeservice.Security.Jwt.JwtTokenFilter;
import com.trader.exchangeservice.Security.Jwt.JwtUtil;
import com.trader.exchangeservice.Security.UserDetail.AuthUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private final AuthUserDetailService authUserDetailService;
    private final AuthEntryPoint authEntryPoint;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AuthUserDetailService authUserDetailService, AuthEntryPoint authEntryPoint, JwtUtil jwtUtil) {
        this.authUserDetailService = authUserDetailService;
        this.authEntryPoint = authEntryPoint;
        this.jwtUtil = jwtUtil;
    }



    @Bean
    public JwtTokenFilter AuthTokenFilter(){

        return new JwtTokenFilter(jwtUtil, authUserDetailService);
    }



    @Bean
    public DaoAuthenticationProvider authProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{


        httpSecurity.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v1/currency").permitAll()
                .antMatchers("/v1/home").permitAll()
                .antMatchers("/v1/exception/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        httpSecurity.authenticationProvider(authProvider());

        httpSecurity.addFilterBefore(AuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
