package com.bh.live.pojo.res.user.token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *@Author: WuLong
 *@Date: 2019/7/16 22:05
 *@Description: 登录，注册返回token
 */
@Data
@AllArgsConstructor
public class TokenRes implements Serializable {
    private static final long serialVersionUID = -8809450809297201185L;
    /**
     * token
     */
    private String token;
}
