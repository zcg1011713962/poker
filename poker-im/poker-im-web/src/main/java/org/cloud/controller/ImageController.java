package org.cloud.controller;


import org.cloud.cst.APIConstant;
import lombok.extern.slf4j.Slf4j;
import org.cloud.annotation.ApiMonitor;
import org.cloud.emu.ApiEmu;
import org.cloud.entity.ImageRequest;
import org.cloud.entity.common.BaseResponse;
import org.cloud.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ImageController {
    @Autowired
    private ChatService<BaseResponse> chatService;

    @ApiMonitor(ApiEmu.CHAT_IMAGES_GENERATIONS)
    @PostMapping(APIConstant.CHAT_IMAGES_GENERATIONS)
    Mono<BaseResponse> createImages(@RequestBody ImageRequest imageRequest) {
        log.info("{}", imageRequest);
        return Mono.fromFuture(chatService.createImages(imageRequest));
    }


}
