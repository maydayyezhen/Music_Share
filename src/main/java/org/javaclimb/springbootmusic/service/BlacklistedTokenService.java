package org.javaclimb.springbootmusic.service;

import org.javaclimb.springbootmusic.model.BlacklistedToken;
import org.javaclimb.springbootmusic.repository.BlacklistedTokenRepository;
import org.javaclimb.springbootmusic.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class BlacklistedTokenService {
    public final BlacklistedTokenRepository blacklistedTokenRepository;
    private final JwtUtil jwtUtil;
    public BlacklistedTokenService(BlacklistedTokenRepository blacklistedTokenRepository, JwtUtil jwtUtil) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public void blacklistToken(String token) {
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpirationDate(jwtUtil.getExpirationDate(token));
        blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    @Transactional
    public void cleanupExpiredTokens() {
        blacklistedTokenRepository.deleteAllByExpirationDateBefore(new Date());
    }
}
