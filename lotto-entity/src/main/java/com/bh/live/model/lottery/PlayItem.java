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
 * 投注项表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_play_item")
@ApiModel(value = "PlayItem对象", description = "投注项表")
public class PlayItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "投注项编号")
    private String itemNo;

    @ApiModelProperty(value = "投注位编号")
    private String bitNo;

    @ApiModelProperty(value = "内容(数值)")
    private String content;

    @ApiModelProperty(value = "投注项显示排序")
    private Integer sort;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Integer status;


}
