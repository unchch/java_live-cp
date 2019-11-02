package com.bh.live.controller.system;

import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.http.RequestResponseUtil;
import com.bh.live.common.utils.token.JsonWebTokenUtil;
import com.bh.live.model.cms.AuthUser;
import com.bh.live.service.system.IAuthUserService;
import com.google.common.collect.Maps;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuthLoginController
 * @description: AuthLoginController
 * @author: yq.
 * @date 2019-08-08 13:42:00
 */
@RestController
@RequestMapping("/sys")
@Slf4j
@Api(tags = "管理系统-登录")
public class SystemController {

    private static final String STR_USERNAME = "username";
    private static final String STR_REALNAME = "realName";
    private static final String STR_AVATAR = "avatar";

    public static final long REFRESH_PERIOD_TIME = 3600 * 10L;

    @Autowired
    private RedisUtil redisManager;

    @Autowired
    private IAuthUserService authUserService;

    /**
     * 系统用户登录
     * 登录签发 JWT ,这里已经在 passwordFilter 进行了登录认证
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request) {
        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
        String uid = params.get("uid");
        if (CommonUtil.isEmpty(uid)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1003);
        }
        AuthUser authUser = authUserService.selectByUid(uid);
        if (CommonUtil.isEmpty(authUser)) {
            throw new ServiceRuntimeException(CodeMsg.CMS_SYS_1001);
        }
        authUser.setPassword(null);
        authUser.setSalt(null);

        //用户类型
        AuthUser param = new AuthUser();
        param.setUid(uid);
        String roles = authUserService.loadAccountRole(param);
        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), uid,
                "token-server", REFRESH_PERIOD_TIME >> 1, roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis
        redisManager.setByFastJson(String.format(UserRedisKey.SYS_USER_JWT_TOKEN, uid), jwt, REFRESH_PERIOD_TIME, TimeUnit.SECONDS);

        //插入日志
        //LogExeManager.getInstance().executeLogTask(LogTaskFactory.loginLog(uid, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 1, "登录成功"));

        Map<String, Object> ret = Maps.newHashMap();
        ret.put("jwt", jwt);
        ret.put("authUser", authUser);

        return Result.success(ret);
    }

//    /**
//     * description 用户账号的注册
//     */
//    @PostMapping("/register")
//    public Message accountRegister(HttpServletRequest request) {
//        Map<String, String> params = RequestResponseUtil.getRequestBodyMap(request);
//        AuthUser authUser = new AuthUser();
//        String uid = params.get("uid");
//        String password = params.get("password");
//        String userKey = params.get("userKey");
//        //TODO domainId 暂时由前端传参,后期由子站域名解析
//        String domainIdStr = "1";
//        checkObjectNotNull(domainIdStr, ModuleType.BACK, "domainId");
//        checkArgument(domainIdStr.matches("^[0-9]*$"), ModuleType.BACK, "domainId");
//        Long domainId = Long.valueOf(domainIdStr);
//        checkPositive(domainId, ModuleType.BACK, "domainId");
//        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(password) || CommonUtil.isEmpty(domainId)) {
//            // 必须信息缺一不可,返回注册账号信息缺失
//            return new Message().error(2003, "账户信息缺失");
//        }
//        if (accountService.isAccountExistByUid(uid)) {
//            // 账户已存在
//            return new Message().error(2001, "账户已存在");
//        }
//
//        authUser.setUid(uid);
//        authUser.setDomainId(domainId);
//
//        // 从Redis取出密码传输加密解密秘钥
//        String tokenKey = redisManager.getByFastJson(String.format(UserRedisKey.SYS_USER_TOKEN_KEY, IpUtil.getIpFromRequest(WebUtils.toHttp(request)).toUpperCase() + userKey), String.class);
//        String realPassword = AesUtil.aesDecode(password, tokenKey);
//        String salt = StringUtils.getRandomString(6);
//        // 存储到数据库的密码为 MD5(原密码+盐值)
//        authUser.setPassword(ShiroMd5Util.genMd5Pass(realPassword, salt));
//        authUser.setSalt(salt);
//        authUser.setCreateTime(new Date());
//        if (!StringUtils.isEmpty(params.get(STR_USERNAME))) {
//            authUser.setUsername(params.get(STR_USERNAME));
//        }
//        if (!StringUtils.isEmpty(params.get(STR_REALNAME))) {
//            authUser.setRealName(params.get(STR_REALNAME));
//        }
//        if (!StringUtils.isEmpty(params.get(STR_AVATAR))) {
//            authUser.setAvatar(params.get(STR_AVATAR));
//        }
//
//        authUser.setStatus(CommonStatusEnum.NORMAL.getCode());
//        authUser.setCreateBy("用户自己注册");
//        //新注册用户不会赋权了
//        if (authUserService.addUser(authUser)) {
//            LogExeManager.getInstance().executeLogTask(LogTaskFactory.registerLog(uid, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 1, "注册成功"));
//            return new Message().ok(2002, "注册成功");
//        } else {
//            LogExeManager.getInstance().executeLogTask(LogTaskFactory.registerLog(uid, IpUtil.getIpFromRequest(WebUtils.toHttp(request)), (short) 0, "注册失败"));
//            return new Message().ok(1111, "注册失败");
//        }
//    }

}
