package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {

    /**
     * 權限ID
     */
    @Id
    @Column(name = "role_id", nullable = false, length = 2)
    private String roleId;

    /**
     * 權限名稱
     */
    @Column(name = "name", nullable = false, length = 10)
    private String name;

    /**
     * 權限所屬的類別
     */
    @Column(name = "category", nullable = false, length = 10)
    private String category;

    /**
     * 權限與使用者資料的多對多關聯，每個權限可以對應多個使用者資料，每個使用者資料也可以有多個權限
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<UserProfile> userProfiles;

    @Override
    public int hashCode() {
        return Objects.hash(roleId, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRole other = (UserRole) obj;
        return Objects.equals(roleId, other.roleId) && Objects.equals(name, other.name);
    }
}
