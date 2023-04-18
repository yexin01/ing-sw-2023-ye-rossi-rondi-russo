package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.model.Game;



import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;


public class GameController implements PropertyChangeListener {
    private PlayerController playerController;
    /*
        private SetupController setupController;

     */
    private TurnController turnController;

    private Game game;
    private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

    public GameController(Game game) throws Exception {
        this.game=game;
        initializeControllers();
    }
    public Game getModel() {
        return game;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public void initializeControllers() throws Exception {
        GameRules gameRules=new GameRules();
        int maxPlayers=gameRules.getMaxPlayers();
        playerController=new PlayerController(this);
        turnController=new TurnController(game);
        turnController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
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
        }else{

            try {
                playerController.turnPhase(message);
            } catch (Exception e) {
            }
            PropertyChangeEvent event =
                    new PropertyChangeEvent(nicknamePlayer, "TurnPhase", messageName, turnController.getCurrentPhase());
            listeners.firePropertyChange(event);

        }

    }
    //TODO add listeners SetupController
   /* public void createListeners() {
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

    */


    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
