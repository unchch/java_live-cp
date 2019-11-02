package com.bh.live.model.lottery.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bh.live.pojo.req.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 彩种订单配置表
 * </p>
 *
 * @author lgs
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_order_config")
@ApiModel(value="OrderConfig对象", description="彩种订单配置表")
public class OrderConfig extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "彩种code")
    private Integer seedNo;

    @ApiModelProperty(value = "可以发单用户群组；类型用，分割开")
    private String userGroup;

    @ApiModelProperty(value = "用户方案")
    private String userPlan;

    @ApiModelProperty(value = "提前多少秒。禁止发布竞猜")
    private Integer endTime;

    @ApiModelProperty(value = "一期最大发布数目")
    private Integer maxNum;

    @ApiModelProperty(value = "奖池开关")
    private Integer prizeOnOff;

    @ApiModelProperty(value = "分配人数")
    private Integer person;

    @ApiModelProperty(value = "平局分配最小值")
    private BigDecimal avgCoinMin;

    @ApiModelProperty(value = "平局分配最大值")
    private BigDecimal avCoinMax;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    private String modifyBy;


}
