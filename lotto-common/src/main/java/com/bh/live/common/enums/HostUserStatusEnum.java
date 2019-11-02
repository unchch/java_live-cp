package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum HostUserStatusEnum implements BaseEnum {
    ONLINE(2, "在线"),
    LIVING(1, "直播中"),
    UN_ONLINE(0, "离线"),
    ;
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    HostUserStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HostUserStatusEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (HostUserStatusEnum hostUserStatusEnum : HostUserStatusEnum.values()) {
            if (hostUserStatusEnum.code.equals(code)) {
                return hostUserStatusEnum;
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
