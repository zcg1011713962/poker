package org.cloud;

import io.netty.buffer.ByteBuf;

public interface BasePacketHandler<T>{

    int protoCode();

    ByteBuf handle(byte[] b) throws Exception;

}
