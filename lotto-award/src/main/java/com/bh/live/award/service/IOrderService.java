package com.bh.live.award.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bh.live.common.annotation.ParamsNotNull;
import com.bh.live.model.trade.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author WuLong
 * @since 2019-08-05
 */
public interface IOrderService extends IService<Order> {

    /**
     *@description 查询订单集合
     *@author WuLong
     *@date 2019/8/5 20:36
     *@param orders 订单编号集合
     *@return List
     */
     List<Order> getOrderInfoBos(List<String> orders,List<Integer> status);
    /**
     *@description 查询订单集合
     *@author WuLong
     *@date 2019/8/8 23:18
     *@param issueNo 彩期编号
     * @param seedNo 彩种编号
     * @param status 订单流程状态 - 1:待支付，2:待开奖，3:待兑奖，4:未中奖，5:已派奖，6:已关闭，7:已撤单，8:被撤销(待重置派奖)，9:已取消(派奖)
     *@return List<Order>
     */
     List<Order> getOrderByIssueNoSeedNoStatus(String issueNo, Integer seedNo,List<Integer> status);

    /**
     * @param seedNo  彩种ID
     * @param issueNo 彩种期号
     * @param status  订单状态集合
     * @return List<String>  list订单
     * @description 查询订单集合
     * @author WuLong
     * @date 2019/8/9 14:25
     */
    List<String> selectDrawCodeOrderNos(Integer seedNo, String issueNo, List<Integer> status);

    /**
     * 修改开奖失败的订单为待开奖
     * @param failOrders
     * @author WuLong
     * @date 2019-08-13 20:40
     * @return
     */
    void updateOrderDrawFail(List<String> failOrders);
}
