package com.coursemaster.authservice.user.domain;

import com.coursemaster.authservice.user.api.User;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Optional<User> findByEmail(String email) {
        return userService.findByEmail(email);
    }

    public void save(User user) { userService.save(user); }

    public Optional<String> getFullUserNameById(Integer id) { return userService.getFullUserNameById(id); }
}
