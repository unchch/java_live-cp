package com.bh.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.res.inform.IssueCurrentRes;

/**
 * @ClassName IIssueService
 * @description: IIssueService
 * @author: yq.
 * @date 2019-08-10 14:21:00
 */
public interface IIssueService extends IService<Issue> {

    /**
     * 查询彩期
     *
     * @param seedNo
     * @return IssueCurrentRes：last:上一期，current:当前期，next:下一期
     */
    IssueCurrentRes selectCurrentIssue(Integer seedNo);
}
