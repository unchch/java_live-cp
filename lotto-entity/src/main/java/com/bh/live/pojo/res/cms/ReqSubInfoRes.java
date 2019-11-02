package com.bh.live.pojo.res.cms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2019/4/12 4:46 PM
 * @description
 * @author yq.
 * @since
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSubInfoRes {

    /**
     * 登录名
     */
    private String uid;

    /**
     * 角色
     */
    private String roles;
}
