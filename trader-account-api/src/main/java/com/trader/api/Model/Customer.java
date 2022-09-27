package com.trader.api.Model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "customer_sequence"),
                    @Parameter(name = "initial_value", value = "100000000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long customerId;


    @NotNull
    private String customerName;


    @NotNull
    private String customerSurname;

    @NotNull
    @Column(unique = true)
    private String customerEmail;

    @NotNull
    private String customerPassword;

    @OneToMany(mappedBy = "customerId",fetch = FetchType.EAGER)
    private Set<Account> customerAccounts;


    @OneToMany(mappedBy = "customerId",fetch = FetchType.EAGER)
    private Set<CustomerRole> customerRoles = new HashSet<>();


    public Customer() {
    }


    public Customer(String customerName, String customerSurname, String customerEmail, String customerPassword) {
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerSurname, customerEmail, customerPassword);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public Set<Account> getCustomerAccounts() {
        return customerAccounts;
    }

    public Set<CustomerRole> getCustomerRoles() {
        return customerRoles;
    }
}
