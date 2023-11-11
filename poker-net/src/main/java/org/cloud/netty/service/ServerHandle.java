package org.cloud.netty.service;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;

public interface ServerHandle<T> extends Handle{

    T init();

    default T bind(ServerBootstrap bootstrap){
        return null;
    }

    default T bind(Bootstrap bootstrap){
        return null;
    }

}

