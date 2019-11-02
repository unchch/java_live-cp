package com.bh.live.service.impl.user;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.config.JwtProperties;
import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.enums.DeviceEnum;
import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CopyUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.JwtHelper;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.dao.user.LiveUserDao;
import com.bh.live.model.cms.UserStatisticsCMS;
import com.bh.live.model.configuration.LiveConfiguration;
import com.bh.live.model.user.LiveUser;
import com.bh.live.model.user.UserStatistics;
import com.bh.live.model.wallet.Wallet;
import com.bh.live.pojo.req.user.LiveUserFullReq;
import com.bh.live.pojo.req.user.SendMessageReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.LiveUserFullExcelRes;
import com.bh.live.pojo.res.user.LiveUserFullRes;
import com.bh.live.pojo.res.user.UserStatisticsRes;
import com.bh.live.service.configuration.ILiveConfigurationService;
import com.bh.live.service.user.ILiveUserService;
import com.bh.live.service.wallet.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播用户表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@Service
@Slf4j
public class LiveUserServiceImpl extends ServiceImpl<LiveUserDao, LiveUser> implements ILiveUserService {

    @Resource
    private ILiveConfigurationService liveConfigurationService;

    @Resource
    private IWalletService walletService;

    @Resource
    private LiveUserDao liveUserDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    JwtHelper jwtHelper;

    @Autowired
    JwtProperties jwtProperties;


    @Resource
    private RestTemplate httpClientTemplate;

    // 根据用户id查询用户通常信息
    private LiveUser queryLiveUserInfoByID(Integer userId) {
        Assert.notNull(userId, "用户id不能为空");
        return super.getById(userId);
    }

    @Override
    public TableDataInfo queryLiveUserPage(LiveUserFullReq req) {
        req = req == null ? new LiveUserFullReq() : req;
        // 入参设置
        LambdaQueryWrapper<LiveUser> query = new QueryWrapper<LiveUser>().lambda();
        if (ObjectUtil.isNotEmpty(req.getNickname())) {
            // 昵称
            query.like(LiveUser::getNickname, req.getNickname());
        }
        if (ObjectUtil.isNotEmpty(req.getMobile())) {
            // 手机号
            query.like(LiveUser::getMobile, req.getMobile());
        }
        if (ObjectUtil.isNotEmpty(req.getId())) {
            // 用户id
            query.eq(LiveUser::getId, req.getId());
        }
        if (ObjectUtil.isNotEmpty(req.getIsOnline())) {
            // 用户是否在线
            query.eq(LiveUser::getIsOnline, req.getIsOnline());
        }
        if (ObjectUtil.isNotEmpty(req.getChannel())) {
            // 用户注册平台0:ios 1：安卓 2：pc
            query.eq(LiveUser::getChannel, req.getChannel());
        }
        // 时间范围搜索
        if (ObjectUtil.isNotEmpty(req.getStartTime())) {
            query.gt(LiveUser::getCreatTime, req.getStartTime());
        }
        if (ObjectUtil.isNotEmpty(req.getEndTime())) {
            query.lt(LiveUser::getCreatTime, req.getEndTime());
        }
//        用户类型 0普通 1专家 2主播
        if (ObjectUtil.isNotEmpty(req.getUserType())) {
            if(req.getUserType()==1){
                query.eq(LiveUser::getIsExpert, 1);
            }else if(req.getUserType()==2){
                query.eq(LiveUser::getIsAnchor, 1);
            }else{
                query.eq(LiveUser::getIsExpert, 0);
                query.eq(LiveUser::getIsAnchor, 0);
            }
        }
        //排序
        query.orderByDesc(LiveUser::getCreatTime);
//        //是否是专家  0否  1是
//        if (ObjectUtil.isNotEmpty(req.getIsExpert())) {
//            query.lt(LiveUser::getIsExpert, req.getIsExpert());
//        }
//        //是否是主播  0否 1是
//        if (ObjectUtil.isNotEmpty(req.getIsAnchor())) {
//            query.lt(LiveUser::getIsAnchor, req.getIsAnchor());
//        }
        // 出参打包
        List<LiveUserFullRes> list = new LinkedList<LiveUserFullRes>();
        // 查询数据
        IPage<LiveUser> page = super.page(new Page<LiveUser>(req.getPageNum(), req.getPageSize()), query);
        List<Integer> userList = new ArrayList<>();
        for (Iterator<LiveUser> iterator = page.getRecords().iterator(); iterator.hasNext(); ) {
            LiveUser user = iterator.next();
            userList.add(user.getId());
            LiveUserFullRes res = new LiveUserFullRes();
            BeanUtils.copyProperties(user, res);// 类型转换
            list.add(res);
        }
        if (userList != null && userList.size() > 0) {
            List<Wallet> wallets = walletService.queryWalletByUserIds(userList);
            Map<Integer, Wallet> walletMaps = null;
            if (wallets != null) {
                walletMaps = wallets.stream().collect(Collectors.toMap(Wallet::getUserId, Function.identity(), (key1, key2) -> key2));
            }
            if (walletMaps != null) {
                for (LiveUserFullRes liveUserFullRes : list) {
                    Wallet wallet = walletMaps.get(liveUserFullRes.getId());
                    if (wallet != null) {
                        liveUserFullRes.setAllMoney(wallet.getAllMoney());
                    }
                }
            }
        }
        return new TableDataInfo(list, page.getTotal());
    }

