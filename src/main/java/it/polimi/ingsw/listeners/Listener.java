package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.server.SendMessages;

public class Listener extends EventListener{

    public Listener(SendMessages sendMessage) {
        super(sendMessage);
    }

    @Override
    public void onEvent(Object messagePayload) {
        sendMessage.sendAll((MessagePayload)messagePayload,MessageFromServerType.DATA);
    }


}
