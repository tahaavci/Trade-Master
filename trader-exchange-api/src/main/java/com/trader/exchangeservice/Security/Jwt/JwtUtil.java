package com.trader.exchangeservice.Security.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secretKey;

    private static final Logger logger =  LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(@Value("${trader.secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }


    public String getEmailFromToken(String token){

        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token).getSubject();
    }


    public boolean tokenIsValid(String token){

        try {

            JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token).getNotBeforeAsInstant();
            return true;

        }catch (JWTDecodeException ex){

            logger.error("Token Decoding Problem --->"+ex.getMessage());
            return false;
        }
    }




}
