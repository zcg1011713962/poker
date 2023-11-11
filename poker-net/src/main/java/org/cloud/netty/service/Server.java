package org.cloud.netty.service;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;

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
    default ServerBootstrap getWebSocketServer(){
        return null;
    }

}
