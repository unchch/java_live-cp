package com.bh.live.pojo.req.inform;

import lombok.Data;

import java.io.Serializable;

/**
 * 号码遗漏
 * @author mayn
 *
 */
@Data
public class NumberOmitDto implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 public NumberOmitDto() {
		 this.location ="";
		 this.historyOmitBig = 0;
		 this.historyOmitNew =0;
		 this.monthOmitBig =0;
		 this.monthOmitNew = 0;
		// this.number = 0;
		 this.presentOmit = 0;
		 this.today  = 0;
		 this.todayOmitBig  = 0;
		 this.todayOmitNew = 0;
		 this.weekOmitBig = 0;
		 this.weekOmitNew = 0;
		 
	 }
	
	/**
	 *  比对位置 
	 */
	private String location;   
	
	/**
	 * 创建时间
	 */
	private String dateTime;
	
	/**
	 * 比对号码 
	 */
	private String number;   
	
	/**
	 * 当天出现次数
	 */
	private Integer today;  
	
	/**
	 * 当前遗漏次数
	 */
	private Integer presentOmit;  
	
	
	/**
	 * 当天遗漏次数
	 */
	private Integer todayOmitNew;  
	
	/**
	 * 当天遗漏次数最大值
	 */
	private Integer todayOmitBig;

	/**
	 * 当周遗漏次数
	 */
	private Integer weekOmitNew;  
	
	/**
	 * 当周遗漏次数最大值
	 */
	private Integer weekOmitBig; 
	
	/**
	 * 当月遗漏次数
	 */
	private Integer monthOmitNew; 
	
	/**
	 * 当月遗漏次数最大值
	 */
	private Integer monthOmitBig; 
	
	/**
	 * 历史遗漏次数
	 */
	private Integer historyOmitNew; 
	
	/**
	 * 历史遗漏次数最大值
	 */
	private Integer historyOmitBig; 
}
