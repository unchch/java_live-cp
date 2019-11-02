package com.bh.live.pojo.res.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bh.live.pojo.req.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("主播预告")
public class HostAdvanceRes extends PageParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预告id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "主播id")
	private Integer hostId;

	@ApiModelProperty(value = "直播彩种")
	private Integer seedNo;

	@ApiModelProperty(value = "开播时间(时分秒)")
	private String startTime;

	@ApiModelProperty(value = "结束时间(非必须)")
	private String endTime;

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
	
	/********************附加信息******************************/
	@ApiModelProperty(value = "主播图像")
    private String hostIcon;
	
	@ApiModelProperty(value = "主播状态 0离线 2在线 1直播中")
    private Integer hostStatus;
	
	@ApiModelProperty(value = "人气值")
    private Integer hotVal;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;
	
	@ApiModelProperty(value = "房间id")
	private Integer roomId;

	@ApiModelProperty(value = "预告时间 2019/07/20  20：30-22：30")
	private String advanceTime;

	@ApiModelProperty(value = "彩种名称")
	private String seedName;

	@ApiModelProperty(value = "是否启用  0不  1没")
	private Integer isUsing;

	@ApiModelProperty(value = "是否关注  0不  1关注")
	private Integer isAttend;

	@ApiModelProperty(value = "封面")
	private String cover;

}
