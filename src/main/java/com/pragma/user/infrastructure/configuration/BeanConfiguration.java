package com.pragma.user.infrastructure.configuration;


import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.usecase.AuthUseCase;
import com.pragma.user.domain.usecase.UserUseCase;
import com.pragma.user.infrastructure.out.jpa.adapter.BCryptPasswordEncoderAdapter;
import com.pragma.user.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.user.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.user.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import com.pragma.user.infrastructure.security.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BeanConfiguration {


    @Bean
    public IUserPersistencePort userPersistencePort(IUserRepository repository, IUserEntityMapper mapper) {
        return new UserJpaAdapter(repository, mapper);
    }


    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userPersistencePort, passwordEncoderPort);
    }


    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new BCryptPasswordEncoderAdapter(new BCryptPasswordEncoder());
    }


    @Bean
    public IAuthServicePort authServicePort(IUserPersistencePort userPersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort,
                                            JwtUtil jwtUtil) {
        return new AuthUseCase(userPersistencePort, passwordEncoderPort, jwtUtil);
    }


/*
    @Bean

   AuthenticationManager authManager (HttpSecurity http) throws  Exception{
       return  http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
     @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoderPort().encode("1234"))
                .roles("PROPIETARIO")
                .build()
        );
        return manager;
    }

    */
}


