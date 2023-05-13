package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;

import java.util.TimerTask;

public class PingHandler extends TimerTask {

    private Client client;

    PingHandler(Client client) {
        super();
        this.client = client;
    }

    @Override
    public void run() {
        MessageHeader header = new MessageHeader(MessageType.ERROR, client.getNickname());
        MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
        payload.put(Data.ERROR, ErrorType.PING_NOT_RECEIVED);
        Message message = new Message(header, payload);

        client.messageQueue.add(message);

    }

}