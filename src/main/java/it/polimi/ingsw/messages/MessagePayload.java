package it.polimi.ingsw.messages;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessagePayload implements Serializable {
    private Map<KeyPayload, Object> data;
    public MessagePayload() {
        this.data = new HashMap<>();
    }

    public void put(KeyPayload key, Object value) {
        data.put(key, value);
    }

    public Object get(KeyPayload key) {
        return data.get(key);
    }


}
