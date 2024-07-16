package com.designershop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http. // 省略其他配置
				authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
					authorizationManagerRequestMatcherRegistry.requestMatchers("/public/**").permitAll() // 允許公開訪問的路徑
							.requestMatchers("/private/**").hasRole("USER") // 需要 USER 角色
							.requestMatchers("/admin/**").hasRole("ADMIN"); // 需要 ADMIN 角色
				}).httpBasic(Customizer.withDefaults()); // 使用 HTTP Basic 認證
		return http.build();
	}
}
