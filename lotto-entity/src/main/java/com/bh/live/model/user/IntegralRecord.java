package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户-积分记录
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("uc_integral_record")
@ApiModel(value = "IntegralRecord对象", description = "用户-积分记录")
public class IntegralRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "积分获取来源（参考枚举）")
    private String source;

    @ApiModelProperty(value = "积分值")
    private Integer integralValue;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "ip所在地")
    private String ipAddr;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
