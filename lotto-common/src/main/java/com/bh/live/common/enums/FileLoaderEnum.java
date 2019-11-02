package com.bh.live.common.enums;

/**
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/30 14:26
 */
public enum  FileLoaderEnum  implements  BaseEnum{
    USER(1,"user/"),
    LOTTO(2,"lotto/"),
    GUESS(3,"guess/"),
    ANCHOR(4,"anchor/"),
    OPERATION(5,"operation/"),
    GIFT(6,"gift/")
    ;
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    FileLoaderEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FileLoaderEnum getEnumByCode(Integer code){
        if(null==code){
            return null;
        }
        for(FileLoaderEnum fileLoaderEnum:FileLoaderEnum.values()){
            if(fileLoaderEnum.code.equals(code)){
                return fileLoaderEnum;
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

    @Override
    public Object code() {
        return code;
    }

    @Override
    public String value() {
        return name;
    }
}
