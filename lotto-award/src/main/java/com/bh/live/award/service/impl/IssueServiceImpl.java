package com.bh.live.award.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.award.dao.IssueDao;
import com.bh.live.award.service.IIssueService;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.model.lottery.Issue;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 彩期 服务实现类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
@Service
public class IssueServiceImpl extends ServiceImpl<IssueDao, Issue> implements IIssueService {

    @Override
    public Issue selectBySeedIssueNo(String issueNo, Integer seedNo) {
        QueryWrapper<Issue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Issue::getIssueNo, issueNo).eq(Issue::getSeedNo, seedNo);
        return getOne(queryWrapper, false);
    }

    @Override
    public boolean updateStatusBySeedIssueNo(String issueNo, Integer seedNo, Integer status, String result) {
        if (CommonUtil.isEmpty(result) && status == null) {
            return false;
        }
        Issue issue = new Issue();
        if (status != null) {
            issue.setStatus(status);
        }
        if (CommonUtil.isNotEmpty(result)) {
            issue.setResult(result);
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("seed_no", seedNo);
        updateWrapper.eq("issue_no", issueNo);
        return update(issue, updateWrapper);
    }
}