    @Override
    public boolean updateLiveUserInfo(LiveUserFullReq req) {

        LiveUser liveUser = new LiveUser();
        BeanUtils.copyProperties(req, liveUser);// 类型转换
        return super.update(liveUser,
                new UpdateWrapper<LiveUser>().lambda().eq(LiveUser::getId, liveUser.getId()));
    }

    @Override
    public boolean isProhibit(Integer userId, String prohibitType) {
        // 根据id查询用户信息
        LiveUser liveUser = this.queryLiveUserInfoByID(userId);
        // 如果用户已经禁止则解除，否则就禁止
        if ("0".equals(prohibitType)) {// 0:禁止登录
            // 是否可以登录 0不可 1可以
            Integer islogin = liveUser.getIsLogin() == 0 ? 1 : 0;
            liveUser.setIsLogin(islogin);
        } else if ("1".equals(prohibitType)) {// 1:禁止发布竞猜方案
            // 是否可以发布竞猜 0不可 1可以
            Integer isPublish = liveUser.getIsPublish() == 0 ? 1 : 0;
            liveUser.setIsPublish(isPublish);
        } else if ("2".equals(prohibitType)) {// 2:禁止聊天室发言
            // 是否可以直播间发言 0不可 1可以
            Integer isSpeak = liveUser.getIsSpeak() == 0 ? 1 : 0;
            liveUser.setIsSpeak(isSpeak);
        }

        refreshHeadToken(liveUser);//刷新缓存
        return super.update(liveUser,
                new UpdateWrapper<LiveUser>().lambda().eq(LiveUser::getId, liveUser.getId()));
    }

    /**
     * 清除用户缓存
     *
     * @param liveUser
     * @return
     */
    public boolean cleanUserCache(LiveUser liveUser) {
//		String key = redisUtil.getJWTTokenCacheKey(userId, mobile);
//		redisUtil.del(key);
        refreshHeadToken(liveUser);

        UpdateWrapper<LiveUser> wrapper = new UpdateWrapper<LiveUser>();
        //修改用户在线状态
        LiveUser user = new LiveUser();
        user.setId(liveUser.getId());
        user.setIsOnline(0);
        super.updateById(user);
        return true;
    }

    @Transient
    @Override
    public boolean forceToExit(Integer userId) {
        /**
         * 实现步骤 一、判断用户是否在线 1、不在线:不做处理 2、在线:执行以下处理 二、清除用户缓存(强制下线) 三、记录被禁止登录时长、修改在线状态
         */
        LiveUser liveUser = this.queryLiveUserInfoByID(userId);
        // 是否在线 0不再 1在线
        if(liveUser.getIsOnline() == 1){
            return true;
        }
        //Assert.isTrue(liveUser.getIsOnline() == 1, "该用户已不在线");
        // 修改用户在线状态
        liveUser.setIsOnline(0);
        liveUser.setUpdateTime(new Date());
        // 清除缓存
        cleanUserCache(liveUser);
        // 查询配置时长
        LiveConfiguration config = liveConfigurationService
                .queryUsableConfig(new LiveConfiguration().setTypeValue("no_login"));
        // 加入计时表
        liveUserDao.addUserLeave(liveUser.getId(), config.getConfigValue());
        super.updateById(liveUser);
        return true;
    }

