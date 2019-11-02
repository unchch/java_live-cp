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
 * @since 2019-07-31
 */
@Data
@ApiModel("主播管理返回前端信息对象")
public class HostUserFormRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主播id
     */
    @ApiModelProperty("主播id")
    private Integer userId;

    @ApiModelProperty("主播房间id")
    private Integer roomId;

    @ApiModelProperty("主播等级图标")
    private String lvImage;

    @ApiModelProperty("昵称")
    private String username;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("封面")
    private String cover;

    /**
     * 图库，图片链接以","逗号隔开
     */
    @ApiModelProperty("图库")
    private String gallery;

    @ApiModelProperty("个人简介")
    private String personalProfile;
}
