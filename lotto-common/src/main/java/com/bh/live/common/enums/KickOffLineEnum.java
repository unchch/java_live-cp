package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum KickOffLineEnum implements BaseEnum {
    GG(0, "发广告"),
    YELLOW(1, "发黄色信息"),
    RM(2, "辱骂"),
    MG(3, "发布敏感信息"),
    OTHER(4, "其他");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    KickOffLineEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static KickOffLineEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (KickOffLineEnum sexEnum : KickOffLineEnum.values()) {
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
