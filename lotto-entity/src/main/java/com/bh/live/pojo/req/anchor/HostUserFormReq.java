package com.bh.live.pojo.req.anchor;

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
 * @since 2019-08-13
 */
@Data
@ApiModel(value = "主播添加入参信息", description = "主播添加入参信息")
public class HostUserFormReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("房间id")
    private Integer roomId;

    @ApiModelProperty("账户名")
    private String username;

    @ApiModelProperty("手机号码")
    private String telephone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证号码")
    private String cardNum;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("是否可用 0禁用 1启用")
    private Integer isUsable;

    @ApiModelProperty("是否推荐首页 0否，1是(详情页修改时用)")
    private Integer recommHomePage;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("主播等级")
    private Integer hostLv;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("图库，图片链接以,逗号隔开")
    private String gallery;

    @ApiModelProperty("个人简介")
    private String personalProfile;

}
