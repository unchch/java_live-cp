package com.bh.live.pojo.req.anchor;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("主播预告")
public class HostAdvanceReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预告id")
	private Integer id;

	@ApiModelProperty(value = "主播id")
	private Integer hostId;

	@ApiModelProperty(value = "直播彩种")
	private Integer seedNo;

	@ApiModelProperty(value = "开播时间(时分秒)")
//	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Time startTime;

	@ApiModelProperty(value = "结束时间(非必须)")
//	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Time endTime;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creatTime;

	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	@ApiModelProperty(value = "是否可用 0不可以 1可用")
	private Integer isUsable;

	@ApiModelProperty(value = "直播周期  0周  1天")
	private Integer livePeriod;

	@ApiModelProperty(value = "周存json{'1',..'7'},天存具体日期")
	private String liveDate;

	@ApiModelProperty(value = "是否启用  0不  1没")
	private Integer isUsing;

}
