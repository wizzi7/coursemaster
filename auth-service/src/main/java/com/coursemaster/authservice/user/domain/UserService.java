package com.coursemaster.authservice.user.domain;

import com.coursemaster.authservice.user.api.User;
import java.util.Optional;

interface UserService {
     Optional<User> findByEmail(String email);

     void save(User user);

     Optional<String> getFullUserNameById(Integer id);
}
