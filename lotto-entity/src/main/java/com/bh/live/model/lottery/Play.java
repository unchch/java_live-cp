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
 * 玩法表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_play")
@ApiModel(value = "Play对象", description = "玩法表")
public class Play implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "玩法编号")
    private String playNo;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "玩法模式（1：官方  2：信用）")
    private Integer playMode;

    @ApiModelProperty(value = "父玩法编号")
    private String parentNo;

    @ApiModelProperty(value = "玩法类型（0：导航玩法 1：投注玩法）")
    private Integer playType;

    @ApiModelProperty(value = "玩法名称")
    private String playName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "玩法说明：举例")
    private String explainExample;

    @ApiModelProperty(value = "玩法说明：帮助")
    private String explainHelp;

    @ApiModelProperty(value = "状态（0：停用 1：启用）")
    private Integer status;


}
