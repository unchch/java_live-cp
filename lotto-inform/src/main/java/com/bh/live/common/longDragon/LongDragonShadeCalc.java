package com.bh.live.common.longDragon;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 长龍连开统计
 */
public class LongDragonShadeCalc {
    /**
     * 位置（第一名）对应6种形态的结果映射 < 位置 , <形态,连续次数>  >
     */
    public Map<Integer, Map<String, Integer>> location2ShadeMapping = new HashMap<>();


    public void init() {
        for (int i = 1; i <= 10; i++) {
            Map<String, Integer> shadeMap = new HashMap<>();
            shadeMap.put("大", 0);
            shadeMap.put("小", 0);
            shadeMap.put("单", 0);
            shadeMap.put("双", 0);
            shadeMap.put("龙", 0);
            shadeMap.put("虎", 0);
            shadeMap.put("亚冠和单", 0);
            shadeMap.put("亚冠和双", 0);
            shadeMap.put("亚冠和大", 0);
            shadeMap.put("亚冠和小", 0);

            location2ShadeMapping.put(i, shadeMap);
        }
    }


    public void execute(List<Integer> results) {
        for (int i = 1; i <= results.size(); i++) {
            //冠军
            int ballResult = results.get(i - 1);
            int twoBall = 0;
            int targetBallResult = 0;
            if (i <= 5) {
                //亚军
                twoBall = results.get(i);
                //对应龍虎位置比较的数据
                targetBallResult = results.get(results.size() - i);
            }
            ResultShade resultShade = this.convertResult(ballResult, targetBallResult, twoBall, i);
            String bs = resultShade.getBs();
            String ds = resultShade.getDs();
            String lh = resultShade.getLh();
            String yghbs = resultShade.getZhBigSmall();
            String yghds = resultShade.getZhSingleDouble();

            Map<String, Integer> shadeMap = location2ShadeMapping.get(i);

            int bigContinueTimes = 0;
            int smallContinueTimes = 0;
            if (bs.equals("大")) {
                bigContinueTimes = shadeMap.get(bs);
                bigContinueTimes++;
                smallContinueTimes = 0;
            } else {
                smallContinueTimes = shadeMap.get(bs);
                smallContinueTimes++;
                bigContinueTimes = 0;
            }

            shadeMap.put("大", bigContinueTimes);
            shadeMap.put("小", smallContinueTimes);


            int dContinueTimes = 0;
            int sContinueTimes = 0;
            if (ds.equals("单")) {
                dContinueTimes = shadeMap.get(ds);
                dContinueTimes++;
                sContinueTimes = 0;
            } else {
                sContinueTimes = shadeMap.get(ds);
                sContinueTimes++;
                dContinueTimes = 0;
            }

            shadeMap.put("单", dContinueTimes);
            shadeMap.put("双", sContinueTimes);

            int lContinueTimes = 0;
            int hContinueTimes = 0;
            if (lh != null && lh.equals("龙")) {
                lContinueTimes = shadeMap.get(lh);
                lContinueTimes++;
                hContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(lh)) {
                    hContinueTimes = shadeMap.get(lh);
                    hContinueTimes++;
                    lContinueTimes = 0;
                }
            }

            shadeMap.put("龙", lContinueTimes);
            shadeMap.put("虎", hContinueTimes);

            int yghbContinueTimes = 0;
            int yghsContinueTimes = 0;
            if (yghbs != null && yghbs.equals("亚冠和大")) {
                yghbContinueTimes = shadeMap.get(yghbs);
                yghbContinueTimes++;
                yghsContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(yghbs)) {
                    yghsContinueTimes = shadeMap.get(yghbs);
                    yghsContinueTimes++;
                    yghbContinueTimes = 0;
                }
            }

            shadeMap.put("亚冠和大", yghbContinueTimes);
            shadeMap.put("亚冠和小", yghsContinueTimes);


            int yghsingleContinueTimes = 0;
            int yghdContinueTimes = 0;
            if (yghds != null && yghds.equals("亚冠和单")) {
                yghsingleContinueTimes = shadeMap.get(yghds);
                yghsingleContinueTimes++;
                yghdContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(yghds)) {
                    yghdContinueTimes = shadeMap.get(yghds);
                    yghdContinueTimes++;
                    yghsingleContinueTimes = 0;
                }
            }

            shadeMap.put("亚冠和单", yghsingleContinueTimes);
            shadeMap.put("亚冠和双", yghdContinueTimes);

            location2ShadeMapping.put(i, shadeMap);

        }
    }

    public static void main(String[] args) {
    }

    /**
     * 获取单、双、龍、虎、亚冠和单双
     *
     * @param ballResult1 每组冠军位置号码
     * @param ballResult2 每组龍虎right比较值 1-10(right)  2-9  3-8   .........
     * @param twoBall     每组亚军位置号码
     * @param index       位置索引
     * @return
     */
    private ResultShade convertResult(int ballResult1, int ballResult2, int twoBall, int index) {
        String bs = null;
        String ds = null;
        String lh = null;
        String yghbs = null;
        String yghds = null;
        if (ballResult1 % 2 == 0) {
            ds = "双";
        } else {
            ds = "单";
        }

        if (ballResult1 > 5) {
            bs = "大";
        } else {
            bs = "小";
        }

        if (index <= 5) {
            if (ballResult1 > ballResult2) {
                lh = "龙";
            } else {
                lh = "虎";
            }
        }

        if (index == 1) {
            if ((ballResult1 + twoBall) > 11) {
                yghbs = "亚冠和大";
            } else {
                yghbs = "亚冠和小";
            }

            if ((ballResult1 + twoBall) % 2 == 0) {
                yghds = "亚冠和双";
            } else {
                yghds = "亚冠和单";
            }
        }
        return new ResultShade(ballResult1, bs, lh, ds, yghds, yghbs, null, null, null,null,null);
    }
}
