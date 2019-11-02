package com.bh.live.model.inform;
import lombok.Data;

/**
 * @Description: 十二生肖和五行
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/4 16:39
 */
@Data
public class ZodiacFivePhases {
    /**
     * 类型  五行0 生肖1
     */
    private int typeName;
    /**
     *目标名称
     */
    private String targetName;
    /**
     *号码字符','隔开
     */
    private String numJson;
    /**
     *年份
     */
    private String particularYear;
    /**
     * 号码数组
     */
    private String[] numbers;

    public void setNumJson(String json){
        this.numJson=json;
        this.numbers = json.split(",");
    }
    
}
