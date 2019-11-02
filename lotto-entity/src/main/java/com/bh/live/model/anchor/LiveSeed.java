package com.bh.live.model.anchor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * <p>
 * 直播彩种管理
 * </p>
 *
 * @author Morphon
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_live_seed")
public class LiveSeed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "ls_id",type = IdType.AUTO)
    private Integer lsId;

    /**
     * 彩种编号
     */
    private Integer seedNo;

    /**
     * 彩种分类id
     */
    private Integer categoryNo;

    /**
     * 彩种名称
     */
    private String seedName;

    /**
     * 直播数量
     */
    private Integer liveCount;

    /**
     * 封面
     */
    private String cover;

    /**
     * 状态 0关闭中 1启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否推荐首页 0否 1是
     */
    private Integer recommendIndex;

    /**
     * 是否删除 0否  1是
     */
    private Integer isDel;

}
