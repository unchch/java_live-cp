package com.bh.live.common.TrendUtils;

import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.StatPostionTrendDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;

import java.math.BigDecimal;
import java.util.*;

public class CalcOmit {


    /**
     * 获取最大遗漏 总出现和总遗漏
     * 1.获取指定期数B集合
     * 2.获取指定期数内为指定位置的指定号码的的A集合
     * 3.遍历A集合上一条数据和下一条数据做比较，获取最大遗漏
     * 4.判断A集合最后一条数据是不是B集合中最后一期的数据对应
     * 5.如果不是则继续查找下一条数据计算最大遗漏
     *
     * @param count    指定期数
     * @param position 指定位置 1：冠军 2：亚军 3 第三名 以此类推
     */
    public static Map<String, Object> calcOmit(int count, int position,
                                               List<ColorVarietyModelRes> resultCountVos,
                                               StatPostionTrendDao postionTrendDao, int start, int end, Integer variety) {
        // 结果集
        Map<String, Object> resultList = new HashMap<>();
        // 每次位置走势排序
        List<List<Integer>> ptDtoList = new ArrayList<>();
        // 总出现次数
        List<Integer> totalList = new ArrayList<>();
        // 最大遗漏次数
        List<Integer> totalOmitList = new ArrayList<>();
        //参数
        Map<String, Object> param = new HashMap<>();
        // 封装最大遗漏值
        int maxNum = 0;
        // 指定号码 1-10
        for (int j = start; j <= end; j++) {
            System.out.println("--------------------------号码" + j + "--------------------");
            param.put("ball", j);
            param.put("number", FormStatUtil.getNumberStr(position));
            param.put("count", count);
            param.put("variety", variety);
            // 获取指定期数内指定位置指定好的的A集合
            List<ColorVarietyModelRes> ballRsult = postionTrendDao.queryPk10BallRsult(param);
            // 集合中第一条数据的期号
            String expect = resultCountVos.get(0).getExpect();
            BigDecimal FristExpect = new BigDecimal(expect);
            // 指定期数内最后一条数据的期号
            String expect1 = resultCountVos.get(resultCountVos.size() - 1).getExpect();
            BigDecimal Endexpect = new BigDecimal(expect1);
            param.put("expect", expect1);
            // 从下一个区间找数据
            String nextExpect = postionTrendDao.getNextResult(param);
            System.out.println("A集合第一期：" + FristExpect + ",A集合中最后一期：" + Endexpect);
            // 总次数
            int total = ballRsult.size();
            // 最大遗漏次数
            int num = 0;
            // 每次遗漏排序数组
            List<Integer> evenOmitList = new ArrayList<>();

            for (int i = 0; i < ballRsult.size(); i++) {
                // 每期的遗漏次数
                int evenOmit = 0;
                // 获取当前期
                String expect2 = ballRsult.get(i).getExpect();
                BigDecimal currExpect = new BigDecimal(expect2);
                // 获取第一期与集合中的数据
                if (i == 0) {
/*
                    if (variety == 0 || variety == 2 || variety == 3 || variety == 4 || variety == 5 || variety == 7 || variety == 8 || variety == 9||variety == 10) {}
*/
                    if (variety==1){
                        evenOmit = FristExpect.subtract(currExpect).intValue();
                    }else {
                        evenOmit = subExpect(variety, expect, expect2);
                    }
                    num = addEvenOmitList(num, evenOmit + 1, evenOmitList, count);
                    System.out.println(FristExpect + "-" + currExpect + "=" + evenOmit);
                }
                if (i != ballRsult.size() - 1) {
                    // 获取上一期
                    ColorVarietyModelRes vo = ballRsult.get(i + 1);
                    if (vo != null) {
                        BigDecimal up = new BigDecimal(vo.getExpect());
                        if (variety==1){
                            evenOmit = currExpect.subtract(up).intValue();
                        }else {
                            evenOmit = subExpect(variety, expect2, vo.getExpect());
                        }

                        System.out.println(currExpect + "-" + up + "=" + evenOmit);
                    }
                } else {
                    // 继续找下一条
                    if (variety==1){
                        evenOmit = currExpect.subtract(new BigDecimal(nextExpect)).intValue();
                    }else {
                        evenOmit = subExpect(variety, expect2, nextExpect);
                    }
                    System.out.println("查找到下一期数据为：" + nextExpect);
                    System.out.println(currExpect + "-" + nextExpect + "=" + evenOmit);
                }
                num = addEvenOmitList(num, evenOmit, evenOmitList, count);
            }
            // 如果没有找到则需要继续往下找
            if (ballRsult.size() == 0) {
                System.out.println("没有找到任何数据则继续往下找");
                System.out.println("查找到下一期数据为：" + nextExpect);
                int sub = 0;
                /*if (variety == 0 || variety == 2 || variety == 3 || variety == 4 || variety == 5|| variety == 8 || variety == 9) { } */
                if (variety==1){
                    sub = FristExpect.subtract(new BigDecimal(nextExpect)).intValue();
                }else {
                    sub = subExpect(variety, expect, nextExpect);
                }
                System.out.println(FristExpect + "-" + nextExpect + "=" + sub);
                num = addEvenOmitList(num, sub + 1, evenOmitList, count);
            }
            if (num > maxNum) {
                maxNum = num;
            }
            ptDtoList.add(evenOmitList);
            System.out.println("号码：" + j + ",数组长度：" + evenOmitList.size());
            System.out.println("排序数组：" + evenOmitList);
            totalList.add(total);
            totalOmitList.add(num - 1);
            System.out.println("号码：" + j + ",总出现次数：" + total);
            System.out.println("号码：" + j + ",最大遗漏：" + num);
        }

        resultList.put("totalList", totalList);
        resultList.put("totalOmitList", totalOmitList);
        resultList.put("ptDtoList", ptDtoList);
        resultList.put("maxNum", maxNum);
        return resultList;
    }


