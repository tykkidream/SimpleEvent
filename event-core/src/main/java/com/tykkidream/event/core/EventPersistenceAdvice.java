package com.tykkidream.event.core;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class EventPersistenceAdvice {
    private static Logger logger = LoggerFactory.getLogger(EventPersistenceAdvice.class);

    @Resource
    private EventRepository eventRepository;

    public Object watchPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String transactionId = null;
        WorkUnit unitOfWork = null;
        Object returnValue;

        try{
            unitOfWork = ThreadWorkUnit.getOrCreateThreadWorkUnit();
            unitOfWork.enable();
        } catch (Throwable throwable){
            logger.error(throwable.getMessage(), throwable);
        }

        try {
            returnValue = joinPoint.proceed();
        } catch (Throwable throwable) {
            unitOfWork.rollback();
            throw throwable;
        }

        try {
            if (unitOfWork.isEnable()){
                List<Event> events = unitOfWork.commit();
                logger.info("方法 {} 发布的所有事件：{}", joinPoint.getSignature().getName(), JSON.toJSONString(events));
                eventRepository.save(transactionId, events);
            }
        } catch (Throwable throwable) {
            unitOfWork.rollback();
            logger.error(throwable.getMessage(), throwable);
        }

        return returnValue;

    }
}