    @Override
    public boolean isProhibitLiveUser(Integer userId) {
        /**
         * 实现步骤 一、判断用户是否在线 1、不在线:判断用户是否已经被禁用 是:解除禁用 否:禁止使用 2、在线:清除用户缓存(强制下线) 禁止使用 修改在线状态
         */
        // 获取用户信息
        LiveUser liveUser = this.queryLiveUserInfoByID(userId);
        if (liveUser == null) {
            return false;
        }
        // 是否在线 0不再 1在线
        if (liveUser.getIsOnline() == 0) {
            // 是否可用 0不可 1可用
            Integer isUsable = liveUser.getIsUsable() == 0 ? 1 : 0;
            liveUser.setIsUsable(isUsable);
        } else {
            // 修改缓存，强制下线
            cleanUserCache(liveUser);
            liveUser.setIsUsable(0);
            liveUser.setIsOnline(0);
        }
        refreshHeadToken(liveUser);//刷新缓存
        return super.updateById(liveUser);// 更新用户信息
    }

    @Override
    public boolean resetUserpassword(Integer userId) {
        try {
            LiveUser liveUser = this.queryLiveUserInfoByID(userId);
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setMobile(liveUser.getMobile());//获取用户手机号
            sendMessageReq.setType(MessageCodeEnum.RESET_PASSWORD.getCode());
            String url = "http://172.16.168.89:9350/send/registercode";
            ResponseEntity<Result> resultResponseEntity = httpClientTemplate.postForEntity(url, sendMessageReq, Result.class);
            Result body = resultResponseEntity.getBody();
        }catch (Exception e){
            log.error("重置密码失败",e);
            return false;
        }
        return true;
    }

    @Override
    public UserStatisticsRes queryLiveUserAllInfoByID(Integer userId) {
        UserStatisticsRes res = new UserStatisticsRes();// 信息统计
        LiveUser liveUser = this.queryLiveUserInfoByID(userId);
        BeanUtils.copyProperties(liveUser, res);// 类型转换
        UserStatistics statistics = liveUserDao.queryUserStatisticsById(userId);
        BeanUtils.copyProperties(statistics, res);// 类型转换
        res.setUserId(liveUser.getId());//用户id
        res.setIsOnlist(0);//是否上榜
        return res;
    }

