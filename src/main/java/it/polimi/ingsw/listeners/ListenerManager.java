package it.polimi.ingsw.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerManager {
    private HashMap<String, List<Object>> listenersMap;

    public ListenerManager() {
        listenersMap = new HashMap<String, List<Object>>();
    }

    public void addListener(String eventName, Object listener) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } else {
            List<Object> listeners = new ArrayList<Object>();
            listeners.add(listener);
            listenersMap.put(eventName, listeners);
        }
    }

    public void removeListener(String eventName, Object listener) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            listeners.remove(listener);
        }
    }
    public void fireEvent(String eventName, Object newValue, String playerNickname) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            for (Object listener : listeners) {
                if (listener instanceof EventListener) {
                    ((EventListener) listener).onEvent(eventName, newValue, playerNickname);
                }
            }
        }
    }

}
