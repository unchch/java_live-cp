package com.bh.live.anchor;

import com.alibaba.fastjson.JSONObject;
import com.bh.live.pojo.req.anchor.GiftReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.service.anchor.IGiftService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoGiftApplicationTests {
    @Autowired
    IGiftService giftService;


    /**
     * 测试添加
     */
    @Test
    public void testAdd(){
        GiftReq g=new GiftReq();
        g.setGiftTheme("游轮");
        g.setGiftName("游轮");
        g.setCategoryId(1);
        g.setAmount(new BigDecimal(600));
        g.setAnchorXp(300);
        g.setUserXp(300);
        g.setGroups("1,25,66,188");
        g.setFullGif("fullgif.gif");
        g.setImage("image.gif");
        g.setGif("gift.gif");
        g.setIcon("gift.icon");
        g.setGiftDesc("开着游轮去度假！");
        g.setStatus(1);
        g.setSort(1);
        g.setPlayTimes(5);
        Integer integer = giftService.addGift(g);
        System.out.println(integer);
    }


    /**
     * 测试修改
     */
    @Test
    public void testUpdate(){
        GiftReq g=new GiftReq();
        g.setId(5);
        g.setGiftTheme("游轮2");
        g.setGiftName("游轮2");
        g.setCategoryId(2);
        g.setAmount(new BigDecimal(800));
        g.setAnchorXp(400);
        g.setUserXp(400);
        g.setGroups("1,25,66,199");
        g.setFullGif("fullgif2.gif");
        g.setImage("image2.gif");
        g.setGif("gift2.gif");
        g.setIcon("gift2.icon");
        g.setGiftDesc("开着游轮去度假2！");
        g.setStatus(0);
        g.setSort(2);
        g.setPlayTimes(3);
        giftService.updateGift(g);

    }

    /**
     * 测试修改
     */
    @Test
    public void testDelete(){
        List<Integer> list=new ArrayList<>();
        list.add(4);
        list.add(5);
        giftService.deleteGift(list);
    }

    /**
     * 查询列表
     */
    @Test
    public  void getList(){
        GiftReq giftReq=new GiftReq();
        giftReq.setPageNum(1);
        giftReq.setPageSize(2);
        /*giftReq.setId(3);
        giftReq.setCategoryName("道具");
        giftReq.setGiftName("财神");*/
        TableDataInfo tableDataInfo = giftService.getGifts(giftReq);
        tableDataInfo.getList().forEach(System.out::println);
    }

    /**
     * 礼物详情
     */
    @Test
    public  void getDetail(){
        GiftReq giftById = giftService.getGiftById(3);
        Object json = JSONObject.toJSON(giftById);
        System.out.println(json);
    }

}
