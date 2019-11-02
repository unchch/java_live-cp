package com.bh.live.model.lottery;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 投注位表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_play_bit")
@ApiModel(value = "PlayBit对象", description = "投注位表")
public class PlayBit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "位编号")
    private String bitNo;

    @ApiModelProperty(value = "内容（显示）")
    private String content;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Integer status;

    @ApiModelProperty(value = "是否显示投注位名称（0：不显示 1：显示）")
    private Integer showBitName;


}
