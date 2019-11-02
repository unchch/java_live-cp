package com.bh.live.common.core;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * rest响应实体
 * rest-ful 编码风格，向客户端、前端返回的统一内容
 * {
 * meta:{"code":code,“msg”:message}
 * data:{....}
 * }
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
@Data
public class Message<T> {

    public static final String META_ATTR_SUCCESS = "success";
    public static final String META_ATTR_CODE = "code";
    public static final String META_ATTR_MSG = "msg";
    public static final String META_ATTR_TIMESTAMP = "timestamp";

    /**
     * 消息头meta 存放状态信息 code message
     */
    private Map<String, Object> meta = new HashMap<String, Object>();

    /**
     * 消息内容  存储实体交互数据
     */
    private T data;

    /**
     * 状态类型
     */
    public enum Type {
        /**
         * 成功
         */
        SUCCESS(200, "请求成功"),
        /**
         * 警告
         */
        WARN(301, "操作警告"),
        /**
         * 失败
         */
        FAIL(400, "操作失败"),
        /**
         * 异常
         */
        ERROR(500, "操作异常");

        private final int value;
        private final String msg;

        Type(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }

        public int value() {
            return this.value;
        }

        public String msg() {
            return this.msg;
        }
    }

    /**
     * 返回操作成功消息
     *
     * @return 操作成功消息
     */
    public static Message success() {
        return new UnmodifyMessage(Boolean.TRUE, Type.SUCCESS.value, Type.SUCCESS.msg);
    }

    /**
     * 返回操作失败消息
     *
     * @return 操作失败消息
     */
    public static Message fail() {
        return new UnmodifyMessage(Boolean.FALSE, Type.FAIL.value, Type.FAIL.msg);
    }

    /**
     * 返回操作警告消息
     *
     * @return 操作警告消息
     */
    public static Message warn() {
        return new UnmodifyMessage(Boolean.TRUE, Type.WARN.value, Type.WARN.msg);
    }

    /**
     * 返回操作异常消息
     *
     * @return 操作异常消息
     */
    public static Message error() {
        return new UnmodifyMessage(Boolean.FALSE, Type.ERROR.value, Type.ERROR.msg);
    }

    public Message<T> setMeta(String key, Object object) {
        this.meta.put(key, object);
        return this;
    }

    public Message<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Message<T> ok(int statusCode, String statusMsg) {
        fillMeta(Boolean.TRUE, statusCode, statusMsg);
        return this;
    }

    public Message<T> warn(int statusCode, String statusMsg) {
        fillMeta(Boolean.TRUE, statusCode, statusMsg);
        return this;
    }

    public Message<T> fail(int statusCode, String statusMsg) {
        fillMeta(Boolean.FALSE, statusCode, statusMsg);
        return this;
    }

    public Message<T> error(int statusCode, String statusMsg) {
        fillMeta(Boolean.FALSE, statusCode, statusMsg);
        return this;
    }

    private void fillMeta(boolean success, int code, String msg) {
        this.setMeta(META_ATTR_SUCCESS, success);
        this.setMeta(META_ATTR_CODE, code);
        this.setMeta(META_ATTR_MSG, msg);
        this.setMeta(META_ATTR_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "Message{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }

    /** 
     * 不可编辑静态内部类
     *
     * @param  
     * @return 
     */
    private static class UnmodifyMessage<T> extends Message<T> {

        UnmodifyMessage(boolean success, int code, String msg){
            super.setMeta(META_ATTR_SUCCESS, success);
            super.setMeta(META_ATTR_CODE, code);
            super.setMeta(META_ATTR_MSG, msg);
            super.setMeta(META_ATTR_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
        }

        @Override
        public Message setData(T data) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }

        @Override
        public Message<T> setMeta(String key, Object object) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }

        @Override
        public Message<T> ok(int statusCode, String statusMsg) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }

        @Override
        public Message<T> warn(int statusCode, String statusMsg) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }

        @Override
        public Message<T> fail(int statusCode, String statusMsg) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }

        @Override
        public Message<T> error(int statusCode, String statusMsg) {
            throw new UnsupportedOperationException("静态模板消息不支持设置返回数据，请`new Message()`操作！");
        }
    }

}
