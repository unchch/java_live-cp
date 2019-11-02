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
 * 礼物分类枚举
 * @author
 * @version 1.0.0 2019-07-26
 **/
@Getter
@AllArgsConstructor
public enum GiftTypeEnum implements   BaseEnum{

    PRPO(1, "道具"),

    LUCKY_BAG(2, "福袋"),

    GENERAL(3, "普通");


    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String name;

    public static String getName(Integer code) {
        GiftTypeEnum res = Arrays.asList(values()).stream()
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

    public static GiftTypeEnum get(int code) {
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
