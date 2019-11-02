package com.bh.live.common.core.controller;

import com.bh.live.common.constant.CommonConstants;
import com.bh.live.common.constant.UserRedisKey;
import com.bh.live.common.enums.DeviceEnum;
import com.bh.live.common.http.ServletUtils;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.*;
import com.bh.live.model.user.LiveUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

/**
 * web层通用数据处理
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Slf4j
public class BaseController {
    @Autowired
    protected RedisUtil redisUtil;

    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }


    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }


    /**
     * 获取当前请求ip
     *
     * @return
     */
    protected String getAddr() {
        return IpUtil.getIpAddress(getRequest());
    }
    /**
     *@description 获取头部信息封装成LiveUser对象
     *@author WuLong
     *@date 2019/8/2 14:55
     *@param
     *@return {@link LiveUser}
     *@exception
     */
    protected LiveUser getHeaderLiveUser(){
        LiveUser liveUser = null;
        String header = getRequest().getHeader(CommonConstants.ZUUL_HEADER_DATA);
        if(CommonUtil.isNotEmpty(header)){
            try {
                header = URLDecoder.decode(header,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Map<String, Object> zuulHeaderMap = JsonUtil.json2map(header);
            liveUser = new LiveUser();
            liveUser.setId(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_ID).toString()));
            liveUser.setMobile(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_MOBILE).toString());
            liveUser.setIsOnline(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_ONLINE).toString()));
            liveUser.setIsPublish(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_PUBLISH).toString()));
            liveUser.setIsLogin(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_SPEAK).toString()));
            liveUser.setIsSpeak(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_LOGIN).toString()));
            liveUser.setIsUsable(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_USABLE).toString()));
            liveUser.setNickname(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_NICK_NAME).toString());
            liveUser.setIsExpert(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_EXPERT).toString()));
            liveUser.setIsAnchor(Integer.valueOf(zuulHeaderMap.get(CommonConstants.LOGIN_JWT_CLAIMS_ACCOUNT_IS_ANCHOR).toString()));
        }
        return liveUser;
    }

    /**
     *@description 获取访问设备
     *@author WuLong
     *@date 2019/8/5 18:24
     *@param
     *@return  {@link DeviceEnum}
     *@exception
     */
    protected DeviceEnum getDevice(){
        String header_user_agent = getRequest().getHeader(CommonConstants.HEADER_USER_AGENT);
        String userAgent = header_user_agent.toLowerCase();
        if(userAgent.indexOf("micromessenger")!= -1){
            return DeviceEnum.WX;
        }else if(userAgent.indexOf("android") != -1){
            return DeviceEnum.ANDROID;
        }else if(userAgent.indexOf("iphone") != -1 || userAgent.indexOf("ipad") != -1 || userAgent.indexOf("ipod") != -1){
            return DeviceEnum.IOS;
        }else{
            return DeviceEnum.PC;
        }
    }
    /**
     *@description 获取访问userAgent
     *@author WuLong
     *@date 2019/8/7 18:13
     *@return 内容
     */
    protected String getUserAgent(){
        return getRequest().getHeader(CommonConstants.HEADER_USER_AGENT);
    }

    /**
     *@description 根据动态秘钥解密
     *@author WuLong
     *@date 2019/7/31 11:37
     *@param content 待解密的内容
     *@param  userKey 用户的动态解密key
     *@return {@link Result}
     */
    protected Result decrypt(String content,String userKey){
        String host = getAddr();
        try {
            Object tokenKey = redisUtil.get(String.format(UserRedisKey.SYS_USER_TOKEN_KEY, host + userKey));
            if(null == tokenKey){
                return Result.error(CodeMsg.E_20011);
            }
            return Result.success(AesUtil.decrypt(content,tokenKey.toString(),userKey));
        } catch (Exception e) {
            log.error("动态秘钥解密失败:"+e.getMessage(),e);
            return Result.error(CodeMsg.E_20010);
        }
    }

    public void setInsertProperties(Class c, Object o) {
        try {
            CopyUtil.setField(c, "createBy", o, "admin");
            CopyUtil.setField(c, "modifyBy", o, "admin");
            CopyUtil.setField(c, "createTime", o, new Date());
            CopyUtil.setField(c, "updateTime", o, new Date());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpdateProperties(Class c, Object o) {
        try {
            CopyUtil.setField(c, "modifyBy", o, "admin");
            CopyUtil.setField(c, "updateTime", o, new Date());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
