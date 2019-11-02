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
public enum AttributesTypeEnum implements BaseEnum {

    ZODIAC(1, "生肖"),

    FIVEELEMENTS(2, "五行"),

    POULTRYBEAST(3, "家禽野兽"),

    MALEGIRLXIAO(4, "男女生肖"),

    TDMALEGIRLXIAO(5, "天地生肖"),

    SJMALEGIRLXIAO(6, "四季生肖"),

    ChESSPAINTING(7, "琴棋书画"),

    SSMALEGIRLXIAO(8, "三色生肖")
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
        AttributesTypeEnum res = Arrays.asList(values()).stream()
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

    public static AttributesTypeEnum get(int code) {
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
