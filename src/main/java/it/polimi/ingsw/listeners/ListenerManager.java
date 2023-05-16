package it.polimi.ingsw.listeners;



import it.polimi.ingsw.message.KeyAbstractPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {
    private HashMap<KeyAbstractPayload, List<EventListener>> listenersMap;



    public ListenerManager() {
        listenersMap = new HashMap<KeyAbstractPayload, List<EventListener>>();
    }

    public void addListener(KeyAbstractPayload eventName, EventListener listener) {
        if (listenersMap.containsKey(eventName)) {
            List<EventListener> listeners = listenersMap.get(eventName);
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } else {
            List<EventListener> listeners = new ArrayList<EventListener>();
            listeners.add(listener);
            listenersMap.put(eventName, listeners);
        }
    }

    public void removeListener(KeyAbstractPayload eventName, EventListener listener) {
        if (listenersMap.containsKey(eventName)) {
            List<EventListener> listeners = listenersMap.get(eventName);
            listeners.remove(listener);
        }
    }
    public void fireEvent(KeyAbstractPayload eventName, String playerNickname, Object newValue) {
        if (listenersMap.containsKey(eventName)) {
            List<EventListener> listeners = listenersMap.get(eventName);
            for (EventListener listener : listeners) {
                try {
                    listener.fireEvent(eventName,playerNickname,newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
