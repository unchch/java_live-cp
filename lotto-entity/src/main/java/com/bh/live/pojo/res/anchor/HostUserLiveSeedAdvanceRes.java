package com.bh.live.pojo.res.anchor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName HostUserLiveSeedAdvanceRes
 * @description: HostUserLiveSeedAdvanceRes
 * @author: Morphon
 * @date 2019-08-06 18:38:00
 */
@Data
@ApiModel("主播直播预告")
public class HostUserLiveSeedAdvanceRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154494L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "预告时间 2019/07/20  20:30-22:30")
    private String advanceTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
