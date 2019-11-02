package com.bh.live.pojo.req.user;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wuhuanrong
 * @Version: 1.0
 * @date: 2019/7/25 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("关注对象")
public class AttentionReq extends PageParam implements Serializable {

    /**
     * 用户类型 0普通 1专家 2主播
     */
    @ApiModelProperty("用户类型 0普通 1专家 2主播")
    private Integer userType;

    /**
     * 是否关注 0未关注 1已关注
     */
    @ApiModelProperty("是否关注 0取消 1已关注")
    private Integer isAttent;
}
