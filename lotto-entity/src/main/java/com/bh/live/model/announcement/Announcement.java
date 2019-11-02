package com.bh.live.model.announcement;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_announcement")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章类型
     */
    @ApiModelProperty("文章类型 0新闻  1活动 2公告")
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
    @ApiModelProperty("状态 0无效 1有效")
    private Integer status;

    /**
     * 是否置顶
     */
    @ApiModelProperty("是否置顶  0否  1是")
    private Integer isStick;

    /**
     * 是否热门
     */
    @ApiModelProperty("是否热门  0否  1是")
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


}
