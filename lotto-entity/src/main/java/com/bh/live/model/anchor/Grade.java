package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户、主播等级表
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_grade")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 等级id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 等级类型  0用户  1主播
     */
    private Integer lvType;

    /**
     * 等级名称
     */
    private String lvName;

    /**
     * 等级值
     */
    private String lvValue;

    /**
     * 是否可用 0不可 1可用
     */
    private Integer isUsable;

    /**
     * 等级经验范围最小值
     */
    private Integer lvMin;

    /**
     * 等级经验范围最大值
     */
    private Integer lvMax;

    /**
     * 等级图标
     */
    private String lvImage;

    /**
     *是否删除 0否  1是
     */
    private Integer isDel;

}
