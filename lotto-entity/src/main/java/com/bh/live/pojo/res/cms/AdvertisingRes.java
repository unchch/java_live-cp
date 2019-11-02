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
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/25 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("广告返回对象")
public class AdvertisingRes implements Serializable {

    private static final long serialVersionUID = -5247224285951357898L;
    /**
     *id
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String describes;

    /**
     * 内链
     */
    @ApiModelProperty("内链")
    private String internalChain;

    /**
     * 外链
     */
    @ApiModelProperty("外链")
    private String otherChain;

    /**
     * 平台
     */
    @ApiModelProperty("terrace")
    private String terrace;

    /**
     * 是否发布
     */
    @ApiModelProperty("是否发布 0否  1发布")
    private Integer isRelease;

    /**
     * 图片
     */
    @ApiModelProperty("图片url")
    private String img;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date creationTime;

    /**
     * 有效时间
     */
    @ApiModelProperty("有效时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date validTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modificationTime;

    /**
     * 计划上线时间
     */
    @ApiModelProperty("计划上线时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date planUpTime;

    /**
     * 计划下线时间
     */
    @ApiModelProperty("计划下线时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date planDownTime;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

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
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String modify;

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
    /**
     * 文件
     */
    @ApiModelProperty("文件")
    private byte[] file;
    /**
     * 是否推荐
     */
    @ApiModelProperty("是否推荐")
    private Integer isRecommend;

    @ApiModelProperty("内外链接  0内 1外")
    private Integer isChain;

    @ApiModelProperty("内链类型  0直播间  1活动")
    private Integer internalChainType;

    @ApiModelProperty("广告编号")
    private String advertisingSerial;

}
