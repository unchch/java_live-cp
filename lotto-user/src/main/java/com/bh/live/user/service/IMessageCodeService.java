package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.MessageCode;

/**
 * <p>
 * 短信验证码 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-07-23
 */
public interface IMessageCodeService extends IService<MessageCode> {

    /**
     *@description 发送短信
     *@author WuLong
     *@date 2019/7/23 10:02
     *@param mobile 手机号
     *@param  messageCodeEnum 类型
     *@return Result
     *@exception
     */
    Result sendMessage(String mobile, MessageCodeEnum messageCodeEnum)throws ServiceRuntimeException;
    /**
     *@description 查询数据库短信
     *@author WuLong
     *@date 2019/7/23 10:02
     *@param mobile 手机号
     *@param  messageCodeEnum 类型
     *@return Result
     *@exception
     */
    Result getMessage(String mobile, MessageCodeEnum messageCodeEnum)throws ServiceRuntimeException;
}
