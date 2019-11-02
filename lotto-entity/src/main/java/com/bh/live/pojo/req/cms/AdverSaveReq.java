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
 * @Author: jiangxiaodu
 * @Version: 1.0
 * @date: 2019/7/25 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("广告请求对象")
public class AdverSaveReq implements Serializable {

    private static final long serialVersionUID = -5247224285951357898L;
    
    @ApiModelProperty("id")
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

    @ApiModelProperty("图片url")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planUpTime;

    @ApiModelProperty("计划下线时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planDownTime;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("排序")
    private String sort;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("修改人")
    private String modify;

    @ApiModelProperty("发布开始时间")
    @JsonProperty("startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date startDate;

    @ApiModelProperty("发布结束时间")
    @JsonProperty("endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date endDate;

   /* @ApiModelProperty("文件")
    private MultipartFile file;*/

    @ApiModelProperty("是否推荐")
    private Integer isRecommend;
    
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("内外链接  0内 1外")
    private Integer isChain;

    @ApiModelProperty("内链类型  0直播间  1活动")
    private Integer internalChainType;

    @ApiModelProperty("广告编号")
    private String advertisingSerial;
}
