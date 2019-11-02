package com.bh.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.constant.LotteryConstants;
import com.bh.live.common.exception.Assert;
import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.dao.IssueDao;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.res.inform.IssueCurrentRes;
import com.bh.live.pojo.res.inform.IssueEntityRes;
import com.bh.live.service.IIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName IssueServiceImpl
 * @description: IssueServiceImpl
 * @author: yq.
 * @date 2019-08-10 14:21:00
 */
@Service
@Slf4j
public class IssueServiceImpl extends ServiceImpl<IssueDao, Issue> implements IIssueService {

    @Autowired
    private IssueDao issueDao;

    @Override
    public IssueCurrentRes selectCurrentIssue(Integer seedNo) {
        Assert.isNotNull(seedNo, CodeMsg.E_80014);

        List<IssueEntityRes> res = issueDao.selectIssue(seedNo);
        if (CommonUtil.isEmpty(res) || res.size() != LotteryConstants.VALUE_3) {
            log.error("selectCurrentIssue exception. res:{}", res);
            throw new ServiceRuntimeException(CodeMsg.E_60202);
        }

        IssueCurrentRes ret = new IssueCurrentRes()
                .setLast(res.get(0))
                .setCurrent(res.get(1))
                .setNext(res.get(2));
        log.info("selectCurrentIssue. ret:{}", ret);
        return ret;
    }
}
