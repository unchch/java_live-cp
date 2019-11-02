package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.Gift;
import com.bh.live.pojo.req.anchor.GiftReq;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 礼物表 服务类
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-26
 */
public interface IGiftService extends IService<Gift> {

    /**
     * 新增礼物
     * @return
     */
    Integer addGift(GiftReq gift);

    /**
     * 修改礼物
     * @return
     */
    Integer updateGift(GiftReq gift);

    /**
     * 删除礼物
     * @return
     */
    Integer deleteGift(List<Integer> ids);

    /**
     * 获取礼物列表
     * @return
     */
    TableDataInfo getGifts(GiftReq gift);

    /**
     * 获取礼物列表
     * @return
     */
    GiftReq getGiftById(Integer giftId);


}
