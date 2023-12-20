package com.coursemaster.authservice.user.domain;

import com.coursemaster.authservice.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    User save(User user);
}
