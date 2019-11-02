package com.bh.live.model.announcement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author JiangXiaodu
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_advertising")
public class Advertising implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String describes;

    @ApiModelProperty("内链")
    private String internalChain;

    @ApiModelProperty("外链")
    private String otherChain;

    @ApiModelProperty("平台")
    private String terrace;

    @ApiModelProperty("是否发布")
    private Integer isRelease;

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date creationTime;

    @ApiModelProperty("有效时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date validTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modificationTime;

    @ApiModelProperty("计划上线时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date planUpTime;

    @ApiModelProperty("计划下线时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date planDownTime;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("修改人")
    private String modify;

    @ApiModelProperty("状态  0无效  1有效")
    private Integer status;

    @ApiModelProperty("是否推荐 0推")
    private Integer isRecommend;

    @ApiModelProperty("文件名称")
    private String fileName;
    
    @ApiModelProperty("类型 0 文字广告，1 图片广告")
    private Integer type;

    @ApiModelProperty("内外链接  0内 1外")
    private Integer isChain;

    @ApiModelProperty("内链类型  0直播间  1活动")
    private Integer internalChainType;

    @ApiModelProperty("广告编号")
    private String advertisingSerial;

}
