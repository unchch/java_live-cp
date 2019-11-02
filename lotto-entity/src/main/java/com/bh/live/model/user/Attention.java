package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 关注表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("uc_attention")
@ApiModel(value = "Attention对象", description = "关注表")
public class Attention implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关注id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "被关注人id")
    private Integer targetId;

    @ApiModelProperty(value = "是否关注 0未关注 1已关注")
    private Integer isAttent;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "注册时间")
    private Date creatTime;


}
