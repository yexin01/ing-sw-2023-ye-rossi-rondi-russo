package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.networkmessages.NetworkMessage;

/**
 * This interface is used to notify the implementer of an incoming message
 */
public interface ClientUpdateListener {
    void onUpdate(NetworkMessage message);
}
