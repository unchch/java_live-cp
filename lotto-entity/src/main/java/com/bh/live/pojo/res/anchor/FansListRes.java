package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName FansListRes
 * @description: FansListRes
 * @author: Morphon
 * @date 2019-08-06 18:38:00
 */
@Data
@ApiModel("粉丝列表")
public class FansListRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154494L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "粉丝头像")
    private String icon;

}
