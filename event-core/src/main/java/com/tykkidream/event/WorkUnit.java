package com.tykkidream.event;

import java.util.List;

public interface WorkUnit {
    boolean isEnable();

    void enable();

    void disable();

    void publishEvent(Event event);

    List<Event> commit();

    void rollback();
}
