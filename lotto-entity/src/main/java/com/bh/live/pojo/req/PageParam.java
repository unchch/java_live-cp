package com.bh.live.pojo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础分页对象
 *
 * @author Y.
 */
@Data
@ApiModel(value = "PageParam", discriminator = "分页对象")
public class PageParam implements Serializable {

    /**
     * @Fields serialVersionUID : 通用分页对象
     */
    private static final long serialVersionUID = 5380848651584135103L;

    @TableField(exist = false)
    @ApiModelProperty(value = "当前页码", required = true)
    private Integer pageNum = 1;

    @TableField(exist = false)
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer pageSize = 10;

    @JsonInclude
    public Integer getPageStart() {
        return (pageNum - 1) * pageSize;
    }
}
