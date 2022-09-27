package com.trader.api.Repository;

import com.trader.api.Model.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole,Long> {


    @Query(value = "select * from customerrole c where customer_id = ?1",nativeQuery = true)
    Set<CustomerRole> getCustomerRoles(long id);

}
