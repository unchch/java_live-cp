package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.pojo.req.anchor.HostAdvanceReq;
import com.bh.live.pojo.res.anchor.AdvancesRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;

import java.util.List;

/**
 * <p>
 * 主播预告表 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-05
 */
public interface IHostAdvanceService extends IService<HostAdvance> {

    /**
     * 获取彩种的所有主播预告
     *
     * @param seedNo
     * @return
     * @auth Morphon
     */
    List<AdvancesRes> getHostAdvances(Integer seedNo, String type);

}
