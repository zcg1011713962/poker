package org.cloud.enums;

public enum RoomType {
    TEXASHOLDEM_NORMAL(0, "德州普通局");

    private int id;

    private String desc;

    RoomType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
