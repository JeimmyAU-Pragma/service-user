package com.pragma.user.infrastructure.security.jwt;

import com.pragma.user.domain.model.UserModel;
import com.pragma.user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserModel user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),

                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase()))
        );
    }
}