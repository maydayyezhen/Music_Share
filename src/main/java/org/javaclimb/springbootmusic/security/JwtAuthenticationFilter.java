package org.javaclimb.springbootmusic.security;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.javaclimb.springbootmusic.service.BlacklistedTokenService;
import org.javaclimb.springbootmusic.service.CustomUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
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

        String uri = null;
        if (request != null) {
            uri = request.getRequestURI();
        }
        // 直接放行不需要JWT校验的路径
        if ("/users/login".equals(uri) || "/users/register".equals(uri)) {
            if (filterChain != null) {
                filterChain.doFilter(request, response);
            }
            return;
        }
        // 从请求中获取 JWT Token
        String token = null;
        if (request != null) {
            token = getJwtFromRequest(request);
        }
        // 如果 Token 存在并且有效
        try {
            if (token != null && jwtUtil.validateToken(token) && !blacklistedTokenService.isTokenBlacklisted(token)) {
                String username = jwtUtil.getUsername(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("Token missing or invalid");
            }
        } catch (BadCredentialsException e) {
            // 直接拦截响应
            if (response != null) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token missing or invalid\"}");
            }
            return;
        }
        // 继续处理请求
        if (filterChain != null) {
            filterChain.doFilter(request, response);
        }
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

