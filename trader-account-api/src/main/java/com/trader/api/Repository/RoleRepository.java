package com.trader.api.Repository;

import com.trader.api.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRole(Role.Roles role);

}

