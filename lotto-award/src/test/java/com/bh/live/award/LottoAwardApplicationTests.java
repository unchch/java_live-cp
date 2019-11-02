package com.bh.live.award;

import com.bh.live.award.service.IOrderService;
import com.bh.live.common.utils.RedisUtil;
import com.bh.live.model.trade.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoAwardApplicationTests {

    @Autowired
    RedisUtil redisUtil;
    @Resource
    private IOrderService orderService;

    @Test
    public void contextLoads() {
        redisUtil.set("test","1230");
        Object test = redisUtil.get("test");
        System.out.println(String.valueOf(test));
        redisUtil.del("test");
        Object test1 = redisUtil.get("test");
        if(test1==null){
            System.out.println("数据已删除");
        }
    }

    @Test
    public void getOrderByIssueNoSeedNoStatus(){
        List<Order> orders = orderService.getOrderByIssueNoSeedNoStatus("20190808001", 401, Arrays.asList(2));
        for(Order order : orders ){
            System.out.println(order.toString());
        }
    }

}
