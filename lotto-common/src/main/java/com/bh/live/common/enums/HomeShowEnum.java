package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum HomeShowEnum implements BaseEnum {
    YES(0, "否"),
    NO(1, "是");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    HomeShowEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HomeShowEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (HomeShowEnum homeShowEnum : HomeShowEnum.values()) {
            if (homeShowEnum.code.equals(code)) {
                return homeShowEnum;
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
