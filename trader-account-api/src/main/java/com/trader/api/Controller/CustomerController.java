package com.trader.api.Controller;


import com.trader.api.Security.Jwt.AuthEntryPoint;
import com.trader.api.Service.CustomerService;
import com.trader.api.Dto.CustomerDto;
import com.trader.api.Dto.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private static final Logger logger =  LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @PostMapping("/retrieve-customer")
    public ResponseEntity<CustomerDto> GetCustomer(@RequestBody Map<String,String> request){

        logger.info("Exchange service request for customer email "+request.get("email"));
        return ResponseEntity.ok(TypeConverter.CustomerToDto(customerService.GetCustomerByEmail(request.get("email")).orElse(null)));
    }

}
