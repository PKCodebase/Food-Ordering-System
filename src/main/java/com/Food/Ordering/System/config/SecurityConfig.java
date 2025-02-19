package com.Food.Ordering.System.config;

import com.Food.Ordering.System.exception.CustomAccessDeniedHandler;
import com.Food.Ordering.System.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/user/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/restaurants/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .requestMatchers("/food/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .requestMatchers("/category/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .requestMatchers("/orders/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .requestMatchers("/cart/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .requestMatchers("/ingredients/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
