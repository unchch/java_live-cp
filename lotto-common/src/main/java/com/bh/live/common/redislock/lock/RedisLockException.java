package com.bh.live.common.redislock.lock;
/**
 * @description: RedisLockException
 * @author Y.
 * @date 2019-8-1 18:31:24
 */
public class RedisLockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public RedisLockException(String message) {
		this.message = message;
	}

	@Override
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}