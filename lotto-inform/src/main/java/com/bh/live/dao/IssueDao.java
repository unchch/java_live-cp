package com.bh.live.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.lottery.Issue;
import com.bh.live.pojo.res.inform.IssueEntityRes;
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
     * 查询彩期
     *
     * @param seedNo
     * @return
     */
    List<IssueEntityRes> selectIssue(@Param("seedNo") Integer seedNo);
}
