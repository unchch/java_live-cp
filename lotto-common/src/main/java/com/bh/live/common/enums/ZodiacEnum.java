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
public enum ZodiacEnum implements BaseEnum {

    MOUSE(1, "鼠","mouse"),
    CATTLE(2, "牛","cattle"),
    TIGER(3, "虎","tiger"),
    RABBIT(4, "兔","rabbit"),
    DRAGON(5, "龍","dragon"),
    SNAKE(6, "蛇","snake"),
    HOUSE(7, "马","horse"),
    SHEEP(8, "羊","sheep"),
    MONKEY(9, "猴","monkey"),
    CHICKEN(10, "鸡","chicken"),
    DOG(11, "狗","dog"),
    PIG(12, "猪","pig")
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
     * 生肖
     */
    private final String zodiac;

    public static String getName(Integer code) {
        ZodiacEnum res = Arrays.asList(values()).stream()
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

    public static ZodiacEnum get(int code) {
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
