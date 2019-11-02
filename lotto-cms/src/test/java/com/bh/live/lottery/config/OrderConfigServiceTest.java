package com.bh.live.lottery.config;

import com.bh.live.model.lottery.config.OrderConfig;
import com.bh.live.service.lottery.config.IOrderConfigService;
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
public class OrderConfigServiceTest {

    @Autowired
    private IOrderConfigService seedService;

    @Test
    public void selectSeedPageTest() {
        OrderConfig req = new OrderConfig();
        req.setPageNum(1);
        req.setPageSize(2);
//        TableDataInfo tableDataInfo = seedService.page(req);
//        tableDataInfo.getList().forEach(System.out::println);
    }
}
