package org.cloud.enums;

public enum ErrorCode {
    PACKET_FORMAT_ERROR(90001, "协议包格式错误"),
    FUTURE_ERROR(90002, "future异常");

    private byte id;

    private String desc;

    ErrorCode(int i, String desc) {
    }

    public byte getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
