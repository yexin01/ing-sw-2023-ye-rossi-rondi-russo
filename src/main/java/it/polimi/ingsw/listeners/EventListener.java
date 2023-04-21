package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.SendMessages;

public abstract class EventListener {

    public final SendMessages sendMessage;

    protected EventListener(SendMessages sendMessage) {
        this.sendMessage = sendMessage;
    }

    public abstract void onEvent(EventType eventType, Object newValue,String nickname);
}
