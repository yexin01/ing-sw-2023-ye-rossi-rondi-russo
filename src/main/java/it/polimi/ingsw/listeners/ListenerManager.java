package it.polimi.ingsw.listeners;



import it.polimi.ingsw.messages.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {
    private HashMap<EventType, List<EventListener>> listenersMap;

    public ListenerManager() {
        listenersMap = new HashMap<EventType, List<EventListener>>();
    }

    public void addListener(EventType eventName, EventListener listener) {
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

    public void removeListener(EventType eventName, EventListener listener) {
        if (listenersMap.containsKey(eventName)) {
            List<EventListener> listeners = listenersMap.get(eventName);
            listeners.remove(listener);
        }
    }
    public void fireEvent(EventType eventName, String playerNickname, Object newValue) {
        if (listenersMap.containsKey(eventName)) {
            List<EventListener> listeners = listenersMap.get(eventName);
            for (EventListener listener : listeners) {
                listener.fireEvent(eventName,playerNickname,newValue);
            }
        }
    }

}
