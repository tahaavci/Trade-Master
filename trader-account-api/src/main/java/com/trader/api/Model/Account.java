package com.trader.api.Model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Account {

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
        private Long accountId;

        @Enumerated(EnumType.STRING)
        private Currency accountType;

        @DecimalMin(value ="0.0")
        private BigDecimal accountBalance;

        @ManyToOne(fetch = FetchType.LAZY,optional = false)
        @JoinColumn(name = "customerId",referencedColumnName = "customerId")
        private Customer customerId;


        public Account() {
        }


        public Account(Currency accountType, BigDecimal accountBalance, Customer customerId) {
                this.accountType = accountType;
                this.accountBalance = accountBalance;
                this.customerId = customerId;
        }


        @Override
        public int hashCode() {
                return Objects.hash(accountId, accountType, accountBalance, customerId);
        }

        public Long getAccountId() {
                return accountId;
        }

        public Currency getAccountType() {
                return accountType;
        }

        public BigDecimal getAccountBalance() {
                return accountBalance;
        }

        public Customer getCustomerId() {
                return customerId;
        }

        public enum Currency {

                USD,
                TRY,
                EUR,
                GOLD
        }

}
