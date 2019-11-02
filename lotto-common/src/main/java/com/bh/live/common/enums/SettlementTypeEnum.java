package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum SettlementTypeEnum implements BaseEnum {
    LEVEL_CAL(0, "等级结算"),
    GIFT_CAL(1, "礼物数结算"),
    CUSTOM(2, "自定义");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    SettlementTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SettlementTypeEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (SettlementTypeEnum settlementTypeEnum : SettlementTypeEnum.values()) {
            if (settlementTypeEnum.code.equals(code)) {
                return settlementTypeEnum;
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
