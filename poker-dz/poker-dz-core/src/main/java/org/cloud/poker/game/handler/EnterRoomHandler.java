package org.cloud.poker.game.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.cloud.BasePacketHandler;
import org.cloud.enums.ErrorCode;
import org.cloud.enums.ProtoCode;
import org.cloud.poker.proto.request.EnterRoomIn;
import org.cloud.poker.proto.response.EnterRoomOut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnterRoomHandler implements BasePacketHandler {
    @Override
    public int protoCode() {
        return ProtoCode.ENTER_ROOM.getCode();
    }

    @Override
    public ByteBuf handle(byte[] b) throws Exception {
        EnterRoomIn.EnterRoomRequest request = EnterRoomIn.EnterRoomRequest.parseFrom(b);
        String userId = request.getUserId();
        log.info("用户加入房间userId:{}", userId);



        EnterRoomOut.EnterRoomResponse response = EnterRoomOut.EnterRoomResponse.newBuilder()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(StrUtil.EMPTY).build();
        return Unpooled.wrappedBuffer(response.toByteArray());
    }
}
