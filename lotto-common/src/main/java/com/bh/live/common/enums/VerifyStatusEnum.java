package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum VerifyStatusEnum implements BaseEnum {
    NO_PASS(0, "拒绝"),
    PASS(1, "通过"),
    WAITING(2, "审核中");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    VerifyStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static VerifyStatusEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (VerifyStatusEnum verifyStatusEnum : VerifyStatusEnum.values()) {
            if (verifyStatusEnum.code.equals(code)) {
                return verifyStatusEnum;
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
