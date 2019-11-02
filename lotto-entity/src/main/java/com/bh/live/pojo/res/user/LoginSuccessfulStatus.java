package com.bh.live.pojo.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/26 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录成功状态返回对象")
public class LoginSuccessfulStatus implements Serializable {
    private static final long serialVersionUID = -1323282544313900670L;
    /**
     * id
     */
    @ApiModelProperty("userId")
    private String  userId;
    /**
     * 用户呢称
     */
    @ApiModelProperty("用户呢称")
    private String  name;
    /**
     * 关注数
     */
    @ApiModelProperty("关注数")
    private Integer attendNum;
    /**
     * 粉丝数
     */
    @ApiModelProperty("粉丝数")
    private Integer fansNum;
    /**
     * 彩币
     */
    @ApiModelProperty("彩币")
    private Integer allMoney;
    /**
     * 可提款金额
     */
    @ApiModelProperty("可提款金额")
    private Integer notdepositlMoney;
}
