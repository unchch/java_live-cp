package com.bh.live.pojo.req.anchor;

import com.bh.live.pojo.req.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 直播间
 * </p>
 *
 * @author Morphon
 * @since 2019-07-26
 */
@Data
@ApiModel("直播间前端入参信息（没有备注默认搜索传参）")
public class HostRoomReq extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id（未开播时为空），点结束时传参")
    private Integer id;

    @ApiModelProperty("房间id (搜索和开播时传参)")
    private Integer roomId;

    @ApiModelProperty("用户id（点开播时传参）")
    private Integer userId;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("开始时间 (结束时传参)")
    private String startTime;

    @ApiModelProperty("预播时间 （点开播时传参）")
    private String yLiveTime;

    @ApiModelProperty("彩种编号 （点开播时传参）")
    private Integer seedNo;

    @ApiModelProperty("彩种名称 （开播时传参）")
    private String seedName;

    @ApiModelProperty("房间状态 0未开播，1直播中 2已完成")
    private Integer status;

    @ApiModelProperty("主播昵称")
    private String nickname;

    /*@ApiModelProperty("踢下限原因")
    private Integer kickOffLine;*/

    @ApiModelProperty("是否推荐首页 0否，1是 （修改时传参）")
    private Integer recommHomePage;

    @ApiModelProperty("排序（修改时传参）")
    private Integer sort;

    @ApiModelProperty(value = "预告id（点开播时传参）")
    private Integer advanceId;

}
