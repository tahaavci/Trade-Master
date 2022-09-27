package com.trader.api.Controller;

import com.trader.api.Security.Jwt.JwtUtil;
import com.trader.api.Security.UserDetail.AuthUserDetail;
import com.trader.api.Dto.LoginRequestDto;
import com.trader.api.Dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {


        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;

        public LoginController(AuthenticationManager authenticationManager,JwtUtil jwtUtil) {
                this.authenticationManager = authenticationManager;
                this.jwtUtil = jwtUtil;
        }

        @PostMapping("/login")
        public ResponseEntity<?> CustomerLogin(@Valid @RequestBody LoginRequestDto loginRequestDto){


                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = jwtUtil.createToken(authentication);

                AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();


                return ResponseEntity.ok(new LoginResponseDto(authUserDetail.getCustomerId(),token));
        }


        @GetMapping("/home")
        public ResponseEntity<String> testmethod(){


              return ResponseEntity.ok("Home Page");
        }



}
