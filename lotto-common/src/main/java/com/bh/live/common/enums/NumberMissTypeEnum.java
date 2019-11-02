package com.bh.live.common.enums;

import com.bh.live.common.utils.CommonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 号码遗漏号码选择规则
 *
 * @author Administrator
 * @title: NumberMissTypeEnum
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/28  17:52
 */
@Getter
@AllArgsConstructor
public enum NumberMissTypeEnum implements BaseEnum {

    NUMBER(0, "号码"),

    SINGLE_DOUBLE(1, "单双"),

    BIG_SMALL(2, "大小"),

    DRAGON_TIGER(3, "龙虎"),

    CHOOSE_THREE(4, "任选3"),

    CHOOSE_FOUR(5, "任选4"),

    CHOOSE_FIVE(6, "任选5"),

    CHOOSE_SIX(7, "任选6"),

    CHOOSE_SENVEN(8, "任选7"),

    TOTAL_TWO(9, "总和两面"),

    TOTAL_SIZE(10, "总和大小"),

    COUNT(11, "点数"),

    SHORT_CARD(12, "短牌"),

    LONG_CARD(13, "长牌"),

    THREE_ARMY(14, "三军"),
    ;

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String name;

    public static String getName(Integer code) {
        NumberMissTypeEnum res = Arrays.asList(values()).stream()
                .filter(item -> Integer.valueOf(item.getCode()).compareTo(code) == 0)
                .findFirst()
                .orElse(null);
        if (CommonUtil.isEmpty(res)) {
            return StringUtils.EMPTY;
        }
        return res.getName();
    }

    public static List<Map<Object, Object>> getAll() {
        List<Map<Object, Object>> res = Lists.newArrayList();
        Arrays.asList(values()).forEach(item -> {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("code", item.getCode());
            map.put("name", item.getName());
            res.add(map);
        });
        return res;
    }

    public static NumberMissTypeEnum get(int code) {
        return Arrays.asList(values()).stream()
                .filter(item -> item.getCode() == code)
                .findFirst()
                .orElse(null);
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
