package org.javaclimb.springbootmusic.security;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.javaclimb.springbootmusic.service.BlacklistedTokenService;
import org.javaclimb.springbootmusic.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final BlacklistedTokenService blacklistedTokenService;
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService, BlacklistedTokenService blacklistedTokenService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.blacklistedTokenService = blacklistedTokenService;
    }
    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain) throws ServletException, IOException {
        // 从请求中获取 JWT Token
        String token = null;
        if (request != null) {
            token = getJwtFromRequest(request);
        }
        // 如果 Token 存在并且有效
        if (token != null && jwtUtil.validateToken(token) && !blacklistedTokenService.isTokenBlacklisted(token)) {
            String username = jwtUtil.getUsername(token);
            // 使用 CustomUserDetailsService 根据用户名加载用户信息
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            // 创建认证对象，传递用户信息
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将认证信息存入 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 继续处理请求
        if (filterChain != null) {
            filterChain.doFilter(request, response);
        }
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");  // 获取 Authorization 头部
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // 去掉 "Bearer " 前缀，返回剩下的 Token
        }
        return null;  // 如果没有 Bearer Token，返回 null
    }

}

