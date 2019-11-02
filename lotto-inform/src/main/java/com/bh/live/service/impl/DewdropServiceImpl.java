package com.bh.live.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.enums.VarietyTypeEnum;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.DewdropDao;
import com.bh.live.dao.ZodiacFivePhasesDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.model.inform.ZodiacFivePhases;
import com.bh.live.pojo.req.inform.DewDropReq;
import com.bh.live.pojo.res.inform.*;
import com.bh.live.service.DewdropService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DewdropServiceImpl implements DewdropService {

	@Resource
    private DewdropDao dewdropDao;

	@Resource
	private ZodiacFivePhasesDao zodiacFivePhasesDao;
    /**
     * 1-10个数字 1-5 为前  6-10为后
     * */
	@Override
	public Map<String, Object> queryDewdropAround(String param, String varietyType) {

		//有时间传过来查当天 没有查3天内数据
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		Map<String, Object> json = Maps.newHashMap();
		//号码1-10
		Map<String,Object> number = Maps.newHashMap();
		Map<String,Object> number1 = Maps.newHashMap();
		Map<String,Object> number2 = Maps.newHashMap();
		Map<String,Object> number3 = Maps.newHashMap();
		Map<String,Object> number4 = Maps.newHashMap();
		Map<String,Object> number5 = Maps.newHashMap();
		Map<String,Object> number6 = Maps.newHashMap();
		Map<String,Object> number7 = Maps.newHashMap();
		Map<String,Object> number8 = Maps.newHashMap();
		Map<String,Object> number9 = Maps.newHashMap();
		Map<String,Object> number10 = Maps.newHashMap();
		number.put("1", number1);
		number.put("2", number2);
		number.put("3", number3);
		number.put("4", number4);
		number.put("5", number5);
		number.put("6", number6);
		number.put("7", number7);
		number.put("8", number8);
		number.put("9", number9);
		number.put("10", number10);
		//所有顺序
		json.put("number", number);
		
		//数据前后
		List<String> one =Lists.newArrayList();
		List<String> two =Lists.newArrayList();
		List<String> three =Lists.newArrayList();
		List<String> four =Lists.newArrayList();
		List<String> five =Lists.newArrayList();
		List<String> six =Lists.newArrayList();
		List<String> seven =Lists.newArrayList();
		List<String> eight =Lists.newArrayList();
		List<String> nine =Lists.newArrayList();
		List<String> ten =Lists.newArrayList();
		
		//幸运飞艇数据

		//查询今日 默认查今日前后数量
		List<StatHistoryDraw> draws = dewdropDao.queryStatHistoryPK10(dateMap.get("start"),dateMap.get("end"),varietyType);
		for (int i = 0; i < draws.size(); i++) {
			String [] oneCode = draws.get(i).getOpenCode().split(",");
			for (int j = 0; j < oneCode.length; j++) {
				if("1".equals(oneCode[j])){
					if(j<5){
					one.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number1.put("qian", ((Integer)number1.get("qian")==null ?1:(Integer)number1.get("qian"))+1);
						}
					}else{
						one.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number1.put("hou",  ((Integer)number1.get("hou")==null ?1:(Integer)number1.get("hou"))+1);
						}
					}
				}else if("2".equals(oneCode[j])){
					if(j<5){
						two.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number2.put("qian", ((Integer)number2.get("qian")==null ?1:(Integer)number2.get("qian"))+1);
						}
					}else{
						two.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number2.put("hou", ((Integer)number2.get("hou")==null ?1:(Integer)number2.get("hou"))+1);
						}
					}
				}else if("3".equals(oneCode[j])){
					if(j<5){
						three.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number3.put("qian", ((Integer)number3.get("qian")==null ?1:(Integer)number3.get("qian"))+1);
						}
					}else{
						three.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number3.put("hou", ((Integer)number3.get("hou")==null ?1:(Integer)number3.get("hou"))+1);
						}
					}
				}else if("4".equals(oneCode[j])){
					if(j<5){
						four.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number4.put("qian", ((Integer)number4.get("qian")==null ?1:(Integer)number4.get("qian"))+1);
						}
					}else{
						four.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number4.put("hou", ((Integer)number4.get("hou")==null ?1:(Integer)number4.get("hou"))+1);
						}
					}
				}else if("5".equals(oneCode[j])){
					if(j<5){
						five.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number5.put("qian", ((Integer)number5.get("qian")==null ?1:(Integer)number5.get("qian"))+1);
						}
					}else{
						five.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number5.put("hou", ((Integer)number5.get("hou")==null ?1:(Integer)number5.get("hou"))+1);
						}
					}
				}else if("6".equals(oneCode[j])){
					if(j<5){
						six.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number6.put("qian", ((Integer)number6.get("qian")==null ?1:(Integer)number6.get("qian"))+1);
						}
					}else{
						six.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number6.put("hou", ((Integer)number6.get("hou")==null ?1:(Integer)number6.get("hou"))+1);
						}
					}
				}else if("7".equals(oneCode[j])){
					if(j<5){
						seven.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number7.put("qian",((Integer)number7.get("qian")==null ?1:(Integer)number7.get("qian"))+1);
						}
					}else{
						seven.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number7.put("hou", ((Integer)number7.get("hou")==null ?1:(Integer)number7.get("hou"))+1);
						}
					}
				}else if("8".equals(oneCode[j])){
					if(j<5){
						eight.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number8.put("qian", ((Integer)number8.get("qian")==null ?1:(Integer)number8.get("qian"))+1);
						}
					}else{
						eight.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number8.put("hou", ((Integer)number8.get("hou")==null ?1:(Integer)number8.get("hou"))+1);
						}
					}
				}else if("9".equals(oneCode[j])){
					if(j<5){
						nine.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number9.put("qian", ((Integer)number9.get("qian")==null ?1:(Integer)number9.get("qian"))+1);
						}
					}else{
						nine.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number9.put("hou", ((Integer)number9.get("hou")==null ?1:(Integer)number9.get("hou"))+1);
						}
					}
				}else if("10".equals(oneCode[j])){
					if(j<5){
						ten.add("前");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number10.put("qian", ((Integer)number10.get("qian")==null ?1:(Integer)number10.get("qian"))+1);
						}
					}else{
						ten.add("后");
						if(st.get("st").getTime() <= draws.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= draws.get(i).getOpenTime().getTime()){
							number10.put("hou", ((Integer)number10.get("hou")==null ?1:(Integer)number10.get("hou"))+1);
						}
					}
				}
			}
		}
		JSONArray box =new JSONArray();
		box.add(one); 
		JSONArray box2 =new JSONArray();
		box2.add(two); 
		JSONArray box3 =new JSONArray();
		box3.add(three); 
		JSONArray box4 =new JSONArray();
		box4.add(four); 
		JSONArray box5 =new JSONArray();
		box5.add(five); 
		JSONArray box6 =new JSONArray();
		box6.add(six); 
		JSONArray box7 =new JSONArray();
		box7.add(seven); 
		JSONArray box8 =new JSONArray();
		box8.add(eight); 
		JSONArray box9 =new JSONArray();
		box9.add(nine); 
		JSONArray box10 =new JSONArray();
		box10.add(ten); 
		number1.put("list", box);
		number2.put("list", box2);
		number3.put("list", box3);
		number4.put("list", box4);
		number5.put("list", box5);
		number6.put("list", box6);
		number7.put("list", box7);
		number8.put("list", box8);
		number9.put("list", box9);
		number10.put("list", box10);
		return json;
	}

	@Override
	public Result queryInfoDome(String a, String b) {
		return Result.success( dewdropDao.queryInfoDome());
	}

	/**
	 * 冠军和露珠
	 * 冠军和值 3-11为小 12-19为大
	 * 冠军和值为单为单，和值为双为双  
	 * */
	@Override
	public Map<String, Object> queryDewdropChampion(String param,String varietyType) {
		//有时间传过来查当天 没有查3天内数据
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		Map<String, Object> json = Maps.newHashMap();
		JSONObject singleDouble = new JSONObject();
		JSONObject bigSmall = new JSONObject();
		bigSmall.put("name", "冠军大小");
		singleDouble.put("name", "冠亚单双");
		//幸运飞艇游戏数据
		List<StatHistoryDraw> dewdropData = dewdropDao.queryStatHistoryPK10(dateMap.get("start"),dateMap.get("end"),varietyType);
		List<String> bigSmallList = Lists.newArrayList();
		List<String> dansuanlist = Lists.newArrayList();
		singleDouble.put("list", dansuanlist);
		bigSmall.put("list", bigSmallList);

		//查询今日 默认查今日前后数量
		for (int i = 0; i < dewdropData.size(); i++) {
				if("小".equals(dewdropData.get(i).getBigSmall())){
					bigSmallList.add("小");
					if(st.get("st").getTime() <= dewdropData.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= dewdropData.get(i).getOpenTime().getTime()){
						bigSmall.put("qian", ((Integer)bigSmall.get("qian")==null ?1:(Integer)bigSmall.get("qian"))+1);
					}
					
				}else{
					bigSmallList.add("大");
					if(st.get("st").getTime() <= dewdropData.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= dewdropData.get(i).getOpenTime().getTime()){
						bigSmall.put("hou", ((Integer)bigSmall.get("hou")==null ?1:(Integer)bigSmall.get("hou"))+1);
					}
				}
				
				if("双".equals(dewdropData.get(i).getSingleDouble())){
					dansuanlist.add("双");
					if(st.get("st").getTime() <= dewdropData.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= dewdropData.get(i).getOpenTime().getTime()){
						singleDouble.put("hou", ((Integer)singleDouble.get("hou")==null ?1:(Integer)singleDouble.get("hou"))+1);
					}
				}else{
					dansuanlist.add("单");
					if(st.get("st").getTime() <= dewdropData.get(i).getOpenTime().getTime() && st.get("ed").getTime()>= dewdropData.get(i).getOpenTime().getTime()){
						singleDouble.put("qian", ((Integer)singleDouble.get("qian")==null ?1:(Integer)singleDouble.get("qian"))+1);
					}
				}
		}

		
		json.put("dansuan", singleDouble);
		json.put("daxiao", bigSmall);
		
		return json;
	}
	
	/**
	 * 综合露珠
	 * 单双 大小龙虎  位置 冠亚和
	 * */
	@Override
	public Map<String, Object> queryComprehensiveDewdrop(String param,String varietyType,String type) {
		//有时间传过来查当天 没有查3天内数据
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		Map<String, Object> json = Maps.newHashMap();
		
		//号码大小单双龙虎
		
		
		//开奖球数量
		int siz = FormStatUtil.getTypeOfBallNumber(varietyType);
		
		for (int i = 1; i <= siz; i++) {
			DewdropResultRes one = new DewdropResultRes();
			DewDropReq dto = new DewDropReq();
			dto.setEndDate(dateMap.get("end"));
			dto.setStartDate(dateMap.get("start"));
			dto.setVarietyType(varietyType);
			dto.setType(type);
			//1-10字段名
			dto.setField(FormStatUtil.getNumberStr(i));
			if (i<=5){
				//龙虎
				dto.setPaream(FormStatUtil.getNumberStrDragonTiger(i));
			}
			//查询大小单双龙虎质和。
			List<DewdropRes>  list= dewdropDao.querySmallBig(dto);
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			//质和
			StringBuffer sb3 = null ;
			if(VarietyTypeEnum.HEAVY_TIME.code().toString().equals(varietyType) ||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
					||VarietyTypeEnum.TIME.code().toString().equals(varietyType)||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
				sb3 = new StringBuffer();
			}
			StringBuffer sb4 = new StringBuffer();
			
			//尾数大小
			StringBuffer sb5 =null;
			//合数单双
			StringBuffer sb6 = null;
			if(VarietyTypeEnum.FAST10.code().toString().equals(varietyType) ||VarietyTypeEnum.LUCKY_FARM.code().toString().equals(varietyType)){
				sb5 = new StringBuffer();
				sb6 = new StringBuffer();
			}
			comprehensiveNumber(varietyType, st, one, list, sb, sb2, sb3, sb4, sb5, sb6);
			
			
			relativeMethod(one, sb, sb2, sb3, sb4,sb5,sb6);
			
			//查询大小单双龙虎的今日累计数CumulativeQuantityRes量
			dto.setStartDate(dateMap.get("oneDay"));
			CumulativeQuantityRes vo =dewdropDao.queryCumulativeQuantity(dto);
			one.setBig(vo.getBig());
			one.setSmall(vo.getSmall());
			one.setSingle(vo.getSingle());
			one.setDoubles(vo.getDoubles());
			one.setFlat(vo.getFlat());
			//重庆时时彩 新疆时时彩 天津时时彩
			if(VarietyTypeEnum.HEAVY_TIME.code().toString().equals(varietyType)||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
					||VarietyTypeEnum.TIME.code().toString().equals(varietyType)||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
				one.setPrime(vo.getPrime());
				one.setComposite(vo.getComposite());
			}
			if(i<6){
				one.setDragon(vo.getDragon());
				one.setTiger(vo.getTiger());
			}
			
			json.put(i+"", one);
		}
		
		//冠亚和 特殊号码
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end").toString());
		dto.setStartDate(dateMap.get("start").toString());
		dto.setVarietyType(varietyType);
		dto.setType(type);
		List<DewdropRes> crownSubList =dewdropDao.queryCrownSub(dto);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sb5 = new StringBuffer();
		StringBuffer sb6 = new StringBuffer();
	
		DewdropResultRes crownSub = new DewdropResultRes();
		SumBigSmall(varietyType, st, crownSub, crownSubList, sb, sb2, sb3, null, sb5, sb6);
		relativeMethod(crownSub, sb, sb2, sb3, null, sb5, sb6);
		
		dto.setCrownSub(FormStatUtil.getNumberStr(11));
		dto.setStartDate(dateMap.get("oneDay").toString());
		CumulativeQuantityRes vo =dewdropDao.queryCumulativeQuantity(dto);
		crownSub.setBig(vo.getBig());
		crownSub.setSmall(vo.getSmall());
		crownSub.setSingle(vo.getSingle());
		crownSub.setDoubles(vo.getDoubles());
		crownSub.setFlat(vo.getFlat());
		json.put("11", crownSub);
		
		return json;
	}
	/**
	 * 1-10号的 尾数大小 合数单双
	 * */
	private void comprehensiveNumber(String varietyType, Map<String, Date> st, DewdropResultRes one,
                                     List<DewdropRes> list, StringBuffer sb, StringBuffer sb2, StringBuffer sb3, StringBuffer sb4,
                                     StringBuffer sb5, StringBuffer sb6) {
		for (int j = 0; j < list.size(); j++) {
					if(sb != null) {
						sb.append(list.get(j).getBigSmall());
					}
					if(sb2 != null) {
						sb2.append(list.get(j).getSingleDouble());
					}
					if(VarietyTypeEnum.HEAVY_TIME.code().toString().equals(varietyType)||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
							||VarietyTypeEnum.TIME.code().toString().equals(varietyType)||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
						sb3.append(list.get(j).getPrimeCompound());
					}
					if(list.get(j).getDragonTiger() != null) {
						sb4.append(list.get(j).getDragonTiger());
					}
					if(VarietyTypeEnum.FAST10.code().toString().equals(varietyType) ||VarietyTypeEnum.LUCKY_FARM.code().toString().equals(varietyType)){
						String number =list.get(j).getNumber();
						int lastNumber=0;
						int fastNumber=0;
						if(Integer.parseInt(number)>=10){
							lastNumber =Integer.parseInt(list.get(j).getNumber().split("")[number.length()-1]);
							fastNumber =Integer.parseInt(number.split("")[0]);
						}else{
							lastNumber=Integer.parseInt(number);
						}
						
						if(sb5 != null){
							if(lastNumber >=5){
								sb5.append("大");
								if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
									one.setMantissaBig(one.getMantissaBig()+1);
								}
							}else{
								sb5.append("小");
								if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
									one.setMantissaSmall(one.getMantissaSmall() + 1);
								}
							}
						}
						
						if(sb6 != null){
							if((lastNumber+fastNumber)%2 ==0){
								if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
									one.setSumDoubles(one.getSumDoubles() + 1);
								}
								sb6.append("双");
							}else{
								if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
									one.setSumSingle(one.getSumSingle() + 1);
								}
								sb6.append("单");
							}
						}
					}
				}
		
	}

	/**
	 * 总和 尾数大小 和值单双
	 * */
	private void SumBigSmall(String varietyType, Map<String, Date> st, DewdropResultRes one, List<DewdropRes> list,
			StringBuffer sb, StringBuffer sb2, StringBuffer sb3, StringBuffer sb4, StringBuffer sb5, StringBuffer sb6) {
		for (int j = 0; j < list.size(); j++) {
			
			if(sb != null) {
				sb.append(list.get(j).getBigSmall());
			}
			if(sb2 != null) {
				sb2.append(list.get(j).getSingleDouble());
			}
			if(VarietyTypeEnum.HEAVY_TIME.code().toString().equals(varietyType)||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
					||VarietyTypeEnum.TIME.code().toString().equals(varietyType)||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
				sb3.append(list.get(j).getPrimeCompound());
			}
			if(list.get(j).getDragonTiger() != null) {
				sb4.append(list.get(j).getDragonTiger());
			}
			if(VarietyTypeEnum.FAST10.code().toString().equals(varietyType) ||VarietyTypeEnum.LUCKY_FARM.code().toString().equals(varietyType) ||VarietyTypeEnum.SELECTED_11_5.code().toString().equals(varietyType)){
				String number =list.get(j).getCrownSub();
				int lastNumber=0;
				int fastNumber=0;
				if(Integer.parseInt(number)>=10){
					lastNumber =Integer.parseInt(number.split("")[number.length()-1]);
					fastNumber =Integer.parseInt(number.split("")[0]);
				}else{
					lastNumber=Integer.parseInt(number);
				}
				
				if(sb5 != null){
					if(lastNumber >=5){
						sb5.append("大");
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
							one.setMantissaBig(one.getMantissaBig()+1);
						}
					}else{
						sb5.append("小");
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
							one.setMantissaSmall(one.getMantissaSmall() + 1);
						}
					}
				}
				
				if(sb6 != null){
					if((lastNumber+fastNumber)%2 ==0) {
						if (list.get(j).getOpenTime().getTime() >= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <= st.get("ed").getTime()){
							one.setSumDoubles(one.getSumDoubles() + 1);
						}
						sb6.append("双");
					}else{
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
							one.setSumSingle(one.getSumSingle() + 1);
						}
						sb6.append("单");
					}
				}
			}
		}
	}
	
	/**
	 * 号码 尾数单双
	 * */
	private void numberMantissaSingleDouble(Map<String, Date> st, DewdropResultRes one, List<DewdropRes> list, StringBuffer sb6) {
		for (int j = 0; j < list.size(); j++) {
				String number =list.get(j).getNumber();
				int lastNumber=0;
				int fastNumber=0;
				if(Integer.parseInt(number)>=10){
					lastNumber =Integer.parseInt(number.split("")[number.length()-1]);
					fastNumber =Integer.parseInt(number.split("")[0]);
				}else{
					lastNumber=Integer.parseInt(number);
				}
				 
				if(sb6 != null){
					if((lastNumber+fastNumber)%2 ==0){
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
							one.setSumDoubles(one.getSumDoubles()+1);
						}
						sb6.append("双");
					}else{
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
							one.setSumSingle(one.getSumSingle() + 1);
						}
						sb6.append("单");
					}
				}
		}
	}
	/**
	 * 号码  和值大小
	 * */
	private void numberSumBigSmall(Map<String, Date> st, DewdropResultRes one, List<DewdropRes> list, StringBuffer sb5) {
		for (int j = 0; j < list.size(); j++) {
				String number =list.get(j).getNumber();
				int lastNumber=0;
				if(Integer.parseInt(number)>=10){
					lastNumber =Integer.parseInt(number.split("")[number.length()-1]);
				}else{
					lastNumber=Integer.parseInt(number);
				}
				if(sb5 != null){
					if(lastNumber >=5){
						sb5.append("大");
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
							one.setMantissaBig(one.getMantissaBig()+1);
						}
					}else{
						sb5.append("小");
						if(list.get(j).getOpenTime().getTime()>= st.get("st").getTime() && list.get(j).getOpenTime().getTime() <=st.get("ed").getTime()) {
							one.setMantissaSmall(one.getMantissaSmall() + 1);
						}
					}
				}
		}
	}
	/**
	 * 查询范围 
	 * param 日期
	 * map key start end oneDay
	 * */
	private Map<String, String> dateMap(String param,String varietyType) {
		Map<String,String> map = Maps.newHashMap(); 
		String startDate;
		String endDate;
		String oneDate;
		LocalDateTime nowDay = LocalDateTime.now();
		LocalDateTime  today = nowDay.minusDays(3);
		LocalDateTime  oneDay = nowDay.minusDays(1);
		LocalDateTime  toDay = nowDay.minusDays(-1);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(param==null || "".equals(param)){
			//幸运飞艇,圣地 新疆时时彩 最晚 为 4点
			if(VarietyTypeEnum.LUCKY_AIRSHIP.code().toString().equals(varietyType) ||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
					||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
				startDate = df.format(today)+" 05:00:00";
				endDate= df.format(toDay)+" 05:00:00";
				oneDate = df.format(nowDay)+" 05:00:00";
			}else{
				startDate = df.format(today)+" 00:00:00";
				endDate= df.format(nowDay)+" 23:59:59";
				oneDate = df.format(oneDay)+" 23:59:59";
			}
		}else{
			
			if(VarietyTypeEnum.LUCKY_AIRSHIP.code().toString().equals(varietyType) ||VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType)
					||VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String endTime = null;
				try {
					Date time =sdf.parse(param);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(time);
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					endTime =sdf.format(calendar.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				startDate = param+" 05:00:00";
				endDate= endTime+" 05:00:00";
				oneDate = param+" 05:00:00";
			}else{
				startDate =  param+" 00:00:00";
				endDate =  param+" 23:59:59";
				oneDate =  param+" 00:00:00";
			}
		}
		
		//香港彩查今年内的数据
		if(VarietyTypeEnum.HONG_KONG_LOTTERY.code().toString().equals(varietyType)){
			endDate = df.format(today)+" 23:59:59";
			startDate =nowDay.getYear()+"-01-01 00:00:00";
		}
		map.put("start", startDate);
		map.put("end", endDate);
		map.put("oneDay", oneDate);
		
		return map;
	}
	/**
	 * String 转 Date st ed
	 * */
	private Map<String,Date> getDate(String startDate,String endDate){
		Map<String,Date> map = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date st = null;
		Date ed = null;
		try {
			st = sdf.parse(startDate);
			ed = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("st", st);
		map.put("ed", ed);
		return map;
	}
	//个大小单双龙虎 放入 List里
	private void relativeMethod(DewdropResultRes one, StringBuffer sb, StringBuffer sb2, StringBuffer sb3,
			StringBuffer sb4,StringBuffer sb5,StringBuffer sb6) {
		List<String> list = Lists.newArrayList();
		if(sb!= null) {
			list.add(sb.toString());
		}else if(sb2!= null) {
			list.add(sb2.toString());
		}
		if(sb3!= null) {
			list.add(sb3.toString());
		}
		if(sb4!= null) {
			list.add(sb4.toString());
		}
		if(sb5!= null) {
			list.add(sb5.toString());
		}
		if(sb6!= null) {
			list.add(sb6.toString());
		}
		one.setList(list);
	}
	
	/**
	 * 号码单双露珠
	 * */
	@Override
	public Map<String, Object> querySingleDoubleDewdrop(String param,String varietyType,String type) {
		Map<String ,Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end"));
		dto.setVarietyType(varietyType);
		dto.setType(type);
		int siz = FormStatUtil.getTypeOfBallNumber(varietyType);
		for (int i = 1; i <= siz; i++) {
			dto.setStartDate(dateMap.get("start"));
			dto.setField(FormStatUtil.getNumberStr(i));
			List<DewdropRes> draws = dewdropDao.querySmallBig(dto);
			//查询大小单双龙虎质和。
			//大小走势
			StringBuffer sb = new StringBuffer();
			//单双走势
			StringBuffer sb2 = new StringBuffer();
			for (int j = 0; j < draws.size(); j++) {
				sb.append(draws.get(j).getBigSmall());
				sb2.append(draws.get(j).getSingleDouble());
			}
			DewdropResultRes one = new DewdropResultRes();
			relativeMethod(one, sb, sb2, null, null, null, null);
			
			//查询大小单双龙虎的今日累计数量
			dto.setStartDate(dateMap.get("oneDay"));
			CumulativeQuantityRes vo =dewdropDao.queryCumulativeQuantity(dto);
			one.setBig(vo.getBig());
			one.setSmall(vo.getSmall());
			one.setSingle(vo.getSingle());
			one.setDoubles(vo.getDoubles());
			one.setFlat(vo.getFlat());
			json.put(""+i, one);
		}
		return json;
	}
		

	/**
	 * 龙湖露珠
	 * */
	@Override
	public Map<String, Object> queryDragonTigerDewdrop(String param,String varietyType) {
		Map<String,Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end"));
		dto.setStartDate(dateMap.get("start"));
		dto.setVarietyType(varietyType);
		
		int siz = FormStatUtil.getTypeOfBallNumber(varietyType)/2;
		//pk10龙虎露珠个数
		if("1".equals(varietyType)){
			siz = 5;
			//重庆时时彩的露珠个数 新疆时时彩
		}else if((""+VarietyTypeEnum.HEAVY_TIME.getCode()).equals(varietyType) || (""+VarietyTypeEnum.NEW_TIME.getCode()).equals(varietyType)){
			siz=1;
		}
		for (int i = 1; i <=siz ; i++) {
			 dto.setPaream(FormStatUtil.getNumberStrDragonTiger(i));
			 List<DewdropRes> draws = dewdropDao.querySmallBig(dto);
			 StringBuffer sb = new StringBuffer();
			 for (int j = 0; j < draws.size(); j++) {
				 sb.append(draws.get(j).getDragonTiger());
			 }
			DewdropResultRes one = new DewdropResultRes();
			relativeMethod(one, sb, null, null, null, null, null);
				
			//查询大小单双龙虎的今日累计数量
			dto.setStartDate(dateMap.get("oneDay"));
			CumulativeQuantityRes vo =dewdropDao.queryCumulativeQuantity(dto);
			one.setDragon(vo.getDragon());
			one.setTiger(vo.getTiger());
			one.setFlat(vo.getFlat());
			json.put(i+"", one);
		}
		
		return json;
	}

	@Override
	public int querySetForm(String openTime,int ball, String type,String match,String varietyType) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime nowDay = LocalDateTime.now();
		LocalDateTime today = nowDay.minusDays(Long.parseLong(openTime));
		String  endDate= df.format(nowDay)+" 23:59:59";
		String startDate = df.format(today)+" 00:00:00";
		DewDropReq vo = new DewDropReq();
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setField(FormStatUtil.getNumberStr(ball));
		vo.setType(type);
		vo.setVarietyType(varietyType);
		vo.setPaream(FormStatUtil.getNumberStrDragonTiger(ball));
		List<DewDropReq> dewdropData = dewdropDao.queryStatHistoryPK10DESC(vo);
		if(dewdropData.isEmpty()){return 0;}
		StringBuffer buf= new StringBuffer();
		
		//尾数大小  合数单双
		if(VarietyTypeEnum.LUCKY_FARM.code().toString().equals(varietyType)||VarietyTypeEnum.FAST10.code().toString().equals(varietyType)||VarietyTypeEnum.SELECTED_11_5.code().toString().equals(varietyType)){
			for (int i = 0; i < dewdropData.size(); i++) {
				String number =dewdropData.get(i).getPaream();
				int lastNumber=0;
				if(Integer.parseInt(number)>10){
					lastNumber =Integer.parseInt(dewdropData.get(i).getPaream().split("")[number.length()-1]);
				}else{
					lastNumber =Integer.parseInt(number);
				}
				if("ws".equals(type)){
					if(lastNumber >5){
						buf.append("大");
					}else{
						buf.append("小");
					}
					
				}else if("hs".equals(type)){
					int fastNumber =Integer.parseInt(dewdropData.get(i).getPaream().split("")[0]);
					if((lastNumber+fastNumber)%2 ==0){
						buf.append("双");
					}else{
						buf.append("单");
					}
				}
			}
		}else{
			for (int i = 0; i < dewdropData.size(); i++) {
				buf.append(dewdropData.get(i).getPaream());
			}
		}
		String str = buf.toString().replace(match, "-");
		String[] arr =str.split("-");
		int count =arr.length-1;
		return count;
	}

	/**
	 * 总和露珠
	 * */
	@Override
	public Object queryTotalDewdrop(String param, String varietyType) {
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		DewdropResultRes resutVo = new DewdropResultRes();
		//总和露珠
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end"));
		dto.setCrownSub(FormStatUtil.getNumberStr(11));
		dto.setVarietyType(varietyType);
		dto.setStartDate(dateMap.get("start"));
		
		List<DewdropRes> DewdropRes = dewdropDao.querySmallBig(dto);

		dto.setStartDate(dateMap.get("oneDay"));
		//快3处理
		CumulativeQuantityRes numberVo= dewdropDao.queryCumulativeQuantity(dto);
		resutVo.setBig(numberVo.getBig());
		resutVo.setSmall(numberVo.getSmall());
		resutVo.setSingle(numberVo.getSingle());
		resutVo.setDoubles(numberVo.getDoubles());
		
		if((""+VarietyTypeEnum.FAST3.getCode()).equals(varietyType)){
			for (int i = 0; i < DewdropRes.size(); i++) {
				String[] code = DewdropRes.get(i).getOpenCode().split(",");
				
				if((""+VarietyTypeEnum.FAST3.code()).equals(varietyType)){
					//豹子通吃
					if(code[0].equals(code[1]) && code[1].equals(code[2])){
						if(DewdropRes.get(i).getOpenTime().getTime()>=st.get("st").getTime() && DewdropRes.get(i).getOpenTime().getTime() <=st.get("ed").getTime()){
							if("大".equals(DewdropRes.get(i).getBigSmall())){
								resutVo.setBig(resutVo.getBig()-1);
							}else{
								resutVo.setSmall(resutVo.getSmall()-1);
							}
							if("单".equals(DewdropRes.get(i).getSingleDouble())){
								resutVo.setSingle(resutVo.getSingle()-1);
							}else{
								resutVo.setDoubles(resutVo.getDoubles()-1);
							}
							resutVo.setEat(resutVo.getEat()+1);
						}
						DewdropRes.get(i).setBigSmall("吃");
						DewdropRes.get(i).setSingleDouble("吃");
						
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		//快10 和 幸运农场 有总和尾数
		StringBuffer sb3= new StringBuffer();
		StringBuffer sb5= new StringBuffer();
		SumBigSmall(varietyType, st, resutVo, DewdropRes, sb, sb2, sb3, null, sb5, null);
		relativeMethod(resutVo, sb, sb2, sb3, sb5, null, null);
		
		return resutVo;
	}

	/**
	 * 号码露珠
	 * */
	@Override
	public Map<String, Object> queryNumberDewdrop(String param, String varietyType) {
		Map<String,Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		int siz = 0;
		int dex =0;
		//开奖号码 1-10
		if(VarietyTypeEnum.HOLY_LAND.code().toString().equals(varietyType) || VarietyTypeEnum.NEW_TIME.code().toString().equals(varietyType) || VarietyTypeEnum.TIME.code().toString().equals(varietyType)){
			siz=10;
		}
		//开奖号码 1-5
		if(VarietyTypeEnum.FAST3.code().toString().equals(varietyType)){
			siz=6;
			dex=1;
		}
		//开奖号码 1-20
		if(VarietyTypeEnum.LUCKY_FARM.code().toString().equals(varietyType) || VarietyTypeEnum.FAST10.code().toString().equals(varietyType)){
			siz=20;
			dex=1;
		}
		//号码露珠 0-9 个号码
		for (int i = 0+dex; i < siz+dex; i++) {
			NumberDewdropRes vo = new NumberDewdropRes();
			List<StatHistoryDraw> draw =dewdropDao.queryStatHistoryPK10(dateMap.get("start"), dateMap.get("end"), varietyType);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < draw.size(); j++) {
				//openCode有两种号码      01,02,03号码跟 1,2,3号码；处理
				String[] openCode = draw.get(j).getOpenCode().split(",");
				boolean flag= false;
				// code 0->01
				for (int k = 0; k < openCode.length; k++) {
					if(Integer.parseInt(openCode[k]) == i){
						flag=true;
					}
				}
				if(flag){
					sb.append("√");
					if(draw.get(j).getOpenTime().getTime()>=st.get("st").getTime() && draw.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
						vo.setAlways(vo.getAlways()+1);
					}
				}else{
					sb.append("×");
					if(draw.get(j).getOpenTime().getTime()>=st.get("st").getTime() && draw.get(j).getOpenTime().getTime() <=st.get("ed").getTime()){
						vo.setFuture(vo.getFuture()+1);
					}
				}
			}
			vo.setList(sb.toString());
			json.put(""+i, vo);
		}
		return json;
	}

	/**
	 * 北京快乐8 奇偶露珠
	 * */
	@Override
	public Map<String, Object> queryOddEveDewdrop(String param, String varietyType) {
		Map<String, Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		List<StatHistoryDraw> list =dewdropDao.queryStatHistoryPK10(dateMap.get("start"), dateMap.get("end"), varietyType);
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			int size =0;
			String[] openCode = list.get(i).getOpenCode().split(",");
			for (int j = 0; j < openCode.length; j++) {
				if(Integer.parseInt(openCode[j]) %2==0){
					++size;
				}
			}
			if(size <10){
				sb.append("奇");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("eve", json.get("eve") == null ? 1 : (int) json.get("eve") + 1);
				}
			}else if(size == 10){
				sb.append("和");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("and", json.get("and") == null ? 1 : (int) json.get("and") + 1);
				}
			}else {
				sb.append("偶");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("odd", json.get("odd") == null ? 1 : (int) json.get("odd") + 1);
				}
			}
		}
		json.put("list", sb.toString());
		return json;
	}

	/**
	 * 上下盘露珠
	 * */
	@Override
	public Map<String, Object> queryUpDownDewdrop(String param, String varietyType) {
		Map<String, Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		List<StatHistoryDraw> list =dewdropDao.queryStatHistoryPK10(dateMap.get("start"), dateMap.get("end"), varietyType);
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			int size =0;
			String[] openCode = list.get(i).getOpenCode().split(",");
			for (int j = 0; j < openCode.length; j++) {
				if(Integer.parseInt(openCode[j]) <=40){
					++size;
				}
			}
			if(size <10){
				sb.append("上");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("up", json.get("up") == null ? 1 : (int) json.get("up") + 1);
				}
			}else if(size == 10){
				sb.append("中");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("centre", json.get("centre") == null ? 1 : (int) json.get("centre") + 1);
				}
			}else {
				sb.append("下");
				if(list.get(i).getOpenTime().getTime()>=st.get("st").getTime() && list.get(i).getOpenTime().getTime() <=st.get("ed").getTime()) {
					json.put("down", json.get("down") == null ? 1 : (int) json.get("down") + 1);
				}
			}
		}
		json.put("list", sb.toString());
		return json;
	}

	/**
	 * 尾数大小露珠
	 * */
	@Override
	public Map<String, Object> queryLastNumberDewdrop(String param, String varietyType) {
		Map<String, Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		int siz =FormStatUtil.getTypeOfBallNumber(varietyType);
		
		for (int i = 1; i <= siz; i++) {
			DewdropResultRes one = new DewdropResultRes();
			DewDropReq dto = new DewDropReq();
			dto.setStartDate(dateMap.get("start"));
			dto.setEndDate(dateMap.get("end"));
			dto.setVarietyType(varietyType);
			dto.setField(FormStatUtil.getNumberStr(i));
			StringBuffer sb5= new StringBuffer();
			List<DewdropRes> list =dewdropDao.querySmallBig(dto);
			
			numberSumBigSmall(st, one, list, sb5);
			
			relativeMethod(one, null, null, null, null, sb5, null);
			json.put(i+"", one);
		}
		if(!VarietyTypeEnum.HONG_KONG_LOTTERY.code().toString().equals(varietyType)){
			DewdropResultRes vo = new DewdropResultRes();
			DewDropReq dto = new DewDropReq();
			dto.setStartDate(dateMap.get("start"));
			dto.setEndDate(dateMap.get("end"));
			dto.setVarietyType(varietyType);
			dto.setCrownSub(FormStatUtil.getNumberStr(11));
			List<DewdropRes> crownSubList =dewdropDao.querySmallBig(dto);
			StringBuffer sb =new StringBuffer();
			SumBigSmall(varietyType, st, vo, crownSubList, null, null, null, null, sb, null);
			relativeMethod(vo, null, null, null, null,sb,null);
			json.put("11", vo);
		}
		return json;
	}
	/**
	 * 合数单双露珠
	 * */
	@Override
	public Map<String, Object> querySumNumberDewdrop(String param, String varietyType) {
		Map<String, Object> json = Maps.newHashMap();
		Map<String ,String> dateMap = dateMap(param,varietyType);
		Map<String ,Date> st = getDate(dateMap.get("oneDay"),dateMap.get("end"));
		
		int siz =FormStatUtil.getTypeOfBallNumber(varietyType);
		
		for (int i = 1; i <= siz; i++) {
			DewdropResultRes one = new DewdropResultRes();
			DewDropReq dto = new DewDropReq();
			dto.setStartDate(dateMap.get("start"));
			dto.setEndDate(dateMap.get("end"));
			dto.setVarietyType(varietyType);
			dto.setField(FormStatUtil.getNumberStr(i));
			StringBuffer sb5= new StringBuffer();
			List<DewdropRes> list =dewdropDao.querySmallBig(dto);
			
			numberMantissaSingleDouble(st, one, list, sb5);
			
			relativeMethod(one, sb5, null, null, null, null, null);
			json.put(i+"", one);
		}
		if(!VarietyTypeEnum.HONG_KONG_LOTTERY.code().toString().equals(varietyType)){
			DewdropResultRes vo = new DewdropResultRes();
			DewDropReq dto = new DewDropReq();
			dto.setStartDate(dateMap.get("start"));
			dto.setEndDate(dateMap.get("end"));
			dto.setVarietyType(varietyType);
			dto.setCrownSub(FormStatUtil.getNumberStr(11));
			List<DewdropRes> crownSubList =dewdropDao.querySmallBig(dto);
			StringBuffer sb =new StringBuffer();
			
			SumBigSmall(varietyType, st, vo, crownSubList, null, null, null, null, null, sb);
			relativeMethod(vo, sb, null, null, null,null,null);
			json.put("11", vo);
		}
		return json;
	}

	/**
	 * 中发白
	 * */
	@Override
	public Map<String, Object> queryMiddleHairDewdrop(String openTime, String varietyType) {
		Map<String ,String> dateMap = dateMap(openTime,varietyType);
		Map<String,Object> json = Maps.newHashMap();
		int siz =FormStatUtil.getTypeOfBallNumber(varietyType);
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end"));
		dto.setVarietyType(varietyType);
		for (int i = 1; i <= siz; i++) {
			dto.setStartDate(dateMap.get("start"));
			dto.setField(FormStatUtil.getNumberStr(i));
			StringBuffer sb =new StringBuffer();
			List<MiddleHairRes> str=	dewdropDao.queryMiddleHairDewdrop(dto);
			for (int j = 0; j < str.size(); j++) {
				sb.append(str.get(j).getList());
			}
			dto.setStartDate(dateMap.get("oneDay"));
			MiddleHairRes vo=	dewdropDao.queryMiddleHairNumber(dto);
			vo.setList(sb.toString());
			json.put(""+i, vo);
		}
		return json;
	}

	/**
	 * 东西南北
	 * */
	@Override
	public Map<String, Object> queryNorthSouthDewdrop(String openTime, String varietyType) {
		Map<String ,String> dateMap = dateMap(openTime,varietyType);
		Map<String,Object> json = Maps.newHashMap();
		int siz =FormStatUtil.getTypeOfBallNumber(varietyType);
		DewDropReq dto = new DewDropReq();
		dto.setEndDate(dateMap.get("end"));
		dto.setVarietyType(varietyType);
		for (int i = 1; i <= siz; i++) {
			dto.setStartDate(dateMap.get("start"));
			dto.setField(FormStatUtil.getNumberStr(i));
			StringBuffer sb =new StringBuffer();
			List<NorthSouthDewdropRes> str=	dewdropDao.queryNorthSouthDewdrop(dto);
			for (int j = 0; j < str.size(); j++) {
				sb.append(str.get(j).getList());
			}
			dto.setStartDate(dateMap.get("oneDay"));
			NorthSouthDewdropRes vo=	dewdropDao.queryNorthSouthNumber(dto);
			vo.setList(sb.toString());
			json.put(""+i, vo);
		}
		return json;
	}

	
	/**
	 * 香港彩
	 * */
	@Override
	public Map<String, Object> queryHongKongColour(String code) {
		Map<String,Object> json = Maps.newHashMap();
		LocalDateTime nowDay = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String endDate = df.format(nowDay);
		String startDate =nowDay.getYear()+"-01-01 00:00:00";
		int siz =FormStatUtil.getTypeOfBallNumber("11");
		DewDropReq dto = new DewDropReq();
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		dto.setType(code);
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
        int year = Calendar.getInstance().get(Calendar.YEAR);  
		List<ZodiacFivePhases> zodiacFivePhases = zodiacFivePhasesDao.queryZodiacFivePhases(year);
		String[] poultry={"牛","马","羊","鸡","狗","猪"};
		String[] sex={"兔","蛇","羊","鸡","猪",""};
		String[] word={"牛","兔","龙","马","猴","猪"};
		String[] red={"鼠","兔","马","鸡","",""};
		String[] blue={"虎","蛇","猴","猪","",""};
		String[] green={"牛","龙","羊","狗","",""};
		List<Integer> poultryList = Lists.newArrayList();
		List<Integer> sexList = Lists.newArrayList();
		List<Integer> wordList = Lists.newArrayList();
		List<Integer> redList = Lists.newArrayList();
		List<Integer> blueList = Lists.newArrayList();
		List<Integer> greenList = Lists.newArrayList();
		for (int i = 0; i < zodiacFivePhases.size(); i++) {
			for (int j = 0; j < poultry.length; j++) {
				String[] numberJson = zodiacFivePhases.get(i).getNumbers();
				List<Integer> strsToList1=  Lists.newArrayList();
				for (int k = 0; k < numberJson.length; k++) {
					strsToList1.add(Integer.parseInt(numberJson[k]));
				}
				
				if(poultry[j].equals(zodiacFivePhases.get(i).getTargetName())){
					poultryList.addAll(strsToList1);
				}
				if(sex[j].equals(zodiacFivePhases.get(i).getTargetName())){
					sexList.addAll(strsToList1);
				}
				if(word[j].equals(zodiacFivePhases.get(i).getTargetName())){
					wordList.addAll(strsToList1);
				}
				if(red[j].equals(zodiacFivePhases.get(i).getTargetName())){
					redList.addAll(strsToList1);
				}
				if(blue[j].equals(zodiacFivePhases.get(i).getTargetName())){
					blueList.addAll(strsToList1);
				}
				if(green[j].equals(zodiacFivePhases.get(i).getTargetName())){
					greenList.addAll(strsToList1);
				}
			}
		}
		
		for (int j = 1; j <= siz; j++) {
			dto.setField(FormStatUtil.getNumberStr(j));
			List<HongKongColourRes> list =dewdropDao.queryHongKongColour(dto,poultryList,sexList,wordList,redList,blueList,greenList);
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getPoultry());
				sb2.append(list.get(i).getMan());
				sb3.append(list.get(i).getWorld());
			}
			json.put(j+"", sb.toString());
			if("zhds".equals(code) || "zhdx".equals(code)){
				return json;
			}else if("txlm".equals(code)) {
				json.put("nv", sb2.toString());
				json.put("td", sb3.toString());
				return json;
			}
		}
		return json;
	}

	/**
	 * 正码龙虎
	 * */
	@Override
	public Map<String, Object> queryCodeDragonTiger() {
		Map<String,Object> map = Maps.newLinkedHashMap();
		CodeDragonTigerRes c = new CodeDragonTigerRes();
		Field[] field = c.getClass().getDeclaredFields();
		//1~2 - 5~6球
		List<CodeDragonTigerRes> list  = dewdropDao.queryCodeDragonTiger();
		 for (int i = 1; i <= 6; i++) {
			for (int j = i+1; j <=6; j++) {
				StringBuilder sb=new StringBuilder();
				for (int k = 0; k < list.size(); k++) {
					int fast = getMethod(field, i,list.get(k));
					int last = getMethod(field, j,list.get(k)); 
					if(fast>last){
						sb.append("龙");
					}else{
						sb.append("虎");
					}
					
				}
				map.put(i+"-"+j, sb.toString());
			}
		}
		return map;
	}

	/**
	 * get方法名 获取方法属性
	 * */
	private int getMethod(Field[] field, int i, CodeDragonTigerRes codedt) {
		String fast =field[i].getName();
		String str1 = fast.substring(0, 1);
		String str2 = fast.substring(1, fast.length());
		String method_get = "get" + str1.toUpperCase() + str2;
		int number =0;
		try {
			Method method = codedt.getClass().getMethod(method_get, null);
			number=	(int) method.invoke(codedt);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return number;
	}

}
