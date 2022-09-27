package com.trader.exchangeservice.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/exception")
@CrossOrigin(origins = "http://localhost:3000")
public class ExceptionController {


    @GetMapping("accessDenied/{email}")
    public ResponseEntity ForbiddenException(@PathVariable String email){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied : "+email);
    }

    @GetMapping("unauthorized")
    public ResponseEntity<?> Unauthorized(){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized Access");
    }





}
