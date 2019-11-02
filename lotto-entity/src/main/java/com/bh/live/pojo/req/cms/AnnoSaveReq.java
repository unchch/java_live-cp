package com.bh.live.pojo.req.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import com.bh.live.pojo.req.PageParam;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("公告请求对象")
public class AnnoSaveReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("文章类型")
    private String type;

    @ApiModelProperty("标题")
    private String title;
    
    @ApiModelProperty("发布时间")
    private String releaseTime;

    @ApiModelProperty("平台")
    private String terrace;
    
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("是否置顶")
    private Integer isStick;

    @ApiModelProperty("是否热门")
    private Integer isHot;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifyTime;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("修改人")
    private String modify;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("发布开始时间")
    @JsonProperty("startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date startDate;

    @ApiModelProperty("发布结束时间")
    @JsonProperty("endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date endDate;
}
