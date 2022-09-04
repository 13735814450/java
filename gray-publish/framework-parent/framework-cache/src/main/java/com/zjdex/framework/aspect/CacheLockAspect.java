package com.zjdex.framework.aspect;

import com.zjdex.framework.annotation.CacheLock;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.service.RedissonService;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.data.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wxh
 * @date 2019/10/9
 * @description 分布式锁切面
 */
@Aspect
@Component
public class CacheLockAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheLockAspect.class);

    private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private RedissonService redissonService;

    @Around("@annotation(cacheLock)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, CacheLock cacheLock) throws Throwable {
        /*
        1: 增加空参数的分布式锁控制
        2：prefix必须，保证锁key
        3: value为空情况，则方法进行锁
         */
        StringBuffer localKey = new StringBuffer();
        //类名+方法名
        localKey.append(proceedingJoinPoint.getSignature());
        //加前缀
        if(!StringUtils.isEmpty(cacheLock.prefix())){
            localKey.append(":").append(cacheLock.prefix());
        }

        if (!StringUtils.isEmpty(cacheLock.value())) {
            //加参数
            Map<String, Object> parametersMap = new HashMap<String, Object>(16);
            getParameters(proceedingJoinPoint, parametersMap);
            if (!parametersMap.isEmpty()) {
                String param = getLockKey(cacheLock.value(),parametersMap);
                if(!StringUtils.isEmpty(param)){
                    localKey.append(":").append(param);
                }
            }
        }

        Long expireTime = cacheLock.expireTime();
        Long timeout = cacheLock.timeout();
        LOGGER.info("localKey:" + localKey);
        boolean result = redissonService.tryLock(localKey.toString(), TimeUnit.MILLISECONDS, timeout,
                expireTime);
        if(!result){
            throw new CodeException(ResultCode.Codes.SUBMIT_REPEAT);
        }
        Object response = null;
        try {
            response = proceedingJoinPoint.proceed();
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            redissonService.unlock(localKey.toString());
        }
        return response;
    }

    /**
     * 获取分布式锁key
     * @param spelKey spel表达式
     * @param parametersMap
     * @return
     */
    private String getLockKey(String spelKey, Map<String, Object> parametersMap){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(parametersMap);
        Object spelValue = parser.parseExpression(spelKey).getValue(context, Object.class);

        String locakKey = spelValue.toString();
        return locakKey;
    }

    public static void getParameters(ProceedingJoinPoint joinPoint, Map<String, Object> map) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] paramNames = DISCOVERER.getParameterNames(method);
        if (paramNames != null && paramNames.length != 0) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < paramNames.length; i++) {
                map.put(paramNames[i], args[i]);
            }
        }
    }
}
