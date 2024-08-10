package com.designershop.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.designershop.entities.UserProfile;
import com.designershop.entities.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyUser implements UserDetails {

	private final UserProfile userProfile;

	@Override
	public List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (!userProfile.getUserRoles().isEmpty()) {
			for (UserRole userRole : userProfile.getUserRoles()) {
				switch (userRole.getRoleCategory()) {
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
