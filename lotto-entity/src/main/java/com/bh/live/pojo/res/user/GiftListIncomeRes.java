package com.bh.live.pojo.res.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("礼物赠送清单")
public class GiftListIncomeRes {

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("礼物种类id，去枚举取值")
    private Integer categoryId;

    @ApiModelProperty("礼物数")
    private Long giftNum;

    @ApiModelProperty("礼物价值")
    private BigDecimal amount;

    @ApiModelProperty("分成比例，需要x100")
    private BigDecimal divide;

    @ApiModelProperty("主播最终收入")
    private BigDecimal income;

    @ApiModelProperty("送礼物用户昵称")
    private String nickname;

    @ApiModelProperty("礼物名称")
    private String giftName;

}
