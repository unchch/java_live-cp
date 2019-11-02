package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum HostRoomStatusEnum implements BaseEnum {
    UN_LIVE(0, "未开播"),
    LIVING(1, "直播中"),
    FINISHED(2, "已完成");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    HostRoomStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HostRoomStatusEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (HostRoomStatusEnum sexEnum : HostRoomStatusEnum.values()) {
            if (sexEnum.code.equals(code)) {
                return sexEnum;
            }
        }
        return null;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object code() {
        return code;
    }

    @Override
    public String value() {
        return name;
    }
}
