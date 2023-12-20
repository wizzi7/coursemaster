package com.coursemaster.authservice.token.domain;

import com.coursemaster.authservice.token.api.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
interface TokenRepository extends CrudRepository<Token, Integer> {

    @Query("select t from Token t join User u on t.user.id = u.id where u.id = :userId and  (t.expired = false and t.revoked = false)")
    List<Token> findAllValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);

    @Query(value = "select t from Token t where t.token = :token")
    Optional<Token> findUserIdByToken(String token);
}
