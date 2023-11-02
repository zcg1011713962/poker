package org.cloud.config;

import org.cloud.cst.APIConstant;
import org.cloud.util.JwtUtil;
import org.cloud.web.auth.JwtAuthenticationFailureHandler;
import org.cloud.web.auth.JwtAuthenticationManager;
import org.cloud.web.auth.JwtServerAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtUtil jwtUtil) {
        return http
                .cors().disable().addFilterAt(new CorsConfig(), SecurityWebFiltersOrder.CORS)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange().pathMatchers(HttpMethod.POST, APIConstant.GATEWAY_LOGIN).permitAll()
                .anyExchange().authenticated().and()
                .addFilterAt(authenticationFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    /**
     * 校验过滤器
     *
     * @param jwtUtil JWT工具类
     * @return
     */
    public AuthenticationWebFilter authenticationFilter(JwtUtil jwtUtil) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(new JwtAuthenticationManager(jwtUtil));
        // 核心校验转换
        filter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter(jwtUtil));
        filter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        return filter;
    }


}
