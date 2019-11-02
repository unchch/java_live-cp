package com.bh.live.pojo.res.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AuthUserSimpleRes
 * @description: 简单用户对象
 * @author: yq.
 * @date 2019-08-07 18:48:00
 */
@Data
@ApiModel(value = "简单用户对象", description = "简单用户对象")
public class AuthUserSimpleRes implements Serializable {
    private static final long serialVersionUID = 799792277116914893L;

    @ApiModelProperty(value = "用户ID")
    private String uid;
    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "上级用户ID")
    private String parentUserId;
}
