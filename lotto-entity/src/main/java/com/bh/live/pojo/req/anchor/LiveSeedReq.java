package com.bh.live.pojo.req.anchor;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Morphon
 * @date 2019/7/25 17:13
 * @desc
 * @Version 1.0
 */
@Data
@ApiModel(value = "直播彩种入参bean", description = "直播彩种入参bean")
public class LiveSeedReq extends PageParam implements Serializable {

    @ApiModelProperty(value = "主键id，删除修改传参")
    private Integer lsId;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "是否推荐首页")
    private Integer recommendIndex;

    @ApiModelProperty(value = "彩种排序")
    private Integer sort;

    @ApiModelProperty(value = "彩种封面")
    private String cover;

    @ApiModelProperty("状态 0关闭 1启用")
    private Integer status;

}
