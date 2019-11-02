package com.bh.live.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.LiveRecord;
import com.bh.live.pojo.res.anchor.LiveRecordRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主播直播记录 服务类
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
public interface ILiveRecordService extends IService<LiveRecord> {

    /**
     * 获取直播记录
     *
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getRecords(Integer userId, String begin, String end);

}
