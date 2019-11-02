package com.bh.live.model.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 平台全局敏感词过滤表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_keyword")
@ApiModel("敏感词前端交互对象")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "kw_id", type = IdType.AUTO)
    @ApiModelProperty("序号")
    private Integer kwId;

    /**
     * 敏感词名称
     */
    @ApiModelProperty("敏感词")
    private String kwName;

    /**
     * 敏感词级别
     */
    @ApiModelProperty("敏感词级别 0一般 1危险")
    private Integer kwLv;

    /**
     * 替换显示的内容
     */
    @ApiModelProperty("替换内容")
    private String rpContent;

    /**
     * 状态 0禁用 1启用
     */
    @ApiModelProperty("状态 0禁用 1启用")
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


}
