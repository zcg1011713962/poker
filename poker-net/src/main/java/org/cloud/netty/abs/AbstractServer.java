package org.cloud.netty.abs;

import cn.hutool.core.lang.UUID;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.exception.FutureException;
import org.cloud.enums.SocketPort;
import org.cloud.manager.CacheManager;
import org.cloud.manager.EventLoopGroupManager;
import org.cloud.manager.ServerManager;
import org.cloud.netty.service.Server;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class AbstractServer<T> implements Server<CompletableFuture<BaseResponse>> {
    private static volatile Bootstrap udpBootstrap;
    private static volatile ServerBootstrap websocketBootstrap;
    private ChannelHandler channelHandler;
    private String id;
    protected int port;
    protected Channel channel;

    public AbstractServer(int port) {
        this.id = UUID.fastUUID().toString();
        this.port = port;
    }

    protected void channelHandler(Protocol protocol) {
        Optional<ChannelHandler> optional = CacheManager.protocolTable().row(protocol).keySet().stream().findFirst();
        if (optional.isPresent()) {
            channelHandler = optional.get();
            return;
        }
        throw FutureException.show("请检查是否配置了正确的channelHandler");
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Channel channel() {
        if (channel != null && channel.isOpen()) {
            return channel;
        }
        return null;
    }

    @Override
    public EventLoopGroup getBossGroup() {
        return EventLoopGroupManager.getBossGroup();
    }

    @Override
    public EventLoopGroup getWorkerGroup() {
        return EventLoopGroupManager.getWorkGroup();
    }

    @Override
    public Bootstrap getUDPServer() {
        if (udpBootstrap == null) {
            synchronized (Server.class) {
                if (udpBootstrap == null) {
                    udpBootstrap = new Bootstrap().group(getWorkerGroup())
                            .option(ChannelOption.SO_BROADCAST, true)
                            .channel(NioDatagramChannel.class)
                            .handler(channelHandler);
                }
            }
        }
        return udpBootstrap;
    }

    @Override
    public ServerBootstrap getWebSocketServer() {
        if (websocketBootstrap == null) {
            synchronized (Server.class) {
                if (websocketBootstrap == null) {
                    websocketBootstrap = new ServerBootstrap().group(getWorkerGroup(), getWorkerGroup())
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(channelHandler);
                }
            }
        }
        return websocketBootstrap;
    }

    @Override
    public CompletableFuture<BaseResponse> bind(ServerBootstrap bootstrap) {
        CompletableFuture<BaseResponse> completableFuture = new CompletableFuture<>();
        try {
            bootstrap.bind(port).sync().addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    channel = f.channel();
                    log.info("------成功监听端口-------{}:{}", SocketPort.getDescByPort(port), port);
                    completableFuture.complete(BaseResponse.success(null));
                } else {
                    completableFuture.completeExceptionally(f.cause());
                }
            }).get();
        }catch (Exception e){
            log.error("{}", e.getMessage());
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

    @Override
    public CompletableFuture<BaseResponse> bind(Bootstrap bootstrap) {
        CompletableFuture<BaseResponse> completableFuture = new CompletableFuture<>();
        try {
            bootstrap.bind(port).sync().addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    channel = f.channel();
                    log.info("------成功监听端口-------{}:{}", SocketPort.getDescByPort(port), port);
                    completableFuture.complete(BaseResponse.success(null));
                } else {
                    completableFuture.completeExceptionally(f.cause());
                }
            }).get();
        }catch (Exception e){
            log.error("{}", e.getMessage());
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

    @Override
    public CompletableFuture<BaseResponse> write(ByteBuf byteBuf) {
        return null;
    }

    @Override
    public CompletableFuture<BaseResponse> close() {
        CompletableFuture<BaseResponse> completableFuture = new CompletableFuture<>();
        channel.close().addListener((ChannelFutureListener) f ->{
            if(f.isSuccess()){
                if(ServerManager.remove(id()) == null){
                    completableFuture.complete(BaseResponse.success(null));
                }
                completableFuture.completeExceptionally(FutureException.show("ServerManager 移除服务端缓存失败" + id()));
            }
            completableFuture.completeExceptionally(f.cause());
        });
        return completableFuture;
    }


}
