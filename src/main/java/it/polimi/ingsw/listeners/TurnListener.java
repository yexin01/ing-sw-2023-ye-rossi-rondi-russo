package it.polimi.ingsw.listeners;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnListener implements PropertyChangeListener {
    //TODO change
    //private final ServerView view;


    //public TurnListener(ServerView view) {
    //    this.view = view;
    //}


    public void endTurn(String nickname, String turnStarter) {
        System.out.println(nickname +" END TURN ");
        //MessagePayload payload = new MessagePayload();
        //payload.setAttribute("TurnEnder", turnEnder);
       // payload.setAttribute("TurnStarter", turnStarter);
       // view.sendMessage(payload, "EndTurn", ServerMessageType.GAME_UPDATE);
    }





    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "EndTurn" -> endTurn((String) evt.getOldValue(), (String) evt.getSource());
        }
    }

}