    @Override
    public Result refreshHeadToken(LiveUser liveUser) {
        try {
            String jwtTokenCacheKey = RedisUtil.getJWTTokenCacheKey(String.valueOf(liveUser.getId()), liveUser.getMobile());
            Object o = redisUtil.get(jwtTokenCacheKey);
            //判断缓存是否存在
            if (o == null) {
                return Result.error(CodeMsg.E_20001);
            }
            Map<String, String> tokenKeyMap = jwtHelper.verifyToken(o.toString());
            int auto = Integer.valueOf(tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_AUTO));
            String userAgent = tokenKeyMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_USER_AGENT);
            String tokenValue = jwtHelper.getTokenValue(liveUser, auto, userAgent);
            long expireTime = jwtProperties.getExpireTime();
            if (auto == 1) {
                expireTime = jwtProperties.getWeekExpireTime();
            }
            //token写入缓存
            redisUtil.set(jwtTokenCacheKey, tokenValue, expireTime);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(CodeMsg.E_20007);
        }
    }

    @Override
    public UserStatisticsCMS queryUserStatistics() {
        List<LiveUser> list = super.list(new QueryWrapper<LiveUser>().lambda().eq(LiveUser::getIsUsable, 1));
        UserStatisticsCMS cms = new UserStatisticsCMS();
        cms.setOnlineAndroid(list.stream().filter(e -> e.getIsOnline() == 1 && e.getTerminal() != null
                && e.getTerminal().toLowerCase().equals("android")).count());
        cms.setOnlinePC(list.stream().filter(e -> e.getIsOnline() == 1 && e.getTerminal() != null
                && e.getTerminal().toLowerCase().equals("pc")).count());
        cms.setOnlineIOS(list.stream().filter(e -> e.getIsOnline() == 1 && e.getTerminal() != null
                && e.getTerminal().toLowerCase().equals("ios")).count());
        cms.setAllMoney(liveUserDao.queryAllMoney());
        cms.setNewlyAndroid(list.stream().filter(e -> DateUtils.isIinYesterday(e.getCreatTime())
                && e.getTerminal() != null && e.getTerminal().toLowerCase().equals("android")).count());
        cms.setNewlyPC(list.stream().filter(e -> DateUtils.isIinYesterday(e.getCreatTime())
                && e.getTerminal() != null && e.getTerminal().toLowerCase().equals("pc")).count());
        cms.setNewlyIOS(list.stream().filter(e -> DateUtils.isIinYesterday(e.getCreatTime())
                && e.getTerminal() != null && e.getTerminal().toLowerCase().equals("ios")).count());
        return cms;
    }

    @Override
    public  List<LiveUserFullExcelRes> queryUserTransExcel(LiveUserFullReq req) {
        req = req == null ? new LiveUserFullReq() : req;
        // 入参设置
        LambdaQueryWrapper<LiveUser> query = new QueryWrapper<LiveUser>().lambda();
        if (ObjectUtil.isNotEmpty(req.getNickname())) {
            // 昵称
            query.like(LiveUser::getNickname, req.getNickname());
        }
        if (ObjectUtil.isNotEmpty(req.getMobile())) {
            // 手机号
            query.like(LiveUser::getMobile, req.getMobile());
        }
        if (ObjectUtil.isNotEmpty(req.getId())) {
            // 用户id
            query.eq(LiveUser::getId, req.getId());
        }
        if (ObjectUtil.isNotEmpty(req.getIsOnline())) {
            // 用户是否在线
            query.eq(LiveUser::getIsOnline, req.getIsOnline());
        }
        if (ObjectUtil.isNotEmpty(req.getChannel())) {
            // 用户注册平台0:ios 1：安卓 2：pc
            query.eq(LiveUser::getChannel, req.getChannel());
        }
        // 时间范围搜索
        if (ObjectUtil.isNotEmpty(req.getStartTime())) {
            query.gt(LiveUser::getCreatTime, req.getStartTime());
        }
        if (ObjectUtil.isNotEmpty(req.getEndTime())) {
            query.lt(LiveUser::getCreatTime, req.getEndTime());
        }
//        用户类型 0普通 1专家 2主播
        if (ObjectUtil.isNotEmpty(req.getUserType())) {
            if(req.getUserType()==1){
                query.eq(LiveUser::getIsExpert, 1);
            }else if(req.getUserType()==2){
                query.eq(LiveUser::getIsAnchor, 1);
            }else{
                query.eq(LiveUser::getIsExpert, 0);
                query.eq(LiveUser::getIsAnchor, 0);
            }
        }
        //排序
        query.orderByDesc(LiveUser::getCreatTime);
        // 出参
        List<LiveUserFullExcelRes> list = null;
        // 查询数据
        List<LiveUser> liveUsers = getBaseMapper().selectList(query);
        //转到Excel专用req
        list = CopyUtil.copyPropertiesList(LiveUserFullExcelRes.class,liveUsers);
        LiveUser liveUser = new LiveUser();
        //用户idList
        List<Integer> userList = new ArrayList<>();
        if(null != list && list.size() > 0){
            if(null != list && list.size() > 0){
                list.forEach(userFullExcelRes -> {
                    //用户类型
                    userList.add(userFullExcelRes.getId());
                    userFullExcelRes.setCreateTimeStr(DateUtils.parseDateToStr("yyyy-MM-dd HH:ss", userFullExcelRes.getCreatTime()));
                    BeanUtils.copyProperties(userFullExcelRes, liveUser);// 类型转换
                    userFullExcelRes.setUserTypeName(UserEnum.UserTypeEnum.getUserType(liveUser).getName());       //用户类型
                    userFullExcelRes.setChannelName(DeviceEnum.ChannelModeEnum.valueOf(userFullExcelRes.getChannel()).getDesc());    //用户注册渠道
                });
                List<Wallet> wallets = walletService.queryWalletByUserIds(userList);    //用户金额明细
                if(null != wallets && wallets.size() > 0){
                    Map<Integer, Wallet> walletMap = wallets.stream().collect(Collectors.toMap(Wallet::getUserId, Function.identity(), (key1, key2) -> key2));
                    if (walletMap != null) {
                        list.forEach(userFullExcelRes -> {
                            Wallet wallet = walletMap.get(userFullExcelRes.getId());
                            if (wallet != null) {
                                userFullExcelRes.setAllMoney(wallet.getAllMoney());
                            }
                        });
                    }
                }
            }
        }
        return list;
    }
}

