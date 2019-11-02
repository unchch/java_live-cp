package com.bh.live.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.model.user.LiveUser;
import com.bh.live.model.user.MessageCode;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.user.dao.LiveUserDao;
import com.bh.live.user.dao.MessageCodeDao;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.user.service.IMessageCodeService;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 短信验证码 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-07-23
 */
@Service
public class MessageCodeServiceImpl extends ServiceImpl<MessageCodeDao, MessageCode> implements IMessageCodeService {
    @Resource
    MessageCodeDao messageCodeDao;
    @Resource
    LiveUserDao userDao;


    @Override
    public Result sendMessage(String mobile, MessageCodeEnum messageCodeEnum) throws ServiceRuntimeException {
        //如果是发送注册短信
        if(messageCodeEnum == MessageCodeEnum.REGISTER){
            QueryWrapper<LiveUser> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(LiveUser::getMobile, mobile);
            LiveUser userPhone = userDao.selectOne(queryWrapper);
            //判断手机号是否已经注册
            if (userPhone != null) {
                return Result.error(CodeMsg.E_10008);
            }
        }
        //生成验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        Map<String, String> beginAndEndTime = DateUtils.getBeginAndEndTime(new Date());
        //TODO 这里需要对接第三方发送短信验证码
        //验证码入库
        QueryWrapper<MessageCode> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MessageCode::getMobile, mobile).eq(MessageCode::getType, messageCodeEnum.getCode())
                .between(MessageCode::getCreateTime, beginAndEndTime.get("begin"), beginAndEndTime.get("end"));
        Integer sendCount = messageCodeDao.selectCount(queryWrapper);
        if (sendCount > 10000) {
            return Result.error(CodeMsg.E_30001.code, String.format(CodeMsg.E_30001.message,messageCodeEnum.getName()));
        }
        MessageCode messageCode = new MessageCode();
        messageCode.setCreateTime(new Date());
        messageCode.setMobile(mobile);
        messageCode.setType(messageCodeEnum.getCode());
        messageCode.setCode(verifyCode);
        int count = messageCodeDao.insert(messageCode);
        if (count > 0) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.E_30000);
        }
    }

    @Override
    public Result getMessage(String mobile, MessageCodeEnum messageCodeEnum) throws ServiceRuntimeException {
        //验证码入库
        QueryWrapper<MessageCode> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MessageCode::getMobile, mobile).eq(MessageCode::getType, messageCodeEnum.getCode()).orderByDesc(MessageCode::getCreateTime);
        MessageCode messageCode = getOne(queryWrapper, false);
        return Result.success(messageCode);
    }
}
