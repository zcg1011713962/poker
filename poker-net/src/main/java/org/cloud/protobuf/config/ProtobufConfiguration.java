package org.cloud.protobuf.config;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.cloud.emu.Protocol;
import org.cloud.manager.CacheManager;
import org.cloud.netty.abs.AbstractInitializer;
import org.cloud.protobuf.handler.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 自定义protobuf编解码器
 */
@Configuration
@ConditionalOnProperty(prefix = "protobuf.user-defined", name = "enable", havingValue = "true")
public class ProtobufConfiguration {

    // 使用 @Scope 注解声明为原型（多例）作用域
    @Bean
    @Scope("prototype")
    public ProtobufHandler protobufHandler(DispatcherHandler dispatcherHandler) {
        return new ProtobufHandler(dispatcherHandler);
    }

    @Bean
    public AbstractInitializer websocketInitializer(ProtobufHandler protobufHandler){
        // SocketChannel双向通信
        AbstractInitializer handler = new AbstractInitializer<SocketChannel>(false) {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                pipeline.addLast(new HttpServerCodec());
                //以块的方式来写的处理器
                pipeline.addLast(new ChunkedWriteHandler());
                //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                pipeline.addLast(new HttpObjectAggregator(8192));
                //ws://server:port/context_path
                //ws://localhost:9999/ws
                //参数指的是context_path
                pipeline.addLast(new WebSocketServerProtocolHandler("/dz"));
                // 空闲检测超时
                pipeline.addLast(new IdleStateHandler(120, 120, 120));
                pipeline.addLast(new WebSocketHeartbeatHandler());
                // 自定义编解码
                pipeline.addLast(new ChannelHandler[]{new BasePacketDecoder()});
                pipeline.addLast(protobufHandler);
                pipeline.addLast(new ChannelHandler[]{new BasePacketEncoder()});
            }
        };
        CacheManager.protocolTable().put(Protocol.WEBSOCKET_PROTOBUF, handler, false);
        return handler;
    }

}
