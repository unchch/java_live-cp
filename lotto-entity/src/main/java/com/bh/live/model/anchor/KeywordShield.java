package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 房间发言关键词过滤表
 * </p>
 *
 * @author Morphon
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_keyword_shield")
public class KeywordShield implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @TableId(value = "shield_id", type = IdType.AUTO)
    private Integer shieldId;

    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String keyword;

    /**
     * 房间号
     */
    @ApiModelProperty("房间id")
    private Integer roomId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
