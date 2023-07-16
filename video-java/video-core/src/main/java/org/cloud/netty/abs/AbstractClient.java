package org.cloud.netty.abs;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.cloud.emu.Protocol;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.exception.FutureException;
import org.cloud.manager.CacheManager;
import org.cloud.manager.ClientManager;
import org.cloud.manager.EventLoopGroupManager;
import org.cloud.netty.service.Client;
import org.cloud.util.Md5Util;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractClient<T> implements Client<CompletableFuture<BaseResponse>> {
    private ChannelHandler channelHandler;
    private static volatile Bootstrap bootstrap;
    protected Channel channel;
    protected String url;
    private String id;

    public AbstractClient(String url){
        this.id =  Md5Util.calculateMD5(url);
        this.url = url;
    }

    @Override
    public String id() {
       return id;
    }

    @Override
    public Channel channel() {
        if (channel != null) {
            return channel;
        }
        throw new FutureException("通道为空");
    }


    @Override
    public EventLoopGroup getWorkerGroup() {
        return EventLoopGroupManager.getWorkGroup();
    }

    protected void channelHandler(Protocol protocol) {
        Optional<ChannelHandler> optional = CacheManager.protocolTable().row(protocol).keySet().stream().findFirst();
        if(optional.isPresent()){
            channelHandler = optional.get();
            return;
        }
        throw new FutureException("获取channelHandler失败");
    }

    @Override
    public Bootstrap getTcpClient() {
        if (bootstrap == null) {
            synchronized (Client.class){
                if(bootstrap == null){
                    bootstrap = new Bootstrap().group(getWorkerGroup())
                            .channel(NioSocketChannel.class)
                            .handler(channelHandler)
                            .option(ChannelOption.SO_KEEPALIVE, true) // 长连接
                            .option(ChannelOption.SO_RCVBUF, Integer.MAX_VALUE)
                            .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 1024, 65536 * 100))
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15);
                }
            }
        }
        return bootstrap;
    }

    @Override
    public CompletableFuture<BaseResponse> close() {
        CompletableFuture<BaseResponse> completableFuture = new CompletableFuture<>();
        channel.close().addListener((ChannelFutureListener) f ->{
            if(f.isSuccess()){
                if(ClientManager.remove(id()) == null){
                    completableFuture.complete(BaseResponse.success(null));
                }
                completableFuture.completeExceptionally(new FutureException("ClientManager 移除客户端缓存失败" + id()));
            }
            completableFuture.completeExceptionally(f.cause());
        });
        return completableFuture;
    }

    @Override
    public CompletableFuture<BaseResponse> write(ByteBuf byteBuf) {
        if (channel != null && channel.isOpen()) {
            CompletableFuture<BaseResponse> completableFuture = new CompletableFuture<>();
            channel.writeAndFlush(byteBuf).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    completableFuture.complete(BaseResponse.success(null));
                } else {
                    completableFuture.completeExceptionally(f.cause());
                }
            });
            return completableFuture;
        }
        throw new FutureException("通道不可用");
    }
}
