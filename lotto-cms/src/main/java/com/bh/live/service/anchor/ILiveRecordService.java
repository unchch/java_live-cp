package com.bh.live.service.anchor;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.LiveRecord;

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
