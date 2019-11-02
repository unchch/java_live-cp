package com.bh.live.common.longDragon;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 香港彩统计
 * 1-49
 * ype  1: 特码热门  2：特码尾数热门  3：正码热门  4：生肖特码热门
 *      5：生肖正码热门  6：波色特码热门 7：波色正码热门
 */
public class HongKongCalc {
    /**
     * 彩种类型
     */
    private Integer type;

    private Integer startNum;

    private Integer endNum;

    public HongKongCalc(Integer type) {
        this.type = type;
        if (type == 1 || type == 3) {
            startNum = 1;
            endNum = 49;
        }else if(type == 2){
            startNum =0;
            endNum =9;
        }else if(type == 4){
            startNum =1;
            endNum =12;
        }
    }

    /**
     * 球号  ----  》 出现时的次数
     */
    public List<Map<Object, Integer>> hongKongMap = new LinkedList<>();

    public void init() {
        for (int i = startNum; i <= endNum; i++) {
            Map<Object,Integer> retMap = new LinkedHashMap<>();
            retMap.put(i,0);
            hongKongMap.add(retMap);
        }
    }

    public void execute(List<Integer> results) {
        for (int n = 0; n <= results.size()-1; n++) {
            int ballNum = results.get(n);
            Map<Object,Integer> map = hongKongMap.get(ballNum-1);
            if (map.containsKey(ballNum)) {
                map.put(ballNum,map.get(ballNum)+1);
            }
        }
    }

    public static void main(String[] args) {
    }
}
