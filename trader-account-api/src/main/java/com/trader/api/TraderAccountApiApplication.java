package com.trader.api;

import com.trader.api.Model.Account;
import com.trader.api.Model.Customer;
import com.trader.api.Model.CustomerRole;
import com.trader.api.Model.Role;
import com.trader.api.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Clock;

@SpringBootApplication
public class TraderAccountApiApplication implements CommandLineRunner{

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final RoleRepository roleRepository;
	private final CustomerRoleRepository customerRoleRepository;

	public TraderAccountApiApplication(CustomerRepository customerRepository, AccountRepository accountRepository, RoleRepository roleRepository, CustomerRoleRepository customerRoleRepository) {
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;
		this.customerRoleRepository = customerRoleRepository;

	}

	public static void main(String[] args) {
		SpringApplication.run(TraderAccountApiApplication.class, args);
	}


	@Bean
	public Clock clock(){
		return Clock.systemDefaultZone();
	}

	@Override
	public void run(String... args) throws Exception {

		// All Customers Password ==> 12345

		customerRoleRepository.deleteAll();
		roleRepository.deleteAll();
		accountRepository.deleteAll();
		customerRepository.deleteAll();

		Customer tom = customerRepository.save(
				new Customer("Tom","Cruise","info@tom.net","$2a$12$87AjD/3GAz8Rf4.xI8s86OT6h/6t0LiT.Vwc.0hmrLcezsavEkOKu"));

		Customer jerry = customerRepository.save(
				new Customer("Jerry","Cruise","info@jerry.net","$2a$12$87AjD/3GAz8Rf4.xI8s86OT6h/6t0LiT.Vwc.0hmrLcezsavEkOKu"));


		Customer visitor = customerRepository.save(
				new Customer("Jerry","Cruise","info@visitor.net","$2a$12$87AjD/3GAz8Rf4.xI8s86OT6h/6t0LiT.Vwc.0hmrLcezsavEkOKu"));

		Customer exchangeService = customerRepository.save(
				new Customer("Exchange","Service","service@access","$2a$12$87AjD/3GAz8Rf4.xI8s86OT6h/6t0LiT.Vwc.0hmrLcezsavEkOKu"));


		Account a1 = accountRepository.save(new Account(Account.Currency.USD, BigDecimal.valueOf(5000.0),tom));
		Account a2 = accountRepository.save(new Account(Account.Currency.EUR, BigDecimal.valueOf(5000.0),tom));
		Account a3 = accountRepository.save(new Account(Account.Currency.GOLD, BigDecimal.valueOf(5000.0),tom));
		Account a4 = accountRepository.save(new Account(Account.Currency.TRY, BigDecimal.valueOf(5000.0),tom));


		Account b1 = accountRepository.save(new Account(Account.Currency.USD, BigDecimal.valueOf(3000.0),jerry));
		Account b2 = accountRepository.save(new Account(Account.Currency.EUR, BigDecimal.valueOf(3000.0),jerry));
		Account b3 = accountRepository.save(new Account(Account.Currency.GOLD, BigDecimal.valueOf(3000.0),jerry));
		Account b4 = accountRepository.save(new Account(Account.Currency.TRY, BigDecimal.valueOf(3000.0),jerry));


		Role role_customer = roleRepository.save(new Role(Role.Roles.Customer));
		Role service_role = roleRepository.save(new Role(Role.Roles.Service));


		customerRoleRepository.save(new CustomerRole(tom,role_customer));
		customerRoleRepository.save(new CustomerRole(jerry,role_customer));
		customerRoleRepository.save(new CustomerRole(exchangeService,service_role));

	}
}
