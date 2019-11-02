package com.bh.live.common.longDragon;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 幸运农场 长龍连开统计
 */
public class LuckyFarmShadeCalc {
    /**
     * 位置（第一名）对应6种形态的结果映射 < 位置 , <形态,连续次数>  >
     */
    public Map<Integer, Map<String, Integer>> LuckyFarmShadeMapping = new HashMap<>();


    public void init() {
        for (int i = 1; i <= 8; i++) {
            Map<String, Integer> shadeMap = new HashMap<>();
            shadeMap.put("大", 0);
            shadeMap.put("小", 0);
            shadeMap.put("单", 0);
            shadeMap.put("双", 0);
            shadeMap.put("龙", 0);
            shadeMap.put("虎", 0);
            shadeMap.put("总和单", 0);
            shadeMap.put("总和双", 0);
            shadeMap.put("总和大", 0);
            shadeMap.put("总和小", 0);
            shadeMap.put("总和和", 0);
            shadeMap.put("总和尾大", 0);
            shadeMap.put("总和尾小", 0);
            shadeMap.put("合单", 0);
            shadeMap.put("合双", 0);
            shadeMap.put("尾大", 0);
            shadeMap.put("尾小", 0);

            LuckyFarmShadeMapping.put(i, shadeMap);
        }
    }

    public void execute(List<Integer> results) {
        Long sum = results.stream().collect(Collectors.summarizingInt(value -> value)).getSum();
        for (int i = 1; i <= results.size(); i++) {
            //冠军
            int ballResult = results.get(i - 1);
            int targetBallResult = 0;
            if (i <= 4) {
                //对应龍虎位置比较的数据
                targetBallResult = results.get(results.size() - i);
            }
            ResultShade resultShade = this.convertResult(ballResult, targetBallResult, i, sum);
            //大小
            String bs = resultShade.getBs();
            //单双
            String ds = resultShade.getDs();
            //龍虎
            String lh = resultShade.getLh();
            //总和大小
            String zhbs = resultShade.getZhBigSmall();
            //总和单双
            String zhds = resultShade.getZhSingleDouble();
            //和 尾号大小
            String tailNumber = resultShade.getTailNumber();
            //和单、和双
            String hSd = resultShade.getHSingleDouble();
            // 尾大小 0-4 小  5-9 大
            String wBs = resultShade.getWBigSmall();

            Map<String, Integer> shadeMap = LuckyFarmShadeMapping.get(i);

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

            int zhbContinueTimes = 0;
            int zhsContinueTimes = 0;
            int zhhContinueTimes = 0;
            if (zhbs != null && zhbs.equals("总和大")) {
                zhbContinueTimes = shadeMap.get(zhbs);
                zhbContinueTimes++;
                zhsContinueTimes = 0;
                zhhContinueTimes = 0;
            } else if (zhbs != null && zhbs.equals("总和小")) {
                if (shadeMap.containsKey(zhbs)) {
                    zhsContinueTimes = shadeMap.get(zhbs);
                    zhsContinueTimes++;
                    zhbContinueTimes = 0;
                    zhhContinueTimes = 0;
                }
            } else {
                if (shadeMap.containsKey(zhbs)) {
                    zhhContinueTimes = shadeMap.get(zhbs);
                    zhhContinueTimes++;
                    zhbContinueTimes = 0;
                    zhsContinueTimes = 0;
                }
            }

            shadeMap.put("总和大", zhbContinueTimes);
            shadeMap.put("总和小", zhsContinueTimes);
            shadeMap.put("总和和", zhhContinueTimes);


            int zhsingleContinueTimes = 0;
            int zhdContinueTimes = 0;
            if (zhds != null && zhds.equals("总和单")) {
                zhsingleContinueTimes = shadeMap.get(zhds);
                zhsingleContinueTimes++;
                zhdContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(zhds)) {
                    zhdContinueTimes = shadeMap.get(zhds);
                    zhdContinueTimes++;
                    zhsingleContinueTimes = 0;
                }
            }

            shadeMap.put("总和单", zhsingleContinueTimes);
            shadeMap.put("总和双", zhdContinueTimes);

            int tailNumBContinueTimes = 0;
            int tailNumSContinueTimes = 0;
            if (tailNumber != null && tailNumber.equals("总和尾大")) {
                tailNumBContinueTimes = shadeMap.get(tailNumber);
                tailNumBContinueTimes++;
                tailNumSContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(tailNumber)) {
                    tailNumSContinueTimes = shadeMap.get(tailNumber);
                    tailNumSContinueTimes++;
                    tailNumBContinueTimes = 0;
                }
            }

