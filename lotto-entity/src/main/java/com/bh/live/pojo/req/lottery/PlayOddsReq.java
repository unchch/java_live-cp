package com.bh.live.pojo.req.lottery;

import com.bh.live.pojo.res.lottery.PlayOddsRes;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName PlayOddsReq
 * @description: 玩法赔率修改req
 * @author: yq.
 * @date 2019-07-26 17:46:00
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "玩法赔率修改req", description = "玩法赔率修改req")
public class PlayOddsReq extends PlayOddsRes implements Serializable {
    private static final long serialVersionUID = 7207050580714524340L;
}
