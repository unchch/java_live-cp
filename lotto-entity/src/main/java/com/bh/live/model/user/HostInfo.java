package com.bh.live.model.user;

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
 * 主播附加信息表
 * </p>
 *
 * @author lgs
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("uc_host_info")
@ApiModel(value = "HostInfo对象", description = "主播附加信息表")
public class HostInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "uc_user_id")
    private Integer userId;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "图库，图片链接以','逗号隔开")
    private String gallery;

    @ApiModelProperty(value = "个人简介")
    private String personalProfile;

    @ApiModelProperty(value = "首页全部主播排序")
    private Integer sort;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
