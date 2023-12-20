package com.coursemaster.authservice.user.web;

import com.coursemaster.authservice.user.domain.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/api/v1/uaa/users")
public class UserController {

    private final UserFacade userFacade;

    @Operation(summary = "Returns user full name", description = "Returns user full name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getFullUserNameById/{id}")
    public ResponseEntity<String> getFullUserNameById(@PathVariable("id") Integer id) {
        return userFacade.getFullUserNameById(id)
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }
}
