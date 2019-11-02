package com.bh.live.pojo.req.lottery;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName SeedCategoryReq
 * @description: SeedCategoryReq
 * @author: yq.
 * @date 2019-07-23 17:35:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "彩种分类", description = "彩种分类")
public class SeedCategoryReq implements Serializable {

    private static final long serialVersionUID = 1583023337457249888L;

    /**
     * 彩种分类编号
     */
    private Integer categoryNo;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类类型（0：高频彩 1：低频彩）
     */
    private Integer categoryType;

    /**
     * 分类模式（1：传统玩法  2：聊天室玩法  3：棋牌玩法  4：视讯玩法）
     */
    private Integer categoryMode;
}
