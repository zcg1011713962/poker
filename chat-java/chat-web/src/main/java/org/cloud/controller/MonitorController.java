package org.cloud.controller;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cst.APIConstant;
import lombok.extern.slf4j.Slf4j;
import org.cloud.annotation.ApiMonitor;
import org.cloud.emu.ApiEmu;
import org.cloud.entity.common.BaseResponse;
import org.cloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class MonitorController {
    @Autowired
    private RedisUtil redisUtil;

    @ApiMonitor(ApiEmu.CHAT_COUNT)
    @GetMapping(APIConstant.CHAT_COUNT)
    public Mono<BaseResponse<JSONObject>> chatCount() {
        Object count = redisUtil.get(ApiEmu.CHAT.getDescribe());
        JSONObject json = new JSONObject();
        json.set("count", count == null ? 0 : count);
        return Mono.just(BaseResponse.success(json));
    }

}
