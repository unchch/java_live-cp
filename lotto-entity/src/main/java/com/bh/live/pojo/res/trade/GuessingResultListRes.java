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
 * 方案列表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("实时竞猜列表对象")
public class GuessingResultListRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "竞猜名称")
    private String guessName;

    @ApiModelProperty(value = "竞猜内容")
    private String content;

}
