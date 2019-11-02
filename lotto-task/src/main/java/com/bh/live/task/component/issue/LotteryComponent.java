package com.bh.live.task.component.issue;

import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.model.lottery.Seed;
import com.bh.live.task.service.lottery.IIssueArgumentService;
import com.bh.live.task.service.lottery.ISeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName LotteryComponent
 * @description: select db
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Component
@Slf4j
public class LotteryComponent {

    @Autowired
    private ISeedService seedService;

    @Autowired
    private IIssueArgumentService issueArgumentService;

    /**
     * 获取所有的彩种
     *
     * @return
     */
    public List<Seed> getAllSeed() {
        return seedService.selectAllSeed();
    }

    /**
     * 获取彩期配置arg
     */
    public List<IssueArgument> selectAllIssueArgument() {
        return issueArgumentService.selectByIssueArgument();
    }

    /**
     * 所有彩期根据seedNo分组
     *
     * @return Map K:seedNo, V:list
     */
    public Map<Integer, List<IssueArgument>> selectAllIssueArgumentToMap() {
       return issueArgumentService.selectAllIssueArgumentToMap();
    }
}
