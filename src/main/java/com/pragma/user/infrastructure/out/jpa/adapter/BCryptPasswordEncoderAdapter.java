package com.pragma.user.infrastructure.out.jpa.adapter;

import com.pragma.user.domain.spi.IPasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderAdapter implements IPasswordEncoderPort {

    private final BCryptPasswordEncoder encoder;

    public BCryptPasswordEncoderAdapter(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
