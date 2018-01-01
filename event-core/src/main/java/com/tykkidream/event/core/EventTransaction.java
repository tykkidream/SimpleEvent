package com.tykkidream.event.core;

/**
 * Created by Tykkidream on 2018/1/1.
 */
public class EventTransaction {
    private String type;
    private String id;

    public EventTransaction(String type, String id) {
        setType(type);
        setId(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
