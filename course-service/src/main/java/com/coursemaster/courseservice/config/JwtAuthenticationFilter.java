package com.coursemaster.courseservice.config;

import com.coursemaster.courseservice.user.api.RoleDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final String GUEST = "GUEST";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        null, jwtToken, getUserAuthorities(claims));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        } else if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            List<GrantedAuthority> guestAuthorities = Collections.singletonList(new SimpleGrantedAuthority(GUEST));
            UsernamePasswordAuthenticationToken guestToken = new UsernamePasswordAuthenticationToken(null, null, guestAuthorities);
            SecurityContextHolder.getContext().setAuthentication(guestToken);
        }
        chain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> getUserAuthorities(Claims claims) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(claims.get("role").toString(), RoleDTO.class).getAuthorities().stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }
}
