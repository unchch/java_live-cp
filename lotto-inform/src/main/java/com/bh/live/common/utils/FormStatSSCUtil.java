package com.bh.live.common.utils;


import com.bh.live.pojo.res.inform.OpenStatisticsRes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 重庆时时彩开号统计的形态统计
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
public class FormStatSSCUtil {

    /**
     * 获取第一次匹配成功的最后坐标
     */
    private static int findIndex = 0;
    /**
     * 匹配成黄色块
     */
    private static String bgColor = "yellow";

    public static void formStatAll(List<List<List<OpenStatisticsRes>>> rightList_2) {
        findIndex = 0;
        for (List<List<OpenStatisticsRes>> list : rightList_2) {
            // 首次形态
            formStatFrist(list);
            // 跳号和重复
            JumpNum(isRepeat(list));

        }
        // 排除已经生成的形态
        for (int i = findIndex; i < rightList_2.size(); i++) {
            // 二次形态,还没完成，可删
            // formStatTwo(rightList_2.get(i));
        }
    }

    /**
     * 0        73    8    88
     * 6        9     1    18
     * 22                  2
     * 03                  74
     * 6                   4
     * 先匹配 第一列和第二列 的形态
     * 再根据形态去匹配数据
     * 从0 角标开始匹配
     *
     * @param rightList
     */
    public static void formStatFrist(List<List<OpenStatisticsRes>> rightList) {
        List<OpenStatisticsRes> ssList = new ArrayList<>();
        String xt = null;
        // 循环获取第一列的值和第二列比较形态
        W:
        for (int i = 0; i < rightList.get(0).size(); i++) {
            OpenStatisticsRes dto = rightList.get(0).get(i);
            for (int j = 0; j < rightList.get(1).size(); j++) {
                OpenStatisticsRes d = rightList.get(1).get(i);
                // 判断，大小单双形态
                if (d.getSingleDouble().equals(dto.getSingleDouble())) {
                    xt = dto.getSingleDouble();
                } else if (d.getBigSmall().equals(dto.getBigSmall())) {
                    xt = dto.getBigSmall();
                }
                if (xt != null) {
                    break W;
                }
            }
        }
        // 有形态的情况下再去匹配下一个
        match(xt, rightList, ssList);
    }

    /**
     * 用于测试，可删除
     * @param args
     */
    public static void main(String[] args) {
        List<List<OpenStatisticsRes>> rightList = new ArrayList<>();
        List<OpenStatisticsRes> list1 = new ArrayList<>();
        list1.add(new OpenStatisticsRes(7));
        list1.add(new OpenStatisticsRes(3));
        List<OpenStatisticsRes> list2 = new ArrayList<>();
        list2.add(new OpenStatisticsRes(2));
        List<OpenStatisticsRes> list3 = new ArrayList<>();
        list3.add(new OpenStatisticsRes(3));
        list3.add(new OpenStatisticsRes(4));
        List<OpenStatisticsRes> list4 = new ArrayList<>();
        list4.add(new OpenStatisticsRes(8));
        List<OpenStatisticsRes> list5 = new ArrayList<>();
        list5.add(new OpenStatisticsRes(7));
        list4.add(new OpenStatisticsRes(3));
        List<OpenStatisticsRes> list6 = new ArrayList<>();
        list6.add(new OpenStatisticsRes(7));
        List<OpenStatisticsRes> list7 = new ArrayList<>();
        list7.add(new OpenStatisticsRes(5));
        list7.add(new OpenStatisticsRes(3));
        List<OpenStatisticsRes> list8 = new ArrayList<>();
        list8.add(new OpenStatisticsRes(5));
        list8.add(new OpenStatisticsRes(2));
        List<OpenStatisticsRes> list9 = new ArrayList<>();
        list9.add(new OpenStatisticsRes(6));
        List<OpenStatisticsRes> list10 = new ArrayList<>();
        list10.add(new OpenStatisticsRes(0));
        rightList.add(list1);
        rightList.add(list2);
        rightList.add(list3);
        rightList.add(list4);
        rightList.add(list5);
        rightList.add(list6);
        rightList.add(list7);
        rightList.add(list8);
        rightList.add(list9);
        rightList.add(list10);
        formStatTwo(rightList);

    }

