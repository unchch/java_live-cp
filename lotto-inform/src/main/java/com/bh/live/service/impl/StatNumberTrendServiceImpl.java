package com.bh.live.service.impl;

import com.bh.live.common.TrendUtils.CalcHBDSDX;
import com.bh.live.common.TrendUtils.CalcOmit;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.StatPostionTrendDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.service.StatNumberTrendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 号码走势
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/26 12:24
 */
@Service
public class StatNumberTrendServiceImpl implements StatNumberTrendService {

    @Resource
    private StatPostionTrendDao postionTrendDao;

    /**
     * 查找指定球号的走势
     *
     * @param position 位置
     * @param count    期数
     * @return
     */
    @Override
    public Map<String, Object> queryNumberTrend(int position, int count, Integer variety) {
        System.out.println(" ======= 开始号码走势报表 ===== ");
        Long starttime = System.currentTimeMillis();
        if (count == 0) {
            // 获取当天期号
            count = postionTrendDao.queryOpenCount(variety);
        }
        Map<String, Object> resultMap = new HashMap<>();
        //整合所有期数对象
        List<ColorVarietyModelRes> resultList = new ArrayList<>();
        // 获取指定期数的结果对象
        List<ColorVarietyModelRes> resultCountVos = postionTrendDao.queryPk10Rsult(count, null, variety);
        resultList.addAll(resultCountVos);

        resultMap.put("expectList", CalcOmit.getExpectList(resultCountVos));

        Map<String, Object> CalcOmit = getIndex(resultCountVos, position, count, variety);
        // 获得最大遗漏
        int maxOmit = (Integer) CalcOmit.get("totalOmit");
        System.out.println("总最大遗漏是：" + maxOmit);
        // 获取最大遗漏重新去查询数据
        resultCountVos = postionTrendDao.queryPk10Rsult(count, maxOmit, variety);
        resultList.addAll(resultCountVos);
        // 反向，正向，重号，单，双，大，小
        List<Integer> numbers = getNumbers(resultList,position);
        Map<String, Object> hb_ds_dx_all = getHB_DS_DX(numbers, count);
        //移除不必要的属性
        CalcOmit.remove("totalOmit");
        resultMap.put("CalcOmit", CalcOmit);
        resultMap.put("hb_ds_dx_all", hb_ds_dx_all);
        System.out.println(" ======= 号码走势结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
        return resultMap;
    }

    /**
     * 查找数据中等于num所在位置的数据
     *
     * @param resultCountVos
     * @param num
     * @param count
     * @param variety
     * @return
     */
    private Map<String, Object> getIndex(List<ColorVarietyModelRes> resultCountVos, int num, int count, int variety) {
        Map<String, Object> resultMap = new HashMap<>();
        // 获取坐标
        List<ColorVarietyModelRes> l1 = new ArrayList<>();
        List<ColorVarietyModelRes> l2 = new ArrayList<>();
        List<ColorVarietyModelRes> l3 = new ArrayList<>();
        List<ColorVarietyModelRes> l4 = new ArrayList<>();
        List<ColorVarietyModelRes> l5 = new ArrayList<>();
        List<ColorVarietyModelRes> l6 = new ArrayList<>();
        List<ColorVarietyModelRes> l7 = new ArrayList<>();
        List<ColorVarietyModelRes> l8 = new ArrayList<>();
        List<ColorVarietyModelRes> l9 = new ArrayList<>();
        List<ColorVarietyModelRes> l10 = new ArrayList<>();
        for (int i = 0; i < resultCountVos.size(); i++) {
            ColorVarietyModelRes vo = resultCountVos.get(i);
            if (vo.getNumberOneBall() == num) {
                l1.add(vo);
            } else if (vo.getNumberTwoBall() == num) {
                l2.add(vo);
            } else if (vo.getNumberThreeBall() == num) {
                l3.add(vo);
            } else if (vo.getNumberFourBall() == num) {
                l4.add(vo);
            } else if (vo.getNumberFiveBall() == num) {
                l5.add(vo);
            } else if (vo.getNumberSixBall() == num) {
                l6.add(vo);
            } else if (vo.getNumberSevenBall() == num) {
                l7.add(vo);
            } else if (vo.getNumberEightBall() == num) {
                l8.add(vo);
            } else if (vo.getNumberNineBall() == num) {
                l9.add(vo);
            } else if (vo.getNumberTenBall() == num) {
                l10.add(vo);
            }
        }
        // 得到出现总次数
        List<Integer> totalCount = new ArrayList<>();
        totalCount.add(l1.size());
        totalCount.add(l2.size());
        totalCount.add(l3.size());
        totalCount.add(l4.size());
        totalCount.add(l5.size());
        totalCount.add(l6.size());
        totalCount.add(l7.size());
        totalCount.add(l8.size());
        totalCount.add(l9.size());
        totalCount.add(l10.size());
        resultMap.put("totalCount", totalCount);

        List<Integer> totalOmitCount = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("count", count);
        param.put("variety", variety);
        // 获取间隔最大的遗漏
        int totalOmit = 0;

        // 查找每个列的数据
        for (int i = 1; i <= 10; i++) {
            param.put("number", FormStatUtil.getNumberStr(i));
            param.put("ball", num);
            Map<String, Object> map = new HashMap<>();
            switch (i) {
                case 1:
                    map = getList(resultCountVos, l1, param);
                    break;
                case 2:
                    map = getList(resultCountVos, l2, param);
                    break;
                case 3:
                    map = getList(resultCountVos, l3, param);
                    break;
                case 4:
                    map = getList(resultCountVos, l4, param);
                    break;
                case 5:
                    map = getList(resultCountVos, l5, param);
                    break;
                case 6:
                    map = getList(resultCountVos, l6, param);
                    break;
                case 7:
                    map = getList(resultCountVos, l7, param);
                    break;
                case 8:
                    map = getList(resultCountVos, l8, param);
                    break;
                case 9:
                    map = getList(resultCountVos, l9, param);
                    break;
                case 10:
                    map = getList(resultCountVos, l10, param);
                    break;
            }
            // 添加总遗漏次数
            Integer maxOmit = (Integer) map.get("maxOmit");
            if (maxOmit > totalOmit) {
                totalOmit = maxOmit;
            }
            totalOmitCount.add(maxOmit-1);
            resultMap.put("totalOmit", totalOmit);
            resultMap.put("totalOmitCount", totalOmitCount);
            resultMap.put("list_" + i, map.get("evenOmitList"));
        }
        return resultMap;
    }

    private Map<String, Object> getList(List<ColorVarietyModelRes> resultCountVos, List<ColorVarietyModelRes> list, Map<String, Object> param) {
        Integer count = (Integer) param.get("count");
        Integer variety = (Integer) param.get("variety");
        Map<String, Object> map = new HashMap<>();
        List<Integer> evenOmitList = new ArrayList<>();
        // 最大遗漏次数
        int num = 0;
        // 总期数集合
        String expect = resultCountVos.get(0).getExpect();
        BigDecimal FristExpect = new BigDecimal(expect);
        BigDecimal Endexpect =null;
        String nextExpect =null;
        // 指定期数内最后一条数据的期号
        if (list.size()!=0){
            String expect1 = list.get(list.size() - 1).getExpect();
            Endexpect = new BigDecimal(expect1);
            param.put("expect", Endexpect);
            // 从下一个区间找数据
            nextExpect = postionTrendDao.getNextResult(param);
            if (nextExpect.equals(expect1)) {
                // 如果相同，重新再次查找
                param.put("expect", Endexpect.subtract(new BigDecimal(1)));
                nextExpect = postionTrendDao.getNextResult(param);
            }
        }else if (list.size()==0){
            param.put("expect", resultCountVos.get(resultCountVos.size()-1).getExpect());
            // 从下一个区间找数据
            nextExpect = postionTrendDao.getNextResult(param);
        }
        for (int i = 0; i < list.size(); i++) {
            // 每期的遗漏次数
            int evenOmit = 0;
            // 获取当前期
            String expect1 = list.get(i).getExpect();
            BigDecimal currExpect = new BigDecimal(expect1);
            // 获取第一期与集合中的数据
            if (i == 0) {
                evenOmit = FristExpect.subtract(currExpect).intValue();
                num = CalcOmit.addEvenOmitList(num, evenOmit+1, evenOmitList, count);
                System.out.println(FristExpect + "-" + currExpect + "=" + evenOmit);
            }
            if (i != list.size() - 1) {
                // 获取上一期
                ColorVarietyModelRes vo = list.get(i + 1);
                if (vo != null) {
                    BigDecimal up = new BigDecimal(vo.getExpect());
                    if (variety==0){
                        evenOmit =  CalcOmit.subExpect(variety,expect1,vo.getExpect());
                    }else {
                        evenOmit = currExpect.subtract(up).intValue();
                    }
                    System.out.println(currExpect + "-" + up + "=" + evenOmit);
                }
            } else {
                // 继续找下一条
                if (variety==0){
                    evenOmit =  CalcOmit.subExpect(variety,expect1,nextExpect);
                }else {
                    evenOmit = currExpect.subtract(new BigDecimal(nextExpect)).intValue();
                }
                System.out.println("查找到下一期数据为：" + nextExpect);
                System.out.println(currExpect + "-" + nextExpect + "=" + evenOmit);
            }
            num = CalcOmit.addEvenOmitList(num, evenOmit, evenOmitList, count);
        }
        // 如果没有找到则需要继续往下找
        if (list.size() == 0) {
            System.out.println("没有找到任何数据则继续往下找");
            System.out.println("查找到下一期数据为：" + nextExpect);
            int sub=0;
            if (variety==0){
                sub =  CalcOmit.subExpect(variety,expect,nextExpect);
            }else {
                sub = FristExpect.subtract(new BigDecimal(nextExpect)).intValue();
            }
            System.out.println(FristExpect + "-" + nextExpect + "=" + sub);
            num = CalcOmit.addEvenOmitList(num, sub, evenOmitList, count);
        }
        System.out.println("最大遗漏是：" + num);
        map.put("maxOmit", num);
        map.put("evenOmitList", evenOmitList);
        return map;
    }

    /**
     * 获取期数中指定号码的所在位置集合
     * @param resultCountVos
     * @return
     */
    private List<Integer> getNumbers(List<ColorVarietyModelRes> resultCountVos,int num){
        List<Integer> numbers=new ArrayList<>();
        int n=0;
        for (int i = 0; i < resultCountVos.size(); i++) {
            ColorVarietyModelRes vo = resultCountVos.get(i);
            if (vo.getNumberOneBall() == num) {
                n=1;
            } else if (vo.getNumberTwoBall() == num) {
                n=2;
            } else if (vo.getNumberThreeBall() == num) {
                n=3;
            } else if (vo.getNumberFourBall() == num) {
                n=4;
            } else if (vo.getNumberFiveBall() == num) {
                n=5;
            } else if (vo.getNumberSixBall() == num) {
                n=6;
            } else if (vo.getNumberSevenBall() == num) {
                n=7;
            } else if (vo.getNumberEightBall() == num) {
                n=8;
            } else if (vo.getNumberNineBall() == num) {
                n=9;
            } else if (vo.getNumberTenBall() == num) {
                n=10;
            }
            numbers.add(n);
        }
        return numbers;
    }
    /**
     * 反嚮：當前期號碼比上一期號碼小；
     * 正嚮：當前期號碼比上一期號碼大；
     * 重號：當前期號碼與上期號碼相同。
     * 前：小于等于5
     * 后：大于5
     * @param numbers
     * @param count
     * @return
     */
    public Map<String, Object> getHB_DS_DX(List<Integer> numbers, int count) {
        Map<String, Object> resultMap = new HashMap<>();
        // 声明 反向，正向，重号，前，后 : 坐标集合
        List<Integer> fxList = new ArrayList<>(), zxList = new ArrayList<>(),
                chList = new ArrayList<>(), beforeList = new ArrayList<>(), behindList = new ArrayList<>();
        int fx=0,zx=0,ch=0,before=0,behind=0;
        int size = numbers.size();
        for (int i = 0; i < numbers.size(); i++) {
            Integer up = numbers.get(i);
            if (i<numbers.size()-1){
                Integer next = numbers.get(i + 1);
                if (up<next){
                    fxList.add(i);
                    if (i<count){
                        fx++;
                    }
                }else if(up>next){
                    zxList.add(i);
                    if (i<count){
                        zx++;
                    }
                }else if (up.equals(next)){
                    chList.add(i);
                    if (i<count){
                        ch++;
                    }
                }
                if (up<6){
                    beforeList.add(i);
                    if (i<count){
                        before++;
                    }
                }else if (up>5){
                    behindList.add(i);
                    if (i<count){
                        behind++;
                    }
                }
            }
        }

        System.out.println("所有" + numbers);
        System.out.println("反向" + fxList);
        System.out.println("重号" + chList);
        System.out.println("正向" + zxList);
        System.out.println("前" + beforeList);
        System.out.println("后" + behindList);

        // 总出现次数
        List<Integer> totalCount = new ArrayList<>();
        totalCount.add(fx);
        totalCount.add(ch);
        totalCount.add(zx);
        totalCount.add(before);
        totalCount.add(behind);
        resultMap.put("totalCount", totalCount);
        // 反向，正向，重号，前，后: 遗漏集合 {总遗漏次数,遗漏集合}
        List<Integer> fxOmitList = CalcHBDSDX.eachList(fxList, size,count);
        List<Integer> chOmitList = CalcHBDSDX.eachList(chList, size,count);
        List<Integer> zxOmitList = CalcHBDSDX.eachList(zxList, size,count);
        List<Integer> beforeOmitList = CalcHBDSDX.eachList(beforeList, size,count);
        List<Integer> behindOmitList = CalcHBDSDX.eachList(behindList, size,count);

        // 总遗漏次数
        List<Integer> totalOmitCount = new ArrayList<>();
        totalOmitCount.add(fxOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(chOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(zxOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(beforeOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());
        totalOmitCount.add(behindOmitList.stream().sorted(Comparator.reverseOrder()).findFirst().get());

        resultMap.put("totalOmitCount", totalOmitCount);

        // 正向，反向，重号，前，后 遗漏排序集合
        Map<String, List<Integer>> hb_ds_dx_map = new HashMap<>();
        hb_ds_dx_map.put("fxOmitList", fxOmitList);
        hb_ds_dx_map.put("chOmitList", chOmitList);
        hb_ds_dx_map.put("zxOmitList", zxOmitList);
        hb_ds_dx_map.put("beforeOmitList", beforeOmitList);
        hb_ds_dx_map.put("behindOmitList", behindOmitList);

        resultMap.put("hb_ds_dx_map", hb_ds_dx_map);

        return resultMap;

    }
}
