package com.tykkidream.event;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class WorkUnitAdvice {
    @Resource
    private WorkUnitManager unitOfWorkManager;

    private static Logger logger = LoggerFactory.getLogger(WorkUnitAdvice.class);

    public Object watchPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
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
                unitOfWorkManager.publishEvent(events);
            }
        } catch (Throwable throwable) {
            unitOfWork.rollback();
            logger.error(throwable.getMessage(), throwable);
        }

        return returnValue;

    }

    public void setWorkUnitManager(WorkUnitManager unitOfWorkManager) {
        this.unitOfWorkManager = unitOfWorkManager;
    }
}
