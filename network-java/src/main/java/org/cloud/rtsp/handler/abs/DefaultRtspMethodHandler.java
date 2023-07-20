package org.cloud.rtsp.handler.abs;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspMethods;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.exception.FutureException;
import org.cloud.rtp.RtpServer;
import org.cloud.rtsp.RtspClient;
import org.cloud.rtsp.entity.RTSPDigest;
import org.cloud.rtsp.entity.RtspEntity;
import org.cloud.rtsp.entity.RtspReqPacket;
import org.cloud.rtsp.entity.RtspSDParser;
import org.cloud.rtp.VideoACK;
import org.cloud.util.PortUtil;
import org.cloud.util.RtspUtil;
import org.cloud.util.ThreadPoolUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class DefaultRtspMethodHandler<T> extends RtspMethodHandler<RtspClient> {

    @Override
    public void optionsHandler(RtspClient rtspClient, HttpHeaders headers) {
        int cseq = Integer.parseInt(headers.get(RtspHeaderNames.CSEQ)) + 1;
        rtspClient.write(RtspReqPacket.describe(rtspClient.getRtspEntity().getUri(), cseq));
    }

    @Override
    public void describeHandler(RtspClient rtspClient, HttpHeaders headers) {
        // WWW-Authenticate -> Digest realm="IP Camera(J2914)", nonce="446c8be9ca7682b00f43b2bae7e98b86", stale="FALSE"
        int cseq = Integer.parseInt(headers.get(RtspHeaderNames.CSEQ)) + 1;
        String authenticate = headers.get("WWW-Authenticate");
        Map<String, String> authMap = RtspUtil.decode(authenticate);
        RtspEntity rtspEntity = rtspClient.getRtspEntity();
        rtspEntity.setNonce(authMap.get("nonce"));
        rtspEntity.setRealm(authMap.get("realm"));
        RTSPDigest digest = new RTSPDigest(rtspEntity.getUserName(), rtspEntity.getRealm(), rtspEntity.getNonce(), rtspEntity.getUri(), RtspMethods.DESCRIBE.name(), rtspEntity.getPassword());
        String response = digest.calculateResponse();
        rtspClient.write(RtspReqPacket.auth(rtspEntity.getUri(), rtspEntity.getUserName(), authMap.get("nonce"), authMap.get("realm"), response,  cseq));
    }

    @Override
    public void sdpHeaderHandler(RtspClient rtspClient, HttpHeaders headers) {
        String contentLength = headers.get(RtspHeaderNames.CONTENT_LENGTH);
        int cseq = Integer.parseInt(headers.get(RtspHeaderNames.CSEQ)) + 1;
        rtspClient.getRtspSDParser().setContentLength(Integer.parseInt(contentLength));
        rtspClient.getRtspSDParser().setCseq(cseq);
    }

    @Override
    public void sdpCompositeHandler(RtspClient rtspClient, String sdpFragment) {
        RtspSDParser rtspSDParser = rtspClient.getRtspSDParser();
        boolean ret = rtspSDParser.append(sdpFragment).parser();
        if (ret) {
            log.info("解析SDP \n{}", rtspClient.getRtspSDParser());
            RtspEntity rtspEntity = rtspClient.getRtspEntity();
            RTSPDigest digest = new RTSPDigest(rtspEntity.getUserName(), rtspEntity.getRealm(), rtspEntity.getNonce(), rtspEntity.getUri(), RtspMethods.SETUP.name(), rtspEntity.getPassword());
            String response = digest.calculateResponse();
            int rtpPort = PortUtil.offerPort(true);
            int rtcpPort = PortUtil.offerPort(false);
            new RtpServer.Builder().setPort(rtpPort).build().thenAccept(r ->{
                if(r.getStatus() == HttpStatus.HTTP_OK){
                    rtspClient.write(RtspReqPacket.setup(rtspEntity.getUri(), rtspSDParser.getTransport(), rtpPort, rtcpPort, rtspSDParser.getTrackID(), StrUtil.EMPTY, rtspEntity.getUserName(), rtspEntity.getNonce(), rtspEntity.getRealm(), response, rtspClient.getRtspSDParser().getCseq()));
                }
            }).exceptionally(f ->{
                log.error("{}", f.getMessage());
                return null;
            });
        }
    }

    @Override
    public void setupHandler(RtspClient rtspClient, HttpHeaders headers) {
        String sessionLine = headers.get(RtspHeaderNames.SESSION);
        int cseq = Integer.parseInt(headers.get(RtspHeaderNames.CSEQ)) + 1;
        if(StrUtil.isBlank(sessionLine)){
            throw new FutureException("setup session return null");
        }
        String [] s = sessionLine.split(";");
        String session = s[0].trim();
        String timeout = s[1].split("=")[1].trim();
        int t = StrUtil.isBlank(timeout) ? 60 : Integer.parseInt(timeout);
        rtspClient.getRtspSDParser().setLastCseq(cseq);
        rtspClient.getRtspSDParser().setSession(session);
        rtspClient.getRtspSDParser().setTimeout(t);
        if(rtspClient.getRtspSDParser().getConfirmedTimeout().compareAndSet(false, true)){
            ThreadPoolUtil.videoSchedulerExecutor().schedule(new VideoACK(rtspClient.getRtspSDParser()), t, TimeUnit.SECONDS);
        }
        rtspClient.write(RtspReqPacket.play(rtspClient.getRtspEntity().getUri(), session, cseq));
    }


}
