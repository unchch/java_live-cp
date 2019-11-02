package com.bh.live.user;

import com.bh.live.model.user.Attention;
import com.bh.live.pojo.req.user.AttentionReq;
import com.bh.live.pojo.res.page.TableDataInfo;
import com.bh.live.user.service.IAttentionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LottoAttentionApplicationTests {
    @Autowired
    IAttentionService attentionService;


    /**
     * 测试添加或者取消关注
     */
    @Test
    public void testAttention(){
        Attention req=new Attention();
        req.setUserId(1);
        req.setTargetId(12);
        req.setIsAttent(1);
        attentionService.insertOrUpdate(req);
    }

    /**
     * 查询已关注列表
     */
    @Test
    public  void getList1(){
       /* AttentionReq req=new AttentionReq();
        req.setUserId(2);
        req.setPageNum(1);
        req.setPageSize(1);
        TableDataInfo tableDataInfo = attentionService.queryAttentList(req);
        tableDataInfo.getList().forEach(System.out::println);*/
    }

    /**
     * 查询粉丝列表
     */
    @Test
    public  void getList2(){
       /* AttentionReq req=new AttentionReq();
        req.setTargetId(2);
        req.setPageNum(2);
        req.setPageSize(1);
        TableDataInfo tableDataInfo = attentionService.queryAttentList(req);
        tableDataInfo.getList().forEach(System.out::println);*/
    }
}
