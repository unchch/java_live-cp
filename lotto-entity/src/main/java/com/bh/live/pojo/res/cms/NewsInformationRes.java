package com.bh.live.pojo.res.cms;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/26 11:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新闻资讯返回对象")
public class NewsInformationRes {
    private String msg;
    private Integer status;
    private  String time;
}
