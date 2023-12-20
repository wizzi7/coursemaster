package com.coursemaster.authservice.config;

import com.coursemaster.authservice.user.api.RoleDTO;
import com.coursemaster.authservice.user.api.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${application.security.jwt.refresh_token.expiration}")
    private Long refreshExpiration;

    public String extractUsername(String token) {
        return exctractClaim(token, Claims::getSubject);
    }

    public <T> T exctractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("currentUserId", userDetails.getId());
        extraClaims.put("role", getUserAuthorities(userDetails));
        return generateToken(extraClaims, userDetails);
    }

    private String getUserAuthorities(User userDetails) {
        ObjectMapper mapper = new ObjectMapper();
        RoleDTO roleDTO = new RoleDTO(userDetails.getRole().name(), userDetails.getRole().getAuthorities().stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList());
        try {
            return mapper.writeValueAsString(roleDTO);
        } catch (JsonProcessingException e) {
            log.warn("Cannot map user authorities for user id: {}", userDetails.getId());
            return StringUtils.EMPTY;
        }
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(User userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", getUserAuthorities(userDetails));
        return buildToken(extraClaims, userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return exctractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
