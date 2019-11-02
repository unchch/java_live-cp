package com.bh.live.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.bh.live.common.utils.ExpectUtil;
import com.bh.live.common.utils.FormStatUtil;
import com.bh.live.dao.DewdropDao;
import com.bh.live.dao.TwoDataStatisticsDao;
import com.bh.live.model.inform.StatHistoryDraw;
import com.bh.live.pojo.req.inform.DewDropReq;
import com.bh.live.pojo.res.inform.NumberRuleDateRes;
import com.bh.live.pojo.res.inform.NumberRuleHistogramRes;
import com.bh.live.pojo.res.inform.TwoDataStatisticsRes;
import com.bh.live.service.TwoDataStatisticsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TwoDataStatisticsServiceImpl implements TwoDataStatisticsService {

	@Resource
	private TwoDataStatisticsDao twoDataStatisticsDao;
	
	@Resource
	private DewdropDao dewdropDao;
	
	/**
	 * 两面数据统计
	 * 以每10期為一組，統計最近20組中單雙大小的出現次數。
	 * 
	 * */
	@Override
	public List<Object> queryTwoDataStatistics(int number,String varietyType) {
		//20组数据
		List<Object> json = Lists.newArrayList(); 
		//3天内的数据
		//今天
		List<TwoDataStatisticsRes> list=twoDataStatisticsDao.queryTwoDataStatistics(FormStatUtil.getNumberStr(number),varietyType,0);
		//昨天
		List<TwoDataStatisticsRes> list2=twoDataStatisticsDao.queryTwoDataStatistics(FormStatUtil.getNumberStr(number),varietyType,-1);
		//2天前
		List<TwoDataStatisticsRes> list3=twoDataStatisticsDao.queryTwoDataStatistics(FormStatUtil.getNumberStr(number),varietyType,-2);
		List<TwoDataStatisticsRes> data = Lists.newArrayList();
		List<TwoDataStatisticsRes> data2 = Lists.newArrayList();
		List<TwoDataStatisticsRes> data3 = Lists.newArrayList();
		
		//结构转换
		statisticsReverse(list, data);
		statisticsReverse(list2, data2);
		statisticsReverse(list3, data3);
		
		List<TwoDataStatisticsRes> all = Lists.newArrayList();
		all.addAll(data);
		all.addAll(data2);
		all.addAll(data3);
		//20组
		for (int i = 0; i<all.size(); i++) {
			if(i<20){
				json.add(all.get(i));
			}
		}
		
		return json;
	}

	private void statisticsReverse(List<TwoDataStatisticsRes> list, List<TwoDataStatisticsRes> data) {
		TwoDataStatisticsRes vo = new TwoDataStatisticsRes();
		for (int i = 0; i < list.size(); i++) {
			if( i!=0 &&i%10==0){
				data.add(vo);
				vo = new TwoDataStatisticsRes();
			}
			TwoDataStatisticsRes statistics =list.get(i);
			vo.setXiao(vo.getXiao()+statistics.getXiao());
			vo.setDa(vo.getDa()+statistics.getDa());
			vo.setDan(vo.getDan()+statistics.getDan());
			vo.setSuan(vo.getSuan()+statistics.getSuan());
			vo.setMaxExpect(statistics.getMaxExpect());
			vo.setOpenTime(statistics.getOpenTime());
			if(vo.getMinExpect()==null) {
                vo.setMinExpect(statistics.getMinExpect());
            }
		}
		data.add(vo);
		Collections.reverse(data);
	}

	/**
	 * 号码规律
	 * */
	@Override
	public Map<String, Object> queryNumberLaw(int param,int expect,String varietyType) {
		//查询一个位置的号码规律 所有号码
		Map<String,Object> map = Maps.newHashMap();
		if(param>10){
			for (int e = 1; e <= 10; e++) {
				List<Integer> list =queryIsotope(e,expect,varietyType);
				map.put(e+"", list);
			}
		}else{
			map= queryLaw(param,expect,varietyType);	
		}
		 
		return map;
	}
	//同位数数组
	private List<Integer> queryIsotope(int param, int expect,String varietyType) {
		//拿到同位数
		//查询范围内的游戏数据 多查两位
		List<StatHistoryDraw> dewData = dewdropDao.queryExpectLimt(expect+2,varietyType);
		List<Integer> isotope = Lists.newArrayList();
		for (int i = 0; i<dewData.size()-2 ; i++) {
			//过滤多余数据   
				//上一个开奖数据
				String[] codeArr = dewData.get(i+1).getOpenCode().split(",");
				for (int j = 0; j < codeArr.length; j++) {
					//开奖号码比较
					if(codeArr[j].equals(param+"") ){
						//拿到同位数
						int samePlace = Integer.parseInt(dewData.get(i).getOpenCode().split(",")[j]);
						isotope.add(samePlace);
					}
				}
		}
		return isotope;
	}
	//号码数据
	private Map<String, Object> queryLaw(int param,int expect,String varietyType) {
		Map<String,Object> map =Maps.newHashMap();
		//拿到同位数
		//多查两位
		List<StatHistoryDraw> dewData = dewdropDao.queryExpectLimt(expect+2,varietyType);
		List<NumberRuleDateRes> list = Lists.newArrayList();
		JSONArray isotope = new JSONArray();
		int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0,i10=0;
		for (int i = 0; i<dewData.size()-2 ; i++) {
			NumberRuleDateRes ruleVo = new NumberRuleDateRes();
			//过滤多余数据   
			ruleVo.setExpect(dewData.get(i).getExpect());
			ruleVo.setTime(dewData.get(i).getOpenTime());
			ruleVo.setCode(dewData.get(i).getOpenCode());
				//上一个开奖数据
				String[] codeArr = dewData.get(i+1).getOpenCode().split(",");
				for (int j = 0; j < codeArr.length; j++) {
					//开奖号码比较
					if(codeArr[j].equals(param+"") ){
						//拿到同位数
						int samePlace = Integer.parseInt(dewData.get(i).getOpenCode().split(",")[j]);
						ruleVo.setCheNumber(samePlace);
						int samePlace2 =0;
						String[] codeArr2 = dewData.get(i+2).getOpenCode().split(",");
						for (int k = 0; k < codeArr2.length; k++) {
							if(codeArr2[k].equals(param+"") ){
								samePlace2 = Integer.parseInt(dewData.get(i+1).getOpenCode().split(",")[k]);
							}
						}
						isotope.add(samePlace);
						if(samePlace < samePlace2){
							ruleVo.setState("降");
						}else if(samePlace == samePlace2){
							ruleVo.setState("平");
						}else if(samePlace > samePlace2){
							ruleVo.setState("升");
						}
						
						if(samePlace<=5){
							ruleVo.setBigSmall("小");
						}else{
							ruleVo.setBigSmall("大");
						}
						
						if(samePlace%2==0){
							ruleVo.setSingleDouble("双");
						}else{
							ruleVo.setSingleDouble("单");
						}
						
						//计算同位数出现次数
						if(1==samePlace){
							++i1;
						}else if(2==samePlace){
							i2++;
						}else if(3==samePlace){
							++i3;
						}else if(4==samePlace){
							++i4;
						}else if(5==samePlace){
							++i5;
						}else if(6==samePlace){
							++i6;
						}else if(7==samePlace){
							++i7;
						}else if(8==samePlace){
							++i8;
						}else if(9==samePlace){
							++i9;
						}else if(10==samePlace){
							++i10;
						}
					}
				}
				list.add(ruleVo);
		}
		
		//号码出现次数百分比
		int total = i1+i2+i3+i4+i5+i6+i7+i8+i9+i10;
		String s1 = getPercent(i1, total);
		String s2 = getPercent(i2, total);
		String s3 = getPercent(i3, total);
		String s4 = getPercent(i4, total);
		String s5 = getPercent(i5, total);
		String s6 = getPercent(i6, total);
		String s7 = getPercent(i7, total);
		String s8 = getPercent(i8, total);
		String s9 = getPercent(i9, total);
		String s10 = getPercent(i10, total);
		List<NumberRuleHistogramRes> hList = Lists.newArrayList();
		NumberRuleHistogramRes h1 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h2 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h3 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h4 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h5 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h6 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h7 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h8 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h9 = new NumberRuleHistogramRes();
		NumberRuleHistogramRes h10 = new NumberRuleHistogramRes();
		
		h1.setBall("号码1");h2.setBall("号码2");h3.setBall("号码3");h4.setBall("号码4");h5.setBall("号码5");
		h6.setBall("号码6");h7.setBall("号码7");h8.setBall("号码8");h9.setBall("号码9");h10.setBall("号码10");
		
		h1.setCapacity(s1);h2.setCapacity(s2);h3.setCapacity(s3);h4.setCapacity(s4);h5.setCapacity(s5);
		h6.setCapacity(s6);h7.setCapacity(s7);h8.setCapacity(s8);h9.setCapacity(s9);h10.setCapacity(s10);
		
		h1.setNumber(i1);h2.setNumber(i2);h3.setNumber(i3);h4.setNumber(i4);h5.setNumber(i5);
		h6.setNumber(i6);h7.setNumber(i7);h8.setNumber(i8);h9.setNumber(i9);h10.setNumber(i10);
		
		hList.add(h1);hList.add(h2);hList.add(h3);hList.add(h4);hList.add(h5);
		hList.add(h6);hList.add(h7);hList.add(h8);hList.add(h9);hList.add(h10);
		
		//树状图
		map.put("histogram", hList);
		
		//表格数据
		map.put("table",list);
		return map;
	}

	//计算百分比
	private String getPercent(int x,int total){
		//接受百分比的值
		String result="";
		double tempresult=x*1.00/total*1.00;
		//##.00% 百分比格式，后面不足2位的用0补齐
		DecimalFormat df1 = new DecimalFormat("0.00%");
		result= df1.format(tempresult);
		return result;
	}

	//遗漏大数据
		@Override
		public Map<String, Object> queryMissingBigData(String dateType,int ball,String varietyType) {
			DewDropReq dto= new DewDropReq();
			Map<String,Object> json = Maps.newHashMap();
			Map<Integer,Integer> big = Maps.newHashMap();
			Map<Integer,Integer> small = Maps.newHashMap();
			Map<Integer,Integer> single = Maps.newHashMap();
			Map<Integer,Integer> doubles = Maps.newHashMap();
			Map<Integer,Integer> dragon = Maps.newHashMap();
			Map<Integer,Integer> tiger = Maps.newHashMap();
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime nowDay = LocalDateTime.now();
			dto.setEndDate(df.format(nowDay));
			if("Month".equals(dateType)){
				LocalDateTime  today = nowDay.minusMonths(1);
				dto.setStartDate(df.format(today));
			}else if("Quarter".equals(dateType)){
				LocalDateTime  today = nowDay.minusMonths(3);
				dto.setStartDate(df.format(today));
			}else if("HalfofYear".equals(dateType)){
				LocalDateTime  today = nowDay.minusMonths(6);
				dto.setStartDate(df.format(today));
			}else if("Year".equals(dateType)){
				LocalDateTime  today = nowDay.minusYears(1);
				dto.setStartDate(df.format(today));
			}else if("test".equals(dateType)){
				LocalDateTime  today = nowDay.minusDays(1);
				dto.setStartDate(df.format(today));
			}
			dto.setField(FormStatUtil.getNumberStr(ball));
			dto.setPaream(FormStatUtil.getNumberStrDragonTiger(ball));	
			dto.setVarietyType(varietyType);
			List<TwoDataStatisticsRes> list =twoDataStatisticsDao.queryMissingBigOneData(dto);
			int[] bigIndex = {0,0,0},smallIndex ={0,0,0},singleIndex={0,0,0},doubleIndex={0,0,0},dragonIndex={0,0,0},tigerIndex ={0,0,0};
			for (int i = 0; i < list.size(); i++) {
				//大的遗漏
				if("大".equals(list.get(i).getBigSmall())){
					bigIndex=putDragonTiger(bigIndex,big,list,i,varietyType);
					lastOmit(bigIndex[1],smallIndex[1] ,small,list,i);
				}
				
				//小的遗漏
				if("小".equals(list.get(i).getBigSmall())){
					smallIndex=putDragonTiger(smallIndex,small,list,i,varietyType);
					lastOmit(smallIndex[1],bigIndex[1] ,big,list,i);
				}
				
				if("单".equals(list.get(i).getSingleDouble())){
					singleIndex=putDragonTiger(singleIndex,single,list,i,varietyType);
					lastOmit(singleIndex[1],doubleIndex[1] ,single,list,i);
				}
				if("双".equals(list.get(i).getSingleDouble())){
					doubleIndex =putDragonTiger(doubleIndex,doubles,list,i,varietyType);
					lastOmit(doubleIndex[1],singleIndex[1] ,doubles,list,i);
				}
				
				if("虎".equals(list.get(i).getDragonTiger())){
					tigerIndex = putDragonTiger(tigerIndex,tiger,list,i,varietyType);
					lastOmit(tigerIndex[1],dragonIndex[1],tiger,list,i);
					
				}
				if("龙".equals(list.get(i).getDragonTiger())){
					dragonIndex=putDragonTiger(dragonIndex,dragon,list,i,varietyType);
					lastOmit(dragonIndex[1],tigerIndex[1] ,dragon,list,i);
				}
				
			}
			single.remove(0);
			doubles.remove(0);
			big.remove(0);
			small.remove(0);
			tiger.remove(0);
			dragon.remove(0);
			List<Object> arr = Lists.newArrayList();
			arr.add(big);arr.add(small);arr.add(single);
			arr.add(doubles);arr.add(dragon);arr.add(tiger);
			json.put("list", arr);
			return json;
		}
		//查最后一条数据是大是小
		//put最后一条数据
	private void lastOmit(int bagIndex2, int smallIndex2, Map<Integer,Integer> map,
				List<TwoDataStatisticsRes> list,int i) {
			if(i==list.size()-1){
				int expect = bagIndex2 -smallIndex2;
				map.put(expect,(map.get(expect)== null ?0:map.get(expect))+1);
			}
		}
	//put最大历史遗漏
	private int[] putDragonTiger(int[] index, Map<Integer,Integer > map,
				List<TwoDataStatisticsRes> list, int i,String varietyType) {
		//开始数据， 第一个为未遗漏
			if(index[1] == 0){
				if(index[2] ==0){
					//第一个为大
					int expect=	ExpectUtil.expectGap(list.get(0).getExpect(), list.get(i).getExpect(), varietyType);
					map.put(expect,(map.get(expect)== null ?0:map.get(expect))+1);
				}else{
					int expect=	ExpectUtil.expectGap(list.get(0).getExpect(), list.get(i).getExpect(), varietyType)-1;
					map.put(expect,(map.get(expect)== null ?0:map.get(expect))+1);
				}
				//上次下标
				index[0] =index[1];
				//本次下标
				index[1] =i;
				// boolean  
				index[2]=1;
			}else{
				index[0] =index[1];
				index[1] =i;
				int expect = index[1] -index[0]-1;
				map.put(expect,(map.get(expect)== null ?0:map.get(expect))+1);
			}
			return index;
		}
}
