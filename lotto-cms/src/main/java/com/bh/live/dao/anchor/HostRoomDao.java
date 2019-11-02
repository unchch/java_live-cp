package com.bh.live.dao.anchor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.pojo.req.anchor.HostRoomReq;
import com.bh.live.pojo.req.anchor.LottoTypeReq;
import com.bh.live.pojo.res.anchor.RoomLiveRecord;
import com.bh.live.pojo.res.inform.LottoTypeRes;
import com.bh.live.pojo.res.user.GiftListIncomeRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 直播间 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
public interface HostRoomDao extends BaseMapper<HostRoom> {
    /**
     * 直播首页->彩种直播
     *
     * @param lottoTypeReq req
     * @return
     * @author yq.
     */
    List<LottoTypeRes> queryHostRoomList(@Param("req") LottoTypeReq lottoTypeReq);


    /**
     * @param roomId
     * @return
     * @author Morphon
     */
    @Select("select count(1) from lotto_host_room where id=#{roomId}")
    int checkRoomIdExist(@Param("roomId") Integer roomId);

    /**
     * 查询主播房间预告以及直播间记录
     *
     * @param
     * @return
     * @author Morphon
     */
    List<RoomLiveRecord> queryRoomLiveInfo(IPage<RoomLiveRecord> page, @Param("param") HostRoomReq req);

}
