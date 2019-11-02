package com.bh.live.common.longDragon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 今日号码 --》 未开 统计
 * 1-10号码 1-10号位置 最近一次开奖与当前最新一期的间隔数
 */
public class TimeCalc {
    /**
     * 彩种类型
     */
    private Integer varietyType;

    private Integer startNum;

    private Integer endNum;

    public TimeCalc(Integer varietyType) {
        this.varietyType = varietyType;
        if(varietyType ==0 || varietyType ==1){
            this.startNum = 1;
            this.endNum = 10;
        }else if(varietyType == 2 || varietyType ==7 || varietyType==10){
            this.startNum = 1;
            this.endNum = 5;
        }else if(varietyType == 5){
            this.startNum = 1;
            this.endNum = 8;
        }
    }

    /**
     * 位置 -------》 球号  ----  》 最近出现时的期数
     */
    public Map<Integer, Map<Integer, Long>> locationShadeMapping = new HashMap<>();

    public void init() {
        for (int i = startNum; i <= endNum; i++) {
            Map<Integer, Long> shadeMap = new HashMap<>();
            if (varietyType == 0 || varietyType == 1) {
                shadeMap.put(10, null);
            } else if (varietyType == 2 || varietyType == 7 || varietyType == 10) {
                shadeMap.put(0, null);
            } else if (varietyType == 5) {
                shadeMap.put(10, null);
                shadeMap.put(11, null);
                shadeMap.put(12, null);
                shadeMap.put(13, null);
                shadeMap.put(14, null);
                shadeMap.put(15, null);
                shadeMap.put(16, null);
                shadeMap.put(17, null);
                shadeMap.put(18, null);
                shadeMap.put(19, null);
                shadeMap.put(20, null);
            }
            shadeMap.put(1, null);
            shadeMap.put(2, null);
            shadeMap.put(3, null);
            shadeMap.put(4, null);
            shadeMap.put(5, null);
            shadeMap.put(6, null);
            shadeMap.put(7, null);
            shadeMap.put(8, null);
            shadeMap.put(9, null);
            locationShadeMapping.put(i, shadeMap);
        }
    }

    public void execute(Map<List<Integer>, Long> results) {
        Long except = null;
        for (Map.Entry<List<Integer>, Long> entry : results.entrySet()) {
            except = entry.getValue();
            List<Integer> list = entry.getKey();
            for (int i = 1; i <= list.size(); i++) {
                int ballNum = list.get(i - 1);
                Shade resultShade = this.convertResult(ballNum);
                Integer resultBallNum = resultShade.getBallNum();
                Map<Integer, Long> shadeMap = locationShadeMapping.get(i);
                shadeMap.put(resultBallNum, except);
                locationShadeMapping.put(i, shadeMap);
            }
        }
    }

    public static void main(String[] args) {

//        TimeCalc calc = new TimeCalc();
//        calc.init();
//
//        Map<List<Integer>,Long> list1 = new HashMap<>();
//
//        calc.execute(list1);
//
//        for (Map.Entry<Integer, Map<Integer, Long>> entry : calc.locationShadeMapping.entrySet()) {
//            System.out.println("位置：" + entry.getKey());
//            for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet()) {
//                System.out.println("\t球号：" + entry2.getKey() + "，出现期数：" + entry2.getValue());
//            }
//        }
    }

    /**
     * @param ballResult1 球号
     * @return
     */
    private Shade convertResult(int ballResult1) {
        return new Shade(ballResult1, 0);
    }
}
