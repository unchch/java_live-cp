package com.bh.live.common.longDragon;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时时彩 长龍统计
 */
public class SscShadeCalc {

    /**
     * 位置（第一名）对应6种形态的结果映射 < 位置 , <形态,连续次数>  >
     */
    public Map<Integer, Map<String, Integer>> sscLongDragonShadeMapping = new HashMap<>();

    public void init() {
        for (int i = 1; i <= 5; i++) {
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
            shadeMap.put("质", 0);
            shadeMap.put("合", 0);

            sscLongDragonShadeMapping.put(i, shadeMap);
        }
    }


    public void execute(List<Integer> results) {
        //总和
        Long sum = results.stream().mapToInt((s) -> s).summaryStatistics().getSum();
        for (int i = 1; i <= results.size(); i++) {
            //冠军
            int ballResult = results.get(i - 1);
            //最后一个球
            int lastBall = results.get(4);

            ResultShade resultShade = this.convertResult(ballResult, lastBall, sum, i);
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
            //质数
            String zs = resultShade.getZs();
            //合数
            String hs = resultShade.getHs();

            Map<String, Integer> shadeMap = sscLongDragonShadeMapping.get(i);

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
            int zhxContinueTimes = 0;
            if (zhbs != null && zhbs.equals("总和大")) {
                zhbContinueTimes = shadeMap.get(zhbs);
                zhbContinueTimes++;
                zhxContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(zhbs)) {
                    zhxContinueTimes = shadeMap.get(zhbs);
                    zhxContinueTimes++;
                    zhbContinueTimes = 0;
                }
            }

            shadeMap.put("总和大", zhbContinueTimes);
            shadeMap.put("总和小", zhxContinueTimes);

            //总和单
            int zhdContinueTimes = 0;
            //总和双
            int zhsContinueTimes = 0;
            if (zhds != null && zhds.equals("总和单")) {
                zhdContinueTimes = shadeMap.get(zhds);
                zhdContinueTimes++;
                zhsContinueTimes = 0;
            } else {
                if (shadeMap.containsKey(zhds)) {
                    zhsContinueTimes = shadeMap.get(zhds);
                    zhsContinueTimes++;
                    zhdContinueTimes = 0;
                }
            }

            shadeMap.put("总和单", zhdContinueTimes);
            shadeMap.put("总和双", zhsContinueTimes);


            int zsContinueTimes = 0;
            int hsContinueTimes = 0;
            if (zs != null && zs.equals("质")) {
                zsContinueTimes = shadeMap.get(zs);
                zsContinueTimes++;
                hsContinueTimes = 0;
            } else {
                hsContinueTimes = shadeMap.get(hs);
                hsContinueTimes++;
                zsContinueTimes = 0;

            }
            shadeMap.put("质", zsContinueTimes);
            shadeMap.put("合", hsContinueTimes);

            sscLongDragonShadeMapping.put(i, shadeMap);

        }
    }

    public static void main(String[] args) {
    }

    /**
     * 获取单、双、龍、虎、总和单双，质数
     *
     * @param ballResult1 每组冠军位置号码
     * @param ballResult2 每组龍虎right比较值 1-10(right)  2-9  3-8   .........
     * @param sum         总和
     * @param index       位置索引
     * @return
     */
    private ResultShade convertResult(int ballResult1, int ballResult2, long sum, int index) {
        String bs = null;
        String ds = null;
        String lh = null;
        String zhbs = null;
        String zhds = null;
        String zs = null;
        String hs = null;
        if (ballResult1 % 2 == 0) {
            ds = "双";
        } else {
            ds = "单";
        }

        if (ballResult1 > 4) {
            bs = "大";
        } else {
            bs = "小";
        }

        if (index == 1) {
            if (ballResult1 > ballResult2) {
                lh = "龙";
            } else {
                lh = "虎";
            }
            if (sum >= 23) {
                zhbs = "总和大";
            } else {
                zhbs = "总和小";
            }

            if (sum % 2 == 0) {
                zhds = "总和双";
            } else {
                zhds = "总和单";
            }
        }
        //1、2、3、5、7为质，0、4、6、8、9为合
        if (ballResult1 == 1 || ballResult1 == 2 || ballResult1 == 3 || ballResult1 == 5 || ballResult1 == 7) {
            zs = "质";
        } else {
            hs = "合";
        }
        return new ResultShade(ballResult1, bs, lh, ds, zhds, zhbs, zs, hs,null,null,null);
    }
}
