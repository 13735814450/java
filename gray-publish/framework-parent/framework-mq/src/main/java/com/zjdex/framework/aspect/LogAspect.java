package com.zjdex.framework.aspect;


import com.zjdex.framework.annotation.ApiLog;
import com.zjdex.framework.holder.RequestHolder;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.data.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lindj
 * @date 2019/5/23 0023
 * @description
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final LocalVariableTableParameterNameDiscoverer PARAMETER_NAME_DISCOVERER =
            new LocalVariableTableParameterNameDiscoverer();

    private static SpelExpressionParser expressionParser = new SpelExpressionParser();


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Pointcut("@annotation(com.zjdex.framework.annotation.ApiLog)")
    public void execute() {
    }

    /**
     *
     */
    @Around("execute()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        Date now = new Date();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        ApiLog apiLog = method.getAnnotation(ApiLog.class);
        HttpServletRequest request = RequestHolder.getRequest();
        Map<String, Object> result = new HashMap<String, Object>(16);
        String operatorId = request.getHeader("uid");
        String orgId = request.getHeader("orgId");
        String operateModule = request.getHeader("menuId");

        Map<String, Object> params = build(proceedingJoinPoint);
        String resource = apiLog.resource();
        if(!StringUtil.isEmpty(resource)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariables(params);
            Object operateDataId = expressionParser.parseExpression(resource).getValue(context,
                    Object.class);
            result.put("operateDataId", operateDataId);
        }
        result.put("operateType", apiLog.type());
        result.put("operateContent", apiLog.name());
        result.put("operatorId", operatorId);
        result.put("orgId", orgId);
        result.put("operateModule", operateModule);

        result.put("url", request.getRequestURI());
        result.put("className", signature.getDeclaringTypeName());
        result.put("methodName", signature.getName());
        result.put("createTime", now);
        result.put("modifyTime", now);

        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
            result.put("operateState", "success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            result.put("operateState", "error");
            throw e;
        } finally {
            String value = JsonUtil.objectToJson(result);
            logger.info("{}", value);
            kafkaTemplate.send("app_log", value);
        }

        return object;
    }

    public static Map<String, Object> build(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        if(!StringUtil.isEmpty(paramNames)){
            Object[] args = proceedingJoinPoint.getArgs();
            for (int i = 0; i < paramNames.length; i++) {
                result.put(paramNames[i], args[i]);
            }
        }
        return result;
    }

}
