package org.cloud.service;

import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.user.UserEntity;

public interface UserService {

    BaseResponse verify(UserEntity user);

}
