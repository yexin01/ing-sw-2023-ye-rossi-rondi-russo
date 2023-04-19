package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.MessageFromClient;

public interface Controller {
    void receiveMessageFromClient(MessageFromClient message) throws Exception;
}
