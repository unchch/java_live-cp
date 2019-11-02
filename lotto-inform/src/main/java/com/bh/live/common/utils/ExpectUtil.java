package com.bh.live.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpectUtil {
	
	
	/**
	 * 期数差
	 * @param expect 结束大的期数
	 * @param expect2 开始小的的期数
	 * */
	public static  int expectGap(String expect,String expect2,String varietyType){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int day=0;
		//判断是否日期期数格式
		if(expect.length()<9){
			//正常期数
			return Integer.parseInt(expect2)-Integer.parseInt(expect);
		}
		try {
			Date endDate=sdf.parse(expect.substring(0, 8));
			Date startDate=sdf.parse(expect2.substring(0, 8));
			//相差天数
			day = (int)(endDate.getTime()-startDate.getTime())/(1000*3600*24);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int endExpect=Integer.parseInt(expect.substring(8, expect.length()));
		int startExpect=Integer.parseInt(expect2.substring(8, expect2.length()));
		
		//相差期数
		int exp = endExpect-startExpect;
		
		//每日期数
		int everyExpert =getTypeOfExpect(varietyType);
		
		int sum= day*everyExpert+exp;
		return sum;
	}
	/**
	 * 根据彩种返回开奖期数
	 * */
	 public static  int  getTypeOfExpect(String i) {
	        int number=0;
	        switch (i) {
	            case "0":
	                number=180;
	                break;
	            case "1":
	                number=44;
	                break;
	            case "2":
	                number=59;
	                break;
	            case "3":
	                number=90;
	                break;
	            case "4":
	                number=41;
	                break;
	            case "5":
	                number=59;
	                break;
	            case "6":
	                number=179;
	                break;
	            case "7":
	                number=48;
	                break;
	            case "8":
	                number=42;
	                break;
	            case "9":
	                number=42;
	                break;
	            case "10":
	                number=42;
	                break;
	            case "11":
	                number=1;
	                break;
	        }
	        return  number;
	    }
	public static void main(String[] args) {
		System.out.println(expectGap("20190626043","20190626020","7"));
	}

}
