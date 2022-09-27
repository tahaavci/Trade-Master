package com.trader.api.Service;

import com.trader.api.Model.Customer;
import com.trader.api.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    public Optional<Customer> GetCustomerByEmail(String email){

        return customerRepository.findByCustomerEmail(email);

    }






}
