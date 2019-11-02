package com.bh.live.pojo.req.inform;

import lombok.Data;

@Data
public class DewDropReq {
	private String  field; //字段
	private String startDate;
	private String endDate;
	private String ball; //球号
	private String dragonTiger;
	private String type; //类型
	private String paream; //龙虎字段
	private String crownSub;//特殊号码 冠亚和 总和
	private String varietyType;//彩种类型
	private String openTime;
}
