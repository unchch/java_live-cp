package com.bh.live.pojo.res.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 玩法的编号和名次
 * </p>
 *
 * @author ww
 * @since 2019-07-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("玩法对象")
public class LottoPlayNoNameRes implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "玩法编号")
	private Integer playNo;

	@ApiModelProperty(value = "玩法名称")
	private String playName;

}
