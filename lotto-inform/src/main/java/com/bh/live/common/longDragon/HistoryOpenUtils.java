package com.bh.live.common.longDragon;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *时时彩 位置统计
 */
public class HistoryOpenUtils {

    /**
     * 每个球 对应 5个位置 结果映射 < 球号 , <位置,出现次数>  >
     */
    public Map<Integer, Map<Integer, Integer>> historyMapping = new HashMap<>();

    public void init() {
        for (int i = 0; i <= 9; i++) {
            Map<Integer, Integer> shadeMap = new HashMap<>();
            shadeMap.put(1, 0);
            shadeMap.put(2, 0);
            shadeMap.put(3, 0);
            shadeMap.put(4, 0);
            shadeMap.put(5, 0);
            historyMapping.put(i, shadeMap);
        }
    }

    public void execute(List<Integer> results) {
        for (int i = 1; i <= results.size(); i++) {
            //第一个球
            int ballResult = results.get(i - 1);
            Map<Integer, Integer> shadeMap = historyMapping.get(ballResult);
            int count = shadeMap.get(i);
            count++;
            shadeMap.put(i, count);
            historyMapping.put(ballResult, shadeMap);
        }
    }

    public static void main(String[] args) {
    }
}
