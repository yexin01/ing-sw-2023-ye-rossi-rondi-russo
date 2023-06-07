package it.polimi.ingsw.listeners;



import it.polimi.ingsw.message.KeyAbstractPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * The ListenerManager class manages event listeners for different events in the game.
 * It allows adding listeners for specific events and firing events to notify the listeners.
 */
public class ListenerManager {
    private HashMap<KeyAbstractPayload, List<EventListener>> listenersMap;

    /**
     * Constructor for the ListenerManager class.
     */
    public ListenerManager() {
        listenersMap = new HashMap<KeyAbstractPayload, List<EventListener>>();
    }
    /**
     * Adds an event listener for a specific event.
     * @param eventName The event name.
     * @param listener The event listener to add.
     */
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

    /**
     * Fires an event and notifies all registered listeners for the event.
     * @param eventName The event name.
     * @param playerNickname The player nickname associated with the event.
     * @param newValue The new value associated with the event.
     */
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
