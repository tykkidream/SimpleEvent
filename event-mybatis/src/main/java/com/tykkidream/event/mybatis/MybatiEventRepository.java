package com.tykkidream.event.mybatis;

import com.tykkidream.event.core.Event;
import com.tykkidream.event.core.EventRepository;

import java.util.List;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class MybatiEventRepository implements EventRepository {
    @Override
    public void save(String transactionId, List<Event> events) {

    }
}
