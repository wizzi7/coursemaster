package com.coursemaster.authservice.token.domain;

import com.coursemaster.authservice.token.api.Token;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenFacade {

    private final TokenService tokenService;

    public List<Token> findAllValidTokensByUser(Integer userId) { return tokenService.findAllValidTokensByUser(userId); }

    public Optional<Token> findByToken(String token) { return tokenService.findByToken(token); }

    public Optional<Integer> getUserIdByToken(String token) { return tokenService.findUserIdByToken(token); }

    public void saveAll(List<Token> tokens) { tokenService.saveAll(tokens); }

    public void save(Token token) { tokenService.save(token); }
}
