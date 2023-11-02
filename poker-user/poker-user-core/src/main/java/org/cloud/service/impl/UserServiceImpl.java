package org.cloud.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.user.UserEntity;
import org.cloud.service.UserService;

@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public BaseResponse verify(UserEntity user) {
        return BaseResponse.success(null);
    }
}
