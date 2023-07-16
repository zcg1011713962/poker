package org.cloud.manager;

import io.netty.channel.nio.NioEventLoopGroup;

public class EventLoopGroupManager {
    private static volatile NioEventLoopGroup workGroup;
    private static volatile NioEventLoopGroup bossGroup;

    public static NioEventLoopGroup getWorkGroup() {
        if(workGroup == null){
            synchronized (EventLoopGroupManager.class){
                if(workGroup == null){
                    workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 4 );
                }
            }
        }
        return workGroup;
    }

    public static NioEventLoopGroup getBossGroup() {
        if(bossGroup == null){
            synchronized (EventLoopGroupManager.class){
                if(bossGroup == null){
                    bossGroup = new NioEventLoopGroup(1);
                }
            }
        }
        return bossGroup;
    }

}
