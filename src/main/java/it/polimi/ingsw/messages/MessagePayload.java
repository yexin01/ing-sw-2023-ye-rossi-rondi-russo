package it.polimi.ingsw.messages;

//TODO change PAYLOAD


import it.polimi.ingsw.server.listener.EventType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessagePayload implements Serializable {
    private Map<PayloadKeyServer, Object> data;
    private final EventType event;
    public MessagePayload(EventType event) {
        this.event = event;
        this.data = new HashMap<>();
    }

    public void put(PayloadKeyServer key, Object value) {
        data.put(key, value);
    }

    public Object get(PayloadKeyServer key) {
        return data.get(key);
    }

    public Map<PayloadKeyServer, Object> getAll() {
        return data;
    }

    public EventType getEvent() {
        return event;
    }
}


/*
public class MessagePayload<T> {
    private final T data;

    public MessagePayload(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

 */


/*
public class MessagePayload {
    private final Object object;

    public MessagePayload(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}

 */