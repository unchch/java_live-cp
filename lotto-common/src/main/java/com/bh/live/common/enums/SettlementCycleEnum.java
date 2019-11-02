package com.bh.live.common.enums;

/**
 * @Author: Morphon
 * @Version: 1.0
 * @date: 2019/7/31 14:26
 */
public enum SettlementCycleEnum implements BaseEnum {
    DAY(0, "日结"),
    WEEK(1, "周结"),
    MONTH(2, "月结");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    SettlementCycleEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SettlementCycleEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (SettlementCycleEnum settlementCycleEnum : SettlementCycleEnum.values()) {
            if (settlementCycleEnum.code.equals(code)) {
                return settlementCycleEnum;
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
