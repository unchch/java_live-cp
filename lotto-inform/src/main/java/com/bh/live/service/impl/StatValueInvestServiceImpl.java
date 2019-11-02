package com.bh.live.service.impl;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.common.utils.Permutation;
import com.bh.live.dao.StatValueInvestDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.pojo.res.inform.ValueInvestRep;
import com.bh.live.service.StatValueInvestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 投资价值
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/19 17:56
 */
@Service
public class StatValueInvestServiceImpl implements StatValueInvestService {

    private static final String[] NUMBER;

    static {
        NUMBER = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    }

    @Resource
    private StatValueInvestDao valueInvestDao;


    /**
     * 获取集合数据
     *
     * @param type     0表示全部   类型 单双，大小，龙虎，任选1，任选2，任选3，任选4，任选5，任选6，任选7，任选8，
     * @param position 0表示全部 排名位置 1~10 第一名到第十名 11：冠亚和
     * @param count    期数 100期 200期 500期 1000期
     * @return
     */
    @Override
    public List<ValueInvestRep> queryValueInvest(int type, int position, int count, int variety) {
        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        param.put("position", position);
        param.put("count", count);
        param.put("number", FormStatUtil.getNumberStr(position));
        param.put("variety", variety);
        List<ValueInvestRep> valueInvestList = new ArrayList<>();
        // 任选2~8
        if (type > 4) {
            valueInvestList = getOptionalTwoToEight(param, valueInvestList, position, type, variety);
        }
        // 任选1
        if (type == 4) {
            valueInvestList = getOptionalOne(param, valueInvestList, position);
        }
        // 单双，大小，龙虎
        if (type < 4) {
            valueInvestList = getDs_Dx_Lh(param, valueInvestList, position, type, variety);
        }
        // 打乱集合的排序
        Collections.shuffle(valueInvestList);

        return valueInvestList;
    }

    /**
     * 单双，大小，龙虎
     *
     * @param param
     * @param valueInvestList
     * @param position
     * @param type
     * @return
     */
    public List<ValueInvestRep> getDs_Dx_Lh(Map<String, Object> param, List<ValueInvestRep> valueInvestList, int position, int type, int variety) {
        // 根据类型拼接条件
        conditionStr(param, type, position);
        // 获取单双出现的次数0:单,大,龙 1：双,小,虎
        if (position == 0) {
            int start = 1, end = 11;
            if (type == 3) {
                start = 12;
                end = 16;
            }
            for (int i = start; i <= end; i++) {
                param.put("number", FormStatUtil.getNumberStr(i));
                param.put("position", i);
                param.put("variety", variety);
                List<ValueInvestRep> dtoList = getOddEven(param);
                valueInvestList.add(dtoList.get(0));
                valueInvestList.add(dtoList.get(1));
            }
        } else {
            valueInvestList = getOddEven(param);
        }
        return valueInvestList;
    }

    /**
     * 任选 1
     *
     * @param param
     * @param valueInvestList
     * @param position
     * @return
     */
    public List<ValueInvestRep> getOptionalOne(Map<String, Object> param, List<ValueInvestRep> valueInvestList, int position) {
        // 获取10个10以内不重复的单个数据
        int[] arr = CommonUtil.randomArray(1, 10, 10);
        param.put("list", arr);
        if (position == 0) {
            // 获取全部
            for (int j = 1; j <= 10; j++) {
                param.put("position", j);
                valueInvestList.addAll(getOptional(param, arr));
            }
        } else {
            valueInvestList = getOptional(param, arr);
        }
        return valueInvestList;
    }

