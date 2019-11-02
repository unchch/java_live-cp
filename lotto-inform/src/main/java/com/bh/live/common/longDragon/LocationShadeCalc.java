package com.bh.live.common.longDragon;


import com.bh.live.common.enums.VarietyTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationShadeCalc {

    /**
     * 彩种类型
     */
    private Integer varietyType;

    private Integer startNum;

    private Integer endNum;

    public LocationShadeCalc(Integer varietyType) {
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
     * 位置 -------》 球号  ----  》 出现次数
     */
    public Map<Integer, Map<Integer, Integer>> locationShadeMapping = new HashMap<>();

    public void init() {
        for (int i = startNum; i <= endNum; i++) {
            Map<Integer, Integer> shadeMap = new HashMap<>();
            if (varietyType == 1 || varietyType == 0) {
                shadeMap.put(10, 0);
            } else if (varietyType == VarietyTypeEnum.get(2).getCode()
                    || varietyType == VarietyTypeEnum.get(7).getCode()
                    || varietyType == VarietyTypeEnum.get(10).getCode()) {
                shadeMap.put(0, 0);
            }else if (varietyType == VarietyTypeEnum.get(9).getCode()) {
                shadeMap.put(10, 0);
                shadeMap.put(11, 0);
            }else if (varietyType == VarietyTypeEnum.get(5).getCode()) {
                shadeMap.put(10, 0);
                shadeMap.put(11, 0);
                shadeMap.put(12, 0);
                shadeMap.put(13, 0);
                shadeMap.put(14, 0);
                shadeMap.put(15, 0);
                shadeMap.put(16, 0);
                shadeMap.put(17, 0);
                shadeMap.put(18, 0);
                shadeMap.put(19, 0);
                shadeMap.put(20, 0);
            }
            shadeMap.put(1, 0);
            shadeMap.put(2, 0);
            shadeMap.put(3, 0);
            shadeMap.put(4, 0);
            shadeMap.put(5, 0);
            shadeMap.put(6, 0);
            shadeMap.put(7, 0);
            shadeMap.put(8, 0);
            shadeMap.put(9, 0);
            locationShadeMapping.put(i, shadeMap);
        }
    }

    public void execute(List<Integer> results) {
        for (int i = 1; i <= results.size(); i++) {
            int ballNum = results.get(i - 1);
            Shade resultShade = this.convertResult(ballNum);
            Integer resultBallNum = resultShade.getBallNum();
            Map<Integer, Integer> shadeMap = locationShadeMapping.get(i);

            int time = 0;
            if (resultBallNum == ballNum) {
                time = shadeMap.get(resultBallNum);
                time++;
            }
            shadeMap.put(resultBallNum, time);

            locationShadeMapping.put(i, shadeMap);
        }
    }


    /**
     * @param ballResult1 球号
     * @return
     */
    private Shade convertResult(int ballResult1) {
        return new Shade(ballResult1, 0);
    }
}
