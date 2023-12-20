package com.coursemaster.authservice.token.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    TokenFacade tokenFacade(TokenService tokenService) { return new TokenFacade(tokenService); }

    @Bean
    TokenService tokenService(TokenRepository tokenRepository) {
        return new TokenServiceImpl(tokenRepository);
    }
}
