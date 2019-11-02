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
 * 彩种类型枚举
 * @author
 * @version 1.0.0 2019-06-24
 **/
@Getter
@AllArgsConstructor
public enum VarietyTypeEnum implements BaseEnum {

    LUCKY_AIRSHIP(0, "幸运飞艇",180,5),

    TPK10(1, "北京赛车",44,20),

    HEAVY_TIME(2, "重庆时时彩",59,20),

    HOLY_LAND(3, "圣地彩",90,10),

    FAST3(4, "江苏快3",41,20),

    LUCKY_FARM(5, "幸运农场",59,20),

    FAST8(6, "北京快乐8",179,5),

    NEW_TIME(7, "新疆时时彩",48,20),

    FAST10(8, "广东快了十分",42,20),

    SELECTED_11_5(9, "广东11选5",42,20),

    TIME(10, "天津时时彩",42,20),

    HONG_KONG_LOTTERY(11, "香港彩",40,20),
    ;

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String name;

    /**
     * 每天总共期数
     */
    private final int expectCount;

    /**
     * 间隔期数 分钟
     */
    private final int interval;

    public static String getName(Integer code) {
        VarietyTypeEnum res = Arrays.asList(values()).stream()
                .filter(item -> Integer.valueOf(item.getCode()).compareTo(code) == 0)
                .findFirst()
                .orElse(null);
        if(CommonUtil.isEmpty(res)){
            return StringUtils.EMPTY;
        }
        return res.getName();
    }

    public static List<Map<Object, Object>> getAll(){
        List<Map<Object, Object>> res = Lists.newArrayList();
        Arrays.asList(values()).forEach(item -> {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("code",item.getCode());
            map.put("name",item.getName());
            res.add(map);
        });
        return res;
    }

    public static VarietyTypeEnum get(int code) {
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
