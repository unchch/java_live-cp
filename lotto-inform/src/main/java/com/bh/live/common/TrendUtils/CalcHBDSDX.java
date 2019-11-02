package com.bh.live.common.TrendUtils;

import com.bh.live.pojo.res.inform.ColorVarietyModelRes;

import java.util.*;

import static com.bh.live.common.utils.FormStatUtil.getBall;

public class CalcHBDSDX {
    /**
     * 1、声明反向，正向，重号，单，双，大，小 : 坐标集合
     * 2、做判断依次把坐标存入数组
     * 3、根据坐标得到每次遗漏来生成遗漏集合
     * 4、得到总出现次数、最大遗漏、和遗漏集合
     * 回擺
     * 反嚮：當前期號碼比上一期號碼小；
     * 正嚮：當前期號碼比上一期號碼大；
     * 重號：當前期號碼與上期號碼相同
     *
     * @param position 指定位置
     * @param volist
     * @param type : 1:位置走势  2：亚冠和走势
     */
    public static Map<String, Object> getHB_DS_DX(int position, List<ColorVarietyModelRes> volist, int count, int type, Integer variety) {
       Map<String, Object> resultMap = new HashMap<>();
        // 声明 反向，正向，重号，单，双，大，小 : 坐标集合
        List<Integer> fxList = new ArrayList<>(), zxList = new ArrayList<>(),
                chList = new ArrayList<>(), oddList = new ArrayList<>(),evenList = new ArrayList<>(), bigList = new ArrayList<>(), smallList = new ArrayList<>(),beforeList = new ArrayList<>(), middleList = new ArrayList<>(), behindList = new ArrayList<>();
        int fx=0,zx=0,ch=0,odd=0,even=0,big=0,small=0,before=0,middle=0,behind=0;

        int size = volist.size();
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < volist.size(); i++) {
            ColorVarietyModelRes upVo = volist.get(i);
            all.add(getBall(position, upVo));
            if (i != volist.size() - 1) {
                ColorVarietyModelRes nextVo = volist.get(i + 1);
                Integer upBall = getBall(position, upVo);
                Integer nextBall = getBall(position, nextVo);
                if (upBall < nextBall) {
                    fxList.add(i);
                    if (i<count){
                        fx++;
                    }
                } else if (upBall > nextBall) {
                    zxList.add(i);
                    if (i<count){
                        zx++;
                    }
                } else if (upBall.equals(nextBall)) {
                    chList.add(i);
                    if (i<count){
                        ch++;
                    }
                }
                if (upBall % 2 == 0) {
                    evenList.add(i);
                    if (i<count){
                        even++;
                    }
                } else {
                    oddList.add(i);
                    if (i<count){
                        odd++;
                    }
                }
                if(type == 1){
                    int v=5;
                    // 重庆时时彩 // 圣地彩 //天津时时彩 //新疆时时彩
                    if (variety==2 ||variety==3 || variety==10 || variety == 7){
                        v=4;
                        // 幸运农场 // 广东快乐十分
                    }else if (variety==5 || variety == 8 ){
                        v=10;
                    }
                    if (upBall > v) {
                        if (i<count){
                            big++;
                        }
                        bigList.add(i);
                    } else {
                        if (i<count){
                            small++;
                        }
                        smallList.add(i);
                    }
                }

                if( type == 2){
                    int v=12;
                    // 快3
                    if (variety==4) {
                        v=11;
                    }
                    if (upBall >= v){
                        if (i<count){
                            big++;
                        }
                        bigList.add(i);
                    }else{
                        if (i<count){
                            small++;
                        }
                        smallList.add(i);
                    }
                }

                if( type == 2){
                    if (upBall <= 8) {
                        beforeList.add(i);
                        if(i<count){
                            before++;
                        }
                    } else if (upBall <= 14 && upBall > 8) {
                        middleList.add(i);
                        if(i<count){
                            middle++;
                        }
                    } else {
                        behindList.add(i);
                        if(i<count){
                            behind++;
                        }
                    }
                }
            }
        }
        System.out.println("所有值：" + all);
        System.out.println("反向" + fxList);
        System.out.println("重号" + chList);
        System.out.println("正向" + zxList);
        System.out.println("单" + oddList);
        System.out.println("双" + evenList);
        System.out.println("大" + bigList);
        System.out.println("小" + smallList);