    /**
     * 任选 2~ 8
     *
     * @param param
     * @param valueInvestList
     * @param position
     * @param type
     * @return
     */
    public List<ValueInvestRep> getOptionalTwoToEight(Map<String, Object> param, List<ValueInvestRep> valueInvestList, int position, int type, int variety) {
        // 根据类型获取对应的位数
        int[] c = getCount(type);
        // 获取指定位数所有组合的随机数
        List<String> permutation = Permutation.getPermutation(NUMBER, c[0]);
        int[] arr = CommonUtil.randomArray(0, permutation.size() - 1, c[1]);
        // 获取
        for (int i = 0; i < arr.length; i++) {
            List<Integer> ids = new ArrayList<>();
            String s = permutation.get(arr[i]);
            System.out.println(i + ",根据随机数获取的随机号码：" + s);
            String[] strings = s.split(",");
            for (String n : strings) {
                ids.add(Integer.valueOf(n));
            }
            param.put("ids", ids);
            param.put("list", new int[]{1});
            param.put("project", s);
            if (position == 0) {
                // 如果等于全部，则随机获取号码
                int[] nums = CommonUtil.randomArray(1, 10, 1);
                param.put("position", nums[0]);
            }
            valueInvestList.addAll(getOptional(param, arr));
        }
        return valueInvestList;
    }

    /**
     * 获取
     * 单双，大小，龙虎 同一个接口
     *
     * @param param
     * @return
     */
    public List<ValueInvestRep> getOddEven(Map<String, Object> param) {
        int count = (Integer) param.get("count");
        int t = (Integer) param.get("type");
        int p = (Integer) param.get("position");
        String[] ds_dx = new String[2];
        // 获取单双出现的次数
        String[] oddEvenCount = valueInvestDao.queryValueInvest(param).split(",");
        System.out.println("0：" + oddEvenCount[0] + "-- 1：" + oddEvenCount[1]);
        List<ColorVarietyModelRes> result = valueInvestDao.queryResult((Integer) param.get("count"), (Integer) param.get("variety"));
        List<ValueInvestRep> oddEvenList = new ArrayList<>();
        String position = getPositonStr(p);
        String type = getTypeStr(t);
        getProjectStr(ds_dx, t);
        // 获取当日遗漏和最大遗漏
        int[] omit = getOmit(param, result);
        double[] Theory = getTheory(t, count);
        double[] crownTheory = getCrownTheory(count, p, t);
        double AppearTheory = Theory[0];
        double OmitTheory = Theory[1];
        if (position.equals("冠亚和")) {
            AppearTheory = crownTheory[0];
            OmitTheory = crownTheory[1];
        }
        ValueInvestRep odd = new ValueInvestRep(position, type, ds_dx[0], oddEvenCount[0], AppearTheory, omit[0], OmitTheory, omit[1]);
        // (累计 - 理论) / 理论 * 100 = 偏差
        double value = new BigDecimal(Integer.valueOf(oddEvenCount[0]) - AppearTheory).divide(new BigDecimal(AppearTheory), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).doubleValue();
        odd.setAppearDeviation(value);
        //遗漏平均：（以期数为基数-出现累计）/出现累计 = 平均
        double avg = new BigDecimal(count - Integer.valueOf(oddEvenCount[0])).divide(new BigDecimal(oddEvenCount[0]), 2, BigDecimal.ROUND_DOWN).doubleValue();
        odd.setOmitAvg(avg);
        if (position.equals("冠亚和")) {
            AppearTheory = crownTheory[2];
            OmitTheory = crownTheory[3];
        }
        ValueInvestRep even = new ValueInvestRep(position, type, ds_dx[1], oddEvenCount[1], AppearTheory, omit[2], OmitTheory, omit[3]);
        value = new BigDecimal(Integer.valueOf(oddEvenCount[1]) - AppearTheory).divide(new BigDecimal(AppearTheory), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).doubleValue();
        even.setAppearDeviation(value);
        avg = new BigDecimal(count - Integer.valueOf(oddEvenCount[1])).divide(new BigDecimal(oddEvenCount[1]), 2, BigDecimal.ROUND_DOWN).doubleValue();
        even.setOmitAvg(avg);
        oddEvenList.add(odd);
        oddEvenList.add(even);
        return oddEvenList;
    }

