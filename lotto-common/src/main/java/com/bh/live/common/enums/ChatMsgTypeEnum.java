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
 * 聊天消息类型枚举
 *
 * @author
 * @version 1.0.0 2019-07-26
 */
@Getter
@AllArgsConstructor
public enum ChatMsgTypeEnum implements   BaseEnum{

    NORMAL(1,"普通消息"),

    GIFT(2,"礼物指令"),

    WELCOME(3, "欢迎"),

    NO_TALK(4, "禁言"),

    OPEN_CODE(5,"开奖结果"),

    GUESS(6,"竞猜发布"),

    SYS_INFO(7, "系统消息：消息"),

    CURRENT_OPEN(8, "系统消息：当期开盘")

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
        ChatMsgTypeEnum res = Arrays.asList(values()).stream()
                .filter(item -> Integer.valueOf(item.getCode()).compareTo(code) == 0)
                .findFirst()
                .orElse(null);
        if(CommonUtil.isEmpty(res)){
            return StringUtils.EMPTY;
        }
        return res.getName();
    }

    public static ChatMsgTypeEnum get(int code) {
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
