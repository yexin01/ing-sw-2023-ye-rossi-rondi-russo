package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessagePayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListenerManager {
    private HashMap<EventType, List<Object>> listenersMap;

    public ListenerManager() {
        listenersMap = new HashMap<EventType, List<Object>>();
    }

    public void addListener(EventType eventName, Object listener) {
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

    public void removeListener(EventType eventName, Object listener) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            listeners.remove(listener);
        }
    }
    public void fireEvent(EventType eventName, MessagePayload newValue, String playerNickname) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            for (Object listener : listeners) {
                if (listener instanceof EventListener) {
                    ((EventListener) listener).onEvent(newValue);
                }
            }
        }
    }

}

















/*
public class ListenerManager {
    private Map<EventType, Map<String, List<Object>>> listenersMap;

    public ListenerManager() {
        listenersMap = new HashMap<>();
        for (EventType eventType : EventType.values()) {
            listenersMap.put(eventType, new HashMap<>());
        }
    }

    public void addListener(EventType eventName, Object listener, String playerNickname) {
        Map<String, List<Object>> playerListenersMap = listenersMap.get(eventName);
        if (playerListenersMap.containsKey(playerNickname)) {
            List<Object> listeners = playerListenersMap.get(playerNickname);
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        } else {
            List<Object> listeners = new ArrayList<>();
            listeners.add(listener);
            playerListenersMap.put(playerNickname, listeners);
        }
    }

    public void removeListener(EventType eventName, Object listener, String playerNickname) {
        Map<String, List<Object>> playerListenersMap = listenersMap.get(eventName);
        if (playerListenersMap.containsKey(playerNickname)) {
            List<Object> listeners = playerListenersMap.get(playerNickname);
            listeners.remove(listener);
        }
    }

    public void fireEvent(EventType eventName, Object newValue, String playerNickname) {
        Map<String, List<Object>> playerListenersMap = listenersMap.get(eventName);
        if (playerListenersMap.containsKey(playerNickname)) {
            List<Object> listeners = playerListenersMap.get(playerNickname);
            for (Object listener : listeners) {
                if (listener instanceof EventListener) {
                    ((EventListener) listener).onEvent(eventName, newValue, playerNickname);
                }
            }
        }
    }

    public void fireOnlyPlayer(EventType eventName, Object newValue, String playerNickname) {
        fireEvent(eventName, newValue, playerNickname);
    }

    public void fireAllPlayer(EventType eventName, Object newValue,String nicknameSource) {
        Map<String, List<Object>> allPlayerListenersMap = listenersMap.get(eventName);
        for (String playerNickname : allPlayerListenersMap.keySet()) {
            fireEvent(eventName, newValue, playerNickname);
        }
    }
}

 */







/*
public class ListenerManager {
    private HashMap<Object, List<Object>> listenersMap;

    public ListenerManager() {
        listenersMap = new HashMap<Object, List<Object>>();
    }

    public void addListener(EventType eventName, Object listener) {
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

    public void removeListener(EventType eventName, Object listener) {
        if (listenersMap.containsKey(eventName)) {
            List<Object> listeners = listenersMap.get(eventName);
            listeners.remove(listener);
        }
    }
    public void fireEvent(EventType eventName, Object newValue, String playerNickname) {
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




/*
public class ListenerManager {
    private HashMap<EventType, List<EventListener<?>>> listenersMap; // Usa generics

    public ListenerManager() {
        listenersMap = new HashMap<>();
        for (EventType eventType : EventType.values()) {
            listenersMap.put(eventType, new ArrayList<>());
        }
    }

    public <T> void addListener(EventType eventType, EventListener<T> listener) { // Aggiungi il parametro generico T
        listenersMap.get(eventType).add(listener);
    }

    public <T> void removeListener(EventType eventType, EventListener<T> listener) { // Aggiungi il parametro generico T
        listenersMap.get(eventType).remove(listener);
    }

    public <T> void fireEvent(EventType eventType, T newValue, String playerName) { // Aggiungi il parametro generico T
        EventValue<T> eventValue = new EventValue<>(newValue); // Crea un oggetto EventValue con il valore generico
        List<EventListener<?>> listeners = listenersMap.get(eventType);
        for (EventListener<?> listener : listeners) {
            if (listener instanceof EventListener) {
                ((EventListener<T>) listener).onEvent(eventType, eventValue, playerName); // Usa il parametro generico T
            }
        }
    }
}

*/







/*
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

 */