    /**
     * 二次形态
     * 循环去找形态，直到找到为止
     */
    public static void formStatTwo(List<List<OpenStatisticsRes>> rightList) {
        List<OpenStatisticsRes> ssList = new ArrayList<>();
        String xt1 = null, xt2 = null;
        int flag = 0;
        // 循环获取5个以上相同形态的形态值
        M:
        for (int k = 0; k < rightList.size(); k++) {
            W:
            for (int i = 0; i < rightList.get(k).size(); i++) {
                String c_xt1 = null, c_xt2 = null;
                OpenStatisticsRes dto = rightList.get(k).get(i);
                if (rightList.get(k + 1) != null) {
                    for (int j = 0; j < rightList.get(k + 1).size(); j++) {
                        OpenStatisticsRes d = rightList.get(k + 1).get(j);
                        // 判断，大小单双形态,可能会同时存在
                        if (d.getSingleDouble().equals(dto.getSingleDouble())) {
                            c_xt1 = dto.getSingleDouble();
                            if (xt1 == null) {
                                xt1 = c_xt1;
                            }
                        }
                        if (d.getBigSmall().equals(dto.getBigSmall())) {
                            c_xt2 = dto.getBigSmall();
                            if (xt2 == null) {
                                xt2 = c_xt2;
                            }
                        }
                        if (c_xt1 != null || c_xt2 != null) {
                            // 把第一次匹配到的形态复制给xt，用于匹配是否是连续5个同样的形态
                            flag++;
                            if (flag >= 5) {
                                break M;
                            }
                            break;
                        }
                        break;
                    }
                }

            }
        }
        // 有形态的情况下再去匹配下一个
        match(xt1, rightList, ssList);

    }

    /**
     * 匹配首位开始2位以上的重复数字
     * 73
     * 23
     * 34
     * 0
     * 6
     *
     * @param lists
     * @return
     */
    private static List<List<OpenStatisticsRes>> isRepeat(List<List<OpenStatisticsRes>> lists) {
        // 获取第一列的值
        List<OpenStatisticsRes> dtos = lists.get(0);
        for (int k = 0; k < dtos.size(); k++) {
            Integer curr_num = dtos.get(k).getNumber();
            if ((k + 1) < lists.size()) {
                boolean f = false;
                // 获取下一列的值
                W:
                for (int j = 1; j < lists.size(); j++) {
                    int count = 0;
                    List<OpenStatisticsRes> dtoList = lists.get(j);
                    for (int i = 0; i < dtoList.size(); i++) {
                        Integer next_num = dtoList.get(i).getNumber();
                        if (curr_num.equals(next_num)) {
                            count++;
                            // 设置颜色
                            dtos.get(k).setBgcolor(bgColor);
                            dtoList.get(i).setBgcolor(bgColor);
                        }
                    }
                    // 如果没有匹配则直接跳出
                    if (count == 0 ||  k < dtos.size() - 1) {
                        break W;
                    }
                }
            }
        }
        return lists;
    }

    /**
     * 匹配跳号
     * 73
     * 2
     * 34
     * 0
     * 6
     *
     * @param lists
     * @return
     */
    private static List<List<OpenStatisticsRes>> JumpNum(List<List<OpenStatisticsRes>> lists) {
        // 匹配值的坐标
        int findIndex=-1;
        // 获取第一列的值
        for (int o = 0; o < 2; o++) {
            List<OpenStatisticsRes> dtos = lists.get(o);
            for (int k = 0; k < dtos.size(); k++) {
                Integer curr_num = dtos.get(k).getNumber();
                if ((k + 2) < lists.size()) {
                    boolean f = false;
                    // 获取下一列的值
                    W:
                    for (int j =o + 2; j < lists.size(); j++) {
                        int count = 0;
                        List<OpenStatisticsRes> dtoList = lists.get(j);
                        for (int i = 0; i < dtoList.size(); i++) {
                            Integer next_num = dtoList.get(i).getNumber();
                            if (curr_num.equals(next_num)) {
                                count++;
                            }
                        }
                        // 如果没有匹配则直接跳出
                        if (count == 0 ||  k < dtos.size() - 1) {
                            break W;
                        }else {
                            if (j>findIndex){
                                findIndex=j;
                            }
                        }
                    }
                }
            }
        }
        // 设置黄色背景颜色
        for (int i = 0; i <= findIndex; i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                lists.get(i).get(j).setBgcolor(bgColor);
            }
        }
        return lists;
    }

    /**
     * 根据获取到的形态匹配相同形态的对象来修改颜色
     *
     * @param xt
     * @param rightList
     * @param ssList
     */
    private static void match(String xt, List<List<OpenStatisticsRes>> rightList, List<OpenStatisticsRes> ssList) {
        if (xt != null) {
            for (int i = 0; i < rightList.size(); i++) {
                boolean flag = false;
                // 获取接下来列的数据
                List<OpenStatisticsRes> dtoList = rightList.get(i);
                for (OpenStatisticsRes dto : dtoList) {
                    if (xt.equals(dto.getSingleDouble()) || xt.equals(dto.getBigSmall())) {
                        ssList.add(dto);
                        flag = true;
                    }
                }
                // 如果没有匹配则直接跳出
                if (!flag) {
                    break;
                }
                findIndex++;
            }
            String cs = null;
            if (findIndex == 2) {
                cs = "y_blue";
            } else if (findIndex == 3) {
                cs = "y_green";
            } else if (findIndex > 3) {
                cs = "y_red";
            }
            for (OpenStatisticsRes dto : ssList) {
                dto.setColor(cs);
            }
        }
    }


}
