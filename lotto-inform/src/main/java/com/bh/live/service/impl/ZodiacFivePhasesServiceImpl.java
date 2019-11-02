package com.bh.live.service.impl;

import com.bh.live.common.constant.RedisKey;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.dao.ZodiacFivePhasesDao;
import com.bh.live.model.inform.ZodiacFivePhases;
import com.bh.live.service.ZodiacFivePhasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 十二生肖和五行
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/04 16:42
 */
@Service
public class ZodiacFivePhasesServiceImpl implements ZodiacFivePhasesService {

    @Resource
    private ZodiacFivePhasesDao zodiacFivePhasesDao;

    @Autowired
    private RedisManager redisManager;

    /**
     * 获取指定期数的结果对象
     * 类型  五行0 生肖1
     *
     * @param year 年份
     * @return
     */
    @Override
    public Map<String, Object> queryZodiacFivePhases(int year) {
        // 鼠 牛 虎 兔 龙 蛇 马 羊 猴 鸡 狗 猪
        String mouse = null, cattle = null, tiger = null, rabbit = null, dragon = null,
                snake = null, horse = null, sheep = null, monkey = null, chicken = null, dog = null, pig = null;

        Map<String, Object> result = new HashMap<>();
        List<ZodiacFivePhases> zodiacFivePhases = zodiacFivePhasesDao.queryZodiacFivePhases(year);
        List<ZodiacFivePhases> zodiacList = new ArrayList<>();
        List<ZodiacFivePhases> fivePhasesList = new ArrayList<>();
        for (ZodiacFivePhases model : zodiacFivePhases) {
            if (model.getTypeName() == 0) {
                fivePhasesList.add(model);
            } else if (model.getTypeName() == 1) {
                zodiacList.add(model);
                switch (model.getTargetName()) {
                    case "鼠":
                        mouse = model.getNumJson();
                    case "牛":
                        cattle = model.getNumJson();
                    case "虎":
                        tiger = model.getNumJson();
                    case "兔":
                        rabbit = model.getNumJson();
                    case "龙":
                        dragon = model.getNumJson();
                    case "蛇":
                        snake = model.getNumJson();
                    case "马":
                        horse = model.getNumJson();
                    case "羊":
                        sheep = model.getNumJson();
                    case "猴":
                        monkey = model.getNumJson();
                    case "鸡":
                        chicken = model.getNumJson();
                    case "狗":
                        dog = model.getNumJson();
                    case "猪":
                        pig = model.getNumJson();
                }
            }
        }
        result.put("zodiacList", zodiacList);
        result.put("fivePhasesList", fivePhasesList);
        result.put("waveColorList",zodiacFivePhasesDao.queryZodiac());


        // 家禽 野兽 男肖 女肖 天肖 地肖 春肖 夏肖 秋肖 冬肖 琴肖 棋肖 书肖 画肖 红肖 蓝肖 绿肖
        String poultry = null, beast = null, maleXiao = null, femaleXiao = null, dayXiao = null,
                groundXiao = null, springXiao = null, summerXiao = null, autumnXiao = null, winterXiao = null,
                pianoXiao = null, chessXiao = null, bookXiao = null, paintingXiao = null, redXiao = null, blueXiao = null, greenXiao = null;

        //牛馬羊雞狗豬
        poultry = cattle + "," + horse + "," + sheep + "," + chicken + "," + dog + "," + pig;
        //鼠虎兔龍蛇猴
        beast = mouse + "," + tiger + "," + rabbit + "," + dragon + "," + snake + "," + monkey;

        //鼠牛虎龍馬猴狗
        maleXiao = mouse + "," + cattle + "," + tiger + "," + dragon + "," + horse + "," + monkey +","+ dog;
        //兔蛇羊雞豬
        femaleXiao = rabbit + "," + snake + "," + sheep + "," + chicken + "," + pig;

        //牛兔龍馬猴豬
        dayXiao = cattle + "," + rabbit + "," + dragon + "," + horse + "," + monkey + "," + pig;
        //鼠虎蛇羊雞狗
        groundXiao =  mouse + "," + tiger + "," + snake + "," + sheep + "," + chicken + "," + dog;

        //虎兔龍
        springXiao =  tiger + "," + rabbit + "," + dragon;
        //蛇馬羊
        summerXiao =  snake + "," + horse + "," + sheep;
        //猴雞狗
        autumnXiao =  monkey + "," + chicken + "," + dog;
        //鼠牛豬
        winterXiao =  mouse + "," + cattle + "," + pig;

        //兔蛇雞
        pianoXiao =  rabbit + "," + snake + "," + chicken;
        //鼠牛狗
        chessXiao =  mouse + "," + cattle + "," + dog;
        //虎龍馬
        bookXiao =  tiger + "," + dragon + "," + horse+ "," + dog;
        //羊猴豬
        paintingXiao =  sheep + "," + monkey + "," + pig + "," + dog;

        //鼠兔馬雞
        redXiao =  mouse + "," + rabbit + "," + horse + "," + chicken+ "," + dog;
        //虎蛇猴豬
        blueXiao =  tiger + "," + snake + "," + monkey + "," + pig + "," + dog;
        //牛龍羊狗
        greenXiao =  cattle + "," + dragon + "," + sheep + "," + dog + "," + dog;

        redisManager.setByFastJson(RedisKey.poultryRedis(year),poultry);
        redisManager.setByFastJson(RedisKey.beastRedis(year),beast);
        redisManager.setByFastJson(RedisKey.maleXiaoRedis(year),maleXiao);
        redisManager.setByFastJson(RedisKey.femaleXiaoRedis(year),femaleXiao);
        redisManager.setByFastJson(RedisKey.dayXiaoRedis(year),dayXiao);
        redisManager.setByFastJson(RedisKey.groundRedis(year),groundXiao);
        redisManager.setByFastJson(RedisKey.springXiaoRedis(year),springXiao);
        redisManager.setByFastJson(RedisKey.summerXiaoRedis(year),summerXiao);
        redisManager.setByFastJson(RedisKey.autumnXiaoRedis(year),autumnXiao);
        redisManager.setByFastJson(RedisKey.winterXiaoRedis(year),winterXiao);
        redisManager.setByFastJson(RedisKey.pianoXiaoRedis(year),pianoXiao);
        redisManager.setByFastJson(RedisKey.chessXiaoRedis(year),chessXiao);
        redisManager.setByFastJson(RedisKey.bookXiaoRedis(year),bookXiao);
        redisManager.setByFastJson(RedisKey.paintingXiaoRedis(year),paintingXiao);
        redisManager.setByFastJson(RedisKey.redXiaoRedisKey(year),redXiao);
        redisManager.setByFastJson(RedisKey.blueXiaoRedisKey(year),blueXiao);
        redisManager.setByFastJson(RedisKey.greenXiaoRedisKey(year),greenXiao);
        return result;
    }

    @Override
    public List<ZodiacFivePhases> queryZodiac() {
        return zodiacFivePhasesDao.queryZodiac();
    }

}
