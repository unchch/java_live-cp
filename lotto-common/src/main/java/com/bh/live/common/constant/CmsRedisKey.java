package com.bh.live.common.constant;

/**
 * @ClassName CmsRedisKey
 * @description: CmsRedisKey
 * @author: yq.
 * @date 2019-08-07 17:31:00
 */
public class CmsRedisKey {

    /**
     * 用户权限缓存key
     */
    private static final String AUTH_ROLE_RESOURCE = "auth_resource:role:%s";

    public static String authRoleResourceCacheKey(int id) {
        return String.format(AUTH_ROLE_RESOURCE, id);
    }

    /**
     * 公共用户权限缓存key
     */
    public static final String AUTH_COMMON_RESOURCE = "auth_resource:common";
}
