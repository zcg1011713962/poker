package org.cloud.enums;

public enum ProtoCode {
    ENTER_ROOM(100, "进房协议");

    private int code;

    private String desc;

    ProtoCode(int code, String desc) {
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
