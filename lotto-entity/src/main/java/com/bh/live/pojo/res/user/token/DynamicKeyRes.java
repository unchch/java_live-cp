package com.bh.live.pojo.res.user.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 *@author WuLong
 *@date 2019/7/31 13:09
 *@description 加密字符串动态key对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("加密字符串动态key对象")
public class DynamicKeyRes implements Serializable {
    private static final long serialVersionUID = 8100257614769529177L;

    @ApiModelProperty("公有key给前端进行加密")
    private String key;
    @ApiModelProperty("公有iv给前端进行加密")
    private String iv;

}
