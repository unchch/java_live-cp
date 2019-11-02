package com.bh.live.controller;

import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.pojo.res.live.ChatOpenCode;
import com.bh.live.service.IIssueService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/8/13 17:58
 */
@RestController
@RequestMapping("/seed")
@Api(tags = "彩期")
public class IssueController {

    @Autowired
    IIssueService issueService;


    @ApiOperation(value = "获取彩期", response = ChatOpenCode.class)
    @GetMapping("/selectCurrentIssue")
    public Result selectCurrentIssue(@ApiParam("彩种Id") @RequestParam("seedNo") Integer seedNo) {
        ChatOpenCode openCode = null;
        try {
            IssueCurrentRes res = issueService.selectCurrentIssue(seedNo);
            openCode = new ChatOpenCode();
            // 获取上一期开奖
            IssueEntityRes lastIssue = new IssueEntityRes();
            if (CommonUtil.isNotEmpty(res.getLast())) {
                lastIssue = res.getLast();
            }
            // 获取下一期
            IssueEntityRes currentIssue = new IssueEntityRes();
            if (CommonUtil.isNotEmpty(res.getCurrent())) {
                currentIssue = res.getCurrent();
            }
            openCode.setPeriods(lastIssue.getIssueNo());
            openCode.setOpenCode(lastIssue.getResult());
            openCode.setNextPeriods(currentIssue.getIssueNo());
            long nextTime = 0;
            if (CommonUtil.isNotEmpty(currentIssue.getClosingTime())) {
                nextTime = currentIssue.getClosingTime().getTime() - System.currentTimeMillis();
            }
            openCode.setNextTime(nextTime);
            openCode.setOpenCode(getResult());
        } catch (Exception e) {
            return Result.success(openCode);
        }
        return Result.success(openCode);
    }

    /**
     * 模拟彩果
     *
     * @param
     * @return
     * @author yq.
     */
    private String getResult() {
        List<Integer> res = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collections.shuffle(res);
        Joiner joiner = Joiner.on(",").skipNulls();
        return joiner.join(res);
    }

}
