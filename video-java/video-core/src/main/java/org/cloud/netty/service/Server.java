package org.cloud.netty.service;

import io.netty.bootstrap.Bootstrap;

public interface Server<T> extends Connection<T> {

    /**
     * udp server
     * @return
     */
    default Bootstrap getUDPServer(){
        return null;
    }

    /**
     * websocket server
     * @return
     */
    default Bootstrap getWebSocketServer(){
        return null;
    }

}
