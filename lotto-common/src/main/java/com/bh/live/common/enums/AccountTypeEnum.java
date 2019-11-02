package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/8/7 14:26
 * @desc 账户类型
 */
public enum AccountTypeEnum implements BaseEnum {
    WX(0, "微信"),
    ZFB(1, "支付宝"),
    CARD(2, "银行卡"),
    QQ(4, "QQ"),
    WB(3, "微博");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    AccountTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AccountTypeEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (AccountTypeEnum sexEnum : AccountTypeEnum.values()) {
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
