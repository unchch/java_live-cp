package com.bh.live.task.component.issue.processer;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.rpc.service.inform.IIssueFeignService;
import com.bh.live.task.component.issue.push.PushUtils;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import com.bh.live.task.service.lottery.IIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName ClosingProcessor
 * @description: 彩期封盘process
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component("issueClosingProcessor")
@Slf4j
public class ClosingProcessor extends BaseTaskProcessor {

    @Resource
    private IIssueFeignService issueFeignService;

    @Autowired
    private IIssueService issueService;

    @Autowired
    private PushUtils pushUtil;

    @Override
    public void execute(ScheduleJobInfo jobInfo) {
        Seed seed = (Seed) jobInfo.getExtInfo()[0];

        IssueCurrentRes res = issueFeignService.selectCurrentIssue(seed.getSeedNo());
        if (CommonUtil.isEmpty(res.getLast()) || CommonUtil.isEmpty(res.getCurrent())) {
            log.info("updateIssueStatus exception. last or current is null");
            return;
        }

        // 修改彩期
        issueService.updateIssueStatus(res);
        // 切期推送
        pushUtil.closePush(res);
    }
}