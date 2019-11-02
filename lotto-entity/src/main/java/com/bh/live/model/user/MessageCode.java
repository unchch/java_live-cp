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
 * 短信验证码
 * </p>
 *
 * @author WuLong
 * @since 2019-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_message_code")
public class MessageCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 类型：1注册 2修改密码
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;


}
