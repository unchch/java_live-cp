package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
@ApiModel("主播基本信息返回前端对象")
public class HostUserResDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("主播房间id")
    private Integer roomId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("电话号码")
    private String telephone;

    @ApiModelProperty("玩彩年限")
    private Integer ageLimit;

    @ApiModelProperty("擅长彩种")
    private String adeptLottery;

    @ApiModelProperty("直播经验")
    private Integer experience;

    @ApiModelProperty("主播等级")
    private Integer hostLv;

    @ApiModelProperty("注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("申请主播时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beComeHostTime;

    /**
     * 是否可用 0不可以 1可用
     */
    @ApiModelProperty("状态")
    private Integer isUsable;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("封面")
    private String cover;

    /**
     * 图库，图片链接以","逗号隔开
     */
    @ApiModelProperty("图库")
    private String gallery;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("账户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("身份证号码")
    private String cardNum;

    @ApiModelProperty("居住地")
    private String address;

    @ApiModelProperty("注册来源终端")
    private String device;

    @ApiModelProperty("所属代理")
    private Integer inviteCode;

    @ApiModelProperty("审核成功时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date verifyTime;

    /**
     * 结算类型 0等级结算 1礼物数结算 2自定义
     */
    @ApiModelProperty("结算类型")
    private Integer settlementType;

    @ApiModelProperty("自定义结算值")
    private Integer stValue;

    /**
     * 结算周期 0日结 1周结 2月结
     */
    @ApiModelProperty("结算周期")
    private Integer settlementCycle;

    @ApiModelProperty("是否推荐首页")
    private Integer recommHomePage;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("QQOpenId")
    private String QQOpenId;

    @ApiModelProperty("微博OpenId")
    private String WBOpenId;

    @ApiModelProperty("微信OpenId")
    private String WXOpenId;

    @ApiModelProperty("支付宝OpenId")
    private String ZFBOpenId;

    @ApiModelProperty("是否是专家  0否  1是")
    private Integer isExpert;

    @ApiModelProperty("是否是主播  0否 1是")
    private Integer isAnchor;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastlogin;

    @ApiModelProperty("是否冻结")
    private Integer unUse;
}
