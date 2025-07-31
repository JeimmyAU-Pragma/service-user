package com.pragma.user.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String rawPassword);
}
