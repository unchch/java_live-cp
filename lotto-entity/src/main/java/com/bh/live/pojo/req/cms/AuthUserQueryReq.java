package com.bh.live.pojo.req.cms;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName AuthUserQueryReq
 * @description: 后台用户分页查询
 * @author: yq.
 * @date 2019-08-07 18:48:00
 */
@Data
@ApiModel(value = "后台用户列表查询req", description = "后台用户列表查询req")
@Accessors(chain = true)
public class AuthUserQueryReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 2967782294813966228L;
}
