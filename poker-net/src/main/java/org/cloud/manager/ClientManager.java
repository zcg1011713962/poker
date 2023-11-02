package org.cloud.manager;


import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import org.cloud.entity.exception.FutureException;
import org.cloud.netty.service.Client;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientManager {
    // <clientId,client> forbid null key and value
    private static ConcurrentMap<String, Client> clientMap = new ConcurrentHashMap();

    public static Client put(String clientId, Client client) {
        return clientMap.putIfAbsent(clientId, client);
    }

    public static Client get(String clientId) {
        return clientMap.get(clientId);
    }

    public static Channel channel(String channelId) {
        if (StrUtil.isBlank(channelId)) throw new FutureException("channelId is null");
        Iterator<Map.Entry<String, Client>> it = clientMap.entrySet().iterator();
        while (it.hasNext()) {
            Channel c = it.next().getValue().channel();
            if (channelId.equals(c.id().asLongText())) {
                return c;
            }
        }
        throw new FutureException("根据channelId" + channelId + "获取不到channel");
    }

    public static Client client(String channelId) {
        if (StrUtil.isBlank(channelId))  throw new FutureException("channelId is null");
        Iterator<Map.Entry<String, Client>> it = clientMap.entrySet().iterator();
        while (it.hasNext()) {
            Client client = it.next().getValue();
            Channel c = client.channel();
            if (channelId.equals(c.id().asLongText())) {
                return client;
            }
        }
        throw new FutureException("根据channelId" + channelId + "获取不到client");
    }

    public static Client remove(String clientId) {
        return clientMap.remove(clientId);
    }

}
