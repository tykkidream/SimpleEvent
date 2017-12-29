package com.tykkidream.event;

import java.util.List;

public interface WorkUnit {
    void publishEvent(Event event);

    List<Event> commit();

    void roolback();
}
