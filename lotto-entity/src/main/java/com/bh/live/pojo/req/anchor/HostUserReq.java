package com.bh.live.pojo.req.anchor;

import com.bh.live.pojo.req.PageParam;
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
@ApiModel(value = "主播管理前端入参信息", description = "主播管理端入参信息")
public class HostUserReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主播id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("彩种名称,传彩种id过来(查询用)")
    private String adeptLottery;

    @ApiModelProperty("账户名(查询用)")
    private String username;

    @ApiModelProperty("结算类型(查询用)")
    private Integer settlementType;

    @ApiModelProperty("主播状态 0离线 1在线 2直播中(查询用)")
    private Integer status;

    @ApiModelProperty("是否可用 0不可以 1可用(详情页修改时用)")
    private Integer isUsable;

    @ApiModelProperty("是否推荐首页 0否，1是(详情页修改时用)")
    private Integer recommHomePage;

    @ApiModelProperty("排序(详情页修改时用)")
    private Integer sort;

    /**
     * 结算周期 0日结 1周结 2月结
     */
    @ApiModelProperty("结算周期 0日结 1周结 2月结(详情页修改时用)")
    private Integer settlementCycle;

    @ApiModelProperty("头像(主播资料修改用)")
    private String icon;

    @ApiModelProperty("封面(主播资料修改用)")
    private String cover;

    /**
     * 图库，图片链接以","逗号隔开
     */
    @ApiModelProperty("图库，图片链接以,逗号隔开(主播资料修改用)")
    private String gallery;

    @ApiModelProperty("个人简介(主播资料修改用)")
    private String personalProfile;

}
