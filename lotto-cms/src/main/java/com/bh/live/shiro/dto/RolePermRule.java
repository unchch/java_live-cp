package com.bh.live.shiro.dto;

import com.bh.live.common.utils.token.JsonWebTokenUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * 角色资源规则
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
public class RolePermRule implements Serializable {

    /**
     * 无需验证的权限
     */
    private static final String ANON_ROLE = "role_anon";

    /**
     * 资源URL
     */
    private String path;

    /**
     * 访问资源所需要的角色列表，多个列表用逗号间隔
     */
    private String needRoles;

    /**
     * description 将url needRoles 转化成shiro可识别的过滤器链：url=jwt[角色1、角色2、角色n]
     *
     * @return java.lang.StringBuilder
     */
    public StringBuilder toFilterChain() {
        if (null == this.path || this.path.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> setRole = JsonWebTokenUtil.split(this.getNeedRoles());

        // 约定若role_anon角色拥有此uri资源的权限,则此uri资源直接访问不需要认证和权限
        if (!StringUtils.isEmpty(this.getNeedRoles()) && setRole.contains(ANON_ROLE)) {
            stringBuilder.append("anon");
        }
        //  其他自定义资源uri需通过jwt认证和角色认证
        if (!StringUtils.isEmpty(this.getNeedRoles()) && !setRole.contains(ANON_ROLE)) {
            stringBuilder.append("jwt"+"["+this.getNeedRoles()+"]");
        }

        return stringBuilder.length() > 0 ? stringBuilder : null;
    }

    @Override
    public String toString() {
        return "RolePermRule [url="+path+", needRoles="+needRoles+"]";
    }
}
