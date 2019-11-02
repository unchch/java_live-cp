package com.bh.live.pojo.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户绑定账户表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-30
 */
@Data
@ApiModel("用户账户返回对象")
public class BindingAccountRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("卡号")
    private String account;

    /**
     * 帐号类型  0微信 1支付宝 2银行卡 3微博 4QQ
     */
    @ApiModelProperty("帐号类型")
    private Integer accountType;

    /**
     * 是否可用 0不可以 1可用
     */
    @ApiModelProperty("状态")
    private Integer isUsable;

    /**
     * 银行名称
     */
    @ApiModelProperty("银行卡名称")
    private String bankName;

    /**
     * 支行名称
     */
    @ApiModelProperty("开卡支行")
    private String bankNameBranch;

}
