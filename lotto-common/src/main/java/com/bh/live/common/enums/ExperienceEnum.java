package com.bh.live.common.enums;

/**
 * Create by Administrator ON 2019/7/31
 */
public enum ExperienceEnum implements BaseEnum{
    NONE(0, "无"),
    HAVE(1,"有");
    /**
     * 类型
     */
    private Integer code;
    /**
     * 描述
     */
    private String name;

    ExperienceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ExperienceEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ExperienceEnum experienceEnum : ExperienceEnum.values()) {
            if (experienceEnum.code.equals(code)) {
                return experienceEnum;
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
