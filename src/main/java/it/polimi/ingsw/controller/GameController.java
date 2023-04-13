package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.AcknowledgementDispatcher;
import it.polimi.ingsw.listeners.TurnListener;
import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.ServerView;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class GameController implements PropertyChangeListener {
    private PlayerController playerController;

    private SetupController setupController;
    private TurnController turnController;

    private Game game;
    private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

    public GameController() throws Exception {
        initializeControllers();
    }
    public Game getModel() {
        return game;
    }

    public void initializeControllers() throws Exception {
        GameRules gameRules=new GameRules();
        int maxPlayers=gameRules.getMaxPlayers();
        playerController=new PlayerController(turnController,this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //TODO add  when the network part is finished

        MessageFromClient message = (MessageFromClient) evt.getNewValue();
        String nicknamePlayer = message.getClientMessageHeader().getNicknameSender();
        String messageName = message.getClientMessageHeader().getMessageName();


        if (!nicknamePlayer.equals(turnController.getTurnPlayer().getNickname())) {
            listeners.firePropertyChange(new PropertyChangeEvent(nicknamePlayer, "Error",
                    ErrorMessageType.ILLEGAL_TURN, "This isn't your turn."));
            return;
        }
        if (messageName.equals("EndTurn")) {
            boolean isTurnEnded = turnController.checkIfTurnIsEnded();
            if(!isTurnEnded){
                listeners.firePropertyChange(new PropertyChangeEvent(nicknamePlayer, "Error",
                        ErrorMessageType.TURN_NOT_FINISHED, "You can't end your turn now."));
                return;
            }
            if(turnController.endgame()&& game.getTurnPlayer().equals(game.getLastPlayer())){
                game.endGame();
                return;
            }
            turnController.changePhase();
            PropertyChangeEvent event = new PropertyChangeEvent(nicknamePlayer, "EndTurn", messageName, null);
            listeners.firePropertyChange(event);
        }else{
            try {
                playerController.turnPhase(message);
                PropertyChangeEvent event =
                        new PropertyChangeEvent(nicknamePlayer, "TurnPhase", messageName, turnController.getCurrentPhase());
                listeners.firePropertyChange(event);
            } catch (IllegalArgumentException ignored) {}
        }

    }
    //TODO add listeners SetupController
    public void createListeners(List<ServerView> views, GameLobby lobby) {
        Error error = new Error((Throwable) views);
        AcknowledgementDispatcher ackDispatcher = new AcknowledgementDispatcher(views, lobby);
        listeners.addPropertyChangeListener("Error", (PropertyChangeListener) error);
        listeners.addPropertyChangeListener("TurnPhase", ackDispatcher);
        game.createListeners(views, lobby);
        for(ServerView view: views) {
            TurnListener turnListener = new TurnListener(view);
            listeners.addPropertyChangeListener("EndTurn", turnListener);
            listeners.addPropertyChangeListener("EndPhase", turnListener);
        }
    }

    public SetupController getSetupController() {
        return setupController;
    }

    public void setSetupController(SetupController setupController) {
        this.setupController = setupController;
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }
}
