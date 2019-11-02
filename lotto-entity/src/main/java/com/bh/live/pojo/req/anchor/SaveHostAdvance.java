package com.bh.live.pojo.req.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * <p>
 * 主播预告表
 * </p>
 *
 * @author WW
 * @since 2019-07-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("主播预告保存入参")
public class SaveHostAdvance implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "直播彩种")
	private Integer seedNo;

	@ApiModelProperty(value = "开播时间(时分秒)")
//	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Time startTime;

	@ApiModelProperty(value = "结束时间(非必须)(年月日  时分秒)")
//	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Time endTime;

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
