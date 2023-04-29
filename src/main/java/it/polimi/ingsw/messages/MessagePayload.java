package it.polimi.ingsw.messages;

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

