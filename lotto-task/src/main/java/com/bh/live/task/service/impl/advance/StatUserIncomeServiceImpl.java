package com.bh.live.task.service.impl.advance;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.model.anchor.StatUserIncome;
import com.bh.live.task.dao.advance.StatUserIncomeDao;
import com.bh.live.task.service.advance.IStatUserIncomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 主播礼物收入统计表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@Service
public class StatUserIncomeServiceImpl extends ServiceImpl<StatUserIncomeDao, StatUserIncome> implements IStatUserIncomeService {

    @Resource
    private StatUserIncomeDao userIncomeDao;

    @Override
    public List<StatUserIncome> statUserIncomes() {
        return userIncomeDao.statIncome();
    }


}
