package com.bh.live.service.impl.anchor;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.AdvanceUtil;
import com.bh.live.dao.anchor.HostAdvanceDao;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.pojo.res.anchor.AdvancesRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import com.bh.live.service.anchor.IHostAdvanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 主播预告表 服务实现类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-05
 */
@Service
public class HostAdvanceServiceImpl extends ServiceImpl<HostAdvanceDao, HostAdvance>
        implements IHostAdvanceService {

    @Resource
    private HostAdvanceDao hostAdvanceDao;

    @Override
    public List<AdvancesRes> getHostAdvances(Integer seedNo, String type) {
        List<HostAdvanceRes> advancesRes = hostAdvanceDao.queryHostAdvances(seedNo, type);
        List<AdvancesRes> resList = AdvanceUtil.advanceHandler(advancesRes);
        if (resList == null || resList.size() == 0) {
            return null;
        }
        return resList;
    }
}
