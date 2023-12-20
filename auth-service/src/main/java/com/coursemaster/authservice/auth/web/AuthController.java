package com.coursemaster.authservice.auth.web;

import com.coursemaster.authservice.auth.api.AuthenticationRequest;
import com.coursemaster.authservice.auth.api.AuthenticationResponse;
import com.coursemaster.authservice.auth.api.RefreshToken;
import com.coursemaster.authservice.auth.api.RegisterRequest;
import com.coursemaster.authservice.auth.domain.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/uaa/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Registers new user", description = "Registers new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens details"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Authenticates and authorizes user", description = "Authenticates and authorizes user by credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens details"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        if (response != null)
            return ResponseEntity.ok(response);
        else return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Authenticates and authorizes user by refresh token", description = "Authenticates user by refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens details"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshToken refreshToken) {
        AuthenticationResponse authResponse = authenticationService.refreshToken(refreshToken.getRefreshToken());
        if (authResponse != null)
            return ResponseEntity.ok(authResponse);
        else return ResponseEntity.badRequest().build();
    }
}
