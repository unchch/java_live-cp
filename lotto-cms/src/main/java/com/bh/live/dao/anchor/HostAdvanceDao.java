package com.bh.live.dao.anchor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.model.anchor.HostRoom;
import com.bh.live.model.anchor.HostUser;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.anchor.AdvancesRes;
import com.bh.live.pojo.res.anchor.HostAdvanceRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主播预告表 Mapper 接口
 * </p>
 *
 * @author Morphon
 * @since 2019-08-05
 */
public interface HostAdvanceDao extends BaseMapper<HostAdvance> {

	/**
	 * 获取彩种的所有主播预告
	 * @auth Morphon
	 * @param seedNo
	 * @return
	 */
	List<HostAdvanceRes> queryHostAdvances(@Param("seedNo") Object seedNo,@Param("type") String type);
	
}
