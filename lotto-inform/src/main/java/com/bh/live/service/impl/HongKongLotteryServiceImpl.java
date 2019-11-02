package com.bh.live.service.impl;

import com.alibaba.fastjson.JSON;
import com.bh.live.common.redisUtils.RedisManager;
import com.bh.live.common.utils.ChinaDate;
import com.bh.live.dao.HongKongLotteryDao;
import com.bh.live.dao.ZodiacFivePhasesDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.model.inform.ZodiacFivePhases;
import com.bh.live.pojo.res.inform.HongKongMisssRes;
import com.bh.live.service.HongKongLotteryService;
import com.bh.live.service.StatHistoryDrawService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class HongKongLotteryServiceImpl implements HongKongLotteryService {
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	@Autowired
	private RedisManager redisManager;
	
	@Autowired
	private HongKongLotteryDao hongKongLotteryDao;
	@Autowired
	private ZodiacFivePhasesDao zodiacFivePhasesDao;
	@Autowired
    private StatHistoryDrawService statHistoryDrawService;
	/**
	 * 特码遗漏
	 * */
	@Override
	public Map<String, Object> queryTemaMissing(int total) {
		if(total==0){
			total =300;
		}
		LinkedHashMap<String, Object> json = Maps.newLinkedHashMap ();
		List<String> date  = Lists.newArrayList();
		Set<String> keys = redisTemplate.keys("T_VARIETY_CURRENT:11:*");
		List<String> lists= new ArrayList<>(keys);
		//排序
		Collections.sort(lists);
		//最新数据
		Collections.reverse(lists);
		List<Map<String,Object>> list = Lists.newArrayList();
		//获取redisValue 放入List;
		for (int i = 0; i < lists.size(); i++) {
			if(i==total){
				break;}
			String redisValue = redisManager.get(lists.get(i));
			Map<String,Object> mark= (Map<String,Object>)JSON.parse(redisValue);
			list.add(mark);
				
		}
		//拿到特码
		List<String> value =Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			for (Entry<String, Object> m : list.get(i).entrySet()) {
				if("number_seven_ball".equals(m.getKey())){
					value.add(m.getValue().toString());
				}
			}
		}
		for (int j = 0; j < value.size(); j++) {
			if(j ==0 ||!date.contains(value.get(j))){
				json.put(value.get(j), j);
				date.add(value.get(j));
			}
		}
		for (int i = 1; i <= 49; i++) {
			if(json.get(i+"")==null){
				json.put(i+"", total);
			}
		}
		return json;
	}
	/**
	 * 特码两面数据遗漏
	 * */
	@Override
	public Map<String, Object> queryTwoDateMissing() {
		Map<String, Object> json = Maps.newHashMap();
		
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		//大小单双 合单 合双  尾单 尾小
		int[] temp ={0,0,0,0,0,0,0,0};
		for (int i = 0; i < vo.size(); i++) {
			//大的遗漏。
			if(temp[0]==0){
				if(Integer.parseInt(vo.get(i).getNumberSevenBall())<=24){
					temp[0]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("big", expect);
				}
			}
			//小的遗漏。
			if(temp[1]==0){
				if(Integer.parseInt(vo.get(i).getNumberSevenBall())>24){
					temp[1]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("small", expect);
				}
			}
			//单的遗漏。
			if(temp[2]==0){
				if(Integer.parseInt(vo.get(i).getNumberSevenBall())%2 == 0){
					temp[2]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("single", expect);
				}
			}
			//双的遗漏。
			if(temp[3]==0){
				if(Integer.parseInt(vo.get(i).getNumberSevenBall())%2 != 0){
					temp[3]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("double", expect);
				}
			}
			//合单的遗漏。
			if(temp[4]==0){
				String[] sevenBall =vo.get(i).getNumberSevenBall().split("");
				if(Integer.parseInt(sevenBall[0])+Integer.parseInt(sevenBall[1])%2 == 0){
					temp[4]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("hd", expect);
				}
			}
			//合双的遗漏。
			if(temp[5]==0){
				String[] sevenBall =vo.get(i).getNumberSevenBall().split("");
				if(Integer.parseInt(sevenBall[0])+Integer.parseInt(sevenBall[1])%2 != 0){
					temp[5]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("hs", expect);
				}
			}
			//尾单的遗漏。
			if(temp[6]==0){
				String[] sevenBall =vo.get(i).getNumberSevenBall().split("");
				if(Integer.parseInt(sevenBall[sevenBall.length-1])%2 == 0){
					temp[6]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("wd", expect);
				}
			}
			//尾小的遗漏。
			if(temp[7]==0){
				String[] sevenBall =vo.get(i).getNumberSevenBall().split("");
				if(Integer.parseInt(sevenBall[sevenBall.length-1]) >=5){
					temp[7]=1;
					int expect = vo.get(0).getExpect()-vo.get(i).getExpect();
					json.put("wx", expect);
				}
			}
		}
		return json;
	}
	/**
	 * 特码尾数遗漏
	 * */
	@Override
	public Map<String, Object> queryTmLastNumberMissing() {
		Map<String, Object> json = Maps.newHashMap();
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		//号码 0-9
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < vo.size(); j++) {
				String[] sevenBall = vo.get(j).getNumberSevenBall().split("");
				if(i == Integer.parseInt(sevenBall[sevenBall.length-1])){
					int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
					json.put(i+"", expect);
					break;
				}
			}
		}
		return json;
	}
	@Override
	public Map<String, Object> queryZodiacCyclesMiss() {
		Map<String, Object> json = Maps.newHashMap();
		String[] keys={"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
		json.put("keys", keys);
		/*json.put("items", value);*/
		List<Object> iteams = Lists.newArrayList();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		//12生肖对应数字
		List<ZodiacFivePhases> zodiacFivePhases = zodiacFivePhasesDao.queryZodiacNumber(year);
		//农历部分
		List<ZodiacFivePhases> toDayzodiac = zodiacFivePhasesDao.queryZodiacNumber(year-1);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//今年的游戏数据
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryNowYearData();
		for (int i = 0; i < vo.size(); i++) {
			//香港彩按农历计算 2019 年 年前的生肖属性
			if(vo.get(i).getOpenTime().getTime()<=time){
				for (int j = 0; j < toDayzodiac.size(); j++) {
					List<String> cycles = Lists.newArrayList();
					String[] numbers = toDayzodiac.get(j).getNumbers();
					if(Arrays.asList(numbers).contains(vo.get(i).getNumberSevenBall())){
						cycles.add(vo.get(i).getExpect()+"");
						cycles.add(toDayzodiac.get(j).getTargetName());
						iteams.add(cycles);
					}
				}
			}else{
				for (int j = 0; j < zodiacFivePhases.size(); j++) {
					List<String> cycles = Lists.newArrayList();
					String[] numbers = zodiacFivePhases.get(j).getNumbers();
					if(Arrays.asList(numbers).contains(vo.get(i).getNumberSevenBall())){
						cycles.add(vo.get(i).getExpect()+"");
						cycles.add(zodiacFivePhases.get(j).getTargetName());
						iteams.add(cycles);
					}
				}
			}
		}
		List<Object> team = Lists.newArrayList(iteams);
		json.put("items", team);
		Collections.reverse(iteams);
		//omits
		List<Object> omits = Lists.newArrayList();
		List<String> zodiac =Arrays.asList(keys);
		for (int i = 0; i < iteams.size(); i++) {
			if(i%12 == 0 && i!=0){
				omits.add(zodiac);
				zodiac=Arrays.asList(keys);
			}else{
				String[] zodi=	iteams.get(i).toString().replace("]", "").split(",");
				zodiac = zodiac.stream().filter( s -> !s.equals(zodi[1].trim())).collect(Collectors.toList());
			}
		}
		Collections.reverse(omits);
		json.put("omits", omits);
		return json;
	}
	/**
	 * 号码段遗漏
	 * */
	@Override
	public Map<String, Object> queryNumberSegmentMiss() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		//分5段
		String[] segment = new String[]{"01,02,03,04,05,06,07,08,09,10","11,12,13,14,15,16,17,18,19,20","21,22,23,24,25,26,27,28,29,30","31,32,33,34,35,36,37,38,39,40","41,42,43,44,45,46,47,48,49"};
		
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		for (int i = 0; i < segment.length; i++) {
			for (int j = 0; j < vo.size(); j++) {
				if(segment[i].contains(vo.get(j).getNumberSevenBall())){
					int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
					String[] sp =segment[i].split(",");
					json.put(sp[0]+"-"+sp[sp.length-1], expect);
					break;
				}
			}
		}
		return json;
	}
	/**
	 * 正码遗漏
	 * */
	@Override
	public Map<String, Object> queryZmaMissing(int total) {
		if(total==0){
			total =300;
		}
		Map<String,Object> json  = Maps.newHashMap();
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(total);
		String segment = new String("01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49");
		String[] arr =segment.split(",");
		for (int i = 1; i <= arr.length; i++) {
			json.put(i+"", total);
		}
		for (int j = 0; j < vo.size(); j++) {
			String[] openCode = vo.get(j).getOpenCode().split(",");
			for (int i = 0; i < openCode.length-1; i++) {
				if(segment.contains(openCode[i])){
					segment =segment.replace(openCode[i], "");
					int expect =vo.get(0).getExpect()-vo.get(j).getExpect();
					json.put(Integer.parseInt(openCode[i])+"", expect);
				}
			}
		}
		return json;
	}
	/*
	 * 生肖特码遗漏
	 * */
	@Override
	public Map<String, Object> queryZodiacTemaMissing() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		//2019年 12生肖对应数字
		List<ZodiacFivePhases> calendar = zodiacFivePhasesDao.queryZodiacNumber(year);
		//2019年 农历部分 
		List<ZodiacFivePhases> lunar = zodiacFivePhasesDao.queryZodiacNumber(year-1);
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < lunar.size(); i++) {
			for (int j = 0; j < vo.size(); j++) {
				//农历部分数据
				if(vo.get(j).getOpenTime().getTime()<=time){
					String  ball = vo.get(j).getNumberSevenBall();
					if(lunar.get(i).getNumJson().contains(ball)){
						int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(lunar.get(i).getTargetName(), expect);
						break;
					}
				}else{
					String  ball = vo.get(j).getNumberSevenBall();
					if(calendar.get(i).getNumJson().contains(ball)){
						int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(calendar.get(i).getTargetName(), expect);
						break;
					}
				}
			}
			
		}
		return json;
	}
	/**
	 * 生肖正码遗漏
	 * */
	@Override
	public Map<String, Object> queryZodiacZmaMissing() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		//2019年 12生肖对应数字
		List<ZodiacFivePhases> calendar = zodiacFivePhasesDao.queryZodiacNumber(year);
		//2019年 农历部分 
		List<ZodiacFivePhases> lunar = zodiacFivePhasesDao.queryZodiacNumber(year-1);
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < lunar.size(); i++) {
			for (int j = 0; j < vo.size(); j++) {
				//农历部分数据
				String[]  ball = vo.get(j).getOpenCode().split(",");
				if(vo.get(j).getOpenTime().getTime()<=time){
					if(lunar.get(i).getNumJson().contains(ball[0]) || lunar.get(i).getNumJson().contains(ball[1]) ||lunar.get(i).getNumJson().contains(ball[2])
							||lunar.get(i).getNumJson().contains(ball[3])|| lunar.get(i).getNumJson().contains(ball[4]) ||lunar.get(i).getNumJson().contains(ball[5])){
						int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(lunar.get(i).getTargetName(), expect);
						break;
					}
				}else{
					if(calendar.get(i).getNumJson().contains(ball[0]) || calendar.get(i).getNumJson().contains(ball[1]) ||calendar.get(i).getNumJson().contains(ball[2])
							||calendar.get(i).getNumJson().contains(ball[3])|| calendar.get(i).getNumJson().contains(ball[4]) ||calendar.get(i).getNumJson().contains(ball[5])){
						int expect = vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(calendar.get(i).getTargetName(), expect);
						break;
					}
				}
			}
			
		}
		return json;
	}
	/**
	 * 生肖遗漏
	 * */
	@Override
	public Map<String, Object> queryZodiacMissing() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		//2019年 12生肖对应数字
		List<ZodiacFivePhases> calendar = zodiacFivePhasesDao.queryZodiacNumber(year);
		//2019年 农历部分 
		List<ZodiacFivePhases> lunar = zodiacFivePhasesDao.queryZodiacNumber(year-1);
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			try {
				time= ChinaDate.lunarToSolar(year+"0101", true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		for (int i = 0; i < lunar.size(); i++) {
			for (int j = 0; j < vo.size(); j++) {
				//农历部分数据
				String[]  ball = vo.get(j).getOpenCode().split(",");
				if(vo.get(j).getOpenTime().getTime()<=time){
					for (int k = 0; k < ball.length; k++) {
						if(lunar.get(i).getNumJson().contains(ball[k])){
							json.put(vo.get(j).getExpect()+"", lunar.get(i).getTargetName());
						}
					}
				}else{
					for (int k = 0; k < ball.length; k++) {
						if(calendar.get(i).getNumJson().contains(ball[k])){
							json.put(vo.get(j).getExpect()+"", calendar.get(i).getTargetName());
						}
					}
				}
			}
			
		}
		return json;
	}
	/**
	 * 波色遗漏
	 * */
	@Override
	public Map<String, Object> queryBoseMissing() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		//2019年 12生肖对应数字
		List<ZodiacFivePhases> calendar = zodiacFivePhasesDao.queryZodiac();
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(100);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3波色
		for (int i = 0; i < calendar.size(); i++) {
			for (int j = 0; j < vo.size(); j++) {
				String  ball = vo.get(j).getNumberSevenBall();
				if(vo.get(j).getOpenTime().getTime()<=time){
					if(calendar.get(i).getNumJson().contains(ball)){
						int expect =vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(calendar.get(i).getTargetName(), expect);
						break;
					}
				}else{
					if(calendar.get(i).getNumJson().contains(ball)){
						int expect =vo.get(0).getExpect()-vo.get(j).getExpect();
						json.put(calendar.get(i).getTargetName(), expect);
						break;
					}
				}
			}
			
		}
		return json;
	}
	/**
	 * 历史同期
	 * */
	@Override
	public Map<String, Object> queryHistoricalPeriod() {
		Map<String,Object> json = Maps.newLinkedHashMap();
		List<HongKongMisssRes> vo  =hongKongLotteryDao.queryTwoDateMiss(1);
		List<StatHistoryDraw> draws = hongKongLotteryDao.queryHistoricalPeriod(vo.get(0).getExpect());
		Calendar cal1 = Calendar.getInstance();
		for (int i = 0; i < draws.size(); i++) {
			int year =draws.get(i).getOpenTime().getYear()+1900;
			List<ZodiacFivePhases> lunar = zodiacFivePhasesDao.queryZodiacNumber(year);
			String[] openCode = draws.get(i).getOpenCode().split(",");
			for (int k = 0; k < openCode.length; k++) {
				for (int j = 0; j < lunar.size(); j++) {
					if(lunar.get(j).getNumJson().contains(openCode[k])){
						String zodiac =draws.get(i).getZodiac() ==null ? "":draws.get(i).getZodiac()+",";
						draws.get(i).setZodiac(zodiac+lunar.get(j).getTargetName());
					}
				}
			}
				draws.get(i).setExpect(draws.get(i).getExpect().substring(draws.get(i).getExpect().length() - 3));
				draws.get(i).setSpecialCodeBigSmall(Integer.parseInt(draws.get(i).getNumberSevenBall()) > 24 ? "大" : "小");
				draws.get(i).setSpecialCodeSingleDouble(Integer.parseInt(draws.get(i).getNumberSevenBall()) % 2 == 0 ? "双" : "单");
		}
		json.put("pageList", draws);
		return json;
	}
	/**
	 * 香港彩历史遗漏
	 * */
	@Override
	public Map<String, Object> queryHistoryMissing(String date,String type) {
		Map<String,Object> json = Maps.newLinkedHashMap();
		int year = Calendar.getInstance().get(Calendar.YEAR);  
		List<ZodiacFivePhases> calendar = zodiacFivePhasesDao.queryZodiacFivePhases(year);
		List<ZodiacFivePhases> lunar = zodiacFivePhasesDao.queryZodiacFivePhases(year-1);
		Map<String,Object> param = Maps.newLinkedHashMap();
		param.put("date", date);
		param.put("varietyType", 11);
		Map<String, Object>  map =statHistoryDrawService.page(param);
		List<StatHistoryDraw> list = (List<StatHistoryDraw>) map.get("pageList");
		Collections.reverse(list);
		Long time= 0L;
		try {
			time= ChinaDate.lunarToSolar(year+"0101", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//生肖属性
		calendar = zodiacValue(calendar,type);
		lunar =zodiacValue(lunar,type);
		//12生肖循环
		for (int i = 0; i < lunar.size(); i++) {
			int temp = 0;
			int count =0;
			int expect=0;
			for (int j = 0; j < list.size(); j++) {
				String  ball = list.get(j).getNumberSevenBall();
				if(list.get(j).getOpenTime().getTime()<=time){
					if(lunar.get(i).getNumJson().contains(ball)){
						count++;
						expect =Integer.parseInt(list.get(j).getExpect())-Integer.parseInt(list.get(temp).getExpect())-1;
						temp=j;
						Object number= 	json.get(lunar.get(i).getTargetName()) == null ? 0:json.get(lunar.get(i).getTargetName());
						if((Integer)number< expect) {
                            json.put(lunar.get(i).getTargetName(), expect);
                        }
					}
				}else{
					if(calendar.get(i).getNumJson().contains(ball)){
						count++;
						//-1：当前期数
						expect =Integer.parseInt(list.get(j).getExpect())-Integer.parseInt(list.get(temp).getExpect())-1;
						temp=j;
						Object number= 	json.get(lunar.get(i).getTargetName()) == null ? 0:json.get(lunar.get(i).getTargetName());
						if((Integer)number< expect) {
                            json.put(calendar.get(i).getTargetName(), expect);
                        }
					}
				}
			}
			json.put(calendar.get(i).getTargetName(), json.get(calendar.get(i).getTargetName())+"-"+count);
		}
		return json;
	}
	private List<ZodiacFivePhases> zodiacValue( List<ZodiacFivePhases> calendar ,String type) {
		String[] zodiacKeys="鼠-鼠, 牛-牛, 虎-虎, 兔-兔, 龙-龙, 蛇-蛇, 马-马, 羊-羊, 猴-猴, 鸡-鸡, 狗-狗, 猪-猪".split(",");
		String[] fiveElements="金-金,木-木,水-水,火-火,土-土".split(",");
		String[] poultrys ={"家禽-牛,马,羊,鸡,狗,猪","野兽-鼠,虎,兔,龙,蛇,猴"};//家禽
		String[] sexs ={"男肖-鼠,牛,虎,龙,马,猴,狗","女肖-兔,蛇,羊,鸡,猪"};//男女
		String[] words ={"天肖-牛,兔,龙,马,猴,猪","地肖-鼠,虎,蛇,羊,鸡,狗"};//天地
		String[] seasons={"春肖-虎,兔,龙","夏肖-蛇,马,羊","秋肖-猴,鸡,狗","冬肖-鼠,牛,猪"};//四季
		String[] quadrivium={"琴肖-兔,蛇,鸡","棋肖-鼠,牛,狗","书肖-虎,龙,马","画肖-羊,猴,猪"};//四艺
		String[] threeColour={"红肖-鼠,兔,马,鸡","蓝肖-虎,蛇,猴,猪","绿肖-牛,龙,羊,狗"};//三色
		List<ZodiacFivePhases> value= null;
		if("sx".equals(type)){
			value =ZodiacConfiguration( calendar, zodiacKeys);
		}else if("wx".equals(type)){
			value =ZodiacConfiguration( calendar,   fiveElements);
		}else if("jy".equals(type)){
			value =ZodiacConfiguration( calendar,  poultrys);
		}else if("nv".equals(type)){
			value =ZodiacConfiguration( calendar,  sexs);
		}else if("td".equals(type)){
			value =ZodiacConfiguration( calendar,  words);
		}else if("sj".equals(type)){
			value =ZodiacConfiguration( calendar,  seasons);
		}else if("qj".equals(type)){
			value =ZodiacConfiguration( calendar,  quadrivium);
		}else if("ss".equals(type)){
			value =ZodiacConfiguration( calendar,  threeColour);
		}
		return value;
	}
	private List<ZodiacFivePhases> ZodiacConfiguration( List<ZodiacFivePhases> list, String[] zodiacKeys) {
		List<ZodiacFivePhases> value = Lists.newArrayList();
		for (int k = 0; k < zodiacKeys.length; k++) {
			StringBuffer sb = new  StringBuffer();
			for (int j = 0; j < list.size(); j++) {
				if( zodiacKeys[k].contains(list.get(j).getTargetName())){
					sb.append(","+list.get(j).getNumJson());
				}
			}
			ZodiacFivePhases zodi = new ZodiacFivePhases();
			zodi.setTargetName(zodiacKeys[k].split("-")[0].trim());
			zodi.setNumJson(sb.toString());
			value.add(zodi);
		}
		return value;
	}

}
