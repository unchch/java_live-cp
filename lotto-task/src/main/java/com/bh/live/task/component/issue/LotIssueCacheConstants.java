package com.bh.live.task.component.issue;

/**
 * 彩期缓存常量
 * @author fuxiang
 *
 */
public interface LotIssueCacheConstants {
	
	/**
	 * 彩期配置信息
	 */
	String KEY_ISSUE_ARGUMENT_ALL = "issue_argument_all";
	
	/**
	 * jobcenter中彩期切换lock key
	 */
	String KEY_LOCK_JOB_ISSUE_SWITCH = "job_issue_switch_lock:";
	
	/**
	 * jobcenter中彩期封盘、关盘lock key
	 */
	String KEY_LOCK_JOB_ISSUE_CLOSING = "job_issue_closing_lock:";
	
	/**
	 * jobcenter中彩期arg变更监控lock key
	 */
	String KEY_LOCK_JOB_ISSUE_ARGS_MODIFY = "job_issue_args_modify_lock";
}
