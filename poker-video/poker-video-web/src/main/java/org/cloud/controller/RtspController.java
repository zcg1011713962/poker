package org.cloud.controller;

import org.cloud.entity.common.BaseResponse;
import org.cloud.web.RtspRequest;
import org.cloud.netty.service.RtspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/video")
public class RtspController {

    @Autowired
    private RtspService<RtspRequest> rtspService;

    @RequestMapping("/rtsp/connect")
    public Mono<BaseResponse> connect(@RequestBody RtspRequest rtspRequest){
        return Mono.fromFuture(rtspService.connect(rtspRequest));
    }


    @RequestMapping("/rtsp/disconnect")
    public Mono<BaseResponse> disconnect(@RequestBody RtspRequest rtspRequest){
        return Mono.fromFuture(rtspService.disconnect(rtspRequest));
    }

}
