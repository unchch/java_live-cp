package com.bh.live.model.rankingList;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 排行表
 * </p>
 *
 * @author WW
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lotto_ranking_list")
public class RankingList implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 排行id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/**
	 * 人气值
	 */
	private BigDecimal popularityValue;

	/**
	 * 财富值
	 */
	private BigDecimal treasureValue;

	/**
	 * 连胜值
	 */
	private Integer wingingValue;

	/**
	 * 胜率值
	 */
	private BigDecimal winrateValue;

	/**
	 * 盈利率值
	 */
	private BigDecimal profitrateValue;

	/**
	 * 周期 0天 1周 2月 3总
	 */
	private Integer rankPeriod;

	/**
	 * 是否可用 0不可 1可用
	 */
	private Integer isUsable;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 创建时间
	 */
	private Date creatTime;

	/**
	 * 场数
	 */
	private Integer numberOf;

}
