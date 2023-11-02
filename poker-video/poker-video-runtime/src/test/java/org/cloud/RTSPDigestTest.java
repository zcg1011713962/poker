package org.cloud;

import lombok.extern.slf4j.Slf4j;
import org.cloud.rtsp.entity.RTSPDigest;
import org.cloud.rtsp.entity.RtspUrlParser;

@Slf4j
public class RTSPDigestTest {
    public static void main(String[] args) {
        String url = "rtsp://admin:link123456@192.168.7.12:554/h264/ch1/main/av_stream";
        RtspUrlParser rtspParser = new RtspUrlParser(url);
        if(rtspParser.parse()){
            String method = "DESCRIBE";
            String realm = "realm";
            String nonce = "nonce";
            RTSPDigest digest = new RTSPDigest(rtspParser.getUsername(), realm, nonce, rtspParser.getUri(), method, rtspParser.getPassword());
            String response = digest.calculateResponse();
            log.info("{}", digest.toString());
            log.info(response);
        }
    }
}