    /**
     * 获取单双大小的当前遗漏和最大遗漏
     *
     * @param param
     * @return
     */
    public int[] getOmit(Map<String, Object> param, List<ColorVarietyModelRes> result) {
        Integer type = (Integer) param.get("type");
        Integer position = (Integer) param.get("position");
        int[] omit;
        List<String> ds_dx_lh = getDS_DX_LH(result, type, position);
        // 得到单双的角标
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        switch (type) {
            // 单双
            case 1:
                omit = getOmitList(ds_dx_lh, list1, list2, "单", "双");
                break;
            // 大小
            case 2:
                omit = getOmitList(ds_dx_lh, list1, list2, "大", "小");
                break;
            //冠亚和
            case 3:
                omit = getOmitList(ds_dx_lh, list1, list2, "龙", "虎");
                break;
            // 任选1~8
            default:
                omit = getOmitList(ds_dx_lh, list1, param.get("num").toString().split(","));
                break;
        }
        return omit;
    }

    private int[] getOmitList(List<String> ds_dx_lh, List<Integer> list1, String[] nums) {
        for (int i = 0; i < ds_dx_lh.size(); i++) {
            // 比较
            String s = ds_dx_lh.get(i);
            for (int j = 0; j < nums.length; j++) {
                if (s.equals(nums[j])) {
                    list1.add(i);
                    break;
                }
            }
        }
        int[] Omit = getOmitValue(list1);
        return new int[]{Omit[0], Omit[1]};
    }

    private int[] getOmitList(List<String> ds_dx_lh, List<Integer> list1, List<Integer> list2, String v1, String v2) {
        for (int i = 0; i < ds_dx_lh.size(); i++) {
            // 比较
            String s = ds_dx_lh.get(i);
            if (s.equals(v1)) {
                list1.add(i);
            } else if (s.equals(v2)) {
                list2.add(i);
            }
        }
        int[] lOmit = getOmitValue(list1);
        int[] hOmit = getOmitValue(list2);
        return new int[]{lOmit[0], lOmit[1], hOmit[0], hOmit[1]};
    }

