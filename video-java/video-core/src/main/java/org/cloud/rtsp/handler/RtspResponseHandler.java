package org.cloud.rtsp.handler;


import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import lombok.extern.slf4j.Slf4j;
import org.cloud.manager.ClientManager;
import org.cloud.netty.service.ResponseHandler;
import org.cloud.rtsp.RtspClient;
import org.cloud.rtsp.handler.abs.DefaultRtspMethodHandler;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RtspResponseHandler extends DefaultRtspMethodHandler<RtspClient> implements ResponseHandler {
    private AtomicInteger count = new AtomicInteger();


    @Override
    public void channelRead0(ChannelHandlerContext ctx, DefaultHttpObject msg) throws Exception {
        RtspClient rtspClient = (RtspClient) ClientManager.client(ctx.channel().id().asLongText());
        log.info("消息编号{} from {} to {}", count.incrementAndGet(), ctx.channel().remoteAddress(), ctx.channel().localAddress());
        if (msg instanceof DefaultHttpResponse) {
            DefaultHttpResponse httpResponse = (DefaultHttpResponse) msg;
            int code = httpResponse.status().code();
            HttpHeaders headers = httpResponse.headers();
            log.info("消息编号{} \n{}", count.get(), msg);
            if (code == RtspResponseStatuses.OK.code()) {
                if (StrUtil.isNotBlank(headers.get(RtspHeaderNames.PUBLIC))) {
                    optionsHandler(rtspClient, headers);
                    return;
                }
                if ("application/sdp".equals(headers.get(RtspHeaderNames.CONTENT_TYPE))) {
                    sdpHeaderHandler(rtspClient, headers);
                    return;
                }
                if (StrUtil.isNotBlank(headers.get(RtspHeaderNames.TRANSPORT)) && headers.get(RtspHeaderNames.TRANSPORT).contains("ssrc=")) {
                    setupHandler(rtspClient, headers);
                    return;
                }
            } else {
                if (code == RtspResponseStatuses.UNAUTHORIZED.code()) {
                    describeHandler(rtspClient, headers);
                    return;
                }
            }
        } else if (msg instanceof HttpContent) {
            if (msg instanceof DefaultHttpContent) {
                DefaultHttpContent httpContent = (DefaultHttpContent) msg;
                ByteBuf buf;
                if (httpContent != null && (buf = httpContent.content()) != null) {
                    String sdpFragment = buf.toString(Charset.forName("UTF-8"));
                    log.info("消息编号{} \n{}", count.get(), sdpFragment);
                    sdpCompositeHandler(rtspClient, sdpFragment);
                    return;
                }
            } else if (msg instanceof DefaultLastHttpContent) {
                DefaultLastHttpContent httpContent = (DefaultLastHttpContent) msg;
                ByteBuf buf;
                if (httpContent != null && (buf = httpContent.content()) != null) {
                    String c = buf.toString(Charset.forName("UTF-8"));
                    log.info("消息编号{} \n{}", count.get(), c);
                }
            }
        }
    }

}
