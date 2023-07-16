package org.cloud.netty.service;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import org.cloud.emu.Protocol;

public interface Connection<T> extends ClientHandle<T>, ServerHandle<T> {

    /**
     * bossGroup 用于接受客户端连接
     *
     * @return
     */
    default EventLoopGroup getBossGroup(){
        return null;
    }

    /**
     * 一个NioEventLoopGroup包含一个或多个NioEventLoop，每个NioEventLoop运行在一个独立的线程中，并处理一个或多个Channel的所有事件
     * workerGroup 用于处理已接受连接的流量
     *
     * @return
     */
    default EventLoopGroup getWorkerGroup(){
        return null;
    };

    String id();

    Protocol protocol();

    Channel channel();

    void manager();

    T write(ByteBuf byteBuf);

    T close();

}
