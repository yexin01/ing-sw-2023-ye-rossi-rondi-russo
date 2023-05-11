package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer2;
import it.polimi.ingsw.messages.MessagePayload2;
import it.polimi.ingsw.messages.ServerMessageHeader2;

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
        ServerMessageHeader2 header = new ServerMessageHeader2(EventType.DISCONNECT_REQUEST, client.getNickname());
        MessagePayload2 payload = new MessagePayload2("Ping not received -> Requesting disconnection...");
        MessageFromServer2 message = new MessageFromServer2(header, payload);
        client.messageQueue.add(message);
    }

}