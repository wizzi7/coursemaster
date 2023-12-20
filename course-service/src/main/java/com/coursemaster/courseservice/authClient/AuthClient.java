package com.coursemaster.courseservice.authClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "${feign.url.auth-service}")
public interface AuthClient {

    @GetMapping("/api/v1/uaa/users/getFullUserNameById/{id}")
    String getFullUserNameById(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/uaa/tokens/getUserIdByToken/{token}")
    Integer getUserIdByToken(@PathVariable("token") String token);
}
