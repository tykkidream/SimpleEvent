package com.tykkidream.event.core;

import java.util.List;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public interface EventRepository {
    void save(EventTransaction transaction, List<Event> events);
}
