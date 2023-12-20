package com.coursemaster.authservice.user.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.coursemaster.authservice.user.api.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
        Set.of(
            GUEST
    )),

    INSTRUCTOR(
        Set.of(
            ADD_VIDEO,
            DELETE_VIDEO,
            READ_VIDEO_LIST,
            GUEST
    )),
    ADMIN(
        Set.of(
            ADD_VIDEO,
            DELETE_VIDEO,
            READ_VIDEO_LIST,
            GUEST
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
