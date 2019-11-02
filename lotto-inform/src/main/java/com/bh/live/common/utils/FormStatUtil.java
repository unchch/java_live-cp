package com.bh.live.common.utils;


import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.pojo.res.inform.OpenStatisticsRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 开号统计的形态统计
 * <p>
 * 首次形态：该列出现的第一个形态
 * 灰色：最近的统计不成形态时显示灰色
 * 蓝色：最近的统计形成了2个形态时显示蓝色（形态有：单单、双双、大大、小小）
 * 墨绿：最近的统计形成了3个形态时显示墨绿（形态有：单单单、双双双、大大大、小小小）
 * 红色：最近的统计形成了4个或以上形态时显示红色（形态有：单…、双…、大…、小…）
 * 黄色底块：最近的统计的号码形成2个或以上的形态时显示黄色底块（号码形态有：顺子[譬如123]、跳号[譬如1343]、同号[譬如11]）
 * y_blue: 蓝色
 * y_red: 红色
 * y_green: 墨绿色
 * y_lGreen: 荧光绿
 * 二次形态：该列出现的第二个形态
 * 荧光绿：首次形态之后开始统计，当出现5个或以上形态时显示荧光绿（形态有：单…、双…、大…、小…）
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/14 18:33
 */
public class FormStatUtil {

    /**
     * 得到匹配成功的数据
     */
    private static String result = null;
    /**
     * 匹配成功的坐标值
     */
    private static int findIndex = -1;
    /**
     * 匹配成功的颜色
     */
    private static String color = null;
    /**
     * 是否开启二次形态匹配
     */
    private static boolean isTwice = false;
    /**
     * 匹配成黄色块
     */
    private static String bgColor = "yellow";
    /**
     * 二次形态时四个形态的匹配数据
     */
    private static Map<Integer, String> numbers = new HashMap<>();

    /**
     * 匹配顺子 包含2个以上的顺增和顺减的值 例如：[12][1098][789]
     *
     * @param lists
     */
    private static List<OpenStatisticsRes> Straight(List<OpenStatisticsRes> lists) {
        result = null;
        isTwice = false;
        if (lists.size()>1){
            // 判断第一个和第二个是否是顺子
            int sub= lists.get(0).getNumber()-lists.get(1).getNumber();
            int index;
            int flag = 0;
            // 顺增
            if (sub == -1) {
                flag = -1;
            }
            // 顺减
            else if (sub == 1) {
                flag = 1;
            }
            if (flag != 0) {
                index=1;
                for (int i = 1; i < lists.size(); i++) {
                    if ((i + 1) < lists.size()) {
                        int n1 = lists.get(i).getNumber();
                        int n2 = lists.get(i+1).getNumber();
                        if (n1 == n2) {
                            break;
                        }
                        if (n1 - n2 != flag) {
                            break;
                        }
                        index++;
                    }
                }
                // 根据匹配的坐标 修改为黄色背景
                for (int i = 0; i <= index; i++) {
                    lists.get(i).setBgcolor(bgColor);
                }
            }
        }
        return lists;

    }
    /**
     * 匹配首位开始2位以上的重复数字
     *
     * @param lists
     * @return
     */
    private static List<OpenStatisticsRes> isRepeat(List<OpenStatisticsRes> lists) {
        result = null;
        String numS = "";
        int count = 0;
        // 1:2
        for (int i = 0; i < lists.size(); i++) {
            Integer curr_num = lists.get(i).getNumber();
            if ((i + 1) < lists.size()) {
                Integer next_num = lists.get(i + 1).getNumber();
                if (curr_num.equals(next_num)) {
                    count++;
                    if (i == 0) {
                        numS += curr_num + "" + next_num;
                    } else {
                        numS += next_num;
                    }
                    // 设置颜色
                    lists.get(i).setBgcolor(bgColor);
                    lists.get(i + 1).setBgcolor(bgColor);
                }
            }
            // 没有连续匹配上则退出匹配
            if (i == 0 && count == 0) {
                break;
            } else if (i > 0 && count <= i) {
                break;
            }
        }
        return lists;
    }

