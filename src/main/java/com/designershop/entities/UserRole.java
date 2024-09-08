package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "role_id", nullable = false, length = 2)
    private String roleId;

    @Column(name = "role_name", nullable = false, length = 10)
    private String roleName;

    @Column(name = "role_category", nullable = false, length = 10)
    private String roleCategory;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<UserProfile> userProfiles;

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
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
        return Objects.equals(roleId, other.roleId) && Objects.equals(roleName, other.roleName);
    }
}
