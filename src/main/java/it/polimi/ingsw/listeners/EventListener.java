package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.server.SendMessages;

public abstract class EventListener {

    public final SendMessages sendMessage;

    protected EventListener(SendMessages sendMessage) {
        this.sendMessage = sendMessage;
    }

    public abstract void onEvent(MessagePayload messagePayload);
}
