package com.bh.live.service.lottery;

/**
 * 用户、主播、专家经验值、等级服务类
 * @Author: WJ
 * @date: 2019/8/14
 */
public interface IUserXpGradeService {

    /**
     * 根据积分规则增加经验值
     * @param dictTypeCode
     * @return
     */
    boolean addUserXp(Integer dictTypeCode);
}
