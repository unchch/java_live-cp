package com.bh.live.pojo.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户基础信息
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/31 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户基础信息")
public class UserBaseInfoRes implements Serializable {

    @ApiModelProperty("用户Id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("头像")
    private String imageUrl;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("是否是主播  0否 1是")
    private Integer isAnchor;

    @ApiModelProperty("是否是主播  0否 1是")
    private String isExpert;

    @ApiModelProperty("用户状态 是否可用 0异常 1正常")
    private Integer isUsable;

    @ApiModelProperty("用户关注 0未关注 1关注")
    private Integer isAttent;

    @ApiModelProperty("关注数")
    private Integer attentCount;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("彩币")
    private BigDecimal allMoney;

    @ApiModelProperty("可提金额")
    private BigDecimal depositlMoney;

    @ApiModelProperty("修改昵称次数")
    private  Integer editNameCount;

    @ApiModelProperty("审核状态（-1没有申请 0拒绝 1通过 2待审核）")
    private  Integer verifyStatus;
}
