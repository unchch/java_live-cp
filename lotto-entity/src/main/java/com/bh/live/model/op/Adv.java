package com.bh.live.model.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("op_adv")
@ApiModel(value = "Adv对象", description = "")
public class Adv implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "广告类别")
    private String category;

    @ApiModelProperty(value = "广告标题")
    private String title;

    @ApiModelProperty(value = "跳转链接")
    private String url;

    @ApiModelProperty(value = "广告内容(文字)")
    private Integer contentTxt;

    @ApiModelProperty(value = "广告内容（图片）")
    private String contentImg;

    @ApiModelProperty(value = "排序")
    private Integer sortIdx;

    @ApiModelProperty(value = "状态(0下架、1上架）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "备注")
    private String remark;


}
