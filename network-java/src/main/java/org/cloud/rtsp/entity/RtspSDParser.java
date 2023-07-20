package org.cloud.rtsp.entity;


import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@ToString
public class RtspSDParser implements Serializable {

    private StringBuffer sdp = new StringBuffer();
    private int cseq;
    private String contentType;
    private String contentBase;
    private int contentLength;
    // 视频端口
    private int vPort;
    // 音频端口
    private int aPort;
    private int trackID;
    private String transport;
    // 断开有用
    private String session;
    private int lastCseq;
    // 获取流超时时间
    private int timeout;
    // 超时监听标识
    private AtomicBoolean confirmedTimeout = new AtomicBoolean();
    // 收到RTP包标识
    private boolean receivedRtp;

    public RtspSDParser append(String str) {
        sdp.append(str);
        return this;
    }


    public boolean parser() {
        String content = sdp.toString();
        if (StrUtil.isBlank(content)) {
            return false;
        }
        if (success(content)) {
            return true;
        }
        String[] line = content.split("\r\n");
        for (String c : line) {
            if (c == null) continue;
            if (c.contains("a=")) {
                if (c.contains("trackID=")) {
                    trackID = Integer.parseInt(c.split("trackID=")[1].trim());
                }
            } else if (c.contains("b=")) {

            } else if (c.contains("c=")) {

            } else if (c.contains("m=")) {
                if (c.contains("audio")) {

                } else if (c.contains("org/cloud")) {
                    String [] v = c.split(StrUtil.SPACE);
                    vPort= Integer.parseInt(v[1]);
                    transport = v[2];
                }
            }
        }
        return success(content);
    }

    private boolean success(String content) {
        if (StrUtil.isNotBlank(content) && contentLength > 0 && content.length() == contentLength) {
            return true;
        }
        return false;
    }


}
