package org.cloud.netty.service;


public interface ClientHandle<T> extends Handle{

    default T connect(){
        return null;
    }


}

