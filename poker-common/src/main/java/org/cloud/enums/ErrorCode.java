package org.cloud.enums;

public enum ErrorCode {
    SUCCESS(200, "成功"),
    PACKET_FORMAT_ERROR(90001, "协议包格式错误"),
    FUTURE_ERROR(90002, "future异常");

    private int code;

    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
