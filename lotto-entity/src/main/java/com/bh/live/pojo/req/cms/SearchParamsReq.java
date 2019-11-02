package com.bh.live.pojo.req.cms;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yq.
 * @date 2019/4/15 4:05 PM
 * @description
 * @since
 **/
@Data
public class SearchParamsReq extends PageParam {

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 状态 1-正常 0-异常
     */
    private Integer status;

    @ApiModelProperty(value = "是否管理员", hidden = true)
    private Boolean isAdmin;
}
