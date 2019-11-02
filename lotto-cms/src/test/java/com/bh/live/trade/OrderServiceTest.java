package com.bh.live.trade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bh.live.model.trade.Order;
import com.bh.live.model.trade.vo.OrderVO;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.trade.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName SeedServiceTest
 * @description: SeedServiceTest
 * @author: yq.
 * @date 2019-07-25 11:24:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void selectSeedPageTest() {
        OrderVO req = new OrderVO();
        req.setPageNum(1);
        req.setPageSize(2);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        TableDataInfo tableDataInfo = orderService.selectOrder(req);
        tableDataInfo.getList().forEach(System.out::println);
    }
}
