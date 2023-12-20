package com.coursemaster.authservice.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {

    @Bean
    UserFacade userFacade(UserService userService) {
        return new UserFacade(userService);
    }

    @Bean
    UserService UserService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository); }
}