        // 总出现次数
        List<Integer> totalCount = new ArrayList<>();
        totalCount.add(fx);
        totalCount.add(ch);
        totalCount.add(zx);
        totalCount.add(odd);
        totalCount.add(even);
        totalCount.add(big);
        totalCount.add(small);
        if(type == 2 && variety !=4){
            totalCount.add(before);
            totalCount.add(middle);
            totalCount.add(behind);
        }
        resultMap.put("totalCount", totalCount);

        // 反向，正向，重号，单，双，大，小 ,前，中，后: 遗漏集合 {总遗漏次数,遗漏集合}
        List<Integer> fxOmitList = eachList(fxList, size,count);
        List<Integer> chOmitList = eachList(chList, size,count);
        List<Integer> zxOmitList = eachList(zxList, size,count);
        List<Integer> oddOmitList = eachList(oddList, size,count);
        List<Integer> evenOmitList = eachList(evenList, size,count);
        List<Integer> bigOmitList = eachList(bigList, size,count);
        List<Integer> smallOmitList = eachList(smallList, size,count);
        List<Integer> beforeOmitList = null;
        List<Integer> middleOmitList = null;
        List<Integer> behindOmitList = null;
        if(type == 2 && variety !=4){
            beforeOmitList = eachList(beforeList, size,count);
            middleOmitList = eachList(middleList, size,count);
            behindOmitList = eachList(behindList, size,count);
        }

        // 总遗漏次数
        List<Integer> totalOmitCount = new ArrayList<>();
        totalOmitCount.add(fxOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(chOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(zxOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(oddOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(evenOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(bigOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(smallOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        if(type == 2 && variety !=4){
            totalOmitCount.add(beforeOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
            totalOmitCount.add(middleOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
            totalOmitCount.add(behindOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        }
        resultMap.put("totalOmitCount", totalOmitCount);

        // 正向，反向，重号，单，双，大，小 遗漏排序集合
        Map<String, List<Integer>> hb_ds_dx_map = new HashMap<>();
        hb_ds_dx_map.put("fxOmitList", fxOmitList);
        hb_ds_dx_map.put("chOmitList", chOmitList);
        hb_ds_dx_map.put("zxOmitList", zxOmitList);
        hb_ds_dx_map.put("oddOmitList", oddOmitList);
        hb_ds_dx_map.put("evenOmitList", evenOmitList);
        hb_ds_dx_map.put("bigOmitList", bigOmitList);
        hb_ds_dx_map.put("smallOmitList", smallOmitList);

        if(type == 2 && variety !=4){
            hb_ds_dx_map.put("beforeOmitList", beforeOmitList);
            hb_ds_dx_map.put("middleOmitList", middleOmitList);
            hb_ds_dx_map.put("behindOmitList", behindOmitList);
        }

        resultMap.put("hb_ds_dx_map", hb_ds_dx_map);

        return resultMap;

    }

    /**
     * 获取遗漏集合数据 和最大遗漏
     *
     * @param list
     * @param count 遍历总数
     * @return
     */
    public static List<Integer> eachList(List<Integer> list, int size,int count) {
        int omitNum = 0;
        List<Integer> numList = new ArrayList<>();
        Integer index = list.get(0);
        for (int k =index; k >= 0; k--) {
            if (index>omitNum){
                omitNum=index;
            }
            numList.add(k);
        }
        for (int i = 0; i < list.size(); i++) {
            Integer curr = list.get(i);
            // 存入数组的次数
            Integer eachCount = 0;
            if (i != list.size() - 1) {
                Integer next = list.get(i + 1);
                int sub = next - curr;
                // 得到指定期数内最大遗漏次数
                if (next<count){
                    if (sub > omitNum) {
                        omitNum = sub;
                        System.out.println(i+"，遗漏次数是："+omitNum);
                    }
                }
                eachCount = sub - 1;
            } else {
                // 如果已经是最后一条，则继续拼接数据
                eachCount = size - 1 - curr - 1;
            }
            // 循环遗漏次数生成顺序数组
            for (int k = eachCount; k >= 0; k--) {
                if (numList.size() < count) {
                    numList.add(k);
                    continue;
                }
                break;
            }
        }
        System.out.println("*****************************************************:"+numList);
        return numList;
    }

}
