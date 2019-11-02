package com.bh.live.task.service.impl.advance;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bh.live.common.utils.DateUtils;
import com.bh.live.model.anchor.HostAdvance;
import com.bh.live.task.dao.advance.HostAdvanceDao;
import com.bh.live.task.service.advance.IHostAdvanceService;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * 主播预告表 服务实现类
 * </p>
 *
 * @author WW
 * @since 2019-08-05
 */
@Service
public class HostAdvanceServiceImpl extends ServiceImpl<HostAdvanceDao, HostAdvance>
		implements IHostAdvanceService {

	@Override
	public void changeHostAdvanceStartTime() {
//		// 查询周期性的预播
//		LambdaQueryWrapper<HostAdvance> query = new QueryWrapper<HostAdvance>().lambda();
//		query.le(HostAdvance::getEndTime, DateUtils.getDayTime(new Date(), 0))//直播结束
//				.eq(HostAdvance::getIsUsable, 1).eq(HostAdvance::getLivePeriod, 0);
//		query.orderByAsc(HostAdvance::getStartTime);
//		super.list(query).forEach(advance -> {
//			if (!DateUtils.compare_to(new Date(), advance.getStartTime())) {// 已经开播
//				String[] livedate = advance.getLiveDate().split(";");// 直播周存0;1;2;3;4;5;6(周日一二....六)。天存具体日期
//				int wek = DateUtils.dayForWeek(new Date());// 当前星期 1 6
//				for (int i = 0; i < livedate.length; i++) {
//					int pwek = Integer.parseInt(livedate[i]);
//					if (pwek - wek > 0) {// 周期星期 036
//						advance.setStartTime(
//								DateUtils.getDate(DateUtils.getDayTime(advance.getStartTime(), pwek - wek)));
//						advance.setEndTime(
//								DateUtils.getDate(DateUtils.getDayTime(advance.getEndTime(), pwek - wek)));
//						break;
//					}
//				}
//				super.updateById(advance);// 更新
//			}
//		});
//
	}

//	public static void main(String[] args) {
//		System.out.println(DateUtils.getDayTime(0));
//		System.out.println(new Date());
//		System.out.println(DateUtils.getDate("2019-08-01 12:22:56"));
//		
//	}

}
