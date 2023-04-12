package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.SetupPlayerView;
import it.polimi.ingsw.listeners.TurnListener;
import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.InitialSetup;
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
                //TODO change: notify only the player who won
                listeners.firePropertyChange(new PropertyChangeEvent(game.checkWinner(), "EndGame",
                        false, true));
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

    public void createListeners() {
        //TODO createListener gameController
        throw new IllegalArgumentException();
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
