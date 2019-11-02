package com.bh.live.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回结果集对象
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@ApiModel()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -2118550732427156580L;


    private String message;
    private int code;
    private T data;

    private Result(T data) {
        this.code = CodeMsg.SUCCESS.code;
        this.message = CodeMsg.SUCCESS.message;
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.code;
        this.message = cm.message;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

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
     * 成功时候的调用
     *
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 成功时候的调用
     *
     * @return
     */
    public static Result success(int data) {
        return new Result(data);
    }

    /**
     * 成功，不需要传入参数
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success() {
        return (Result<T>) success(true);
    }

    /**
     * 失败时候的调用(重载)
     *
     * @return
     */
    public static <T> Result<T> error() {
        return new Result<T>(Type.FAIL.value(), Type.FAIL.msg());
    }

    /**
     * 失败时候的调用(重载)
     *
     * @return
     */
    public static <T> Result<T> error(String msg) {
        return new Result<T>(Type.ERROR.value(), msg);
    }

    /**
     * 失败时候的调用(重载)
     *
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>(code, msg);
    }
}
