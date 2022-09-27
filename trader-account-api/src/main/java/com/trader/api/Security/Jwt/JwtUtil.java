package com.trader.api.Security.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.trader.api.Security.UserDetail.AuthUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Component
public class JwtUtil {

    private final String secretKey;
    private final Clock expireTime;

    private static final Logger logger =  LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(@Value("${trader.secret.key}") String secretKey, @Value("${trader.token.expireIn}")int expTime, Clock clock) {
        this.secretKey = secretKey;
        this.expireTime = Clock.offset(clock,Duration.ofMillis(expTime));
    }



    public String createToken(Authentication authentication){


        AuthUserDetail customerPrincipal = (AuthUserDetail) authentication.getPrincipal();


         return JWT.create().withSubject(customerPrincipal.getUsername())
                .withExpiresAt(Instant.now(expireTime))
                .sign(Algorithm.HMAC256(secretKey));
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
