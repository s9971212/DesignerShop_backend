package com.designershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.designershop.security.JwtAuthenticationFilter;
import com.designershop.security.MyLogoutHandler;
import com.designershop.security.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final MyUserDetailsService myUserDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final MyLogoutHandler myLogoutHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/error/**", "/api/auth", "/api/users", "/api/password_forgot",
						"/api/verification/send", // 發送驗證碼
						"/api/verification/check" // 檢查驗證碼
				).permitAll().requestMatchers("/api/users/**").hasAuthority("ROLE_USER")
				.requestMatchers("/admin/users/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated().and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/api/auth").addLogoutHandler(myLogoutHandler)
				.invalidateHttpSession(true).and() // 未來有前端可以加上.deleteCookies(Cookie名稱)，把指定的Cookie刪除
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationManager(authenticationManager());
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(myUserDetailsService);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		return authenticationProvider;
	}
}
