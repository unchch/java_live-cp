package com.bh.live.dao.lottery;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bh.live.model.lottery.Issue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.pojo.req.lottery.IssueReq;
import com.bh.live.pojo.res.lottery.IssueRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 彩期 Mapper 接口
 * </p>
 *
 * @author WJ
 * @since 2019-07-23
 */
public interface IssueDao extends BaseMapper<Issue> {

    /**
     * 开奖列表
     * @return
     */
    List<IssueRes> selectByIssueReq(Page<IssueRes> page, @Param("param") IssueReq issueReq);

}
