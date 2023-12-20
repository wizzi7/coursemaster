package com.coursemaster.courseservice.user.api;

import org.springframework.security.core.context.SecurityContextHolder;
import java.io.Serializable;
import java.util.Optional;

public class CurrentUser implements Serializable {

    public static Optional<String> getCurrentUserToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
    }
}
