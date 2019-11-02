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
 * @since 2019-08-01
 */
@Data
@ApiModel("房间管理员返回前端列表对象")
public class RoomMangerRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id，撤销时用")
    private Integer id;

    @ApiModelProperty("用户账号")
    private String nickname;

    @ApiModelProperty("任命时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("用户等级图标")
    private String userLvIcon;

    @ApiModelProperty("最后进入房间时间，超过30天未进入房间显示不活跃")
    private String intoTime;

}
