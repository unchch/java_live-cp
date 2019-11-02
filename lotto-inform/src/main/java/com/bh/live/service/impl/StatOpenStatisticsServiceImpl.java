package com.bh.live.service.impl;

import com.bh.live.common.utils.FormStatSSCUtil;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.StatOpenStatisticsDao;
import com.bh.live.pojo.res.inform.ColorVarietyModelRes;
import com.bh.live.pojo.res.inform.OpenStatisticsCodeRes;
import com.bh.live.pojo.res.inform.OpenStatisticsRes;
import com.bh.live.service.StatOpenStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 开号统计
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/6/12 10:06
 */
@Service
public class StatOpenStatisticsServiceImpl implements StatOpenStatisticsService {

    @Resource
    private StatOpenStatisticsDao openStatisticsDao;

    @Override
    public Map<String, Object> queryOpenStatistics(Integer count, Integer variety) {
        System.out.println(" ======= 开始开号统计报表 ===== ");
        Long starttime = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        if (count != null) {
            param.put("count", count);
        }
        param.put("variety", variety);
        // 获取最新一期开奖码
        Map<String, Object> topMap = new HashMap<>();
        String[] openCode = openStatisticsDao.getOpenCode(count, variety).split(",");
        topMap.put("number", openCode);
        resultMap.put("top", topMap);

        // 获取开号统计的左列数据
        List<List<OpenStatisticsRes>> leftList = getBold(getOpenCodeLeft(param, variety), openCode);
        // 形态统计
        for (List<OpenStatisticsRes> list : leftList) {
            FormStatUtil.formStat(list);
        }
        // 获取开号统计的右列数据
        List<List<OpenStatisticsRes>> rightList = null;
        List<List<List<OpenStatisticsRes>>> rightList_2 = null;
        if (variety == 2) {
            // 重庆时时彩一列会有多个对象
            rightList_2 = getOpenCodeRight_2(param, variety);
            resultMap.put("rightList", rightList_2);
            FormStatSSCUtil.formStatAll(rightList_2);
        } else {
            rightList = getOpenCodeRight(param, variety);
            for (List<OpenStatisticsRes> list : rightList) {
                FormStatUtil.formStat(list);
            }
        }
        // 封装数据成Map
        List<List<OpenStatisticsRes>> totalList = new ArrayList<>();
        if (variety != 2) {
            for (int i = 0; i < 10; i++) {
                totalList.add(leftList.get(i));
                if (variety != 2) {
                    totalList.add(rightList.get(i));
                }
            }
            resultMap.put("numberList", totalList);
        } else {
            resultMap.put("leftList", leftList);
        }
        // 重庆时时彩获取重号
        if (variety == 2) {
            List<OpenStatisticsRes> chNumbers = null;
            // 获取下一条数据来判断是否有重号
            String[] openCode_two = openStatisticsDao.getOpenCode(count + 1, variety).split(",");
            String ch_number = null;
            int ch_index = 0;
            for (int i = 0; i < openCode.length; i++) {
                if (openCode[i].equals(openCode_two[i])) {
                    ch_number = openCode[i];
                    ch_index = i;
                }
            }
            if (ch_number != null) {
                chNumbers = getChNumbersLeft(leftList.get(ch_index), Integer.valueOf(ch_number));
                FormStatUtil.formStat(chNumbers);
                resultMap.put("ch_leftList", chNumbers);
                chNumbers = getChNumbersRight(rightList_2.get(ch_index), Integer.valueOf(ch_number));
                FormStatUtil.formStat(chNumbers);
                resultMap.put("ch_rightList", chNumbers);
            }
        }

        System.out.println(" ======= 开号统计报表统计结束 ===== 耗时 ：{}" + (System.currentTimeMillis() - starttime));
        return resultMap;
    }

