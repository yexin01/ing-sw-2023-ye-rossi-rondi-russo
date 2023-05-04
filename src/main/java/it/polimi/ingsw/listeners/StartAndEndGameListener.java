package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;

public class StartAndEndGameListener extends EventListener{
    public StartAndEndGameListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        switch(event){
            case START_GAME ->{
                getGameLobby().setModelView((ModelView) newValue);
                //TODO decidere se fare funzione o no
                //getServerView().sendInfo(null);
            }
            case END_GAME ->{
                //TODO in base a come implementiamo la CLI e la GUI vediamo cosa inserire nel messaggio di endGame

            }
        }

    }
}
