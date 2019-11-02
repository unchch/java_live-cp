package com.bh.live.common.enums;

import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuLong
 * @date 2019/8/8 12:38
 * @description 用户操作设备枚举
 */
@AllArgsConstructor
@Getter
public enum DeviceEnum {
    WX("wx", "微信"),
    IOS("ios", "苹果系统"),
    ANDROID("android", "安卓系统"),
    PC("pc", "pc电脑");

    private String code;
    private String desc;


    @AllArgsConstructor
    @Getter
    public enum ChannelModeEnum {
        IOS(0, "苹果"),
        ANDROID(1, "安卓"),
        PC(2, "pc"),
        WX(3, "微信");

        private Integer code;
        private String desc;

        public static ChannelModeEnum valueOf(Integer code) {
            for (ChannelModeEnum value: ChannelModeEnum.values()) {
                if (value.getCode().equals(code)) {
                    return value;
                }
            }
            throw new ResultJsonException(CodeMsg.CMS_SYS_1104);
        }
    }
}
