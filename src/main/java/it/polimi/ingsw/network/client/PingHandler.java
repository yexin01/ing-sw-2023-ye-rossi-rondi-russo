package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;

import java.util.TimerTask;

/**
 * Class that handles the ping of the client to check if the connection is still alive or not, it extends TimerTask to be used as a timer task
 * when the client is pinged by the server and the client does not respond in time
 * the connection is closed and the client is disconnected from the server side and the thread is interrupted
 */
public class PingHandler extends TimerTask {

    private Client client;

    /**
     * Constructor of the class PingHandler that creates a new ping handler
     * @param client the client that has to be pinged
     */
    PingHandler(Client client) {
        super();
        this.client = client;
    }

    /**
     * the run method of the class PingHandler that sends a message to the client if the ping is not received in time
     * the message is added to the queue of messages and the client handler thread handles the disconnection of the client from the server
     */
    @Override
    public void run() {
        MessageHeader header = new MessageHeader(MessageType.ERROR, client.getNickname());
        MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
        payload.put(Data.ERROR, ErrorType.PING_NOT_RECEIVED);
        Message message = new Message(header, payload);

        client.messageQueue.add(message);
    }

}