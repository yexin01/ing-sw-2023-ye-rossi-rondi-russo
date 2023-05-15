package it.polimi.ingsw.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the payload of a message sent between client and server
 */
public class MessagePayload implements Serializable {
    private KeyAbstractPayload key;
    private Map<Data,Object> data; // for example for error message i can put the error type as the only object

    /**
     * Constructor of the class MessagePayload that creates a new message payload without data
     */
    public MessagePayload() {
        this.data = new HashMap<>();
    }

    /**
     * Constructor of the class MessagePayload that creates a new message payload with data
     * @param key the key of the payload
     */
    public MessagePayload( KeyAbstractPayload key) {
        this.data = new HashMap<>();
        this.key=key;
    }

    /**
     * Method that adds a new data to the payload
     * @param key the key of the data
     * @param value the value of the data
     */
    public void put(Data key, Object value) {
        data.put(key, value);
    }

    /**
     * @return the key of the payload
     */
    public KeyAbstractPayload getKey() {
        return key;
    }

    /**
     * @return the data of the payload
     */
    public Map<Data, Object> getData() {
        return data;
    }

    /**
     * Method that returns the data of the payload given the key
     * @param key the key of the data
     * @return the data of the payload given the key
     */
    public Object getContent(Data key) {
        return data.get(key);
    }

}
