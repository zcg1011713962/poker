package org.cloud.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.request.web.RtspRequest;
import org.cloud.manager.ClientManager;
import org.cloud.netty.service.RtspService;
import org.cloud.rtsp.RtspClient;
import org.cloud.rtsp.entity.RtspReqPacket;
import org.cloud.rtsp.entity.RtspUrlParser;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class RtspServiceImpl implements RtspService<RtspRequest> {


    @Override
    public CompletableFuture<BaseResponse> connect(RtspRequest rtspRequest) {
        RtspUrlParser rtspParser = new RtspUrlParser(rtspRequest.getUrl());
        if (!rtspParser.parse()) {
            log.error("RtspUrlParser error");
            return CompletableFuture.completedFuture(BaseResponse.exception("RtspUrlParser error"));
        }
        return new RtspClient.Builder().setUrl(rtspRequest.getUrl()).build();
    }

    @Override
    public CompletableFuture<BaseResponse> disconnect(RtspRequest rtspRequest) {
        RtspClient conn = (RtspClient) ClientManager.get(rtspRequest.getClientId());
        return conn.write(RtspReqPacket.teardown(conn.getRtspEntity().getUri(), conn.getRtspSDParser().getSession(), conn.getRtspSDParser().getLastCseq()));
    }
}
