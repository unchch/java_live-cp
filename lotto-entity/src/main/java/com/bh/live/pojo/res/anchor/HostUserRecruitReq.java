package com.bh.live.pojo.res.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 主播表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@Data
@ApiModel("主播招募前端form表单入参对象")
public class HostUserRecruitReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "性别", required = true)
    private Integer sex;

    @ApiModelProperty(value = "年龄", required = true)
    private Integer age;

    @ApiModelProperty(value = "手机号", required = true)
    private String telephone;

    /*@ApiModelProperty(value = "qq", required = true)
    private Integer qq;*/

    @ApiModelProperty(value = "擅长彩种,多个彩种以英文逗号隔开", required = true)
    private String adeptLottery;

    @ApiModelProperty(value = "直播经验", required = true)
    private Integer experience;

    @ApiModelProperty(value = "电脑系统", required = true)
    private String pcVersion;

    @ApiModelProperty(value = "个人简介", required = true)
    private String personalProfile;

    @ApiModelProperty(value = "玩彩年限", required = true)
    private Integer ageLimit;

    @ApiModelProperty(value = "直播时间", required = true)
    private String liveTime;
}
