package com.tykkidream.event.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class ThreadWorkUnit implements WorkUnit{

    private static final ThreadLocal<ThreadWorkUnit> threadLocal = new ThreadLocal<>();

    private List<Event> evnets = new ArrayList<>();

    private boolean isEnable = true;


    public static ThreadWorkUnit getOrCreateThreadWorkUnit(){
        ThreadWorkUnit threadUnitOfWork = threadLocal.get();
        if (threadUnitOfWork == null){
            threadUnitOfWork = new ThreadWorkUnit();
            threadLocal.set(threadUnitOfWork);
        }
        return threadUnitOfWork;
    }

    @Override
    public boolean isEnable() {
        return isEnable;
    }

    @Override
    public void enable() {
        isEnable = true;
    }

    @Override
    public void disable() {
        isEnable = false;
    }

    @Override
    public void publishEvent(Event event) {
        if (isEnable) {
            evnets.add(event);
        }
    }

    @Override
    public List<Event> commit() {
        List<Event> es = Collections.unmodifiableList(evnets);
        evnets = new ArrayList<>();
        return es;
    }

    @Override
    public void rollback() {
        evnets.clear();
    }
}
