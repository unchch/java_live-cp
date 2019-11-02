package com.bh.live.pojo.req.trade;

import java.io.Serializable;

import com.bh.live.pojo.req.PageParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ww
 * @projectName java_live-cp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("历史方案入参对象")
public class OrderHistoryFullReq extends PageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主播id")
	private Integer userId;

	@ApiModelProperty(value = "当前登录人的id")
	private Integer curUserId;

}