            shadeMap.put("总和尾大", tailNumBContinueTimes);
            shadeMap.put("总和尾小", tailNumSContinueTimes);


            //和单
            int hsContinueTimes = 0;
            //和双
            int hdContinueTimes = 0;
            if (hSd != null && hSd.equals("合单")) {
                hsContinueTimes = shadeMap.get(hSd);
                hsContinueTimes++;
                hdContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(hSd)) {
                    hdContinueTimes = shadeMap.get(hSd);
                    hdContinueTimes++;
                    hsContinueTimes = 0;
                }
            }

            shadeMap.put("合单", hsContinueTimes);
            shadeMap.put("合双", hdContinueTimes);


            //尾大
            int wbContinueTimes = 0;
            //尾小
            int wsContinueTimes = 0;
            if (wBs != null && wBs.equals("尾大")) {
                wbContinueTimes = shadeMap.get(wBs);
                wbContinueTimes++;
                wsContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(wBs)) {
                    wsContinueTimes = shadeMap.get(wBs);
                    wsContinueTimes++;
                    wbContinueTimes = 0;
                }
            }

            shadeMap.put("尾大", wbContinueTimes);
            shadeMap.put("尾小", wsContinueTimes);

            LuckyFarmShadeMapping.put(i, shadeMap);

        }
    }

    public static void main(String[] args) {
    }

    /**
     * 获取单、双、龍、虎、总和单双
     *
     * @param ballResult1 每组冠军位置号码
     * @param ballResult2 每组龍虎right比较值 1-10(right)  2-9  3-8   .........
     * @param index       位置索引
     * @param sum         数字总和  85<=sum<=132 总和大   32<=sum<=83 总和大小   sum = 84 总和和
     * @return
     */
    private ResultShade convertResult(int ballResult1, int ballResult2, int index, long sum) {
        String bs = null;
        String ds = null;
        String lh = null;
        String zhbs = null;
        String zhds = null;
        String tailNum = null;
        String hSingleDouble = null;
        String wBigSmall =null;
        if (ballResult1 % 2 == 0) {
            ds = "双";
        } else {
            ds = "单";
        }

        if (ballResult1 >= 10) {
            bs = "大";
        } else {
            bs = "小";
        }

        if (index <= 4) {
            if (ballResult1 > ballResult2) {
                lh = "龙";
            } else {
                lh = "虎";
            }
        }

        if (index == 1) {
            if (sum >= 85 && sum <= 132) {
                zhbs = "总和大";
            } else if (sum >= 36 && sum <= 83) {
                zhbs = "总和小";
            } else if (sum == 84) {
                zhbs = "总和和";
            }

            if (sum % 2 == 0) {
                zhds = "总和双";
            } else {
                zhds = "总和单";
            }
            if (sum % 10 >= 5) {
                tailNum = "总和尾大";
            } else {
                tailNum = "总和尾小";
            }
        }

        if (getSum(ballResult1)%2 ==0) {
              hSingleDouble ="合双";
        }else{
              hSingleDouble ="合单";
        }

        if (ballResult1 % 10 >=0 && ballResult1 % 10 <=4) {
            wBigSmall ="尾小";
        }else{
            wBigSmall ="尾大";
        }


        return new ResultShade(ballResult1, bs, lh, ds, zhds, zhbs, null, null, tailNum, hSingleDouble, wBigSmall);
    }

    /**
     * 求和
     * @param ball
     * @return
     */
    public int getSum(int ball) {
        int result = 0;
        while (ball != 0) {
            result += ball % 10;
            ball /= 10;
        }
        return result;
    }
}
