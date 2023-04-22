package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.server.SendMessages;

public class Listener extends EventListener{

    protected Listener(SendMessages sendMessage) {
        super(sendMessage);
    }

    @Override
    public void onEvent(MessagePayload messagePayload) {
        sendMessage.sendAll(messagePayload,MessageFromServerType.DATA);
    }


}
