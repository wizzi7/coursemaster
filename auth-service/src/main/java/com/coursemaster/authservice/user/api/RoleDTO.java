package com.coursemaster.authservice.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class RoleDTO implements Serializable {

    String roleName;
    List<String> authorities;
}
