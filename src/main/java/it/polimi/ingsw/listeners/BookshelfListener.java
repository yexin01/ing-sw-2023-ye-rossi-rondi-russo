package it.polimi.ingsw.listeners;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.model.Bookshelf;

import java.util.HashMap;
/*
public class BookshelfListener extends EventListener{
    public BookshelfListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }


/*
    public BookshelfListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }



    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        System.out.println(getClient(nickname).getNickname() +" changed Bookshelf");
        Bookshelf newBookshelf=(Bookshelf) newValue;
        newBookshelf.printBookshelf();
        sendMessage(nickname,newValue, MessageFromServerType.END_TURN);
    }


}
        */