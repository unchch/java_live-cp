package com.bh.live.service.lottery;

import com.bh.live.model.lottery.SeedCategory;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * @author: yq.
 * @since 2019-07-23
 */
public interface ISeedCategoryService extends IService<SeedCategory> {

    /**
     * 查询彩种分类
     * @param req
     * @return
     * @author yq.
     */
    List<SeedCategory> selectSeedCategoryByReq(SeedCategoryReq req);

    /**
     * 查询所有彩种分类编号及名称
     * @return
     * @author yq.
     */
    List<SeedCategoryRes> selectSeedCategory();

    /**
     * 彩种分类及彩种树
     * @return
     * @author yq.
     */
    List<SeedCategoryRes> buildSeedCategoryTree();

    List<SeedCategoryRes> queryLottoHall();

    Map<String, List<LottoTypeRes>> queryLottoType();


    /**
     * 根据多个彩种分类no获取彩种分类
     * @auth Morphon
     * @param ids
     * @return
     */
    List<SeedCategoryRes> querySeedCategoryByNos(List<Integer> ids);
}
