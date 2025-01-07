package com.designershop.config;

import com.designershop.security.JwtAuthenticationFilter;
import com.designershop.security.MyLogoutHandler;
import com.designershop.security.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MyLogoutHandler myLogoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error/**", "/api/auth", "/api/user", "/api/password_forgot", "/api/product/**",
                                "/api/product_likes").permitAll()
                        .requestMatchers("/api/user/**", "/api/product_likes/**", "/api/cart/**", "/api/order/**",
                                "/api/order_delivery/**", "/api/coupon_issuance/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/seller/**").hasAuthority("ROLE_SELLER")
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/api/auth").addLogoutHandler(myLogoutHandler).invalidateHttpSession(true))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager(http))
                .sessionManagement(smgmt -> smgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 未來有前端可以加上.deleteCookies(Cookie名稱)，把指定的Cookie刪除
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }
}
