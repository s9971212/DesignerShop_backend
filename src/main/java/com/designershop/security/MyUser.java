package com.designershop.security;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.designershop.entities.UserProfile;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyUser implements UserDetails {

	private final UserProfile userProfile;

	@Override
	public List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (StringUtils.isNotBlank(userProfile.getUserType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		if (StringUtils.isNotBlank(userProfile.getSellerType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		}
		if (StringUtils.isNotBlank(userProfile.getDesignerType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_DESIGNER"));
		}
		if (StringUtils.isNotBlank(userProfile.getAdminType())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
