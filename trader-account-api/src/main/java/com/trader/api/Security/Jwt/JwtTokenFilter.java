package com.trader.api.Security.Jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.trader.api.Security.UserDetail.AuthUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final AuthUserDetailService userDetailService;

    @Value("#{'${trader.no.filter.url}'.split(',')}")
    private final Set<String> noFilterUrl = new HashSet<>();

    private static final Logger logger =  LoggerFactory.getLogger(JwtTokenFilter.class);

    public JwtTokenFilter(JwtUtil jwtUtil, AuthUserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        final Set<AntPathRequestMatcher> excludedPaths  = noFilterUrl.stream().map(AntPathRequestMatcher::new).collect(Collectors.toSet());

        logger.info("shouldNotFilter --> "+request.getRemoteAddr());
        return excludedPaths.stream().anyMatch( m -> m.matches(request));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            logger.info("doFilterInternal --> "+request.getRemoteAddr());
            String token = getTokenFromRequest(request);
            logger.info("Request Token --->"+token);

            if(token!=null && jwtUtil.tokenIsValid(token)){


                String email = jwtUtil.getEmailFromToken(token);
                logger.info("Resolved Email --->"+email);

                UserDetails customerDetails = userDetailService.loadUserByUsername(email);
                logger.info("Resolved Roles --->"+customerDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customerDetails,
                        null,customerDetails.getAuthorities());


                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // We can save customers logs in database.
                logger.warn("Resource Access Logged : "+request.getRequestURL());
            }

        }catch (TokenExpiredException e){

            logger.error("Token Error --->"+e.getMessage());

        }catch (Exception e){

            logger.error("JwtTokenFilter Exception --->"+e.getMessage());
            logger.error("Generated From ---"+e.getClass().getName());

        }

        filterChain.doFilter(request,response);


    }



    private String getTokenFromRequest(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if(StringUtils.hasText(header) && header.startsWith("Bearer "))
            return header.substring(7);

        return null;
    }
}
