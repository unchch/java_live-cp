package com.bh.live.user;

import com.bh.live.pojo.res.anchor.AnchorRoomInfoRes;
import com.bh.live.pojo.res.anchor.GiftInfoRes;
import com.bh.live.pojo.res.lottery.HistoryLotteryRes;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.user.service.IAnchorRoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnchorRoomApplicationTests {

    @Autowired
    IAnchorRoomService anchorRoomService;


    /**
     * 查询主播推荐
     */
    @Test
    public  void getList1(){
       /* List<AnchorRoomInfoRes> anchorRecommend = anchorRoomService.getAnchorRecommend(22);
        anchorRecommend.forEach(System.out::println);*/
    }

    /**
     * 查询关注的主播
     */
    @Test
    public  void getList2(){
      /* TableDataInfo attentionAnchor = anchorRoomService.getAttentionAnchor(1, 402);
        attentionAnchor.getList().forEach(System.out::println);*/
    }

    /**
     * 查询历史彩种
     */
    @Test
    public  void getList3(){
        List<HistoryLotteryRes> lottoSeed = anchorRoomService.getLottoSeed(1);
        lottoSeed.forEach(System.out::println);
    }

    /**
     * 查询礼物
     */
    @Test
    public  void getList4(){
        List<GiftInfoRes> giftList = anchorRoomService.getGiftList();
        giftList.forEach(System.out::println);
    }
}
