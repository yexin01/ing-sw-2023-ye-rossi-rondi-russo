package it.polimi.ingsw.listeners;

import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.message.KeyDataPayload;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.ClientView;

public class StartAndEndGameListener extends EventListener{
    public StartAndEndGameListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) {
        switch((KeyDataPayload)event){
            case START_GAME ->{
                getGameLobby().setModelView((ModelView) newValue);
                ClientView clientView=new ClientView();
                CLI cl=new CLI();
                clientView.setBoardView(((ModelView) newValue).getBoardView());
                clientView.setBookshelfView(((ModelView) newValue).getBookshelfView("TIZIO"));
                clientView.setPlayerPersonalGoal(((ModelView) newValue).getPlayerPersonalGoal("TIZIO"));
                cl.setClientView(clientView);
                try {
                    cl.askCoordinates();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                //TODO decidere se fare funzione o no
                //getServerView().sendInfo(null);
            }
            case END_GAME ->{
                //TODO in base a come implementiamo la CLI e la GUI vediamo cosa inserire nel messaggio di endGame

            }
        }

    }
}


