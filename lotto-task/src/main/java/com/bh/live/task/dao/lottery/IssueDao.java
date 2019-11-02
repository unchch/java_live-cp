package com.bh.live.task.dao.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.Issue;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩期 Mapper 接口
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
public interface IssueDao extends BaseMapper<Issue> {

    /**
     * 批量新增彩期
     *
     * @param issues
     * @return
     */
    int batchInsertIssue(@Param("list") List<Issue> issues);

    /**
     * 获取每个彩种创建的最后的彩期
     * @return
     */
    @Select("SELECT * FROM lotto_issue WHERE issue_no IN (SELECT refer_issue_no FROM lotto_seed)")
    List<Issue> selectLastCreateIssue();
}
