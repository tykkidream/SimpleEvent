package com.tykkidream.event.core;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class EventPublicationAdvice {
    private static Logger logger = LoggerFactory.getLogger(EventPublicationAdvice.class);

    @Resource
    private WorkUnitManager unitOfWorkManager;

    public Object watchPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();

        try {
            List<Event> events = null;
            logger.info("方法 {} 发布的所有事件：{}", joinPoint.getSignature().getName(), JSON.toJSONString(events));
            unitOfWorkManager.publishEvent(events);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }

        return returnValue;

    }

    public void setWorkUnitManager(WorkUnitManager unitOfWorkManager) {
        this.unitOfWorkManager = unitOfWorkManager;
    }
}
