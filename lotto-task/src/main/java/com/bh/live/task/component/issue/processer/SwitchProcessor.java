package com.bh.live.task.component.issue.processer;

import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.lottery.Seed;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.task.component.issue.quartz.ScheduleJobInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @ClassName SwitchProcessor
 * @description: 彩期切换process
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component("issueSwitchProcessor")
@Slf4j
public class SwitchProcessor extends BaseTaskProcessor {

    @Override
    public void execute(ScheduleJobInfo jobInfo) {
//        Seed seed = (Seed) jobInfo.getExtInfo()[0];
//        log.info("cut issue to send robot message start. seedName:{} time:{}", seed.getSeedName(), DateUtils.getTime());
//
//        //B、获取当前彩期
//        IssueCurrentRes issueRes = issueFeignService.selectCurrentIssue(seed.getSeedNo());
//        if (CommonUtil.isEmpty(issueRes)
//                || CommonUtil.isEmpty(issueRes.getCurrent())
//                || CommonUtil.isEmpty(issueRes.getCurrent().get(0))) {
//            log.warn("SwitchProcessor. not found issue current. seedNo:{}, issueRes:{}", seed.getSeedNo(), issueRes);
//            return;
//        }
//        IssueEntityRes issueCurrent = issueRes.getCurrent().get(0);
//        //验证彩期
//        if (checkCurrentIssueStatus(seed, issueCurrent)) {
//            return;
//        }
//
//        //给指定彩种所有房间发送切期消息
//        sendCutIssueSeedRoomsMsg(issueCurrent);
//
//
//        log.info("cut issue to send robot message end. seedName:{} time:{}", seed.getSeedName(), DateUtils.getTime());
    }

    private boolean checkCurrentIssueStatus(Seed seed, IssueEntityRes currentIssue) {
        //判断彩期是否已经过期
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentIssue.getEndTime());
        long overeconds = DateUtils.compareSecond(cal, DateUtils.getCalenderByDate(DateUtils.getNowDate()));
        //离封盘低于15秒进入该方法就有问题了
        if (overeconds <= 15) {
            //TODO:这里要设置预警提醒
            String info = "彩期切换时发送聊天室消息失败：彩期离封盘时间剩余" + overeconds + "秒,不发送机器人投注消息,currentIssue=" + currentIssue;
            log.error(info);
            return true;
        }
        return false;
    }

    public void sendCutIssueSeedRoomsMsg(IssueEntityRes issue) {

    }
}
