package com.designershop.repositories;

import com.designershop.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    @Query(value = "SELECT * FROM user_role WHERE role_id =:roleId", nativeQuery = true)
    UserRole findByRoleId(@Param("roleId") String roleId);
}
