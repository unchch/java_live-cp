package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播预告返回
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-08-09
 */
@Data
@ApiModel(value = "直播预告", description = "直播预告")
public class LiveAdvanceRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("直播开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date open_time;

    @ApiModelProperty(value = "头像")
    private String imageUrl;

    @ApiModelProperty(value = "名称")
    private String  nickname;

    @ApiModelProperty(value = "房间Id")
    private Integer roomId;

    @ApiModelProperty(value = "人气值")
    private Integer hotVal;

    @ApiModelProperty(value = "直播中截图")
    private String liveCover;

}
