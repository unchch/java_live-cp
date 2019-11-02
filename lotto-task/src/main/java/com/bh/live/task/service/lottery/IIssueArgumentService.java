package com.bh.live.task.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.IssueArgument;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 彩期生成的参数 服务类
 * </p>
 *
 * @author Y.
 * @since 2019-07-23
 */
public interface IIssueArgumentService extends IService<IssueArgument> {
    /**
     * 根据彩种编号查询彩期生成参数
     *
     * @return
     */
    List<IssueArgument> selectByIssueArgument();

    /**
     * 所有彩期根据seedNo分组
     *
     * @return Map K:seedNo, V:list
     */
    Map<Integer, List<IssueArgument>> selectAllIssueArgumentToMap();
}
