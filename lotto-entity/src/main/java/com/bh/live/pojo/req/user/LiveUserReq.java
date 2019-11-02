package com.bh.live.pojo.req.user;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("直播用户")
public class LiveUserReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增id
     */
    @ApiModelProperty("用户id")
    private Integer id;

    /**
     * 主播id
     */
    @ApiModelProperty("主播id")
    private Integer userId;

    /**
     * 强制离线时长/秒
     */
    @ApiModelProperty("强制离线时长/秒")
    private Integer liveTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 是否可用 0不可用 1可用
     */
    @ApiModelProperty("是否可用 0不可用 1可用")
    private Integer isUsable;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

}
