package org.cloud.protobuf;


import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.manager.ServerManager;
import org.cloud.netty.abs.AbstractServer;

import java.util.concurrent.CompletableFuture;
@Slf4j
public class ProtobufServer extends AbstractServer<CompletableFuture<BaseResponse>> {

    private ProtobufServer(int port) {
        super(port);
    }

    @Override
    public Protocol protocol() {
        return Protocol.WEBSOCKET_PROTOBUF;
    }

    @Override
    public void manager() {
        channelHandler(protocol());
        ServerManager.put(id(), this);
    }

    @Override
    public CompletableFuture<BaseResponse> init() {
        Bootstrap bootstrap = null;
        try {
            manager();
            bootstrap = getWebSocketServer();
        } catch (Exception e) {
            log.error("{}", e);
            CompletableFuture c = new CompletableFuture();
            c.completeExceptionally(e.getCause());
            return c;
        }
        return bind(bootstrap);
    }

    public static class Builder {
        private int port;

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public CompletableFuture<BaseResponse> build() {
            ProtobufServer webSocket = new ProtobufServer(port);
            return webSocket.init();
        }

    }


}
