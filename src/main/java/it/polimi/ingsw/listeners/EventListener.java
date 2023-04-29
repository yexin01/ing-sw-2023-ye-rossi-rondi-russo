package it.polimi.ingsw.listeners;


import it.polimi.ingsw.messages.EventType;

public interface EventListener {
    public void fireEvent(EventType event, String playerNickname, Object newValue);

}