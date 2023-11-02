package org.cloud.service;

import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.user.UserEntity;

import java.util.concurrent.CompletableFuture;


public interface LoginService {

    CompletableFuture<BaseResponse> login(UserEntity user);

}