    /**
     * 跳號:从第1位或者第2位开始计算同一個位置隔期出現的號碼
     *
     * @param lists
     */
    private static List<OpenStatisticsRes> JumpNum(List<OpenStatisticsRes> lists) {
        result = null;
        int index = -1;
        for (int i = 0; i < lists.size(); i++) {
            Integer curr_num = lists.get(i).getNumber();
            if ((i + 2) < lists.size()) {
                Integer next_num = lists.get(i + 2).getNumber();
                // 判断同一个位置下是否相同
                if (i == 0 && !curr_num.equals(next_num)) {
                    continue;
                }
                if (i == 1 && !curr_num.equals(next_num)) {
                    break;
                }
                if (curr_num.equals(next_num)) {
                    //设置跳号角标
                    index = i + 2;
                    i++; // 设置跳号角标为查找的起始位置，此处+1 ，再i++
                    continue;
                }
                break;
            }
        }
        if (index != -1) {
            // 修改跳号
            for (int i = 0; i <= index; i++) {
                lists.get(i).setBgcolor(bgColor);
            }
        }
        return lists;
    }

    /**
     * 生成形态
     *
     * @param lists
     */
    public static void formStat(List<OpenStatisticsRes> lists) {
        System.out.println("----------------------首次形态----------------------------");
        // 首次结果
        beginForm(lists);
        System.out.println("匹配到的结果为：" + result);
        System.out.println("匹配到的颜色是：" + color);
        int lenIndex_f = 0;
        if (result != null) {
            // 根据匹配结果设置颜色形态
            lenIndex_f = result.length();
            for (int i = 0; i < lenIndex_f; i++) {
                OpenStatisticsRes dto = lists.get(i);
                dto.setColor(color);
            }
        }
        System.out.println("----------------------二次形态----------------------------");
        // 二次形态，必须排除首次已生成形态的数据
        List<OpenStatisticsRes> twoList = new ArrayList<>(lists);
        if (result != null) {
            for (int i = 0; i < lenIndex_f; i++) {
                twoList.remove(0);
            }
        }
        // 开启二次形态
        isTwice = true;
        beginForm(twoList);
        System.out.println("匹配到的坐标是：" + findIndex);
        System.out.println("匹配到的结果为：" + result);
        System.out.println("匹配到的颜色是：" + color);
        if (result != null) {
            // 根据匹配结果设置颜色形态
            int begin = lenIndex_f + findIndex;
            int end = result.length() + begin;
            // 从第二次形态开始
            for (int i = begin; i < end; i++) {
                OpenStatisticsRes dto = lists.get(i);
                dto.setColor(color);
            }
        }
        // 黄色底块：顺子，同号，跳号
        yellowBgForm(lists);

        System.out.println("----------------------黄色底块----------------------------");

    }

    /**
     * 首次形态的黄色底块
     * 黄色底块：最近的统计的号码形成2个或以上的形态时显示黄色底块（号码形态有：顺子[譬如123]、跳号[譬如1343]、同号[譬如11]）
     *
     * @param lists
     */
    private static void yellowBgForm(List<OpenStatisticsRes> lists) {
        JumpNum(isRepeat(Straight(lists)));
    }

    /**
     * 开始生成首次形态和二次形态数据
     *
     * @param lists
     */
    private static void beginForm(List<OpenStatisticsRes> lists) {
        result = null;
        StringBuilder bigSamll = new StringBuilder();
        StringBuilder singleDouble = new StringBuilder();
        // 生成字符串
        for (OpenStatisticsRes dto : lists) {
            bigSamll.append(dto.getBigSmall());
            singleDouble.append(dto.getSingleDouble());
        }
        System.out.println("大小：" + bigSamll);
        System.out.println("单双：" + singleDouble);
        // 二次形态，匹配5个或以上
        if (isTwice) {
            numbers.clear();
            match(bigSamll, singleDouble, 5);
            System.out.println("二次形态匹配5个及以上");
            color = "y_lGreen";
        } else {
            // 首次形态有四种颜色
            for (int count = 4; count > 1; count--) {
                match(bigSamll, singleDouble, count);
                if (result != null) {
                    if (count == 4) {
                        color = "y_red";
                    } else if (count == 3) {
                        color = "y_green";
                    } else if (count == 2) {
                        color = "y_blue";
                    }
                    System.out.println("首次形态匹配" + count + "个及以上");
                    break;
                }
            }
        }
    }

    /**
     * 循环匹配单双大小
     *
     * @param bigSamll
     * @param singleDouble
     * @param count
     */
    private static void match(StringBuilder bigSamll, StringBuilder singleDouble, int count) {
        String Single = "['单']{" + count + ",}";
        String Double = "['双']{" + count + ",}";
        String Max = "['大']{" + count + ",}";
        String Min = "['小']{" + count + ",}";
        String[] s_d_m_m = {Single, Double, Max, Min};
        // 循环匹配形态
        for (int i = 0; i < s_d_m_m.length; i++) {
            if (i < 2) {
                recursion(s_d_m_m[i], singleDouble);
            } else {
                recursion(s_d_m_m[i], bigSamll);
            }
            if (!isTwice && result != null) {
                break;
            }
        }
        // 二次形态的时候获取角标最小的匹配值
        if (isTwice && numbers.size() != 0) {
            int index = 999;
            for (Integer i : numbers.keySet()) {
                if (i < index) {
                    index = i;
                }
            }
            findIndex = index;
            result = numbers.get(index);
        }

    }

