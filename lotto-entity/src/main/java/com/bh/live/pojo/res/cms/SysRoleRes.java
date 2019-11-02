package com.bh.live.pojo.res.cms;

import lombok.Data;

@Data
public class SysRoleRes {

    private Integer id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

}
