package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("主播预告")
public class AdvancesRes implements Serializable {

	@ApiModelProperty(value = "开播时间(时分秒)")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	@ApiModelProperty(value = "结束时间(非必须)")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	@ApiModelProperty(value = "主播状态 0离线 2在线 1直播中")
    private Integer status;

	@ApiModelProperty(value = "直播周期 0周 1天")
    private Integer livePeriod;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;
	
	@ApiModelProperty(value = "房间id")
	private Integer roomId;

	@ApiModelProperty(value = "预告时间 2019/07/20  20：30-22：30")
	private String advanceTime;

	@ApiModelProperty(value = "预告时间年月日")
	private String liveDate;

	@ApiModelProperty(value = "彩种编号")
	private Integer seedNo;

	@ApiModelProperty(value = "彩种名称")
	private String seedName;

}
