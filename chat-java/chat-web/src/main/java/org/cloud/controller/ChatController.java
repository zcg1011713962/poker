package org.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.cloud.annotation.ApiMonitor;
import org.cloud.cst.APIConstant;
import org.cloud.emu.ApiEmu;
import org.cloud.entity.ChatRequest;
import org.cloud.entity.common.BaseResponse;
import org.cloud.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ChatController{
    @Autowired
    private ChatService<BaseResponse> chatService;

    @ApiMonitor(ApiEmu.CHAT_COMPLETIONS)
    @PostMapping(APIConstant.CHAT_COMPLETIONS)
    public Mono<BaseResponse> chat(@RequestBody ChatRequest chatRequest) {
        log.info("{}", chatRequest);
        return Mono.fromFuture(chatService.completions(chatRequest));
    }

}
