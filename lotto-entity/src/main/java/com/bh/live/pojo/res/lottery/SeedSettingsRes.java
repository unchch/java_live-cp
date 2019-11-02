package com.bh.live.pojo.res.lottery;

import com.bh.live.pojo.req.lottery.SeedSettingsReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SeedSettingsRes
 * @description: 彩种玩法配置res
 * @author: yq.
 * @date 2019-07-26 18:15:00
 */
@Data
@ApiModel(value = "彩种玩法配置res", description = "彩种玩法配置res")
public class SeedSettingsRes extends SeedSettingsReq implements Serializable {

    private static final long serialVersionUID = 4235151666625824619L;
}
