package com.bh.live.pojo.req.lottery;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName SeedReq
 * @description: SeedReq
 * @author: yq.
 * @date 2019-07-23 17:35:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩种信息", description = "彩种信息")
public class SeedReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 5109953424862245209L;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "彩种分类编号")
    private Integer categoryNo;

}
