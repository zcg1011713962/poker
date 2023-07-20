package org.cloud.rtsp.handler.abs;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpObject;
import io.netty.handler.codec.http.HttpHeaders;

public abstract class RtspMethodHandler<T> extends SimpleChannelInboundHandler<DefaultHttpObject> {

    abstract void optionsHandler(T t, HttpHeaders headers);

    abstract void describeHandler(T t, HttpHeaders headers);

    abstract void sdpHeaderHandler(T t, HttpHeaders headers);

    abstract void sdpCompositeHandler(T t,  String sdpFragment);

    abstract void setupHandler(T t,  HttpHeaders headers);

}
