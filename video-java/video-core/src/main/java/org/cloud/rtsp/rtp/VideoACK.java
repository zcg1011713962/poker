package org.cloud.rtsp.rtp;


import lombok.extern.slf4j.Slf4j;
import org.cloud.rtsp.entity.RtspSDParser;

@Slf4j
public class VideoACK implements Runnable {
    private RtspSDParser rtspSDParser;

    public VideoACK(RtspSDParser rtspSDParser) {
        this.rtspSDParser = rtspSDParser;
    }

    @Override
    public void run() {
        if (rtspSDParser.isReceivedRtp()) {
            return;
        }
        // 收不到RTP包
        log.info("此时应该发送终止请求");
    }


}
