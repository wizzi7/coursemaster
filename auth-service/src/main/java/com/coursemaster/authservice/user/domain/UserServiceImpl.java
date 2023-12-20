package com.coursemaster.authservice.user.domain;

import com.coursemaster.authservice.user.api.User;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) { userRepository.save(user); }

    public Optional<String> getFullUserNameById(Integer id) {
        return userRepository.findById(id).map(user -> user.getFirstname() + " " + user.getLastname());
    }
}
