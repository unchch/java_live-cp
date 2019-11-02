package com.bh.live.common.enums.lottery;

import java.util.Objects;

/**
 * @author lgs
 * @title: LotteryEnum
 * @projectName java_live-cp
 * @description: 彩种枚举类
 * @date 2019/7/16  15:45
 */
public enum LotteryPlayEnum {

    /*高频彩*/
    /*幸运飞艇玩法*/

    LUCKY_AIRSHIP_40151("40151", "幸运飞艇定位玩法"),

    LUCKY_AIRSHIP_CHAMPION("4015101", "幸运飞艇冠军"),
    LUCKY_AIRSHIP_RUNNER_UP("4015102", "幸运飞艇亚军"),
    LUCKY_AIRSHIP_THIRD("4015103", "幸运飞艇第三名"),
    LUCKY_AIRSHIP_FOURTH_PLACE("4015104", "幸运飞艇第四名"),
    LUCKY_AIRSHIP_FIFTH_PLACE("4015105", "幸运飞艇第五名"),
    LUCKY_AIRSHIP_SIXTH_PLACE("4015106", "幸运飞艇第六名"),
    LUCKY_AIRSHIP_SEVENTH_PLACE("4015107", "幸运飞艇第七名"),
    LUCKY_AIRSHIP_EIGHTH_PLACE("4015108", "幸运飞艇第八名"),
    LUCKY_AIRSHIP_NINTH_PLACE("4015109", "幸运飞艇第九名"),
    LUCKY_AIRSHIP_TENTH_PLACE("4015110", "幸运飞艇第十名"),
    LUCKY_AIRSHIP_SUM("40152", "幸运飞艇冠亚军和"),
    LUCKY_AIRSHIP_CHAMPION_RUNNER_UP("40153", "幸运飞艇冠亚组合"),
    LUCKY_AIRSHIP_DT("40154", "幸运飞艇龙虎"),
    LUCKY_AIRSHIP_CHAMPION_INDEX("40101", "幸运飞艇冠军"),
    LUCKY_AIRSHIP_CHAMPION_RUNNER_UP_SD("40102", "幸运飞艇冠亚"),
    LUCKY_AIRSHIP_TOP_3("40103", "幸运飞艇前三"),
    LUCKY_AIRSHIP_SIZE("40104", "幸运飞艇大小"),
    LUCKY_AIRSHIP_SD("40105", "幸运飞艇单双"),

    /*北京赛车玩法*/
    BJ_CAR_40251("40251", "幸运飞艇定位玩法"),
    BJ_CAR_CHAMPION("4025101", "北京赛车冠军"),
    BJ_CAR_RUNNER_UP("4025102", "北京赛车亚军"),
    BJ_CAR_THIRD("4025103", "北京赛车第三名"),
    BJ_CAR_FOURTH_PLACE("4025104", "北京赛车第四名"),
    BJ_CAR_FIFTH_PLACE("4025105", "北京赛车第五名"),
    BJ_CAR_SIXTH_PLACE("4025106", "北京赛车第六名"),
    BJ_CAR_SEVENTH_PLACE("4025107", "北京赛车第七名"),
    BJ_CAR_EIGHTH_PLACE("4025108", "北京赛车第八名"),
    BJ_CAR_NINTH_PLACE("4025109", "北京赛车第九名"),
    BJ_CAR_TENTH_PLACE("4025110", "北京赛车第十名"),
    BJ_CAR_SUM("40252", "北京赛车冠亚军和"),
    BJ_CAR_CHAMPION_RUNNER_UP("40253", "北京赛车冠亚组合"),
    BJ_CAR_DT("40254", "北京赛车龙虎"),
    BJ_CAR_CHAMPION_INDEX("40201", "北京赛车冠军"),
    BJ_CAR_CHAMPION_RUNNER_UP_SD("40202", "北京赛车冠亚"),
    BJ_CAR_TOP_3("40203", "北京赛车前三"),
    BJ_CAR_SIZE("40204", "北京赛车大小"),
    BJ_CAR_SD("40205", "北京赛车单双"),


    /*香港彩*/
    HONEGKONG_SPECIAL_CODE("80151", "特码"),
    HONEGKONG_NUM("8015101", "号码"),
    HONEGKONG_TWO_SIDES("8015102", "两面"),
    HONEGKONG_WAVE_COLOR("8015103", "波色"),

    HONEGKONG_FLAT_CODE("80152", "平码"),
    HONEGKONG_FLAT_CODE_ESPECIALLY("80153", "平码特"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_ONE("8015301", "平特一"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_TWO("8015302", "平特二"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_THREE("8015303", "平特三"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_FOUR("8015304", "平特四"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_FIVE("8015305", "平特五"),
    HONEGKONG_FLAT_CODE_ESPECIALLY_SIX("8015306", "平特六"),

    HONEGKONG_ZODIAC("80154", "生肖"),
    HONEGKONG_SPECIAL_ZODIAC("8015401", "特肖"),
    HONEGKONG_FLAT_ZODIAC("8015402", "平肖"),
    HONEGKONG_ONE_ZODIAC("8015403", "一肖"),
    HONEGKONG_TOTAL_ZODIAC("8015404", "总肖"),

    HONEGKONG_CHOOSE_TO_BE_UNSUCCESSFUL_ON("80155", "自选不中"),
    HONEGKONG_FIVE_FAILURES("8015501", "五不中"),
    HONEGKONG_SIX_FAILURES("8015502", "六不中"),
    HONEGKONG_SEVEN_FAILURES("8015503", "七不中"),
    HONEGKONG_EIGHT_FAILURES("8015504", "八不中"),
    HONEGKONG_NINE_FAILURES("8015505", "九不中"),
    HONEGKONG_TEN_FAILURES("8015506", "十不中"),
    HONEGKONG_ELEVEN_FAILURES("8015507", "十一不中"),
    HONEGKONG_TWELVE_FAILURES("8015508", "十二不中"),

    HONEGKONG_MANTISSA("80156", "尾数"),
    HONEGKONG_FLAT_CODE_PASS("80157", "平码过关"),

    HONEGKONG_JOIN_CODE("80158", "连码"),
    HONEGKONG_THREE_PASS("8015801", "三全中"),
    HONEGKONG_THREE_PASS_TWO("8015802", "三中二"),
    HONEGKONG_TWO_PASS("8015803", "二全中"),
    HONEGKONG_TWO_PASS_SPECIAL("8015804", "二中特"),
    HONEGKONG_SPECIAL_PASS("8015805", "特串"),
    ;

    /**
     * 彩种编号
     */
    private String code;
    /**
     * 彩种名称
     */
    private String name;

    LotteryPlayEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static LotteryPlayEnum getLotteryPlay(String value) {
        if (null == value) {
            return null;
        }
        for (LotteryPlayEnum child : values()) {
            if (Objects.equals(value, child.getCode())) {
                return child;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
