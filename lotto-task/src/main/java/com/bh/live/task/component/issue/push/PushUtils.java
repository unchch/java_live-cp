package com.bh.live.task.component.issue.push;

import com.bh.live.common.enums.ChatMsgTypeEnum;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.pojo.req.live.MsgRcpReq;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.pojo.res.live.ChatOpenCode;
import com.bh.live.rpc.service.user.IUserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author yq.
 * @ClassName PushUtils
 * @description PushUtils
 * @date 2019-08-13 15:20:00
 */
@Component
@Slf4j
public class PushUtils {

    @Resource
    private IUserFeignService userFeignService;

    /**
     * 开奖彩果推送
     *
     * @param seedNo         彩种
     * @param currentIssueNo 开奖彩期
     * @param result         开奖结果
     * @return
     * @author yq.
     */
    public void openResultPush(Integer seedNo, String currentIssueNo, String result) {
        try {
            ChatOpenCode openCode = new ChatOpenCode();
            openCode.setOpenCode(result);
            openCode.setPeriods(currentIssueNo);
            MsgRcpReq req = new MsgRcpReq(openCode, ChatMsgTypeEnum.OPEN_CODE.getCode(), String.valueOf(seedNo));
            log.info("openResultPush param:{}", req.toString());
            CompletableFuture.runAsync(() -> {
                Integer ret = userFeignService.sendMsg(req);
                log.info("openResultPush success. ret:{}", ret);
            }).whenComplete((k, e) -> {
                log.warn("openResultPush exception. cause:{}, message:{}", e.getCause(), e.getMessage());
            });
        } catch (Exception e) {
            log.warn("openResultPush catch exception. cause:{}, message:{}", e.getCause(), e.getMessage());
        }
    }

    /**
     * 封盘推送
     *
     * @param issueRes 彩种
     * @return
     * @author yq.
     */
    public void closePush(IssueCurrentRes issueRes) {
        log.info("closePush. req:{}", issueRes.toString());
        try {
            IssueEntityRes last = issueRes.getLast();
            IssueEntityRes current = issueRes.getCurrent();
            if (CommonUtil.isEmpty(last) || CommonUtil.isEmpty(current)) {
                log.error("closePush. IssueCurrentRes error. res:{}", issueRes);
                return;
            }

            // 构建推送参数
            ChatOpenCode openCode = new ChatOpenCode();
            // 上一期
            openCode.setPeriods(last.getIssueNo());
            openCode.setOpenCode(last.getResult());
            openCode.setTotal(last.getTotalIssue());
            openCode.setSurplus(last.getSurplusIssue());
            // 当前期
            openCode.setNextPeriods(current.getIssueNo());
            openCode.setNextTime(DateUtils.getTimestampByNow(current.getClosingTime()));

            MsgRcpReq req = new MsgRcpReq(openCode, ChatMsgTypeEnum.CURRENT_OPEN.getCode(), String.valueOf(last.getSeedNo()));
            log.info("closePush. push param:{}", req.toString());
            CompletableFuture.runAsync(() -> {
                Integer ret = userFeignService.sendMsg(req);
                log.info("closePush success. ret:{}", ret);
            }).whenComplete((k, e) -> {
                log.warn("closePush exception. cause:{}, message:{}", e.getCause(), e.getMessage());
            });
            log.info("closePush. success");
        } catch (Exception e) {
            log.error("closePush catch exception. cause:{}, message:{}", e.getCause(), e.getMessage());
        }
    }
}
