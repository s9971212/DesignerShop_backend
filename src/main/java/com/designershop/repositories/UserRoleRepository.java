package com.designershop.repositories;

import com.designershop.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    @Query(value = "SELECT * FROM user_role WHERE role_id IN (:roleIds)", nativeQuery = true)
    Set<UserRole> findByRoleIds(@Param("roleIds") Set<String> roleIds);
}
