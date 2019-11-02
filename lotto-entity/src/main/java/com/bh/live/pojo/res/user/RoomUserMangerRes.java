package com.bh.live.pojo.res.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播间管理员表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-06
 */
@Data
@ApiModel("特权管理返回前端列表对象")
public class RoomUserMangerRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id，用户辞职时用")
    private Integer id;

    @ApiModelProperty("主播昵称")
    private String nickname;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("主播房间id")
    private Integer roomId;

    @ApiModelProperty("主播房间id")
    private Integer hostId;

}
