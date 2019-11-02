package com.bh.live.pojo.res.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel("公告返回对象")
public class AnnouncementRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    /**
     * 文章类型
     */
    @ApiModelProperty("文章类型")
    private String type;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date releaseTime;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    private String terrace;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;

    /**
     * 是否置顶
     */
    @ApiModelProperty("是否置顶 0 否  1是")
    private Integer isStick;

    /**
     * 是否热门
     */
    @ApiModelProperty("是否热门 0 否 1 是")
    private Integer isHot;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private String sort;

    /**
     * 作者
     */
    @ApiModelProperty("作者")
    private String author;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifyTime;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String modify;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 发布开始时间
     */
    @ApiModelProperty("发布开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @JsonProperty("startTime")
    private  Date startDate;
    /**
     * 发布结束时间
     */
    @ApiModelProperty("发布结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @JsonProperty("endTime")
    private  Date endDate;
    
    @ApiModelProperty("上一篇")
    private String upTitle;

    @ApiModelProperty("上一篇id")
    private String upTitleId;

    @ApiModelProperty("下一篇")
    private String downTitle;

    @ApiModelProperty("下一篇id")
    private String downTitleId;
    
}
