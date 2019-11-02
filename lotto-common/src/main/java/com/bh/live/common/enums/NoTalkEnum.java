package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/8/7 14:26
 * @desc 禁言原因
 */
public enum NoTalkEnum implements BaseEnum {
    GG(0, "刷广告"),
    SP(1, "恶意刷屏"),
    MM(2, "谩骂"),
    OTHER(3, "其他");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    NoTalkEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NoTalkEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (NoTalkEnum sexEnum : NoTalkEnum.values()) {
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
