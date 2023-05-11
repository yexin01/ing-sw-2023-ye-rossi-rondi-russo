package it.polimi.ingsw.message;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessagePayload implements Serializable {

    private Map<KeyAbstractPayload, Object> data;

    public MessagePayload() {
        this.data = new HashMap<>();
    }

    public void put(KeyAbstractPayload key, Object value) {
        data.put(key, value);
    }

    public Object getContent(KeyAbstractPayload key) {
        return data.get(key);
    }


}
