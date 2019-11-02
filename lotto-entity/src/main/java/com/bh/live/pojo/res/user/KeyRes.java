package com.bh.live.pojo.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel("获取key对象")
public class KeyRes implements Serializable {
    private static final long serialVersionUID = 1343282544313900465L;
    /**
     *公有key给前端进行加密
     */
    @ApiModelProperty("公有key给前端进行加密")
    private String publicKey;
}
