package org.cloud.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

public enum SocketPort {
    WEBSOCKET_PROTOBUF_DZ(20000, "德州扑克协议端口");

    private int port;

    private String desc;

    SocketPort(int port, String desc) {
        this.port = port;
        this.desc = desc;
    }

    public int getPort() {
        return port;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByPort(int port){
        SocketPort[] vals = SocketPort.values();
        for(SocketPort it : Arrays.asList(vals)){
            if(port == it.getPort()){
                return it.getDesc();
            }
        }
      return StrUtil.EMPTY;
    }


}
