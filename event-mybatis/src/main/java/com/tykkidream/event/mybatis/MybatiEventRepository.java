package com.tykkidream.event.mybatis;

import com.tykkidream.event.core.Event;
import com.tykkidream.event.core.EventRepository;
import com.tykkidream.event.core.EventTransaction;

import java.util.List;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class MybatiEventRepository implements EventRepository {
    @Override
    public void save(EventTransaction transaction, List<Event> events) {

    }
}
