package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.config.JwtProperties;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.JwtHelper;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.model.user.LiveUser;
import com.bh.live.model.user.MessageCode;
import com.bh.live.model.wallet.Wallet;
import com.bh.live.pojo.req.user.LoginReq;
import com.bh.live.pojo.req.user.RegisterReq;
import com.bh.live.pojo.res.cms.token.TokenRes;
import com.bh.live.user.dao.LiveUserDao;
import com.bh.live.user.service.ILiveConfigurationService;
import com.bh.live.user.service.ILiveUserService;
import com.bh.live.user.service.IMessageCodeService;
import com.bh.live.user.service.IWalletService;
import com.bh.live.user.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 直播用户表 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-07-24
 */
@Service
@Slf4j
public class LiveUserServiceImpl extends ServiceImpl<LiveUserDao, LiveUser> implements ILiveUserService {

    @Resource
    RedisUtil redisUtil;
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    JwtProperties jwtProperties;
    @Resource
    LiveUserDao userDao;
    @Resource
    IMessageCodeService messageCodeService;
    @Resource
    IWalletService walletService;
    @Resource
    ILiveConfigurationService liveConfigurationService;

    @Override
    public Result login(LoginReq loginReq) throws ServiceRuntimeException {
        try {
            QueryWrapper<LiveUser> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(LiveUser::getMobile, loginReq.getMobile());
            LiveUser user = userDao.selectOne(queryWrapper);
            //查询用户是否已存在
            if (user == null) {
                return Result.error(CodeMsg.E_10010);
            }
            //密码登录
            if (loginReq.getMode().equals(UserEnum.LoginModeEnum.PASSWORD.getCode())) {
                String strMD5 = MD5Utils.getStrrMD5(loginReq.getPassword());
                //验证密码是否一致
                boolean saltVerify = MD5Utils.getSaltverifyMD5(strMD5, user.getPassword());
                if (!saltVerify) {
                    return Result.error(CodeMsg.E_10011);
                }
            }
            //短信登录
            else if (loginReq.getMode().equals(UserEnum.LoginModeEnum.SHORT_MESSAGE.getCode())) {
                //验证验证码是否正确
                QueryWrapper<MessageCode> messageCodeQueryWrapper = new QueryWrapper<>();
                messageCodeQueryWrapper.lambda().eq(MessageCode::getMobile, loginReq.getMobile())
                        .eq(MessageCode::getType, MessageCodeEnum.LOGIN.getCode())
                        .orderByDesc(MessageCode::getId);

                Result x = verifyCodeExpireTime(loginReq.getVerifyCode(), messageCodeQueryWrapper);
                if (x != null) {
                    return x;
                }
            }
            UserEnum.IsUsableEnum usableCode = UserEnum.IsUsableEnum.getEnumByCode(user.getIsUsable());
            //用户属性判断
            if (usableCode == UserEnum.IsUsableEnum.NO) {
                return Result.error(CodeMsg.E_10018);
            }
            UserEnum.IsLoginEnum loginCode = UserEnum.IsLoginEnum.getEnumByCode(user.getIsLogin());
            if (loginCode == UserEnum.IsLoginEnum.NO) {
                return Result.error(CodeMsg.E_10019);
            }
            //更新用户信息
            LiveUser updateUser = new LiveUser();
            updateUser.setId(user.getId());
            updateUser.setUpdateTime(new Date());
            updateUser.setLastip(loginReq.getIp());
            updateUser.setTerminal(loginReq.getTerminal());
            updateUser.setIsOnline(UserEnum.OnOffLineEnum.YES.getCode());
            userDao.updateById(updateUser);
            user.setIsOnline(updateUser.getIsOnline());
            //生成token
            String accessToken = getAccessToken(user, loginReq);
            String tokenValue = jwtHelper.getTokenValue(user, loginReq.getAuto(), loginReq.getUserAgent());
            String jwtTokenCacheKey = RedisUtil.getJWTTokenCacheKey(user.getId().toString(), user.getMobile());
            long expireTime = jwtProperties.getExpireTime();
            if (loginReq.getAuto() == 1) {
                expireTime = jwtProperties.getWeekExpireTime();
            }
            //token写入缓存
            redisUtil.set(jwtTokenCacheKey, tokenValue, expireTime);
            return Result.success(new TokenRes(accessToken));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceRuntimeException(CodeMsg.E_10016);
        }
    }

