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
import java.util.Date;

/**
 * <p>
 * 彩种表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lot_seed")
@ApiModel(value = "Seed对象", description = "彩种表")
public class Seed implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "彩种编号")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "彩种类型编号")
    private Integer categoryNo;

    @ApiModelProperty(value = "彩种描述")
    private String description;

    @ApiModelProperty(value = "参考期号,初始化时参考的期号（job每天进行最后期号的设定）")
    private String referIssueNo;

    @ApiModelProperty(value = "时间段内彩期数量")
    private Integer createDays;

    @ApiModelProperty(value = "创建多少天的期号")
    private Date createdDate;

    @ApiModelProperty(value = "停止投注间隔")
    private Integer stopBetInterval;

    @ApiModelProperty(value = "是否显示（0：隐藏 1：显示，2：隐藏官方玩法，3：隐藏信用玩法）")
    private Integer showAble;

    @ApiModelProperty(value = "是否推荐首页（0：不推荐 1：推荐）")
    private Integer showHomePage;

    @ApiModelProperty(value = "销售状态（0：待开售  1：销售中 2：封盘中 3：已截止 4：已停售）")
    private Integer saleState;

    @ApiModelProperty(value = "首页彩种描述")
    private String seedDesc;

    @ApiModelProperty(value = "推荐玩法模式（1：官方 2：信用）")
    private Integer playModeRecommend;

    @ApiModelProperty(value = "推荐官方玩法")
    private String officialPlayRecommend;

    @ApiModelProperty(value = "推荐官方玩法编号")
    private String officialPlayNo;

    @ApiModelProperty(value = "推荐信用玩法")
    private String creditPlayRecommend;

    @ApiModelProperty(value = "推荐信用玩法编号")
    private String creditPlayNo;

    @ApiModelProperty(value = "开奖方式（0：自动开奖  1：手动开奖）")
    private Integer prizeMode;

    @ApiModelProperty(value = "封面PC")
    private String coverPcIcon;

    @ApiModelProperty(value = "封面APP")
    private String coverAppIcon;

    @ApiModelProperty(value = "logo pc")
    private String logoPcIcon;

    @ApiModelProperty(value = "logo app")
    private String logoAppIcon;

    @ApiModelProperty(value = "彩种图")
    private String imageIcon;

    @ApiModelProperty(value = "维护状态（0：非维护状态  1：维护状态）")
    private Integer status;

    @ApiModelProperty(value = "展示顺序")
    private Integer sort;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