    /**
     * 重号的右列数据，值获取第0 坐标的值
     *
     * @param list
     * @param num
     * @return
     */
    public List<OpenStatisticsRes> getChNumbersRight(List<List<OpenStatisticsRes>> list, int num) {
        List<OpenStatisticsRes> numbers = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            OpenStatisticsRes dto = list.get(i).get(0);
            Integer number = dto.getNumber();
            // 如果相同，则找上一个
            if (number == num) {
                numbers.add(new OpenStatisticsRes(list.get(i - 1).get(0).getNumber()));
            }
        }
        return numbers;
    }

    /**
     * 重号的左列数据
     *
     * @param list
     * @param num
     * @return
     */
    public List<OpenStatisticsRes> getChNumbersLeft(List<OpenStatisticsRes> list, int num) {
        List<OpenStatisticsRes> numbers = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            Integer number = list.get(i).getNumber();
            // 如果相同，则找上一个
            if (number == num) {
                OpenStatisticsRes dto = new OpenStatisticsRes(list.get(i - 1).getNumber());
                numbers.add(dto);
            }
        }
        return numbers;
    }


    @Override
    public Map<String, Object> queryHistroy(Integer count, Integer variety) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<ColorVarietyModelRes> colorVarietyModels = openStatisticsDao.queryHistroy(count, variety);
        for (ColorVarietyModelRes vo : colorVarietyModels) {
            Map<String, Object> data = new HashMap<>();
            data.put("data", vo.getExpect());
            String[] split = vo.getOpenCode().split(",");
            Integer[] nums = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                nums[i] = Integer.valueOf(split[i]);
            }
            data.put("number", nums);
            if (variety == 2) {
                String expect = vo.getExpect();
                data.put("data", expect.substring(expect.length() - 2));
                data.put("bigSamll", vo.getBigSmall());
                data.put("singleDouble", vo.getSingleDouble());
            }
            resultList.add(data);
        }
        resultMap.put("histroyList", resultList);
        return resultMap;
    }


    /**
     * 获取开号统计的左列数据
     *
     * @param param
     * @return
     */
    private List<List<OpenStatisticsRes>> getOpenCodeLeft(Map<String, Object> param, Integer variety) {
        List<List<OpenStatisticsRes>> numberList = new ArrayList<>();
        List<OpenStatisticsRes> list = null;

        // 获取10个号码的数据
        for (int i = 1; i <= getEnd(variety); i++) {
            param.put("number", FormStatUtil.getNumberStr(i));
            // 获取开号统计的左列数据
            list = openStatisticsDao.queryOpenStatisticsLeft(param);
            numberList.add(list);
        }
        return numberList;

    }

    private int getEnd(Integer variety) {
        int end = 10;
        switch (variety) {
            case 2:
                end = 5;
                break;
        }
        return end;
    }

    /**
     * 获取开号统计的右列数据
     *
     * @param param
     * @return
     */
    private List<List<OpenStatisticsRes>> getOpenCodeRight(Map<String, Object> param, Integer variety) {
        //685175	7,1,9,5,3,8,10,6,2,4
        //685174	5,8,4,2,3,9,7,6,10,1
        List<OpenStatisticsCodeRes> openStatisticsDtos = openStatisticsDao.queryOpenStatisticsRight(param);
        // 获取最新一期数据的开奖码
        String[] aopencode = openStatisticsDtos.get(0).getAopencode().split(",");
        List<List<OpenStatisticsRes>> rightList = new ArrayList<>();
        for (int i = 0; i < getEnd(variety); i++) {
            String anumber = aopencode[i];
            List<OpenStatisticsRes> upToDownIndexs = new ArrayList<>();
            for (OpenStatisticsCodeRes dto : openStatisticsDtos) {
                System.out.println(dto.getAopencode());
                System.out.println(dto.getBopencode());
                String value = getValue(dto.getAopencode(), dto.getBopencode(), anumber);
                upToDownIndexs.add(new OpenStatisticsRes(Integer.parseInt(value)));
                System.out.println(anumber + ":" + value);
                System.out.println("-------------------------------------------------");
            }
            rightList.add(upToDownIndexs);
        }
        return rightList;
    }

    /**
     * 获取开号统计的右列数据  类型为2 ：重庆时时彩
     *
     * @param param
     * @return
     */
    private List<List<List<OpenStatisticsRes>>> getOpenCodeRight_2(Map<String, Object> param, Integer variety) {
        /**
         * 20190628020	0,0,7,5,0	20190628019	8,6,3,0,5
         * 20190628019	8,6,3,0,5	20190628018	0,2,1,5,3
         * 20190628018	0,2,1,5,3	20190628017	4,7,8,2,0
         * 20190628017	4,7,8,2,0	20190628016	9,7,4,6,9
         */
        List<OpenStatisticsCodeRes> openStatisticsDtos = openStatisticsDao.queryOpenStatisticsRight(param);
        // 获取最新一期数据的开奖码
        String[] aopencode = openStatisticsDtos.get(0).getAopencode().split(",");
        List<List<List<OpenStatisticsRes>>> rightList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String anumber = aopencode[i];
            List<List<OpenStatisticsRes>> upToDownIndexs = new ArrayList<>();
            for (OpenStatisticsCodeRes dto : openStatisticsDtos) {
                List<OpenStatisticsRes> list = new ArrayList<>();
                System.out.println(dto.getAopencode());
                System.out.println(dto.getBopencode());
                String[] b = dto.getBopencode().split(",");
                String[] a = dto.getAopencode().split(",");
                List<Integer> indexs = new ArrayList<>();
                for (int j = 0; j < b.length; j++) {
                    if (b[j].equals(anumber)) {
                        indexs.add(j);
                    }
                }
                for (int k = 0; k < indexs.size(); k++) {
                    list.add(new OpenStatisticsRes(Integer.parseInt(a[indexs.get(k)]), variety));
                }
                if (list.size() > 0) {
                    upToDownIndexs.add(list);
                }
                System.out.println(anumber + ":" + indexs);
                System.out.println("-------------------------------------------------");
            }
            rightList.add(upToDownIndexs);
        }
        return rightList;
    }

    /**
     * 获取 Bopencode 中 anumber 所在角标 ，然后在 Aopencode 中根据角标在找到对应的值
     *
     * @param Aopencode
     * @param Bopencode
     * @param anumber
     * @return
     */
    private String getValue(String Aopencode, String Bopencode, String anumber) {
        //  Aopencode	5,2,7,9,1,6,10,3,8,4	Bopencode	2,5,10,1,4,6,9,3,8,7
        String[] b = Bopencode.split(",");
        String[] a = Aopencode.split(",");
        int index = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i].equals(anumber)) {
                // 得到角标
                index = i;
                break;
            }
        }
        return a[index];
    }

    /**
     * 给左列字体加粗
     * 统计记录的号為开奖号码时，该号的上一个号码加粗显示，隻有左列有加粗。
     * （譬如冠军开奖号码是7，左列记录数据由上至下是12784，那麼2号需要加粗）
     *
     * @param leftList
     */
    private List<List<OpenStatisticsRes>> getBold(List<List<OpenStatisticsRes>> leftList, String[] openCode) {
        for (int j = 0; j < leftList.size(); j++) {
            int code = Integer.parseInt(openCode[j]);
            List<OpenStatisticsRes> numList = leftList.get(j);
            // 从第1坐标开始
            for (int i = 1; i < numList.size(); i++) {
                OpenStatisticsRes dto = numList.get(i);
                // 判断当前号是否等于冠军号
                if (dto.getNumber() == code) {
                    // 则上一个坐标对象的值加粗
                    numList.get(i - 1).setBold("bold");
                }
            }
        }
        return leftList;
    }
}