    /**
     * 存放遗漏数据在集合中
     *
     * @param num
     * @param evenOmit
     * @param evenOmitList
     * @param count
     * @return
     */
    public static int addEvenOmitList(int num, int evenOmit, List<Integer> evenOmitList, int count) {
        if (evenOmit > num) {
            num = evenOmit;
        }
        // 循环遗漏次数生成顺序数组
        for (int k = evenOmit - 1; k >= 0; k--) {
            if (evenOmitList.size() < count) {
                evenOmitList.add(k);
                continue;
            }
            break;
        }
        return num;
    }

    /**
     * 根据类型获取两期相差值
     *
     * @param variety
     * @param c_expect 当前期号     20190704006
     * @param n_expect 下一期的期号  20190703175
     * @return
     */
    public static int subExpect(Integer variety, String c_expect, String n_expect) {
        int js = 0;
        switch (variety) {
            case 0:
                js = 180;
                break;
            case 2:
            case 5:
                js = 59;
                break;
            case 3:
                js = 90;
                break;
            case 4:
                js = 41;
                break;
            case 7:
                js = 48;
                break;
            case 8:
            case 9:
            case 10:
                js = 42;
                break;
        }
        String d1 = c_expect.substring(0, c_expect.length() - 3);
        int n1 = Integer.valueOf(c_expect.substring(c_expect.length() - 3));
        String d2 = n_expect.substring(0, n_expect.length() - 3);
        int n2 = Integer.valueOf(n_expect.substring(n_expect.length() - 3));
        Date date1 = DateUtils.dateTime("yyyyMMdd", d1);
        Date date2 = DateUtils.dateTime("yyyyMMdd", d2);
        int days = DateUtils.differentDays(date2, date1);
        int sub = (days * js + n1) - n2;
        return sub;
    }

    /**
     * 获得 期号集合
     *
     * @param resultCountVos
     * @return
     */
    public static List<String> getExpectList(List<ColorVarietyModelRes> resultCountVos) {
        List<String> expectList = new ArrayList<>();
        for (ColorVarietyModelRes vo : resultCountVos) {
            expectList.add(vo.getExpect());
        }
        return expectList;
    }
}
