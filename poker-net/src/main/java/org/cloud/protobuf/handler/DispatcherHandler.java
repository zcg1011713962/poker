package org.cloud.protobuf.handler;


import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.cloud.BasePacketHandler;
import org.cloud.entity.exception.BaseException;
import org.cloud.entity.poker.BasePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * handler分发器
 */
@Component
@Slf4j
public class DispatcherHandler{
    @Autowired
    private ApplicationContext applicationContext;

    public BasePacketHandler getHandleByCode(short protoCode){
        // 获取所有实现接口的 bean 对象
        Map<String, BasePacketHandler> handlers = applicationContext.getBeansOfType(BasePacketHandler.class);
        // 遍历所有实现接口的 bean 对象
        for (BasePacketHandler pHandler : handlers.values()) {
            // 处理每个实现接口的 bean
            if(pHandler.protoCode() == protoCode){
                log.debug("分发给{}处理协议码:{}", pHandler.getClass(), pHandler.protoCode());
                return pHandler;
            }
        }
        throw BaseException.show("找不到handler处理协议码:" + protoCode);
    }

}
