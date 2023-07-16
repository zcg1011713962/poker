package org.cloud.rtsp.init;


import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.rtsp.RtspDecoder;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.cloud.netty.UserEventHandler;
import org.cloud.netty.abs.AbstractInitializer;
import org.cloud.rtsp.handler.RtspResponseHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 入站事件在ChannelPipeline中由头指针向尾指针传播，只处理Inbound类型的Handler
 * 出站事件由尾指针向头指针传播，只处理Outbound类型的Handler
 */
public class RtspClientlInitializer extends AbstractInitializer<SocketChannel> {

    public RtspClientlInitializer(boolean proxy) {
        super(proxy);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        if(proxy){
            pipeline.addLast("socks4ProxyHandler", new Socks4ProxyHandler(new InetSocketAddress( "127.0.0.1",8808))); // Inbound Outbound
        }
        pipeline.addLast("rtspIdleStateHandler", new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS)); // Inbound Outbound
        pipeline.addLast("rtspHeartBeatHandler", new UserEventHandler()); // Inbound
        pipeline.addLast("rtspDecoder", new RtspDecoder()); // Inbound
        pipeline.addLast("rtspResponseHandler", new RtspResponseHandler()); // Inbound
    }

}
