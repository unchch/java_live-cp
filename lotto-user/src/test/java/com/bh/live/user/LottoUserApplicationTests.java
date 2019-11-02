package com.bh.live.user;

import com.bh.live.common.enums.MessageCodeEnum;
import com.bh.live.common.utils.AesUtil;
import com.bh.live.common.utils.string.StringUtils;
import com.bh.live.user.service.ILiveUserService;
import com.bh.live.pojo.req.user.LoginReq;
import com.bh.live.pojo.req.user.RegisterReq;
import com.bh.live.common.result.Result;
import com.bh.live.user.service.IMessageCodeService;
import com.bh.live.common.utils.JwtHelper;
import com.bh.live.common.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoUserApplicationTests {
    @Resource
    ILiveUserService userService;
    @Resource
    IMessageCodeService messageCodeService;
    @Autowired
    JwtHelper jwtHelper;

    /**
     *@author WuLong
     *@date 2019/7/25 16:03
     *@description 注册
     */
    @Test
    public void testRegister(){
        RegisterReq registerReq = new RegisterReq();
        registerReq.setLastip("192.168.1.1");
        registerReq.setChannel(1);
        registerReq.setPassword("123456");
        registerReq.setMobile("13128737876");
        Result register = userService.register(registerReq);
        try {
            System.out.println(JsonUtil.obj2json(register));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *@author WuLong
     *@date 2019/7/25 16:03
     *@description 登录
     */
    @Test
    public void testLogin(){
        LoginReq loginReq = new LoginReq();
        loginReq.setIp("192.168.1.1");
        loginReq.setMobile("13128737872");
        loginReq.setPassword("123456");
        Result login = userService.login(loginReq);
        try {
            System.out.println(JsonUtil.obj2json(login));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *@author WuLong
     *@date 2019/7/25 16:04
     *@description 获取token
     */
    @Test
    public void token(){
        Map<String, String> c = new LinkedHashMap<>();
        c.put("id", "60");
        c.put("phone","13128737872");
        c.put("username","123456");
        String s = jwtHelper.genToken(c);
        System.out.println(s);
        System.out.println(jwtHelper.verifyToken(s));
    }

    /**
     *@author WuLong
     *@date 2019/7/25 16:04
     *@description 发送短信验证码
     */
    @Test
    public void sendMessageCode(){
        Result result = messageCodeService.sendMessage("13128737872", MessageCodeEnum.REGISTER);
        System.out.println(result);
    }

    public static void main(String[] args) {
        String tokenKey = StringUtils.getRandomString(16);
        String userKey = StringUtils.getRandomString(6);
        try {
            System.out.println(AesUtil.encrypt("你好",tokenKey,userKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
