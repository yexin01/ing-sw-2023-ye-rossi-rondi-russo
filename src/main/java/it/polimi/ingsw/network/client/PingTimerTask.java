package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageHeader;

import java.util.TimerTask;

public class PingTimerTask extends TimerTask {

    private Client client;

    PingTimerTask(Client client) {
        super();
        this.client = client;
    }
    //TODO QUESTO TENIAMO belo belo magari lo chiamiamo in una maniera diversa e aggiungiamo poi la visualizzazione sul client se il server non risponde
    @Override
    public void run() {
        ServerMessageHeader header = new ServerMessageHeader(EventType.DISCONNECT_REQUEST, client.getNickname());
        MessagePayload payload = new MessagePayload("Ping not received -> Requesting disconnection...");
        MessageFromServer message = new MessageFromServer(header, payload);
        client.messageQueue.add(message);
    }

}