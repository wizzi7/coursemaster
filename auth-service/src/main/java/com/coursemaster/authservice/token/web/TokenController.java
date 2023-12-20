package com.coursemaster.authservice.token.web;

import com.coursemaster.authservice.token.domain.TokenFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/uaa/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenFacade tokenFacade;

    @Operation(summary = "Returns user id", description = "Returns user id by token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User id"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getUserIdByToken/{token}")
    public ResponseEntity<Integer> getUserIdByToken(@PathVariable("token") String token) {
        return tokenFacade.getUserIdByToken(token)
                .map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.badRequest().build());
    }
}
