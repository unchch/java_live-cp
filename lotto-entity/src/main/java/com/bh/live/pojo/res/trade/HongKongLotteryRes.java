package com.bh.live.pojo.res.trade;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgs
 * @title: HongKongLotteryRes
 * @projectName java_live-cp
 * @description: 香港彩配置
 * @date 2019/8/10  19:29
 */
public class HongKongLotteryRes {

    private static Map<String, Integer[]> shenxiao = new HashMap<>();

    static {
        shenxiao.put("11", new Integer[]{12, 24, 36, 48});
        shenxiao.put("10", new Integer[]{11, 23, 35, 47});
        shenxiao.put("9", new Integer[]{10, 22, 34, 46});
        shenxiao.put("8", new Integer[]{9, 21, 33, 45});
        shenxiao.put("7", new Integer[]{8, 20, 32, 44});
        shenxiao.put("6", new Integer[]{7, 19, 31, 43});
        shenxiao.put("5", new Integer[]{6, 18, 30, 42});
        shenxiao.put("4", new Integer[]{5, 17, 29, 41});
        shenxiao.put("3", new Integer[]{4, 16, 28, 40});
        shenxiao.put("2", new Integer[]{3, 15, 27, 39});
        shenxiao.put("1", new Integer[]{2, 14, 26, 38});
        shenxiao.put("0", new Integer[]{1, 13, 25, 37, 49});
    }

    public static Map<String, Integer[]> getShenxiao() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        year = 2019;
        //%12在再转换
        int birth = year % 12;
        int index = birth;
        int i = 0;
        Map<Integer, Integer[]> map = new HashMap<>();
        while (true) {
            map.put(birth, shenxiao.get(String.valueOf(i)));
            i--;
            birth++;
            if (birth == 12) {
                birth = 0;
            }
            if (i < 0) {
                i = 11;
            }
            System.out.println("birth" + birth);
            if (birth == index) {
                break;
            }

        }
        Map<String, Integer[]> result = new HashMap<>();
        map.forEach((k, v) -> {
            switch (k) {
                case 0:
                    result.put("hou", v);
                    System.out.println("猴年");
                    break;
                case 1:
                    result.put("ji", v);
                    System.out.println("鸡年");
                    break;
                case 2:
                    result.put("gou", v);
                    System.out.println("狗年");
                    break;
                case 3:
                    result.put("zhu", v);
                    System.out.println("猪年");
                    break;
                case 4:
                    result.put("shu", v);
                    System.out.println("鼠年");
                    break;
                case 5:
                    result.put("niu", v);
                    System.out.println("牛年");
                    break;
                case 6:
                    result.put("hu", v);
                    System.out.println("虎年");
                    break;
                case 7:
                    result.put("tu", v);
                    System.out.println("兔年");
                    break;
                case 8:
                    result.put("long", v);
                    System.out.println("龙年");
                    break;
                case 9:
                    result.put("she", v);
                    System.out.println("蛇年");
                    break;
                case 10:
                    result.put("ma", v);
                    System.out.println("马年");
                    break;
                case 11:
                    result.put("yan", v);
                    System.out.println("羊年");
                    break;
                default:
                    System.out.println("错误!请输入大于0的数");
            }
        });
        return result;
    }


    public static void main(String[] args) {
        System.out.println(getShenxiao());
    }
}
