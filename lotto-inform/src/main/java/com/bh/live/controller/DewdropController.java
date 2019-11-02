package com.bh.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.constant.RedisKey;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.service.DewdropService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cv/dewdrop/")
@Slf4j
public class DewdropController {

    @Autowired
    private DewdropService dewdropService;

    @Autowired
    private RedisManager redisManager;

    /**
     * 号码前后露珠
     */
    @RequestMapping(value = "dewdropAround", method = RequestMethod.GET)
    @ResponseBody
    public Result dewdropAround(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.dewdropAroundRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.dewdropAroundRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryDewdropAround(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.dewdropAroundRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.dewdropAroundRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 冠亚和露珠
     */
    @RequestMapping(value = "dewdropChampion", method = RequestMethod.GET)
    @ResponseBody
    public Result dewdropChampion(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.dewdropChampionRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.dewdropChampionRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryDewdropChampion(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.dewdropChampionRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.dewdropChampionRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }


    /**
     * 综合露珠
     *
     * @param openTime    开奖时间
     * @param varietyType 彩种类型
     * @param type        11选5 是否和
     * @return
     */
    @RequestMapping(value = "comprehensiveDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result comprehensiveDewdrop(String openTime, String varietyType, String type) {
        if (redisManager.has(RedisKey.comprehensiveDewdropRedisKey(openTime, varietyType, type))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.comprehensiveDewdropRedisKey(openTime, varietyType, type))));
        } else {
            Map<String, Object> map = dewdropService.queryComprehensiveDewdrop(openTime, varietyType, type);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.comprehensiveDewdropRedisKey(openTime, varietyType, type), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.comprehensiveDewdropRedisKey(openTime, varietyType, type), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 单双露珠
     */
    @RequestMapping(value = "singleDoubleDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result singleDoubleDewdrop(String openTime, String varietyType, String type) {
        if (redisManager.has(RedisKey.singleDoubleDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.singleDoubleDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.querySingleDoubleDewdrop(openTime, varietyType, type);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.singleDoubleDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.singleDoubleDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }

    }

    /**
     * 龙虎露珠
     *
     * @param openTime
     * @return
     */
    @RequestMapping(value = "dragonTiger", method = RequestMethod.GET)
    @ResponseBody
    public Result dragonTiger(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.dragonTigerDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.dragonTigerDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryDragonTigerDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.dragonTigerDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            }else {
                redisManager.setByFastJson(RedisKey.dragonTigerDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 综合露珠設置形態
     *
     * @param days 天数
     * @param ball 球号
     * @param type 单双 大小
     *             match 匹配字符
     */
    @RequestMapping(value = "settingForm", method = RequestMethod.GET)
    @ResponseBody
    public Result settingForm(String days, int ball, String type, String match, String varietyType) {
        return Result.success(dewdropService.querySetForm(days, ball, type, match, varietyType));
    }

    /**
     * 总和露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "totalDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result totalDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.totalDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.totalDewdropRedisKey(openTime, varietyType))));
        } else {
            Object map = dewdropService.queryTotalDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.totalDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.totalDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 号码露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "numberDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result numberDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.numberDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.numberDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryNumberDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.numberDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.numberDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 北京快乐8 奇偶盘
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "oddEveDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result oddEveDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.oddEveDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.oddEveDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryOddEveDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.oddEveDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.oddEveDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 北京快乐8 上下盘
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "upDownDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result upDownDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.upDownDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.upDownDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryUpDownDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.upDownDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.upDownDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 尾数大小露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "lastNumberDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result lastNumberDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.lastNumberDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.lastNumberDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryLastNumberDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.lastNumberDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.lastNumberDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 合数单双露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @RequestMapping(value = "sumNumberDewdrop", method = RequestMethod.GET)
    @ResponseBody
    public Result sumNumberDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.sumNumberDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.sumNumberDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.querySumNumberDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.sumNumberDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.sumNumberDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 中发白露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @GetMapping(value = "middleHairDewdrop")
    @ResponseBody
    public Result middleHairDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.middleHairDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.middleHairDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryMiddleHairDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.middleHairDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.middleHairDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 东西南北露珠
     *
     * @param openTime    时间
     * @param varietyType 彩种类型
     */
    @GetMapping(value = "northSouthDewdrop")
    @ResponseBody
    public Result northSouthDewdrop(String openTime, String varietyType) {
        if (redisManager.has(RedisKey.northSouthDewdropRedisKey(openTime, varietyType))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.northSouthDewdropRedisKey(openTime, varietyType))));
        } else {
            Map<String, Object> map = dewdropService.queryNorthSouthDewdrop(openTime, varietyType);
            String now = DateUtils.formatDate(new Date());
            if ("".equals(openTime) || null == openTime || now.equals(openTime)) {
                redisManager.setByFastJson(RedisKey.northSouthDewdropRedisKey(openTime, varietyType), map, 5, TimeUnit.MINUTES);
            } else {
                redisManager.setByFastJson(RedisKey.northSouthDewdropRedisKey(openTime, varietyType), map);
            }
            return Result.success(map);
        }
    }

    /**
     * 香港彩露珠
     *
     * @param code 露珠类型
     */
    @GetMapping(value = "HongKongColour")
    @ResponseBody
    public Result HongKongColour(String code) {
        if (redisManager.has(RedisKey.HongKongColourRedisKey(code))) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.HongKongColourRedisKey(code))));
        } else {
            Map<String, Object> map = dewdropService.queryHongKongColour(code);
            redisManager.setByFastJson(RedisKey.HongKongColourRedisKey(code), map, 1, TimeUnit.DAYS);
            return Result.success(map);
        }
    }


    /**
     * 正码龙虎露珠
     */
    @GetMapping(value = "codeDragonTiger")
    @ResponseBody
    public Result codeDragonTiger() {
        if (redisManager.has(RedisKey.codeDragonTigerRedisKey())) {
            return Result.success(JSONObject.parseObject(redisManager.get(RedisKey.codeDragonTigerRedisKey())));
        } else {
            Map<String, Object> map = dewdropService.queryCodeDragonTiger();
            redisManager.setByFastJson(RedisKey.codeDragonTigerRedisKey(), map, 1, TimeUnit.DAYS);
            return Result.success(map);
        }
    }
}
