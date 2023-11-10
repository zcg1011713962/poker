package org.cloud.protobuf.config;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.cloud.emu.Protocol;
import org.cloud.manager.CacheManager;
import org.cloud.netty.abs.AbstractInitializer;
import org.cloud.protobuf.handler.BasePacketDecoder;
import org.cloud.protobuf.handler.BasePacketEncoder;
import org.cloud.protobuf.handler.BasePacketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProtobufConfiguration {

    @Bean
    public AbstractInitializer websocketInitializer(){
        AbstractInitializer handler = new AbstractInitializer<NioServerSocketChannel>(false) {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {
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
                pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                // 自定义编解码
                pipeline.addLast(new ChannelHandler[]{new BasePacketDecoder()});
                pipeline.addLast(new BasePacketHandler());
                pipeline.addLast(new ChannelHandler[]{new BasePacketEncoder()});
            }
        };
        CacheManager.protocolTable().put(Protocol.WEBSOCKET_PROTOBUF, handler, false);
        return handler;
    }

}
