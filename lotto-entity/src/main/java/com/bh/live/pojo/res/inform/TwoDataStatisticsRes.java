package com.bh.live.pojo.res.inform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class TwoDataStatisticsRes implements Serializable {
	private int xiao;
	private int da;
	private int dan;
	private int suan;
	private String maxExpect;
	private String minExpect;
	private Date openTime;
	
	private String bigSmall;
	private String singleDouble;
	private String dragonTiger;
	
	private String dragon;
	private String tiger;
	private String firstDragonTiger;
	private String secondDragonTiger;
	private String thirdDragonTiger;
	private String fourthDragonTiger;
	private String fifthDragonTiger;
	
	private String expect;
}
