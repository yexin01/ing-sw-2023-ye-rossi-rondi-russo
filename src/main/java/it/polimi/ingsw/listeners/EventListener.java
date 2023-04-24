package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;

public interface EventListener {
    public void fireEvent(EventType event,String playerNickname,Object newValue);

}