package com.bh.live.common.constant;

/**
 * 后台用户Redis-key
 * @author Y.
 */
public class UserRedisKey {

    /**
     * 存放系统用户 JWT 的key
     */
    public static final String SYS_USER_JWT_TOKEN = "JWT_TOKEN:SYS_USER:%s";

    /**
     * 存放系统用户 JWT 的 tokenKey
     */
    public static final String SYS_USER_TOKEN_KEY = "TOKEN_KEY:SYS_USER:%s";

    /**
     * 存放活动监听器，Map集合
     */
    public static final String ACTIVITY_LISTENER_KEY = "activity:listener:key";

    /**
     * 主播修改个人简介key
     */
    public static final String HOST_USER_UPDATE_KEY = "HOST_USER_UPDATE:%s";

    /**
     * 主播修改个人简介key
     */
    public static final String HOST_ROOM_NOTICE_KEY = "HOST_ROOM_NOTICE_KEY:%s";

    /**
     * 用户排行榜key
     */
    public static final String USER_RANK_KEY = "USER:RANK:%s:%s:%s";

    /**
     * 房间用户的在线人数
     */
    public static final String ROOM_ONLINE = "USER:ROOM_ONLINE:%s";
}
