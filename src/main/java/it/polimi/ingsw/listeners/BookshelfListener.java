package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;

public class BookshelfListener implements EventListener{

   /*
   @Override
   public void onEvent(EventType eventType, EventValue eventValue, String nickname) {
        Bookshelf newBookshelf=(Bookshelf) eventValue.getValue();
        System.out.println(nickname +" changed Bookshelf");
        newBookshelf.printBookshelf();
    }

    */

    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        Bookshelf newBookshelf=(Bookshelf) newValue;
        System.out.println(nickname +" changed Bookshelf");
        newBookshelf.printBookshelf();
    }
    /*
    @Override
    public void onEvent(String eventName, Object newValue, String nickname) {
        Bookshelf newBookshelf=(Bookshelf) newValue;
        System.out.println(nickname +" changed Bookshelf");
        newBookshelf.printBookshelf();

    }

     */

}
