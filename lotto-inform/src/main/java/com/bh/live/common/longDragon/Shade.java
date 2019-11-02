package com.bh.live.common.longDragon;

import lombok.Data;

@Data
public class Shade {

	/**
	 * 球号
	 */
	private Integer ballNum;

	private int time;


	public Shade(int ballNum,int time) {
		super();
		this.ballNum = ballNum;
		this.time = time;
	}
}
