package it.polimi.ingsw.message;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessagePayload implements Serializable {

    private KeyAbstractPayload key; //Error_connection
    private Map<Data,Object> data; //ci metto un singolo oggetto con la errortype

    public MessagePayload() {
        this.data = new HashMap<>();
    }
    public MessagePayload( KeyAbstractPayload key) {
        this.data = new HashMap<>();
        this.key=key;
       /// data.put(keyAbstractPayload,null);
    }

    public void put(Data key, Object value) {
        data.put(key, value);
    }


    public Object getContent(Data key) {
        return data.get(key);
    }


    public KeyAbstractPayload getKey() {
        return key;
    }
}
//header:DATA   payload Start

/*
    error in header
    nella mappa p.put(ErrorType.qualcosa,ErrorType.getErrorMessage())

    connection in header
    connection_response in keyPayload
 */