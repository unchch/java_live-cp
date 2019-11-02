package com.bh.live.common.interceptor;

import com.bh.live.common.annotation.ParamsNotNull;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CheckParamsInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //拿到该方法上加了注解的参数名称
        List<String> list = getParamsName((HandlerMethod) handler);
        for (String s : list) {
            //获取到参数名称并判断是否为空
            String parameter = request.getParameter(s);
            if (CommonUtil.isEmpty(parameter)){
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                CodeMsg codeMsg = CodeMsg.E_90003;
                codeMsg.message = String.format(codeMsg.message,s);
                response.getWriter().write(JsonUtil.obj2json(Result.error(codeMsg)));
                return false;
            }
        }
        //如果拿到的对象为空,说明没有此注解,直接放行
        return true;
    }

    /**
     * 拿到在参数上加了该注解的参数名称
     */
    private List getParamsName(HandlerMethod handlerMethod) {
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        List<String> list = new ArrayList<String>();
        for (Parameter parameter : parameters) {
            if(parameter.isAnnotationPresent(ParamsNotNull.class)){
                list.add(parameter.getName());
            }
        }
        return list;
    }
}
