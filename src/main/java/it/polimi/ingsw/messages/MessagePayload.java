package it.polimi.ingsw.messages;

//TODO change PAYLOAD
public class MessagePayload {
    private final Object object;

    public MessagePayload(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}