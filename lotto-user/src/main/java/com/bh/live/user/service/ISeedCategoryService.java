package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.lottery.SeedCategory;
import com.bh.live.pojo.req.lottery.SeedCategoryReq;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.lottery.SeedCategoryRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 彩种分类 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-06
 */
public interface ISeedCategoryService extends IService<SeedCategory> {

    /**
     * 根据多个彩种分类no获取彩种分类
     * @auth Morphon
     * @param ids
     * @return
     */
    List<SeedCategoryRes> querySeedCategoryByNos(List<Integer> ids);
}
