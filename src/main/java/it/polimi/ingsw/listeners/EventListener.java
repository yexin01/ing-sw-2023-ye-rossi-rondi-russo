package it.polimi.ingsw.listeners;


import it.polimi.ingsw.exceptions.Error;
/*
public interface EventListener<T> { // Aggiungi il parametro generico T
    void onEvent(EventType eventType, EventValue<T> eventValue, String nickname) ;
}

 */


public interface EventListener {
    void onEvent(EventType eventType, Object newValue,String nickname);
}


