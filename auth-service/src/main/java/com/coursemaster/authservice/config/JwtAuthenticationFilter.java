package com.coursemaster.authservice.config;

import com.coursemaster.authservice.currentuser.CustomUserDetailsService;
import com.coursemaster.authservice.token.domain.TokenFacade;
import com.coursemaster.authservice.user.api.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String GUEST = "GUEST";

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenFacade tokenFacade;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            List<GrantedAuthority> guestAuthorities = Collections.singletonList(new SimpleGrantedAuthority(GUEST));
            UsernamePasswordAuthenticationToken guestToken = new UsernamePasswordAuthenticationToken(null, null, guestAuthorities);
            SecurityContextHolder.getContext().setAuthentication(guestToken);

        } else {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    User userDetails = (User) this.customUserDetailsService.loadUserByUsername(username);

                    var isTokenValid = tokenFacade.findByToken(jwt)
                            .map(t -> !t.isExpired() && !t.isRevoked())
                            .orElse(false);
                    if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (UsernameNotFoundException e) {
                    log.warn("Cannot find user by email: {}", username);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
