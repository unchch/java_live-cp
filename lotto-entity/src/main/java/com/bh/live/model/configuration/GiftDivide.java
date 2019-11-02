package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 礼物数分成比例表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_gift_divide")
@ApiModel(value="GiftDivide对象", description="礼物数分成比例表")
public class GiftDivide implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分成等级（大写字母表示）")
    private String level;

    @ApiModelProperty(value = "最小礼物数")
    private Integer minVal;

    @ApiModelProperty(value = "最大礼物数")
    private Integer maxVal;

    @ApiModelProperty(value = "分成比例（0表示不结算）")
    private BigDecimal divide;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
