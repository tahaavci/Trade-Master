package com.trader.api.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customerrole")
public class CustomerRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long relationId;

    @ManyToOne
    @JoinColumn(name = "customerId",referencedColumnName = "customerId")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "roleId",referencedColumnName = "roleId")
    private Role roleId ;


    public CustomerRole() {

    }

    public CustomerRole(Customer customerId, Role roleId) {
        this.customerId = customerId;
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationId);
    }

    public CustomerRole(Customer customerId) {
        this.customerId = customerId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public Role getRoleId() {
        return roleId;
    }
}
