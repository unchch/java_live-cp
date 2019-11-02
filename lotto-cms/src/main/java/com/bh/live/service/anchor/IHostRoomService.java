package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.result.Result;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.req.anchor.LottoTypeReq;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.page.TableDataInfo;

import java.util.List;

/**
 * <p>
 * 直播间 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
public interface IHostRoomService extends IService<HostRoom> {

    /**
     * 根据条件查询列表
     *
     * @param req
     * @return
     */
    TableDataInfo getByCondition(HostRoomReq req);

    /**
     * 关闭直播间
     *
     * @param id,isUsable
     */
    boolean closeOrOpenRoom(Integer id, Integer status);

    /**
     * 踢下线
     *
     * @param id,hostId,kickOffLine
     */
    void kickOffLine(Integer id, Integer hostId, Integer kickOffLine);


    /**
     * 修改直播间配置
     *
     * @param req
     */
    void update(HostRoomReq req);

    /**
     * 不知道是谁的接口（Morphon添加的备注）
     * @param lottoTypeReq
     * @return
     */
    Result queryHostRoomList(LottoTypeReq lottoTypeReq);

    /**
     * 查询roomId是否存在
     * @param roomId
     * @return
     */
    boolean checkIdExist(Integer roomId);

    /**
     * 直播预告开播
     * @param req
     */
    void openRoomLive(HostRoomReq req);

    /**
     * 结束直播
     * @param req
     */
    void endRoomLive(HostRoomReq req);
}
