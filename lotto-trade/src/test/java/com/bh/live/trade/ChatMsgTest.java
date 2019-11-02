package com.bh.live.trade;

import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.pojo.req.live.MsgRcpReq;
import com.bh.live.pojo.res.live.ChatGuess;
import com.bh.live.pojo.res.live.ChatOpenCode;
import com.bh.live.pojo.res.live.ChatUser;
import com.bh.live.rpc.service.user.IUserFeignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/10 14:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatMsgTest {
    @Resource
    IUserFeignService userFeignService;
    private String seedNo = "402";

    /**
     * 开奖结果
     */
    @Test
    public void TestSendMsg() {
        ChatOpenCode openCode = new ChatOpenCode();
        openCode.setOpenCode("4,5,7,8,3,2,9,6,10,1");
        openCode.setPeriods("741256");
        openCode.setNextTime(1569415257);
        MsgRcpReq req = new MsgRcpReq(openCode, ChatMsgTypeEnum.OPEN_CODE.getCode(), seedNo);
        Integer integer = userFeignService.sendMsg(req);
        System.out.println("发送成功");
    }

    /**
     * 开始竞猜/开盘
     */
    @Test
    public void TestSendMsg3() {
        ChatOpenCode openCode = new ChatOpenCode();
        openCode.setPeriods("741257");
        openCode.setOpenCode("");
        openCode.setNextPeriods("741258");
        openCode.setNextTime(1569874127);
        MsgRcpReq req = new MsgRcpReq(openCode, ChatMsgTypeEnum.CURRENT_OPEN.getCode(), seedNo);
        Integer integer = userFeignService.sendMsg(req);
        System.out.println("发送成功");
    }

    /**
     * 发布竞猜
     */
    @Test
    public void TestSendMsg2() {
        ChatUser user = new ChatUser(10002, "雨雪", UserEnum.ChatUserTypeEnum.ANCHOR.getDesc());
        ChatGuess guess = new ChatGuess("736202", 66,
                "胜,胜,负,胜,胜,负,胜,走,胜,负", "[冠亚军和] 05 06 07 08 09 10 11 12 13 14 15 大 双[第十名] 10 09 08 07 06 05 大", new BigDecimal(10));
        MsgRcpReq req = new MsgRcpReq(guess, ChatMsgTypeEnum.GUESS.getCode(), seedNo, user);
        Integer integer = userFeignService.sendMsg(req);
        System.out.println("发送成功");
    }
}
