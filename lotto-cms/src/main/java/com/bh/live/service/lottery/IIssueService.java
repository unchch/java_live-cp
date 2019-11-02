package com.bh.live.service.lottery;

import com.bh.live.common.enums.award.HandleEnum;
import com.bh.live.common.result.Result;
import com.bh.live.model.lottery.Issue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.pojo.req.lottery.IssueLotteryReq;
import com.bh.live.pojo.req.lottery.IssueReq;
import com.bh.live.pojo.res.page.TableDataInfo;

/**
 * <p>
 * 彩期 服务类
 * </p>
 *
 * @author WJ
 * @since 2019-07-23
 */
public interface IIssueService extends IService<Issue> {

    /**
     * 根据参数查询开奖信息
     *
     * @param issueReq
     * @return TableDataInfo
     */
    TableDataInfo selectByIssueReq(IssueReq issueReq);

    /**
     * 彩期开奖
     *
     * @param issueLotteryReq
     * @param  prizeStatusEnum {@link HandleEnum.PrizeStatusEnum}
     * @return int
     */
    Result issueNoLottery(IssueLotteryReq issueLotteryReq, HandleEnum.PrizeStatusEnum prizeStatusEnum);

    /**
     * 彩期重置开奖
     *
     * @param issueLotteryReq
     * @return int
     */
    int issueNoResetLottery(IssueLotteryReq issueLotteryReq);

    /**
     * 彩期撤单
     *
     * @param issueLotteryReq
     * @return int
     */
    int issueNoRevokeOrder(IssueLotteryReq issueLotteryReq);
}
