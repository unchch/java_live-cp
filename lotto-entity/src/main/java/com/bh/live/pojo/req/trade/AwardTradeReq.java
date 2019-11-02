package com.bh.live.pojo.req.trade;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ww
 * @projectName java_live-cp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("打赏流水入参对象")
public class AwardTradeReq extends PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
