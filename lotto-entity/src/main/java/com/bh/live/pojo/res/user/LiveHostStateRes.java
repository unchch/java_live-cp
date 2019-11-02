package com.bh.live.pojo.res.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("直播用户状态返回")
public class LiveHostStateRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("房间状态 0关闭中，1直播中 2未开播 3已完成")
	private Integer status;

	@ApiModelProperty("主播id")
	private Integer hostId;

	@ApiModelProperty("房间id")
	private Integer roomId;

}
