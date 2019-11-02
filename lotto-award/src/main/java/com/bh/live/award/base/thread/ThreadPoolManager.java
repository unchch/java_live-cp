package com.bh.live.award.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc 线程池
 * @author WuLong
 * @date 2019年8月14日
 */
public class ThreadPoolManager {
	
	private static final ExecutorService  THREAD_POOL = Executors.newCachedThreadPool();
	/**
	 * 通过CachedThreadPool执行任务
	 * @author WuLong
	 * @CreatDate 2019年8月14日
	 * @param task
	 */
	public static void executeCachedThread(Runnable task){
		THREAD_POOL.execute(task);
	}
}
