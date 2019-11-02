package com.bh.live.model.configuration;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 全局配置表
 * </p>
 *
 * @author WW
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("全局配置表")
@TableName("lotto_live_configuration")
public class LiveConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置id
     */
    @ApiModelProperty("配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String typeName;

    /**
     * 类型值
     */
    @ApiModelProperty("类型值")
    private String typeValue;

    /**
     * 配置名称
     */
    @ApiModelProperty("配置名称")
    private String configName;

    /**
     * 配置值
     */
    @ApiModelProperty("配置值")
    private String configValue;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String configDescribe;

    /**
     * 是否可用 0不可以 1可用
     */
    @ApiModelProperty("是否可用 0不可以 1可用")
    private Integer isUsable;

    /**
     * 扩展名称
     */
    @ApiModelProperty("扩展名称")
    private String extendName;

    /**
     * 扩展值
     */
    @ApiModelProperty("扩展值")
    private String extendValue;

    /**
     * 扩展描述
     */
    @ApiModelProperty("扩展描述")
    private String extendDescribe;

    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer sortValue;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date creatTime;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Integer creatBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    private Integer updateBy;


}
