package com.bh.live.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户绑定账户表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_binding_account")
public class BindingAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String accountPassword;

    /**
     * 帐号类型  0微信 1支付宝 2银行卡 3微博 4QQ
     */
    private Integer accountType;

    /**
     * 帐号所属人名字
     */
    private String accountBy;

    /**
     * 是否可用 0不可以 1可用
     */
    private Integer isUsable;

    /**
     * openid
     */
    private String openid;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 支行名称
     */
    private String bankNameBranch;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除  0删除 1不删除
     */
    private Integer isDelete;


}
