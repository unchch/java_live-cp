package com.bh.live.common.enums.lottery;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName SeedShowAbleEnum
 * @description: 彩种隐藏选项枚举
 * @author: yq.
 * @date 2019-07-25 16:52:00
 */
@Getter
@AllArgsConstructor
public enum SeedShowAbleEnum {
    /**
     * 隐藏
     */
    HIDE(0, "隐藏"),
    /**
     * 显示
     */
    DISPLAY(1, "显示"),
    /**
     * 隐藏官方玩法
     */
    HIDE_OFFICIAL(2, "隐藏官方玩法"),
    /**
     * 隐藏信用玩法
     */
    HIDE_CREDIT(3, "隐藏信用玩法");

    private final int code;

    private final String desc;

    /**
     * 根据code获得枚举对象
     * @param code
     * @return
     */
    public static SeedShowAbleEnum of(Integer code) {
        return Lists.newArrayList(values()).stream()
                .filter((e) -> code.compareTo(e.getCode()) == 0)
                .findFirst()
                .orElse(null);
    }
}
