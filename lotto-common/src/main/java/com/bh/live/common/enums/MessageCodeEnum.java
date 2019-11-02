package com.bh.live.common.enums;

/**
 * @author WuLong
 * @title: MessageCodeEnum
 * @projectName java_live-cp
 * @description: 短信枚举类
 * @date 2019/7/23 15:45
 */
public enum MessageCodeEnum {

    REGISTER(1,"注册"),
    RESET_PASSWORD(2,"重置密码"),
    LOGIN(3,"登录")
    ;

    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    MessageCodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MessageCodeEnum getEnumByCode(Integer code){
        if(null==code){
            return null;
        }
        for(MessageCodeEnum messageCodeEnum:MessageCodeEnum.values()){
            if(messageCodeEnum.code.equals(code)){
                return messageCodeEnum;
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
}
