package org.cloud.websocket;


import io.netty.bootstrap.Bootstrap;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.manager.ServerManager;
import org.cloud.netty.abs.AbstractServer;

import java.util.concurrent.CompletableFuture;

public class WebSocket extends AbstractServer<CompletableFuture<BaseResponse>> {

    private WebSocket(int port) {
        super(port);
    }

    @Override
    public Protocol protocol() {
        return Protocol.WEBSOCKET;
    }

    @Override
    public void manager() {
        channelHandler(protocol());
        ServerManager.put(id(), this);
    }

    @Override
    public CompletableFuture<BaseResponse> init() {
        manager();
        Bootstrap bootstrap = getWebSocketServer();
        return bind(bootstrap);
    }

    public static class Builder {
        private int port;

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public CompletableFuture<BaseResponse> build() {
            WebSocket webSocket = new WebSocket(port);
            return webSocket.init();
        }

    }


}
