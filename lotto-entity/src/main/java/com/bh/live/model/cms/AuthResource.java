package com.bh.live.model.cms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资源信息表(菜单,资源),此表所有子站及主站共享
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_auth_resource")
@ApiModel(value = "AuthResource对象", description = "资源信息表(菜单,资源),此表所有子站及主站共享")
public class AuthResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "资源编码-权限字符 例如 后台用户添加 system:user:add")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "资源描述、菜单名称 比如 风控管理-风控管理列表  添加风控设置")
    private String name;

    @ApiModelProperty(value = "父资源编码->菜单")
    private Integer parentId;

    @ApiModelProperty(value = "访问地址URL")
    private String path;

    @ApiModelProperty(value = "类型 1-菜单menu 2-资源 element(rest-api) 3-菜单目录 4-api分类")
    private Integer type;

    @ApiModelProperty(value = "访问方式 GET POST PUT DELETE PATCH")
    private String method;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否隐藏：（0正常，1隐藏）", hidden = true)
    private Integer status;

    /**
     * 是否末级（1：末级，0：非末级）
     */
    @TableField(exist = false)
    private Integer isLastLevel;
}
