package com.bh.live.model.trade;

import com.bh.live.pojo.res.page.TableDataInfo;
import lombok.Data;

/**
 * @author lgs
 * @title: OrderView
 * @projectName java_live-cp
 * @description: 订单页面返回对象
 * @date 2019/8/2  11:32
 */
@Data
public class OrderView {

    private TableDataInfo page;

    private OrderCount orderCount;
}
