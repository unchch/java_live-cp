package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 主播表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@Data
@ApiModel("主播管理返回前端信息对象")
public class HostUserRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 主播id
     */
    @ApiModelProperty("主播id")
    private Integer userId;

    /**
     * 房间号
     */
    @ApiModelProperty("主播房间id")
    private Integer roomId;

    @ApiModelProperty("直播状态")
    private Integer status;

    //导出报表转成文字
    private String statusStr;

    /**
     * 擅长彩票，彩种以逗号隔开
     */
    @ApiModelProperty("擅长彩票")
    private String adeptLottery;

    /**
     * 主播等级
     */
    @ApiModelProperty("主播等级")
    private Integer hostLv;

    /**
     * 注册时间（申请成为主播时间）
     */
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createTimeStr;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    private String lastLoginTimeStr;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("昵称")
    private String username;

    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date verifyTime;

    /**
     * 审核状态（0拒绝 1通过 2待审核 3审核中）
     */
    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

    @ApiModelProperty("推荐首页")
    private Integer recommHomePage;

    private String recommHomePageStr;

    @ApiModelProperty("首页排序")
    private Integer sort;

    @ApiModelProperty("主播状态")
    private Integer isUsable;

    private String isUsableStr;

    @ApiModelProperty("彩币余额")
    private BigDecimal balance;

    @ApiModelProperty("累计充值")
    private BigDecimal rechargeAmount;

    @ApiModelProperty("累计提现次数")
    private Integer withdrawCount;

    @ApiModelProperty("累计提现金额")
    private BigDecimal withdrawAmount;

    @ApiModelProperty("绑定银行卡类型")
    private Integer bandType;

    @ApiModelProperty("登录终端 0:ios  1：android  2：pc")
    private String device;

    @ApiModelProperty("结算类型")
    private Integer settlementType;
}
