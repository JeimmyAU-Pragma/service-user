package com.pragma.user.domain.usecase;

import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.exception.DomainException;
import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import com.pragma.user.infrastructure.security.jwt.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.pragma.user.domain.util.DomainConstants.INVALID_CRENDENTIALS;
import static com.pragma.user.domain.util.DomainConstants.USER_NOT_FOUND;

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
        UserModel user = getUserByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DomainException(INVALID_CRENDENTIALS);
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase())
                )
        );

        return jwtUtil.generateToken(userDetails);
    }


    @Override
    public UserModel getUserByEmail(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new DomainException(USER_NOT_FOUND + email ));
    }
}


