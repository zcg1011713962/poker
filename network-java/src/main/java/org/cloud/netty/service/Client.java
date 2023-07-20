package org.cloud.netty.service;

import io.netty.bootstrap.Bootstrap;


public interface Client<T> extends Connection<T> {

    /**
     * tcp client
     * @return
     */
    default Bootstrap getTcpClient() {
        return null;
    }

}
