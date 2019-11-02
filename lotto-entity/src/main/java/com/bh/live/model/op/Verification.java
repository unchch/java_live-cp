package com.bh.live.model.op;

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
 * 用户验证码表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("op_verification")
@ApiModel(value = "Verification对象", description = "用户验证码表")
public class Verification implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "帐号")
    private String mobile;

    @ApiModelProperty(value = "验证类型  0登录  1注册")
    private Integer type;

    @ApiModelProperty(value = "验证码")
    private String verificationCode;

    @ApiModelProperty(value = "有效时间")
    private Date validTime;

    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String ip;

    private String ipAddr;


}
