package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.AdvanceUtil;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.cms.Keyword;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.req.anchor.HostUserReq;
import com.bh.live.pojo.res.anchor.*;
import com.bh.live.user.dao.HostUserDao;
import com.bh.live.user.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 主播表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-29
 */
@Service
public class HostUserServiceImpl extends ServiceImpl<HostUserDao, HostUser> implements IHostUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IKeywordService keywordService;

    @Autowired
    private ILiveUserService liveUserService;

    @Resource
    private HostUserDao hostUserDao;

    @Autowired
    private IAttentionService attentionService;

    @Autowired
    private IHostAdvanceService hostAdvanceService;

    @Override
    public HostUserFormRes getHostUserById(Integer userId) {
        QueryWrapper<HostUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        HostUser hostUser = baseMapper.selectOne(wrapper);
        if (hostUser != null) {
            HostUserFormRes res = new HostUserFormRes();
            BeanUtils.copyProperties(hostUser, res);
            return res;
        }
        return null;
    }

    @Override
    public void updateHostUser(HostUserReq req) {
        //个人简介修改
        if (StringUtils.isNotEmpty(req.getPersonalProfile())) {
            //过滤敏感词，判断个人简介是否审核通过
            excludeKeyword(req.getPersonalProfile());
            //个人简介10分钟内不能再次修改
            //查询redis十分钟内有没有该用户修改过的简介，如果有，不予修改
            String personalProfile = redisUtil.getByFastJson(String.format(UserRedisKey.HOST_USER_UPDATE_KEY, req.getUserId()), String.class);
            if (StringUtils.isNotEmpty(personalProfile)) {
                throw new RuntimeException("10分钟内不能再次修改个人简介喔，亲！");
            }
            //否则，存在redis，时效10分钟(当前测试5秒)
            redisUtil.setByFastJson(String.format(UserRedisKey.HOST_USER_UPDATE_KEY, req.getUserId()), req.getPersonalProfile(), 10, TimeUnit.MINUTES);
        }
        HostUser hostUser = new HostUser();
        hostUser.setUpdateTime(new Date());
        BeanUtils.copyProperties(req, hostUser);
        UpdateWrapper<HostUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", req.getUserId());
        baseMapper.update(hostUser, wrapper);
    }

    //排除敏感词
    private void excludeKeyword(String personalProfile) {
        List<Keyword> keywords = keywordService.getUsableKeywords();
        if (keywords != null && keywords.size() > 0) {
            List<String> collect = keywords.stream().map(Keyword::getKwName).collect(Collectors.toList());
            for (String str : collect) {
                if (personalProfile.contains(str)) {
                    throw new RuntimeException(String.format("个人简介中包含了敏感词【%s】", str));
                }
            }
        }
    }

    @Override
    public Result registerHostUser(HostUserRecruitReq form) {
        LiveUser user = liveUserService.getUserByMobile(form.getTelephone());
        if (user == null) return Result.error(CodeMsg.E_10007);
        user.setIsAnchor(1);
        user.setSex(form.getSex());
        boolean b = liveUserService.updateById(user);//修改用户属性
        if (!b) return Result.error(CodeMsg.E_500);
        excludeKeyword(form.getPersonalProfile());
        HostUserFormRes hostUserById = getHostUserById(user.getId());
        if (hostUserById != null) return Result.error(CodeMsg.E_10005);
        HostUser hostUser = new HostUser();
        BeanUtils.copyProperties(form, hostUser);
        hostUser.setUserId(user.getId());
        hostUser.setVerifyStatus(2); //待审核
        hostUser.setStatus(user.getIsOnline());
        hostUser.setUsername(user.getNickname());
        hostUser.setIcon(user.getImageUrl());
        hostUser.setDevice(user.getTerminal());
        int insert = hostUserDao.insert(hostUser);//主播信息入库
        if (insert > 0) return Result.success();
        return Result.error(CodeMsg.E_500);
    }

    @Override
    public HostUserHomePageRes selectUserHomePageDetail(Integer userId) {
        HostUserHomePageRes detail = hostUserDao.getUserHomePageDetail(userId);
        //获取关注列表
        detail.setFansList(attentionService.getAttentionByUserId(userId));
        //获取预告列表(一周以内的预告)
        List<HostAdvanceRes> hostAdvanceRes1 = hostAdvanceService.queryHostAdvance(userId, null, 7);
        hostAdvanceRes1.forEach(item -> {
            item.setLiveDate(item.getAdvanceTime());
        });
        //List<HostUserLiveSeedAdvanceRes> hostAdvanceRes = hostAdvanceService.queryHostAdvance(userId);
        List<AdvancesRes> advancesResList = AdvanceUtil.advanceHandler(hostAdvanceRes1);
        detail.setAdvanceList(advancesResList);
        return detail;
    }

    @Override
    public Attention getIsAttention(Integer userId, Integer loginUser) {
        List<Attention> attentions = hostUserDao.getUserAttention(userId, loginUser, "one", null);
        if(CommonUtil.isNotEmpty(attentions)){
            return attentions.get(0);
        }
        return null;
    }

    @Override
    public String getHostNotice(Integer userId) {
        return hostUserDao.hostRoomNotice(userId);
    }

}
