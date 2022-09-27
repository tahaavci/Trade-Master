package com.trader.exchangeservice.Security.Jwt;

import com.trader.exchangeservice.Security.UserDetail.AuthUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger =  LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Value("${trader.exception.accessDenied}")
    private String accessDeniedUrl;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        logger.error("Access Denied Handler ---> Access Denied");

        AuthUserDetail user = (AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user==null){
            logger.error("Failed to load UserDetails");
            throw new ServletException();
        }

        response.sendRedirect(request.getContextPath()+accessDeniedUrl+user.getUsername());
    }

}
