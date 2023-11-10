package org.cloud.websocket.config;


import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.cloud.emu.Protocol;
import org.cloud.entity.properties.WebSocketProperties;
import org.cloud.manager.CacheManager;
import org.cloud.netty.abs.AbstractInitializer;
import org.cloud.websocket.handler.BinaryWebSocketFrameHandler;
import org.cloud.websocket.handler.TextWebSocketHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "websocket", name = "enable", havingValue = "true")
public class WebSocketConfiguration {

    @Bean
    public AbstractInitializer websocketInitializer(WebSocketProperties websocketProperties){
        AbstractInitializer handler = new AbstractInitializer<NioServerSocketChannel>(websocketProperties.isProxy()) {
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
                //websocket定义了传递数据的6中frame类型
                pipeline.addLast(new TextWebSocketHandler());
                pipeline.addLast(new BinaryWebSocketFrameHandler());
            }
        };
        CacheManager.protocolTable().put(Protocol.WEBSOCKET, handler, false);
        return handler;
    }

}
