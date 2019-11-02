package com.bh.live.pay.config;


import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *@Author: WuLong
 *@Date: 2019/7/15 21:51
 *@Description: AOP声明式事务配置
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.bh.live.service.*Service.*(..))";

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        Map<String, TransactionAttribute> txMap = new HashMap<>(16);

        /*配置事务方法的前缀*/
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("batch*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("modify*", requiredTx);
        txMap.put("delete*", requiredTx);
        /*配置只读事务方法的前缀*/
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
       /* *//*其余方法添加事务*//*
        txMap.put("*", requiredTx);*/
        source.setNameMap(txMap);

        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 注册事务
     * @param txAdvice
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}
