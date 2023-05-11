package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessagePayload implements Serializable {
    private String content;

    public MessagePayload(String content){
        this.content = content;
    }

    public String getContent() {
        if(content == null)
            return "";
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
