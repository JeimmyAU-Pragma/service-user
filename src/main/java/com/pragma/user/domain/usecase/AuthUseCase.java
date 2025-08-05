package com.pragma.user.domain.usecase;

import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class AuthUseCase implements IAuthServicePort {

        private final IUserPersistencePort userPersistencePort;
        private final IPasswordEncoderPort passwordEncoder;
        private final JwtUtil jwtUtil;

        public AuthUseCase(IUserPersistencePort userPersistencePort,
                           IPasswordEncoderPort passwordEncoder,
                           JwtUtil jwtUtil) {
            this.userPersistencePort = userPersistencePort;
            this.passwordEncoder = passwordEncoder;
            this.jwtUtil = jwtUtil;
        }

        @Override
        public String login(String email, String password) {
            UserModel user = userPersistencePort.findByEmail(email)
                    .orElseThrow(() -> new DomainException("Usuario con" + email+" no fue encontrado"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new DomainException("Credenciales inválidas");
            }

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority( user.getRol().name()))
            );

            return jwtUtil.generateToken(userDetails);
        }
    }


