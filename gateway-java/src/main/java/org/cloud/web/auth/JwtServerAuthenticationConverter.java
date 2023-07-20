package org.cloud.web.auth;

import org.cloud.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 校验
 */
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {
    private final JwtUtil jwtUtil;

    private static final String BEARER_PREFIX = "Bearer ";

    public JwtServerAuthenticationConverter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = extractTokenFromHeader(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        if (StringUtils.hasText(token)) {
            try {
                if (jwtUtil.validateToken(token)) {
                    List<String> authorities = jwtUtil.authorities(token);
                    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                    if (authorities != null) {
                        for (String authority : authorities) {
                            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
                        }
                    }
                    // 认证
                    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            true, token, grantedAuthorities);
                    return Mono.just(authentication);
                }
            } catch (Exception e) {
                return Mono.error(e);
            }
        }else{
            String path = exchange.getRequest().getPath().pathWithinApplication().value();
            if (Objects.equals(path, "/api/login")) {
                // 跳过认证
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        true, null, null);
                return Mono.just(authentication);
            }
        }
        return Mono.just(new UsernamePasswordAuthenticationToken(false, false));
    }


    private String extractTokenFromHeader(String header) {
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
