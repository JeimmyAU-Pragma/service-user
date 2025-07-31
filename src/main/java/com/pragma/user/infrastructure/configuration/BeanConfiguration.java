package com.pragma.user.infrastructure.configuration;


import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.spi.IPasswordEncoderPort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.usecase.UserUseCase;
import com.pragma.user.infrastructure.out.jpa.adapter.BCryptPasswordEncoderAdapter;
import com.pragma.user.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
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

}


