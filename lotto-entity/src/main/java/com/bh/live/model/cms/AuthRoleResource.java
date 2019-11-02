package com.bh.live.model.cms;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 资源角色关联表
 * </p>
 *
 * @author lgs
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_auth_role_resource")
@ApiModel(value="AuthRoleResource对象", description="资源角色关联表")
public class AuthRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "资源ID")
    private Integer resourceId;
}
