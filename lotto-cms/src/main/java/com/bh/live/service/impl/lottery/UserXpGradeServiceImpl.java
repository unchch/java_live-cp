package com.bh.live.service.impl.lottery;

import com.bh.live.service.impl.lottery.config.UserIntegralConfigServiceImpl;
import com.bh.live.service.lottery.IUserXpGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户等级 服务类
 * @Author: WJ
 * @date: 2019/8/14
 */
@Service
public class UserXpGradeServiceImpl implements IUserXpGradeService {

    @Autowired
    UserIntegralConfigServiceImpl configService;

    @Override
    public boolean addUserXp(Integer dictTypeCode) {
//        configService.selectByParam(null);
        return false;
    }
}
