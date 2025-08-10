package com.pragma.user.infrastructure.configuration;

import com.pragma.user.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import com.pragma.user.infrastructure.security.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.pragma.user.infrastructure.security.jwt.SecurityConstants.ROLE_ADMIN;
import static com.pragma.user.infrastructure.security.jwt.SecurityConstants.ROLE_OWNER;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/v3/api-docs/swagger-config",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html"

                        ).permitAll()
                         .requestMatchers("/api/v1/user/owner").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/owner").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/employee").hasRole(ROLE_OWNER)
                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(userDetailsService, jwtUtil);
    }
}
