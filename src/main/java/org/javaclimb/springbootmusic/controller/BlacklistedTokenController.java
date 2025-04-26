package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.service.BlacklistedTokenService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/blacklistedTokens")
@CrossOrigin("*")
public class BlacklistedTokenController {
    public final BlacklistedTokenService blacklistedTokenService;
    public BlacklistedTokenController(BlacklistedTokenService blacklistedTokenService) {
        this.blacklistedTokenService = blacklistedTokenService;
    }

    // 定期清理过期 token
    @DeleteMapping("/cleanup")
    public void cleanupExpiredTokens() {
        blacklistedTokenService.cleanupExpiredTokens();
    }

}
