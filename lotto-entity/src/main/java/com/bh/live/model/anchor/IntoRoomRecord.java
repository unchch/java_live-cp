package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 进入直播间记录表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_into_room_record")
@ApiModel(value="IntoRoomRecord对象", description="进入直播间记录表")
public class IntoRoomRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "直播间id")
    private Integer roomId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "进入直播间时间")
    private Date intoTime;

}