    /**
     * @param verifyCode 验证码
     * @return Result
     * @description 验证短信码
     * @author WuLong
     * @date 2019/8/2 12:21
     * @Param messageCodeQueryWrapper 查询数据库最新的验证码
     */
    private Result verifyCodeExpireTime(String verifyCode, QueryWrapper<MessageCode> messageCodeQueryWrapper) {
        MessageCode messageCode = messageCodeService.getOne(messageCodeQueryWrapper, false);
        if (null == messageCode) {
            return Result.error(CodeMsg.E_10014);
        }
        Date date = new Date();
        Date createTime = messageCode.getCreateTime();
        int differentSecond = DateUtils.differentSeconds(date, createTime);
        //验证验证码是否已过期
        if (differentSecond > 600) {
            return Result.error(CodeMsg.E_10013);
        }
        String code = messageCode.getCode();
        if (!verifyCode.equals(code)) {
            return Result.error(CodeMsg.E_10012);
        }
        return null;
    }

    @Override
    public Result register(RegisterReq registerReq) throws ServiceRuntimeException {
        LiveUser user = new LiveUser();
        CommonUtil.beanCopy(registerReq, user);
        QueryWrapper<LiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveUser::getMobile, registerReq.getMobile());
        LiveUser userPhone = userDao.selectOne(queryWrapper);
        //判断手机号是否已经注册
        if (userPhone != null) {
            return Result.error(CodeMsg.E_10008);
        }
        //判断昵称是否已经占用
        LiveUser liveUser = getLiveUserByNickname(registerReq.getNickName());
        if (liveUser != null) {
            return Result.error(CodeMsg.E_10022);
        }
        //验证验证码是否正确
        QueryWrapper<MessageCode> messageCodeQueryWrapper = new QueryWrapper<>();
        messageCodeQueryWrapper.lambda().eq(MessageCode::getMobile, registerReq.getMobile())
                .eq(MessageCode::getType, MessageCodeEnum.REGISTER.getCode())
                .orderByDesc(MessageCode::getId);
        Result x = verifyCodeExpireTime(registerReq.getVerifyCode(), messageCodeQueryWrapper);
        if (x != null) {
            return x;
        }
        //设置默认头像
        try {
            QueryWrapper<LiveConfiguration> liveConfigurationQueryWrapper = new QueryWrapper<>();
            liveConfigurationQueryWrapper.lambda().eq(LiveConfiguration::getTypeValue, "user_head")
                    .eq(LiveConfiguration::getConfigName,"default")
                    .eq(LiveConfiguration::getIsUsable, 1);
            LiveConfiguration liveConfiguration = liveConfigurationService.getOne(liveConfigurationQueryWrapper);
            if (liveConfiguration != null) {
                user.setImageUrl(liveConfiguration.getConfigValue());
            }
        } catch (Exception e) {
            log.error("查询默认头像失败:{}", e);
        }
        //入库
        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));
        user.setCreatTime(new Date());
        user.setChannel(1);
        user.setNickname(registerReq.getNickName());
        save(user);
        //初始化钱包
        Wallet wallet = new Wallet();
        wallet.setUserId(user.getId());
        wallet.setNotdepositlMoney(BigDecimal.ZERO);
        wallet.setAllMoney(BigDecimal.ZERO);
        wallet.setDepositlMoney(BigDecimal.ZERO);
        walletService.save(wallet);
        return Result.success();
    }

    @Override
    public LiveUser getLiveUserByNickname(String nickname) throws ServiceRuntimeException {
        QueryWrapper<LiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveUser::getNickname, nickname);
        return userDao.selectOne(queryWrapper);
    }

    @Override
    public LiveUser getUserByMobile(String mobile) throws ServiceRuntimeException {
        QueryWrapper<LiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveUser::getMobile, mobile);
        return userDao.selectOne(queryWrapper);
    }

    private String getAccessToken(LiveUser user, LoginReq loginReq) {
        Map<String, String> c = new LinkedHashMap<>();
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_RANDOM_STRING, CommonUtil.getRandomStr(8));
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID, user.getId().toString());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_TIME_STAMP, System.currentTimeMillis() + "");
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE, user.getMobile());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IP, user.getLastip());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_DEVICE, user.getTerminal());
        c.put(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT, loginReq.getUserAgent());
        return jwtHelper.genToken(c);
    }
}
