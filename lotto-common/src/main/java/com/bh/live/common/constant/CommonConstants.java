package com.bh.live.common.constant;

/**
 * 公共常量类
 * @author lgs
 */
public class CommonConstants {


    /**
     * 魔法值，勿改
     */
    //香港彩 统计
    public static final String specialCode = "特码热门";
    public static final String pk10EndNum = "特码尾数热门";

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TNE = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;

    /**
     *  请求头中存放token 信息的头字段
     */
    public final static String ACCESS_TOKEN = "accessToken";

    /**
     * 用户token缓存前缀
     */
    public final static String ACCOUNT_FAILURE_TOKEN_CACHE_PREFIX = "account_token_";

    /**
     *jwt claims id 信息
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_ID = "ACCOUNT_ID";

    /**
     *jwt claims mobile 信息
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE = "MOBILE";
    /**
     *jwt claims user_type 用户类型 0普通 1专家 2主播 信息
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_TYPE = "TYPE";
    /**
     *jwt claims is_usable 是否可用 0不可 1可用
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_USABLE = "IS_USABLE";
    /**
     *jwt claims is_online 是否在线  0不再  1在线
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_ONLINE = "IS_ONLINE";
    /**
     *jwt claims is_login 是否可以登录  0不可  1可以
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_LOGIN = "IS_LOGIN";
    /**
     *jwt claims is_publish 是否可以发布竞猜  0不可  1可以
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_PUBLISH ="IS_PUBLISH";
    /**
     *jwt claims is_speak 是否可以直播间发言  0不可  1可以
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_SPEAK = "IS_SPEAK";
    /**
     *jwt claims nickname 用户昵称
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_NICK_NAME = "NICK_NAME";
    /**
     *jwt claims AUTO 是否自动登录
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_AUTO = "AUTO";
    /**
     *jwt claims RANDOM_STRING 随机字符串
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_RANDOM_STRING = "RANDOM_STRING";
    /**
     *jwt claims TIME_STAMP 时间戳
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_TIME_STAMP = "TIME_STAMP";
    /**
     *jwt claims  IS_EXPERT是否是专家  0否  1是
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_EXPERT = "IS_EXPERT";
    /**
     *jwt claims  IS_ANCHOR是否是主播  0否 1是
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IS_ANCHOR = "IS_ANCHOR";
    /**
     *jwt claims  IP
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_IP = "IP";
    /**
     *jwt claims  DEVICE
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_DEVICE = "DEVICE";
    /**
     *jwt claims  userAgent
     */
    public static final String LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT = "USER_AGENT";
    /**
     * zuul 头部
     */
    public static final String ZUUL_HEADER_DATA = "ZUUL_HEADER_DATA";
    /**
     * HEADER_USER_AGENT
     */
    public static final String HEADER_USER_AGENT = "User-Agent";
    /**
     * 开奖消息队列AWARD_LOTTERY_QUEUE
     */
    public static final String AWARD_LOTTERY_QUEUE = "AWARD_LOTTERY_QUEUE";
    /**
     * CMS jwt claims  USER_ID
     */
    public static final String LOING_CMS_CLAIMS_ACCOUNT_USER_ID = "USER_ID";
    /**
     * CMS jwt claims  ROLE_ID
     */
    public static final String LOING_CMS_CLAIMS_ACCOUNT_ROLE_ID = "ROLE_ID";
    /**
     *CMS jwt claims  ACCOUNT
     */
    public static final String LOING_CMS_CLAIMS_ACCOUNT = "ACCOUNT";

    /**
     *CMS jwt claims  IS_USABLE
     */
    public static final String LOING_CMS_CLAIMS_ACCOUNT_IS_USABLE = "IS_USABLE";


    /**
     *CMS jwt claims  CONTACT_WAY
     */
    public static final String LOING_CMS_CLAIMS_ACCOUNT_CONTACT_WAY = "CONTACT_WAY";

}
