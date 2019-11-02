package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播间禁言表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@ApiModel("禁言列表返回前端对象信息")
public class NoTalkRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id，解禁时入参用")
    private Integer noTalkId;

    @ApiModelProperty("用户账号")
    private String nickname;

    @ApiModelProperty("用户等级图标")
    private String lvImage;

    @ApiModelProperty("禁言时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("startTime")
    private Date beginTime;

    @ApiModelProperty("有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty("操作人")
    private String operater;

    @ApiModelProperty("禁言IP")
    private String noTalkIp;

    @ApiModelProperty("禁言类型 1用户  2 IP")
    private Integer type;

    @ApiModelProperty("禁言原因")
    private String remark;

}
