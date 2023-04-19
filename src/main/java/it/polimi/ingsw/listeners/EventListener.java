package it.polimi.ingsw.listeners;

public interface EventListener {
    void onEvent(String eventName, Object newValue,String nickname);
}
