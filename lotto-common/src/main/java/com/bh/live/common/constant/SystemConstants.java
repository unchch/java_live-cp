package com.bh.live.common.constant;

import com.bh.live.common.utils.CommonUtil;

import java.util.List;

/**
 * @author yq.
 * @date 2019/4/8 3:32 PM
 * @description
 * @since
 **/
public class SystemConstants {

    /**
     * 超级管理员账户
     */
    public static final String ADMIN_UID = "admin";
    public static final String SYSTEM_UID = "system";

    /**
     * 超级管理员角色id
     */
    public static final Integer ADMIN_ROLE_ID = 100;

    /**
     * 权限-公共菜单资源类型
     */
    public static final Integer AUTH_COMMON_RES_TYPE = 9;

    /**
     * 主站编号
     */
    public static final Long MAIN_DOMAIN_ID = -1L;

    public static boolean isAdmin(String uid) {
        return CommonUtil.isNotEmpty(uid) && SystemConstants.ADMIN_UID.equals(uid);
    }

    public static boolean isAdmin(List<String> uid) {
        return CommonUtil.isNotEmpty(uid) && uid.contains(SystemConstants.ADMIN_UID);
    }

    public static boolean isAdminRole(Integer roleId) {
        return CommonUtil.isNotEmpty(roleId) && SystemConstants.ADMIN_ROLE_ID.equals(roleId);
    }

    public static boolean isAdminRole(List<Integer> roleIds) {
        return CommonUtil.isNotEmpty(roleIds) && roleIds.contains(SystemConstants.ADMIN_ROLE_ID);
    }
}
