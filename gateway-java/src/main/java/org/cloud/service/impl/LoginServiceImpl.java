package org.cloud.service.impl;

import cn.hutool.http.HttpStatus;
import org.apache.dubbo.config.annotation.DubboReference;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.user.UserEntity;
import org.cloud.service.LoginService;
import org.cloud.service.UserService;
import org.cloud.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtUtil jwtUtil;
    @DubboReference(check = false)
    UserService userService;

    @Override
    public CompletableFuture<BaseResponse> login(UserEntity user) {
        return CompletableFuture.supplyAsync(()->{
            BaseResponse baseResponse = userService.verify(user);
            if(baseResponse.getStatus() == HttpStatus.HTTP_OK){
                return BaseResponse.success(jwtUtil.generateToken(user.getUserName()));
            }
            return baseResponse;
        }).exceptionally(e->{
            return BaseResponse.exception(e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
        });
    }

}