    /**
     * 判断
     *
     * @param pattern
     * @param numStr
     */
    private static void recursion(String pattern, StringBuilder numStr) {
        Matcher matcher = Pattern.compile(pattern).matcher(numStr);
        boolean find = matcher.find();
        if (find) {
            String group = matcher.group();
            findIndex = numStr.indexOf(group);
            // 首位形态必须是从第一位开始
            if (findIndex == 0 && !isTwice) {
                result = group;
            } else if (isTwice) {
                result = group;
            }
            // 二次形态时匹配最开始的形态
            if (isTwice) {
                numbers.put(findIndex, result);
            }
        }
    }

    /**
     * 根据类型获得字符串列名
     *
     * @param i
     * @return
     */
    public static String getNumberStr(int i) {
        String number = "";
        switch (i) {
            case 1:
                number = "number_one_ball";
                break;
            case 2:
                number = "number_two_ball";
                break;
            case 3:
                number = "number_three_ball";
                break;
            case 4:
                number = "number_four_ball";
                break;
            case 5:
                number = "number_five_ball";
                break;
            case 6:
                number = "number_six_ball";
                break;
            case 7:
                number = "number_seven_ball";
                break;
            case 8:
                number = "number_eight_ball";
                break;
            case 9:
                number = "number_nine_ball";
                break;
            case 10:
                number = "number_ten_ball";
                break;
            case 11:
                number = "crown_sub";
                break;
            case 12:
                number = "first_dragon_tiger";
                break;
            case 13:
                number = "second_dragon_tiger";
                break;
            case 14:
                number = "third_dragon_tiger";
                break;
            case 15:
                number = "fourth_dragon_tiger";
                break;
            case 16:
                number = "fifth_dragon_tiger";
                break;
        }
        return number;
    }

    public static String getNumberStrDragonTiger(int i) {
        String number = "";
        switch (i) {
            case 1:
                number = "first_dragon_tiger";
                break;
            case 2:
                number = "second_dragon_tiger";
                break;
            case 3:
                number = "third_dragon_tiger";
                break;
            case 4:
                number = "fourth_dragon_tiger";
                break;
            case 5:
                number = "fifth_dragon_tiger";
                break;
        }
        return number;
    }

    /**
     * 根据号码获得具体列数据
     *
     * @param i
     * @return
     */
    public static int getBall(int i, ColorVarietyModelRes vo) {
        int ball = 0;
        switch (i) {
            case 1:
                ball = vo.getNumberOneBall();
                break;
            case 2:
                ball = vo.getNumberTwoBall();
                break;
            case 3:
                ball = vo.getNumberThreeBall();
                break;
            case 4:
                ball = vo.getNumberFourBall();
                break;
            case 5:
                ball = vo.getNumberFiveBall();
                break;
            case 6:
                ball = vo.getNumberSixBall();
                break;
            case 7:
                ball = vo.getNumberSevenBall();
                break;
            case 8:
                ball = vo.getNumberEightBall();
                break;
            case 9:
                ball = vo.getNumberNineBall();
                break;
            case 10:
                ball = vo.getNumberTenBall();
                break;
            case 11:
                ball = Integer.parseInt(vo.getCrownSub());
                break;
        }
        return ball;
    }

    /**
     * 根据彩票类型获得开奖球个数
     */
    public static int getTypeOfBallNumber(String varietyType) {
        int number = 0;
        switch (varietyType) {
            case "0"://幸运飞艇
                number = 10;
                break;
            case "1"://pk10
                number = 10;
                break;
            case "2"://重庆时时彩
                number = 5;
                break;
            case "3"://圣地
                number = 5;
                break;
            case "4"://快3
                number = 3;
                break;
            case "5"://幸运农场
                number = 8;
                break;
            case "6"://北京快乐8
                number = 20;
                break;
            case "7"://新疆时时彩
                number = 5;
                break;
            case "8"://广东快乐十分
                number = 8;
                break;
            case "9"://广东十一选五
                number = 5;
                break;
            case "10"://天津时时彩
                number = 5;
                break;
            case "11"://香港彩
                number = 7;
                break;
        }
        return number;
    }

}
