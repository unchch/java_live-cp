package com.bh.live.pojo.res.lottery;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SeedRes
 * @description: SeedRes
 * @author: yq.
 * @date 2019-07-23 18:38:00
 */
@Data
public class SeedNavRes implements Serializable {

    private static final long serialVersionUID = -4626047106998154494L;

    /**
     * 彩种分类ID
     */
    private Integer categoryNo;

    /**
     *  彩种编号
     */
    private Integer seedNo;

    /**
     * 彩种名称
     */
    private String seedName;

    /**
     * 封面PC
     */
    private String coverPcIcon;

    /**
     * 封面APP
     */
    private String coverAppIcon;
}
