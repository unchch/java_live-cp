package com.bh.live.common.enums.user;

import com.bh.live.common.enums.BaseEnum;
import com.bh.live.common.exception.ResultJsonException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.model.user.LiveUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lgs
 * @title: UserEnum
 * @projectName java_live-cp
 * @description: 用户枚举
 * @date 2019/7/31  10:33
 */
public class UserEnum {

    @AllArgsConstructor
    @Getter
    public enum UserPublishEnum {
        STOP(0, "启用发布竞猜"),
        ON(1, "禁止发布竞猜"),
        ;

        private Integer code;
        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum LoginModeEnum {
        PASSWORD(0, "密码登录"),
        SHORT_MESSAGE(1, "短信登录");

        private Integer code;
        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum UserTypeEnum implements BaseEnum {
        ALL(-1, "全部"),
        USER(0, "普通"),
        EXPERT(1, "专家"),
        ANCHOR(2, "主播"),
        ;

        private Integer code;
        private String name;

        @Override
        public Object code() {
            return code;
        }

        @Override
        public String value() {
            return name;
        }

        public static UserTypeEnum getUserType(LiveUser user) {
            if (user.getIsAnchor() == 1) {
                return ANCHOR;
            }
            if (user.getIsExpert() == 1) {
                return EXPERT;
            }
            return USER;
        }
    }

    /**
     *@description 用户直播间类型
     *@author WuLong
     *@date 2019/8/8 12:50
     */
    @AllArgsConstructor
    @Getter
    public enum ChatUserTypeEnum {
        USER("user"),
        ANCHOR("anchor");
        private String desc;
    }


    /**
     *@description 用户是否在线
     *@author WuLong
     *@date 2019/8/8 12:50
     */
    @AllArgsConstructor
    @Getter
    public enum OnOffLineEnum {
        YES(1, "在线"),
        NO(0, "下线");

        private Integer code;
        private String desc;
    }

    /**
     * @Author: Morphon
     * @Version: 1.0
     * @date: 2019/7/31 14:26
     * 账户是否可用 0不可 1可用
     */
    @AllArgsConstructor
    @Getter
    public enum IsUsableEnum implements BaseEnum {
        YES(1, "可用"),
        NO(0, "不可用");
        /**
         * 类型
         */
        private Integer code;
        /**
         * 描述
         */
        private String name;

        public static IsUsableEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (IsUsableEnum isUsableEnum : IsUsableEnum.values()) {
                if (isUsableEnum.code.equals(code)) {
                    return isUsableEnum;
                }
            }
            return null;
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
    /**
     *@author WuLong
     *@date 2019/8/8 13:22
     *@description 账户是否可以登录  0不可  1可以
     */
    @AllArgsConstructor
    @Getter
    public enum IsLoginEnum implements BaseEnum {
        YES(1, "可以"),
        NO(0, "不可");
        /**
         * 类型
         */
        private Integer code;
        /**
         * 描述
         */
        private String name;

        public static IsLoginEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (IsLoginEnum isLoginEnum : IsLoginEnum.values()) {
                if (isLoginEnum.code.equals(code)) {
                    return isLoginEnum;
                }
            }
            return null;
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
    /**
     *@author WuLong
     *@date 2019/8/8 13:22
     *@description 账户是否可以直播间发言  0不可  1可以
     */
    @AllArgsConstructor
    @Getter
    public enum IsSpeakEnum implements BaseEnum {
        YES(1, "可以"),
        NO(0, "不可");
        /**
         * 类型
         */
        private Integer code;
        /**
         * 描述
         */
        private String name;

        public static IsSpeakEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (IsSpeakEnum isSpeakEnum : IsSpeakEnum.values()) {
                if (isSpeakEnum.code.equals(code)) {
                    return isSpeakEnum;
                }
            }
            return null;
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

    /**
     *@author WuLong
     *@date 2019/8/8 13:22
     *@description 账户是否是专家  0否  1是
     */
    @AllArgsConstructor
    @Getter
    public enum IsExpertEnum implements BaseEnum {
        YES(1, "是"),
        NO(0, "否");
        /**
         * 类型
         */
        private Integer code;
        /**
         * 描述
         */
        private String name;

        public static IsExpertEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (IsExpertEnum isExpertEnum : IsExpertEnum.values()) {
                if (isExpertEnum.code.equals(code)) {
                    return isExpertEnum;
                }
            }
            return null;
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

    /**
     *@author WuLong
     *@date 2019/8/8 13:22
     *@description 账户是否是主播  0否  1是
     */
    @AllArgsConstructor
    @Getter
    public enum IsAnchorEnum implements BaseEnum {
        YES(1, "是"),
        NO(0, "否");
        /**
         * 类型
         */
        private Integer code;
        /**
         * 描述
         */
        private String name;

        public static IsAnchorEnum getEnumByCode(Integer code) {
            if (null == code) {
                return null;
            }
            for (IsAnchorEnum isAnchorEnum : IsAnchorEnum.values()) {
                if (isAnchorEnum.code.equals(code)) {
                    return isAnchorEnum;
                }
            }
            return null;
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

    /**
     * 用户是否可以交易枚举
     */
    @AllArgsConstructor
    @Getter
    public enum FreezeEnum {
        NO(0, "用户允许交易"),
        YES(1, "用户禁止交易");

        private Integer code;
        private String desc;
    }

    /**
     * 用户是否可以提款
     */
    @AllArgsConstructor
    @Getter
    public enum ExtractEnum {
        NO(0, "禁止提款"),
        YES(1, "可以提款");

        private Integer code;
        private String desc;
    }
}
