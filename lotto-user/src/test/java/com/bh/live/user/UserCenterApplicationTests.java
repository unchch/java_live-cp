package com.bh.live.user;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.pojo.res.user.UserBaseInfoRes;
import com.bh.live.user.service.IAttentionService;
import com.bh.live.user.service.IUserCenterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCenterApplicationTests {
    @Autowired
    IUserCenterService userCenterService;


    /**
     * 查询粉丝列表
     */
    @Test
    public  void get(){
        UserBaseInfoRes userById = userCenterService.queryUserById(10000);
        Object json = JSONObject.toJSON(userById);
        System.out.println(json);

    }
}
