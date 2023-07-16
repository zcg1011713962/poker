package org.cloud.web.auth;

import com.alibaba.fastjson.JSONObject;
import org.cloud.entity.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 校验失败处理
 */
public class JwtAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        // 获取错误信息
        ServerWebExchange exchange = webFilterExchange.getExchange();
        // 设置响应状态码和 Content-Type
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 返回响应
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .allocateBuffer()
                        .write(JSONObject.toJSONBytes(BaseResponse.failed(HttpStatus.UNAUTHORIZED.value(), exception.getMessage())))
                        ));
    }
}
