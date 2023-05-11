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

    public void put(KeyAbstractPayload key, Object value) {
        data.put(key, value);
    }


    public Object getContent(KeyAbstractPayload key) {
        return data.get(key);
    }


}

/*
    error in header
    nella mappa p.put(ErrorType.qualcosa,ErrorType.getErrorMessage())

    connection in header
    connection_response in keyPayload
 */