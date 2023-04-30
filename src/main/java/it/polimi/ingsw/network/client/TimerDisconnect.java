package it.polimi.ingsw.network.client;

import java.util.TimerTask;

/**
 * this class is called after tot seconds of inactivity and disconnects the client
 */
public class TimerDisconnect extends TimerTask {

    private DisconnectionListener disconnectionListener;

    TimerDisconnect(DisconnectionListener disconnectionListener) {
        super();
        this.disconnectionListener = disconnectionListener;
    }

    @Override
    public void run() {
        disconnectionListener.onDisconnection();
    }
}