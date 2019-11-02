package com.bh.live.common.enums;

/**
 * @author lgs
 * @title: HighEnum
 * @projectName livebase
 * @description: 高频彩枚举
 * @date 2019/7/9  9:30
 */
public class HighEnum {

    /**
     * 龙虎枚举
     */
    public enum DragonTigerEnum {
        /**
         * 龙
         */
        DRAGON("1", "龙"),
        /**
         * 虎
         */
        TIGER("2", "虎");

        DragonTigerEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 获取龙虎判断
         *
         * @param a
         * @param b
         * @return
         */
        public String getDragonTiger(int a, int b) {
            if (a > b) {
                return DragonTigerEnum.DRAGON.getCode();
            }
            return DragonTigerEnum.TIGER.getCode();
        }
    }

    /**
     * 单双枚举
     */
    public enum SingleDoubleEnum {
        /**
         * 单
         */
        SINGLE("1", "单"),
        /**
         * 双
         */
        DOUBLE("2", "双");

        SingleDoubleEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 获取单双
         *
         * @param a
         * @return
         */
        public String getSingleDouble(int a) {
            int b = 2;
            if (a % b == 0) {
                return SingleDoubleEnum.DOUBLE.getCode();
            }
            return SingleDoubleEnum.SINGLE.getCode();
        }
    }

    public enum MinMaxEnum {
        /**
         * 小
         */
        MIN("1", "小"),
        /**
         * 大
         */
        MAX("2", "大");

        MinMaxEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        /**
         * 获取北京赛车冠亚和值大小
         * @param a
         * @return
         */
        public String getBjCarMinMax(int a) {
            return getMinMax(a,11);
        }

        /**
         * 查看大小
         * @param a
         * @param compareValue 比较值
         * @return
         */
        public String getMinMax(int a, int compareValue) {
            if (a > compareValue) {
                return MinMaxEnum.MAX.getCode();
            }

            return MinMaxEnum.MIN.getCode();
        }
    }
}
