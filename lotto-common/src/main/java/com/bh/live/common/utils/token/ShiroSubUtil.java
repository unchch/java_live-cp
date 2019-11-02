package com.bh.live.common.utils.token;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.pojo.res.cms.ReqSubInfoRes;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yq.
 * @date 2019/4/24 10:34 AM
 * @description shiro主题工具
 * @since
 **/
@Slf4j
public class ShiroSubUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSubUtil.class);

    private ShiroSubUtil() {
    }

    /**
     * 获取当前登录 subject
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前请求的用户信息
     *
     * @return
     */
    public static ReqSubInfoRes getAccountInfo() throws JwtException {
        Subject subject = getSubject();
        if(CommonUtil.isEmpty(subject) || CommonUtil.isEmpty(subject.getPrincipal())){
            log.error("getAccountInfo. subject or principal is null.");
            throw new ServiceRuntimeException(CodeMsg.E_500);
        }
        String strJson = "{%s}";
        String uid = null;
        String roles = null;
        String principal = (String) subject.getPrincipal();
        strJson = String.format(strJson, principal);
        try {
            JSONObject jsonObject = JSONObject.parseObject(strJson).getJSONObject("jwt");
            uid = jsonObject.getString("sub");
            roles = jsonObject.getString("roles");
        } catch (Exception e) {
            LOGGER.error("获取当前请求的用户信息异常", e);
        }
        return new ReqSubInfoRes(uid, roles);
    }


    /**
     * 获取登录账户
     *
     * @return
     */
    public static String getLoginName() {
        return getAccountInfo().getUid();
//        return SystemConstants.ADMIN_UID;
    }

    /**
     * 获取角色，以逗号分隔
     *
     * @return
     */
    public static String getReqRoles() {
        return getAccountInfo().getRoles();
    }

}
