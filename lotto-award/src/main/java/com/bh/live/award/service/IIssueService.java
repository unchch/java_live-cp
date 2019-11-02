package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.Issue;

/**
 * <p>
 * 彩期 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IIssueService extends IService<Issue> {
    /**
     * @param issueNo 期号
     * @param seedNo  彩种编号
     * @return Issue
     * @description 查询彩种信息
     * @author WuLong
     * @date 2019/8/6 15:25
     */
    Issue selectBySeedIssueNo(String issueNo, Integer seedNo);
    /**
     * @param issueNo 期号
     * @param seedNo  彩种编号
     * @param status 状态
     * @param result 彩果
     * @return Issue
     * @description 更新彩期状态
     * @author WuLong
     * @date 2019/8/6 15:25
     */
    boolean updateStatusBySeedIssueNo(String issueNo, Integer seedNo, Integer status,String result);


}
