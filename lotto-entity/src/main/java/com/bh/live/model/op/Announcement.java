package com.bh.live.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("op_announcement")
@ApiModel(value = "Announcement对象", description = "公告表")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章类型 0新闻  1活动 2公告")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布时间")
    private Date releaseTime;

    @ApiModelProperty(value = "平台")
    private String terrace;

    @ApiModelProperty(value = "状态 0无效 1有效")
    private Integer status;

    @ApiModelProperty(value = "是否置顶")
    private Integer isStick;

    @ApiModelProperty(value = "是否热门")
    private Integer isHot;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "修改人")
    private String modify;

    @ApiModelProperty(value = "备注")
    private String remark;


}
