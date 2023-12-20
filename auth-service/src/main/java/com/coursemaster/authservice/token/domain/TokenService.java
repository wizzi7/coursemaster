package com.coursemaster.authservice.token.domain;

import com.coursemaster.authservice.token.api.Token;
import java.util.List;
import java.util.Optional;

interface TokenService {

    List<Token> findAllValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);

    Optional<Integer> findUserIdByToken(String token);

    void saveAll(List<Token> tokens);

    void save(Token token);
}
