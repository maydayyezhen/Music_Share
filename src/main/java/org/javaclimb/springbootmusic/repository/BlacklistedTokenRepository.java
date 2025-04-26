package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Integer> {
    boolean existsByToken(String token);
    void deleteAllByExpirationDateBefore(Date date);
}
