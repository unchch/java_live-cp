package com.bh.live.pojo.res.inform;

import lombok.Data;

import java.util.Date;

@Data
public class NumberRuleDateRes {
	private Date time;
	private String expect;
	private String code;
	private String state;
	private String bigSmall;
	private String singleDouble;
	private int cheNumber;
}
