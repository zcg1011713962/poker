package org.cloud.web.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 校验实体
 */
public class JwtAuthToken extends AbstractAuthenticationToken {

    private final String username;

    public JwtAuthToken(String username) {
        super(null);
        this.username = username;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
