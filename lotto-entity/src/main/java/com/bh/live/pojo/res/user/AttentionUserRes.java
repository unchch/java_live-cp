package com.bh.live.pojo.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/25 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("关注用戶对象")
public class AttentionUserRes implements Serializable {


    @ApiModelProperty("id")
    private Integer id;


    @ApiModelProperty("是否是主播  0否 1是")
    private String isAnchor;

    @ApiModelProperty("是否是专家  0否  1是")
    private String isExpert;

    @ApiModelProperty("用户昵称")
    private String nickname;


    @ApiModelProperty("用户头像")
    private String imageUrl;

}
