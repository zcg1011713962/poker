package org.cloud.web;

import org.cloud.cst.APIConstant;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.user.UserEntity;
import org.cloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(APIConstant.GATEWAY_LOGIN)
    public Mono<BaseResponse> login(@RequestBody UserEntity user) {
        return Mono.fromFuture(loginService.login(user));
    }

}
