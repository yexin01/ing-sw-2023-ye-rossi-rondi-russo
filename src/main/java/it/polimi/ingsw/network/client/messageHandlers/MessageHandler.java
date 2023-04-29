package it.polimi.ingsw.network.client.messageHandlers;


import it.polimi.ingsw.network.client.ClientListener;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientView;

public abstract class MessageHandler{

    private final ClientSocket connection;
    private final ClientListener clientListener;
    private ClientView clientView;

    protected MessageHandler(ClientSocket connection, ClientListener clientListener, ClientView clientView) {
        this.connection = connection;
        this.clientListener = clientListener;
    }


    public ClientSocket getConnection() {
        return connection;
    }


    public ClientListener getClientListener() {
        return clientListener;
    }


    public ClientView getClientView() {
        return clientView;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    abstract void handleMessage(MessageFromServer message) throws Exception;
}
