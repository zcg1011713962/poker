package org.cloud.web.auth;

import org.cloud.util.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 校验
 */
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(Objects.equals(authentication.getPrincipal(), true)){
            if(authentication.getCredentials() == null){
                JwtAuthToken authToken = new JwtAuthToken(null);
                // 设置校验通过
                authToken.setAuthenticated(true);
                return Mono.just(authToken);
            }
            String token = (String) authentication.getCredentials();
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                JwtAuthToken authToken = new JwtAuthToken(username);
                // 设置校验通过
                authToken.setAuthenticated(true);
                return Mono.just(authToken);
            }
        }
        return Mono.error(new AuthenticationException("Invalid JWT token") {});
    }
}
