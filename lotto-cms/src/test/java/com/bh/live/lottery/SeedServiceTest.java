package com.bh.live.lottery;

import com.bh.live.pojo.req.lottery.SeedReq;
import com.bh.live.pojo.req.lottery.SeedSettingsReq;
import com.bh.live.pojo.req.lottery.SeedUpdateReq;
import com.bh.live.pojo.res.lottery.SeedSettingsRes;
import com.bh.live.pojo.res.lottery.SeedUpdateRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.lottery.ISeedService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName SeedServiceTest
 * @description: SeedServiceTest
 * @author: yq.
 * @date 2019-07-25 11:24:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SeedServiceTest {

    @Autowired
    private ISeedService seedService;

    /**
     * 分页
     */
    @Test
    public void selectSeedPageTest() {
        SeedReq req = new SeedReq();
        req.setPageNum(1);
        req.setPageSize(2);
        TableDataInfo page = seedService.selectSeedPage(req);
        page.getList().forEach(System.out::println);
    }
    /**
     * 查询
     */
    @Test
    public void queryTest() {
        SeedUpdateRes res = seedService.query(101);
        System.out.println(res);
    }
    /**
     * 修改
     */
    @Test
    public void updateTest() {
        SeedUpdateReq req = new SeedUpdateReq();
        boolean ret = seedService.update(req);
        Assert.assertTrue(ret);
    }
    /**
     * 设置默认玩法
     */
    @Test
    public void defaultPlay() {
        boolean ret = seedService.defaultPlay(101, 1);
        Assert.assertTrue(ret);
    }
    /**
     * 设置彩种隐藏
     */
    @Test
    public void display() {
        boolean ret = seedService.display(101, 1);
        Assert.assertTrue(ret);
    }
    /**
     * 查询彩种信息
     */
    @Test
    public void querySettings() {
        List<SeedSettingsRes> ret = seedService.querySettings(101);
        System.out.println(ret);
    }
    /**
     * 彩种玩法配置
     */
    @Test
    public void settings() {
        List<SeedSettingsReq> req = Lists.newArrayList();
        boolean ret = seedService.settings(req);
        System.out.println(ret);
    }
}
