package org.cloud.netty.abs;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public abstract class AbstractInitializer<T extends Channel> extends ChannelInitializer<T> {
    protected boolean proxy;

    public AbstractInitializer(boolean proxy){
        this.proxy = proxy;
    }

}