    /**
     * 获取当前遗漏和最大遗漏
     *
     * @param list
     * @return
     */
    public int[] getOmitValue(List<Integer> list) {
        int maxOmit = 0;
        // 当前遗漏
        int currOmit = list.get(0) - 0;
        // 最大遗漏
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                int sub = list.get(i + 1) - list.get(i);
                if (sub > maxOmit) {
                    maxOmit = sub;
                }
            }
        }
        return new int[]{currOmit, maxOmit - 1};
    }

    /**
     * 获取单双大小龙虎,任选值的字符串组合
     *
     * @param result
     * @param type
     * @param position
     */
    public List<String> getDS_DX_LH(List<ColorVarietyModelRes> result, int type, int position) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            String num = getNum(position, result.get(i));
            switch (type) {
                case 1:
                    if (Integer.valueOf(num) % 2 == 0) {
                        list.add("双");
                    } else {
                        list.add("单");
                    }
                    break;
                case 2:
                    int end = 5;
                    // 冠亚和
                    if (position == 11) {
                        end = 11;
                    }
                    if (Integer.valueOf(num) > end) {
                        list.add("大");
                    } else {
                        list.add("小");
                    }
                    break;
                case 3:
                    if (num.equals("龙")) {
                        list.add("龙");
                    } else {
                        list.add("虎");
                    }
                    break;
                default:
                    // 任选1~8
                    list.add(num);
                    break;
            }
        }
        return list;
    }

    public String getNum(int position, ColorVarietyModelRes model) {
        String num = null;
        switch (position) {
            case 1:
                num = model.getNumberOneBall().toString();
                break;
            case 2:
                num = model.getNumberTwoBall().toString();
                break;
            case 3:
                num = model.getNumberThreeBall().toString();
                break;
            case 4:
                num = model.getNumberFourBall().toString();
                break;
            case 5:
                num = model.getNumberFiveBall().toString();
                break;
            case 6:
                num = model.getNumberSixBall().toString();
                break;
            case 7:
                num = model.getNumberSevenBall().toString();
                break;
            case 8:
                num = model.getNumberEightBall().toString();
                break;
            case 9:
                num = model.getNumberNineBall().toString();
                break;
            case 10:
                num = model.getNumberTenBall().toString();
                break;
            case 11:
                num = model.getCrownSub();
                break;
            case 12:
                num = model.getFirstDragonTiger();
                break;
            case 13:
                num = model.getSecondDragonTiger();
                break;
            case 14:
                num = model.getThirdDragonTiger();
                break;
            case 15:
                num = model.getFourthDragonTiger();
                break;
            case 16:
                num = model.getFifthDragonTiger();
                break;
        }
        return num;
    }

    /**
     * 根据任选条件查询数据
     *
     * @param param
     * @param arr
     * @return
     */
    public List<ValueInvestRep> getOptional(Map<String, Object> param, int[] arr) {
        List<ValueInvestRep> optionalList = new ArrayList<>();
        Integer p = (Integer) param.get("position");
        Integer t = (Integer) param.get("type");
        Integer count = (Integer) param.get("count");
        // 使用随机数去数据库做查询
        param.put("number", FormStatUtil.getNumberStr(p));
        String optionalCount = valueInvestDao.queryOptionalCount(param);
        System.out.println("根据随机数得出的结果是：" + optionalCount);
        List<ColorVarietyModelRes> result = valueInvestDao.queryResult((Integer) param.get("count"), (Integer) param.get("variety"));
        String[] split = optionalCount.split(",");
        String position = getPositonStr(p);
        String type = getTypeStr(t);
        for (int i = 0; i < split.length; i++) {
            String project = String.valueOf(arr[i]);
            param.put("num", arr[i]);
            if (t > 4) {
                project = (String) param.get("project");
                param.put("num", project);
            }
            int[] omit = getOmit(param, result);
            double[] theory = getTheory(t, count);
            ValueInvestRep dto = new ValueInvestRep(position, type, project, split[i], theory[0], omit[0], theory[1], omit[1]);
            // (累计 - 理论) / 理论 * 100 = 偏差
            double value = new BigDecimal(Integer.valueOf(split[i]) - theory[0]).divide(new BigDecimal(theory[0]), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).doubleValue();
            dto.setAppearDeviation(value);
            //遗漏平均：（以期数为基数-出现累计）/出现累计 = 平均
            double avg = new BigDecimal(count - Integer.valueOf(split[i])).divide(new BigDecimal(split[i]), 2, BigDecimal.ROUND_DOWN).doubleValue();
            dto.setOmitAvg(avg);
            optionalList.add(dto);
        }
        return optionalList;

    }

    /**
     * 根据类型获取对应的随机个数和总条数
     *
     * @param type
     * @return
     */
    private int[] getCount(int type) {
        // count[0]:随机个数, count[1]:获取总条数
        int[] count = new int[2];
        count[1] = 50;
        switch (type) {
            // 任选2
            case 5:
                count[0] = 2;
                count[1] = 45;
                break;
            // 任选3
            case 6:
                count[0] = 3;
                break;
            // 任选4
            case 7:
                count[0] = 4;
                break;
            // 任选5
            case 8:
                count[0] = 5;
                break;
            // 任选6
            case 9:
                count[0] = 6;
                break;
            // 任选7
            case 10:
                count[0] = 7;
                break;
            // 任选8
            case 11:
                count[0] = 8;
                count[1] = 45;
                break;
        }
        return count;
    }

    /**
     * 封装条件 单双大小龙虎
     *
     * @param param
     * @param type
     */
    private void conditionStr(Map<String, Object> param, int type, int position) {
        String conditionOne = null, conditionTwo = null;
        if (type == 1) {
            conditionOne = " % 2 != 0 ";
            conditionTwo = " % 2 = 0 ";
        } else if (type == 2) {
            if (position == 11) {
                conditionOne = " > 11 ";
                conditionTwo = " < 12 ";
            } else {
                conditionOne = " > 5 ";
                conditionTwo = " < 6 ";
            }

        } else if (type == 3) {
            conditionOne = " = '龙' ";
            conditionTwo = " = '虎' ";
        }
        param.put("conditionOne", conditionOne);
        param.put("conditionTwo", conditionTwo);

    }

    /**
     * 获取坐标字符串名称
     *
     * @param position
     * @return
     */
    public String getPositonStr(int position) {
        String str = null;
        switch (position) {
            case 1:
            case 12:
                str = "冠军";
                break;
            case 2:
            case 13:
                str = "亚军";
                break;
            case 3:
            case 14:
                str = "第三名";
                break;
            case 4:
            case 15:
                str = "第四名";
                break;
            case 5:
            case 16:
                str = "第五名";
                break;
            case 6:
                str = "第六名";
                break;
            case 7:
                str = "第七名";
                break;
            case 8:
                str = "第八名";
                break;
            case 9:
                str = "第九名";
                break;
            case 10:
                str = "第十名";
                break;
            case 11:
                str = "冠亚和";
                break;
        }
        return str;
    }

    /**
     * 根据类型获取字符串名称
     *
     * @param type
     * @return
     */
    private String getTypeStr(int type) {
        String str = null;
        switch (type) {
            case 1:
                str = "单双";
                break;
            case 2:
                str = "大小";
                break;
            case 3:
                str = "龙虎";
                break;
            case 4:
                str = "任选1";
                break;
            case 5:
                str = "任选2";
                break;
            case 6:
                str = "任选3";
                break;
            case 7:
                str = "任选4";
                break;
            case 8:
                str = "任选5";
                break;
            case 9:
                str = "任选6";
                break;
            case 10:
                str = "任选7";
                break;
            case 11:
                str = "任选8";
                break;
        }
        return str;
    }

    /**
     * 根据类型获取方案字符串
     *
     * @param ds_dx
     * @param type
     */
    private void getProjectStr(String[] ds_dx, int type) {
        if (type == 1) {
            ds_dx[0] = "单";
            ds_dx[1] = "双";
        } else if (type == 2) {
            ds_dx[0] = "大";
            ds_dx[1] = "小";
        } else if (type == 3) {
            ds_dx[0] = "龙";
            ds_dx[1] = "虎";
        }
    }

    /**
     * 获得龙虎的理论值
     *
     * @param count
     * @param position
     * @param type
     * @return
     */
    private double[] getCrownTheory(int count, int position, int type) {
        double[] theory = new double[4];
        int t1 = 53;
        int t2 = 47;
        double o1 = 0.88;
        double o2 = 1.12;
        if (position == 11 && type == 2) {
            t1 = t1 ^ t2;
            t2 = t1 ^ t2;
            t1 = t1 ^ t2;
            double temp = o1;
            o1 = o2;
            o2 = temp;
        }
        // 单
        double appearTheory = count / 100 * t1;
        double omitTheory = o1;
        theory[0] = appearTheory;
        theory[1] = omitTheory;
        // 双
        appearTheory = count / 100 * t2;
        omitTheory = o2;
        theory[2] = appearTheory;
        theory[3] = omitTheory;
        return theory;
    }

    /**
     * 根据类型获得出现-理论,遗漏-理论
     * 单双大小龙虎：出现理论：50
     * 任选1~10 ：出现理论10~80
     * <p>
     * 单双大小龙虎：遗漏-理论：1
     * 任选1   2   3     4    5   6     7       8
     * 9   4  2.33  1.5   1  0.66   0.42   0.25
     *
     * @param type
     * @return
     */
    private double[] getTheory(int type, int count) {
        double appearTheory = 0;
        double omitTheory = 0;
        switch (type) {
            case 1:
            case 2:
            case 3:
                appearTheory = count / 2;
                omitTheory = 1;
                break;
            case 4:
                appearTheory = (count / 10) * 1;
                omitTheory = 9;
                break;
            case 5:
                appearTheory = (count / 10) * 2;
                omitTheory = 4;
                break;
            case 6:
                appearTheory = (count / 10) * 3;
                omitTheory = 2.33;
                break;
            case 7:
                appearTheory = (count / 10) * 4;
                omitTheory = 1.5;
                break;
            case 8:
                appearTheory = (count / 10) * 5;
                omitTheory = 1;
                break;
            case 9:
                appearTheory = (count / 10) * 6;
                omitTheory = 0.66;
                break;
            case 10:
                appearTheory = (count / 10) * 7;
                omitTheory = 0.42;
                break;
            case 11:
                appearTheory = (count / 10) * 8;
                omitTheory = 0.25;
                break;
        }
        return new double[]{appearTheory, omitTheory};
    }
}
