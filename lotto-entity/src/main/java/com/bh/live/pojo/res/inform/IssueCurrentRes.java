package com.bh.live.pojo.res.inform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName IssueCurrentRes
 * @description: IssueCurrentRes
 * @author: yq.
 * @date 2019-08-10 14:32:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩期res", description = "彩期res，包括上一期，当前期，下一期")
public class IssueCurrentRes implements Serializable {

    private static final long serialVersionUID = -3318898222541698616L;

    @ApiModelProperty(value = "上一期彩期")
    private IssueEntityRes last;

    @ApiModelProperty(value = "当前期彩期")
    private IssueEntityRes current;

    @ApiModelProperty(value = "下一期彩期")
    private IssueEntityRes next;
}