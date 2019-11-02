package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CombinationsUtil;
import com.bh.live.model.inform.Omission;
import com.bh.live.service.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @title: NumberMissingController
 * @projectName livebase
 * @description: TODO
 * @date 2019/6/25  16:36
 */
@RestController
@Slf4j
@RequestMapping("/numberMiss")
public class NumberMissingController {
    @Autowired
    private NumberMissingService numberMissingService;

    @Autowired
    private NumberSingleDoubleMissService singleDoubleMissService;
    @Autowired
    private NumberBigSmallMissService bigSmallMissService;
    @Autowired
    private NumberDragonTigerMissService dragonTigerMissService;
    @Autowired
    private NumRandomMissService randomMissService;


    public static void main(String[] args) {
    	Integer[] intege = {1,2,3,4,5,6,7,8,9,10};
    	int count = 0;
		CombinationsUtil com = new CombinationsUtil<>(Arrays.asList(intege), 4);
		while (com.iterator().hasNext()) {
			count++;
			System.out.println(com.iterator().next());
		}
		System.out.println("任选3次数"+count);
	}
    /**
     * 号码遗漏查询列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result getNumberMissingList(@RequestParam Integer number, @RequestParam String type, @RequestParam int varietyType) {
        if (StringUtils.isNotEmpty(type)) {
            long millis = System.currentTimeMillis();
            switch (type) {
                case "number":
                    //TODO something
                    System.out.println("开始统计---" + millis);
                    List<Omission> omissionList = numberMissingService.getOmissionCount(number, varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(omissionList);
                //break;
                case "singleDouble":
                    millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> singleDoubleOmission = singleDoubleMissService.getOmissionCount(number,varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(singleDoubleOmission);
                //break;
                case "dragonTiger":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> dragonTigerOmission = dragonTigerMissService.getOmissionCount(number,varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(dragonTigerOmission);
                case "bigSmall":
                	 millis = System.currentTimeMillis();
                     System.out.println("开始统计---" + millis);
                     List<Omission> bigSmallOmission = bigSmallMissService.getOmissionCount(number,varietyType);
                     System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                     return Result.success(bigSmallOmission);
                case "random3":
                	 millis = System.currentTimeMillis();
                     System.out.println("开始统计---" + millis);
                     List<Omission> random3 = randomMissService.getRandom(number,varietyType,3);
                     System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                     return Result.success(random3);
                case "random4":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> random4 = randomMissService.getRandom(number,varietyType,4);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(random4);
                case "random5":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> random5 = randomMissService.getRandom(number,varietyType,5);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(random5);
                case "random6":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> random6 = randomMissService.getRandom(number,varietyType,6);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(random6);
                case "random7":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> random7 = randomMissService.getRandom(number,varietyType,7);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(random7);
                //总和两面
                case "totalTwoFace":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> totalTwoFace = randomMissService.getTotalTwoFace(varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(totalTwoFace);
                //总和大小
                case "totalBigSmall":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> totalBigSmall= randomMissService.getTotalBigSmall(varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(totalBigSmall);
                //骰子点数
                case "diceCount":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> diceCount= randomMissService.getDiceCount(varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(diceCount);
                //短牌
                case "shortCard":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> shortCard= randomMissService.getShortCard(varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(shortCard);
                //长牌
                case "longCard":
                	millis = System.currentTimeMillis();
                    System.out.println("开始统计---" + millis);
                    List<Omission> longCard= randomMissService.getLongCard(varietyType);
                    System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                    return Result.success(longCard);
                //三军
                case "theArmy":
                	millis = System.currentTimeMillis();
                	System.out.println("开始统计---" + millis);
                	List<Omission> theArmy= randomMissService.getTheArmy(varietyType);
                	System.out.println("统计结束---用时" + ((System.currentTimeMillis() - millis) % (1000 * 60)) / 1000 + "秒");
                	return Result.success(theArmy);
                	default: return Result.success();
            }
        }
        return Result.success();
    }


}
