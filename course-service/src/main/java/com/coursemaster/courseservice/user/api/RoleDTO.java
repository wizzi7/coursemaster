package com.coursemaster.courseservice.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private String roleName;
    private Set<String> authorities;
}
