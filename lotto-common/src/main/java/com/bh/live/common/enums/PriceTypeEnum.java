package com.bh.live.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: PriceTypeEnum
 * @projectName java_live-cp
 * @description: 价格类别枚举
 * @date 2019/8/2  11:10
 */
@AllArgsConstructor
@Getter
public enum PriceTypeEnum  implements BaseEnum{
    PRICE_0(0,"免费"),
    PRICE_3(3,"3元"),
    PRICE_8(8,"8元"),
    PRICE_16(16,"16元"),
    PRICE_18(18,"18元"),
    PRICE_20(20,"20元"),
    PRICE_25(25,"25元"),
    PRICE_28(28,"28元"),
    PRICE_30(30,"30元"),
    PRICE_38(38,"38元"),
    PRICE_58(58,"58元"),
    PRICE_68(68,"68元"),
    PRICE_98(98,"98元"),
    PRICE_128(128,"128元"),
    ;
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    @Override
    public Object code() {
        return code;
    }

    @Override
    public String value() {
        return name;
    }}
