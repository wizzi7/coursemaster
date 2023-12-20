package com.coursemaster.authservice.token.domain;

import com.coursemaster.authservice.token.api.Token;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public List<Token> findAllValidTokensByUser(Integer userId) {
        return tokenRepository.findAllValidTokensByUser(userId);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public Optional<Integer> findUserIdByToken(String token) {
        return tokenRepository.findUserIdByToken(token).map(tok -> tok.getUser().getId());
    }

    @Override
    public void saveAll(List<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }
}
