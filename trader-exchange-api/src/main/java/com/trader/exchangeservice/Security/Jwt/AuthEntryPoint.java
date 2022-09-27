package com.trader.exchangeservice.Security.Jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Value("${trader.exception.unauthorized}")
    private String exUrl;
    private static final Logger logger =  LoggerFactory.getLogger(AuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{

            logger.error("AuthEntryPoint ---> "+HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("Remote Address : "+request.getRemoteAddr());
            response.sendRedirect(request.getContextPath()+exUrl);

    }
}
