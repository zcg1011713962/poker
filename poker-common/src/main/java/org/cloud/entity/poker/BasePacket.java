package org.cloud.entity.poker;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasePacket {
    private short protoCode;
    private byte[] bytes;

}
