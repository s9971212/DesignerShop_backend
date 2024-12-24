package com.designershop.security;

import com.designershop.entities.UserProfile;
import com.designershop.entities.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RequiredArgsConstructor
public class MyUser implements UserDetails {

    private final UserProfile userProfile;

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!userProfile.getUserRoles().isEmpty()) {
            for (UserRole userRole : userProfile.getUserRoles()) {
                switch (userRole.getCategory()) {
                    case "buyer":
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        break;
                    case "seller":
                        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
                        break;
                    case "designer":
                        authorities.add(new SimpleGrantedAuthority("ROLE_DESIGNER"));
                        break;
                    case "admin":
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                        break;
                    default:
                        break;
                }
            }
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return userProfile.getAccount();
    }

    @Override
    public String getPassword() {
        return userProfile.getPassword();
    }

    public String getEmail() {
        return userProfile.getEmail();
    }

    public String getPhoneNo() {
        return userProfile.getPhoneNo();
    }
}
