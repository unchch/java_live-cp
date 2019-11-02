package com.bh.live.common.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;

import java.util.Collection;

/**
 * @author lgs
 * @title: Assert
 * @projectName java_live-cp
 * @description: 自定义断言，用于自定义异常的断言扩展
 * @date 2019/7/19  14:39
 */
public class Assert {

    /**
     * 判断是否为真
     *
     * @param expression
     * @param codeMsg
     */
    public static void isTrue(boolean expression, CodeMsg codeMsg) {
        if (!expression) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }

    /**
     * 判断是否为假
     *
     * @param expression
     * @param codeMsg
     */
    public static void isFalse(boolean expression, CodeMsg codeMsg) {
        if (expression) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }

    /**
     * 断言是否为not null
     *
     * @param obj
     * @param codeMsg
     */
    public static void isNotNull(Object obj, CodeMsg codeMsg) {
        if (ObjectUtil.isNull(obj)) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }

    /**
     * 断言是否为null
     *
     * @param obj
     * @param codeMsg
     */
    public static void isNull(Object obj, CodeMsg codeMsg) {
        if (ObjectUtil.isNotNull(obj)) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }


    /**
     * 断言集合是否为null
     *
     * @param obj
     * @param codeMsg
     */
    public static void collectIsNull(Collection obj, CodeMsg codeMsg) {
        if (CollectionUtil.isNotEmpty(obj)) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }

    /**
     * 断言集合是否为null
     *
     * @param obj
     * @param codeMsg
     */
    public static void collectIsNotNull(Collection obj, CodeMsg codeMsg) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new ResultJsonException(Result.error(codeMsg));
        }
    }
}
