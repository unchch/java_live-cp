package com.bh.live.task.service.advance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.model.anchor.HostAdvance;

/**
 * <p>
 * 主播预告表 服务类
 * </p>
 *
 * @author WW
 * @since 2019-08-05
 */
public interface IHostAdvanceService extends IService<HostAdvance> {

	/**
	 * 修改主播周期性开播时间
	 */
	public void changeHostAdvanceStartTime();
}
