package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class MessagePayload implements Serializable {
    public abstract Object getObject();

}
