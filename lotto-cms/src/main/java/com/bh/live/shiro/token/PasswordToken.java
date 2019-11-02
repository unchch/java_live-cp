package com.bh.live.shiro.token;

import com.bh.live.common.utils.AesUtil;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;


/**
 * PasswordToken
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
public class PasswordToken implements AuthenticationToken {

    /**
     * 账户
     */
    private String uid;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String qrcode;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 主机
     */
    private String host;

    /**
     * tokenKey
     */
    private String tokenKey;

    public PasswordToken(String uid, String password, String qrcode, String timestamp, String host, String tokenKey) {
        this.uid = uid;
        this.qrcode = qrcode;
        this.timestamp = timestamp;
        this.host = host;
        this.password = AesUtil.aesDecode(password, tokenKey);
        this.tokenKey = tokenKey;
    }

    @Override
    public Object getPrincipal() {
        return this.uid;
    }

    @Override
    public Object getCredentials() {
        return String.format("%s:%s", this.password, this.qrcode);
    }
}
