package com.bh.live.common.longDragon;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.constant.RedisKey;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.model.inform.StatHistoryDraw;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 香港彩 最大遗漏
 * type  1: 生肖  2：五行  3：家禽野兽  4：男女生肖
 *       5：天地生肖  6：四季生肖 7：琴棋书画  8：三色生肖
  */
public class HongKongMaxOmission {

    @Autowired
    private RedisManager redisManager;

    private Integer type;

    private Integer year;

    public HongKongMaxOmission(Integer type,Integer year) {
        this.type = type;
        this.year = year;
    }

    /**
     * <位置(生肖，五行，家禽.......) , <期数，遗漏期数>>
     */
    public Map<String, Map<String, Integer>> maxOmissionMapping = new HashMap<>();

    public void init() {
        Map<String, Integer> shadeMap = new HashMap<>();
        shadeMap.put("", 0);
        switch (type) {
            case 1:
                maxOmissionMapping.put("鼠", shadeMap);
                maxOmissionMapping.put("牛", shadeMap);
                maxOmissionMapping.put("虎", shadeMap);
                maxOmissionMapping.put("兔", shadeMap);
                maxOmissionMapping.put("龙", shadeMap);
                maxOmissionMapping.put("蛇", shadeMap);
                maxOmissionMapping.put("马", shadeMap);
                maxOmissionMapping.put("羊", shadeMap);
                maxOmissionMapping.put("猴", shadeMap);
                maxOmissionMapping.put("鸡", shadeMap);
                maxOmissionMapping.put("狗", shadeMap);
                maxOmissionMapping.put("猪", shadeMap);
            case 2:
                maxOmissionMapping.put("金", shadeMap);
                maxOmissionMapping.put("木", shadeMap);
                maxOmissionMapping.put("水", shadeMap);
                maxOmissionMapping.put("火", shadeMap);
                maxOmissionMapping.put("土", shadeMap);
            case 3:
                maxOmissionMapping.put("家禽", shadeMap);
                maxOmissionMapping.put("野兽", shadeMap);
            case 4:
                maxOmissionMapping.put("男肖", shadeMap);
                maxOmissionMapping.put("女肖", shadeMap);
            case 5:
                maxOmissionMapping.put("天肖", shadeMap);
                maxOmissionMapping.put("地肖", shadeMap);
            case 6:
                maxOmissionMapping.put("春肖", shadeMap);
                maxOmissionMapping.put("夏肖", shadeMap);
                maxOmissionMapping.put("秋肖", shadeMap);
                maxOmissionMapping.put("冬肖", shadeMap);
            case 7:
                maxOmissionMapping.put("琴肖", shadeMap);
                maxOmissionMapping.put("棋肖", shadeMap);
                maxOmissionMapping.put("书肖", shadeMap);
                maxOmissionMapping.put("画肖", shadeMap);
            case 8:
                maxOmissionMapping.put("红肖", shadeMap);
                maxOmissionMapping.put("蓝肖", shadeMap);
                maxOmissionMapping.put("绿肖", shadeMap);
        }
    }

    public void execute(List<StatHistoryDraw> results) {
        if(type == 1){
            JSONObject.parseObject(redisManager.get(RedisKey.zodiacFivePhasesRedisKey(year)));
        }

        for (int i = 0; i <= results.size(); i++) {

        }
    }

    public static void main(String[] args) {
    }
}
