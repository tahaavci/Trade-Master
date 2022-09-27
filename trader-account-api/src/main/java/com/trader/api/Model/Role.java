package com.trader.api.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "roleId")
    private Set<CustomerRole> customerRoles;


    public Role() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(roleId, role);
    }

    public Role(Roles role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public Roles getRole() {
        return role;
    }

    public Set<CustomerRole> getCustomerRoles() {
        return customerRoles;
    }

    public enum Roles{

        GeneralManager,
        BranchManager,
        Officer,
        Customer,
        Service

    }

}
