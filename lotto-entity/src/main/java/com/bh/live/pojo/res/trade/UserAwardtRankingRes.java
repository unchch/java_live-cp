package com.bh.live.pojo.res.trade;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 打赏记录榜单
 * </p>
 *
 * @author lgs
 * @since 2019-07-24
 */
@Data
@ApiModel(value = "打赏记录榜单", description = "打赏记录榜单")
public class UserAwardtRankingRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户头像")
    private String imageUrl;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "彩币")
    private BigDecimal amount;

}
