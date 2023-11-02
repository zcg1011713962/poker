package org.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cloud.entity.user.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseMapper<UserEntity> {

}
