package org.cloud.manager;


import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import org.cloud.entity.exception.FutureException;
import org.cloud.netty.service.Server;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServerManager {
    // <serverId, server>
    private static ConcurrentMap<String, Server> serverMap = new ConcurrentHashMap();

    public static Server put(String serverId, Server server) {
        return serverMap.putIfAbsent(serverId, server);
    }

    public static Server server(String channelId) {
        if (StrUtil.isBlank(channelId)) {
            throw FutureException.show("channelId is null");
        }
        Iterator<Map.Entry<String, Server>> it = serverMap.entrySet().iterator();
        while (it.hasNext()) {
            Server server = it.next().getValue();
            Channel c = server.channel();
            if (channelId.equals(c.id().asLongText())) {
                return server;
            }
        }
        throw FutureException.show("根据channelId" + channelId + "获取不到server");
    }

    public static Server remove(String serverId) {
        return serverMap.remove(serverId);
    }

}
