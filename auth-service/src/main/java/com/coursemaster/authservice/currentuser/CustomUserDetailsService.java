package com.coursemaster.authservice.currentuser;

import com.coursemaster.authservice.user.api.User;
import com.coursemaster.authservice.user.domain.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userFacade.findByEmail(email).orElse(null);
    }
}
