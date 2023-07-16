package org.cloud.rtsp;

import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.manager.ServerManager;
import org.cloud.netty.abs.AbstractServer;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class RtpServer extends AbstractServer<CompletableFuture<BaseResponse>> {

    private RtpServer(int port){
        super(port);
    }


    @Override
    public Protocol protocol() {
        return Protocol.RTP;
    }

    @Override
    public void manager() {
        channelHandler(protocol());
        ServerManager.put(id(), this);
    }


    @Override
    public CompletableFuture<BaseResponse> init() {
        manager();
        channelHandler(protocol());
        Bootstrap udpServer = getUDPServer();
        return bind(udpServer);
    }

    public static class Builder {
        private int port;

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public CompletableFuture<BaseResponse> build() {
            RtpServer rtpServer = new RtpServer(port);
            return rtpServer.init();
        }

    }

}
