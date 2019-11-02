package com.bh.live.pojo.res.anchor;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("主播直播预告信息")
public class AdvanceRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "直播彩种")
    private Integer seedNo;

    @ApiModelProperty(value = "彩种名称")
    private String seedName;

    @ApiModelProperty(value = "主播时间段预告列表")
    private List<AdvanceRes.Live> host;

    @Data
    @Accessors(chain = true)
    public static class Live {
        private String date;
        private List<HostAdvanceRes> hostAdvanceRes;

    }
}
