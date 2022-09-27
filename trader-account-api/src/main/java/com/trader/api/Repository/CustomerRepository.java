package com.trader.api.Repository;

import com.trader.api.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {


    Optional<Customer> findByCustomerEmail(String customerEmail);

}
