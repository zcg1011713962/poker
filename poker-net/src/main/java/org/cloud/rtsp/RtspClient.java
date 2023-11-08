package org.cloud.rtsp;

import cn.hutool.http.HttpStatus;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.exception.FutureException;
import org.cloud.manager.ClientManager;
import org.cloud.netty.abs.AbstractClient;
import org.cloud.rtsp.entity.RtspEntity;
import org.cloud.rtsp.entity.RtspReqPacket;
import org.cloud.rtsp.entity.RtspSDParser;
import org.cloud.rtsp.entity.RtspUrlParser;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class RtspClient<T> extends AbstractClient<CompletableFuture<BaseResponse>> {
    private RtspEntity rtspEntity;
    private RtspSDParser rtspSDParser = new RtspSDParser();

    private RtspClient(String url) {
        super(url);
        channelHandler(protocol());
    }

    @Override
    public Protocol protocol() {
        return Protocol.RTSP;
    }

    @Override
    public void manager() {
        ClientManager.put(id(), this);
    }

    @Override
    public CompletableFuture<BaseResponse> init() {
        RtspUrlParser rtspParser = new RtspUrlParser(url);
        if (rtspParser.parse()) {
            rtspEntity = new RtspEntity(rtspParser.getUri(), rtspParser.getUsername(), rtspParser.getPassword());
            manager();
        } else {
            CompletableFuture.completedFuture(FutureException.show("解析url错误"));
        }
        return connect().thenApply(success -> {
            if (success.getStatus() == HttpStatus.HTTP_OK) {
                write(RtspReqPacket.options(rtspParser.getUri(), RtspReqPacket.commonCseq.getAndIncrement()));
                HashMap<String, String> params = new HashMap<>(1);
                params.put("clientId", id());
                return BaseResponse.success(params);
            }
            return BaseResponse.failed(500, null);
        });
    }


    public RtspSDParser getRtspSDParser() {
        return rtspSDParser;
    }

    public RtspEntity getRtspEntity() {
        return rtspEntity;
    }

    @Override
    public CompletableFuture<BaseResponse> connect() {
        CompletableFuture<BaseResponse> cFuture = new CompletableFuture<>();
        try {
            RtspUrlParser rtspUrlParser = new RtspUrlParser(url);
            if (!rtspUrlParser.parse()) {
                cFuture.completeExceptionally(FutureException.show("检查RTSP地址"));
            }
            getTcpClient().connect(new InetSocketAddress(rtspUrlParser.getIp(), rtspUrlParser.getPort())).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    channel = future.channel();
                    cFuture.complete(BaseResponse.success(null));
                } else {
                    cFuture.completeExceptionally(future.cause());
                }
            });
        } catch (Exception e) {
            cFuture.completeExceptionally(e);
        }
        return cFuture;
    }


    public static class Builder {
        private String url;

        /**
         * @param url rtsp://admin:link123456@192.168.7.12:554/h264/ch1/main/av_stream
         * @return
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public CompletableFuture<BaseResponse> build() {
            return new RtspClient(url).init();
        }

    }

}
