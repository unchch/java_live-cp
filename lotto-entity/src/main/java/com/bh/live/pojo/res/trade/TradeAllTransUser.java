package com.bh.live.pojo.res.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bh.live.pojo.req.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户流水表
 * </p>
 *
 * @author ww
 * @since 2019-07-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("方案对象")
public class TradeAllTransUser extends PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "交易流水编号")
    private String transCode;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "交易流水类别:1：充值；2：打赏；3：返奖；4：退款；5：提款；6：购买推荐; 7：系统赠送 8：系统扣除 9:主播提成")
    private Integer transType;

    @ApiModelProperty(value = "0：交易失败；1：交易成功；2：待审核； 3：审核通过； 4：审核不通过")
    private Integer transStatus;

    @ApiModelProperty(value = "渠道ID,默认0官方渠道")
    private Integer channelId;

    @ApiModelProperty(value = "交易总金额")
    private BigDecimal transAmount;

    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal cashAmount;

    @ApiModelProperty(value = "账户总余额,（现金总余额+中奖余额）")
    private BigDecimal totalCashBalance;

    @ApiModelProperty(value = "是否重置开派奖标记：0是正常派奖，1是重置派奖")
    private Integer awardFlag;

    @ApiModelProperty(value = "支出还是收入：1支出；2收入")
    private Integer inOut;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createBy;
    
    @ApiModelProperty(value = "修改人")
    private String modifyBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Transient
    @ApiModelProperty(value = "查询条件开始时间")
    @TableField(exist = false)
    @JsonProperty("startTime")
    private String beginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Transient
    @ApiModelProperty(value = "查询条件结束时间")
    @TableField(exist = false)
    private String endTime;

    @ApiModelProperty(value = "礼物数量")
    @TableField(exist = false)
    private Integer giftNum;

    @ApiModelProperty(value = "礼物名称")
    @TableField(exist = false)
    private String giftName;

    @ApiModelProperty(value = "昵称")
    @TableField(exist = false)
    private String nickname;

}
