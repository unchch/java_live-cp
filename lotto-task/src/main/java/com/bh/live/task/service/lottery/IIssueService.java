package com.bh.live.task.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.res.inform.IssueCurrentRes;

/**
 * <p>
 * 彩期 服务类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
public interface IIssueService extends IService<Issue> {

    /**
     * 生成所有彩种彩期
     *
     * @author yq.
     */
    void createNewIssue();

    /**
     * 修改彩期状态
     *
     * @param res
     * @return
     * @author yq.
     */
    void updateIssueStatus(IssueCurrentRes res);
}
