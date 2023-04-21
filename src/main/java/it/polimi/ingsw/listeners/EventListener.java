package it.polimi.ingsw.listeners;

public interface EventListener {

    void onEvent(EventType eventType, Object newValue,String nickname);
}
