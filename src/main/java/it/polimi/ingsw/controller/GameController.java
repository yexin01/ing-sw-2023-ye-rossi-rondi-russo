package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.model.Game;


public class GameController implements Controller{
    private PlayerController playerController;
    /*
        private SetupController setupController;

     */
    private TurnController turnController;

    private Game game;
    //private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

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

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    @Override
    public void receiveMessageFromClient(MessageFromClient message) throws Exception {
        String nicknamePlayer = message.getClientMessageHeader().getNicknameSender();
        String messageName = message.getClientMessageHeader().getMessageName();
        try{
            if (!nicknamePlayer.equals(turnController.getTurnPlayer().getNickname())) {
                throw new Error(ErrorType.ILLEGAL_TURN);
            }
            playerController.turnPhase(message);

        }catch(Exception e){

        }


    }

}
