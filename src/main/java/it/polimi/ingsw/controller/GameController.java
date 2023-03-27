package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer {
    private PlayerController playerController;
    private BoardController boardController;
    private BookshelfController bookshelfController;

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String nickname) {
            if (!playerController.insertNickname(nickname)) {
                try {
                    inizializeGame();
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else playerController.getGame().setFinishopposite();
        }
        if (arg instanceof Integer number) {
            turnPlayer(number);
        }
    }
    public GameController(PlayerController playerController, BoardController boardController) {
        this.boardController = boardController;
        this.playerController = playerController;
    }
    public PlayerController getPlayerController() {
        return playerController;
    }
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
    public BoardController getBoardController() {
        return boardController;
    }
    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }
    public void inizializeGame() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        boardController.fillBag(playerController.getGame().getNumPlayers());
        playerController.firstPlayer();
        playerController.createCommonGoalCard();
    }
    private void turnPlayer(int number) {
        if (boardController.getBoard().getPlayerChoiceX() == -1) {
            boardController.getBoard().setPlayerChoiceX(number);
            boardController.getBoard().setFinishPlayeropposite();
        } else if (boardController.getBoard().getPlayerChoiceY() == -1) {
            boardController.getBoard().setPlayerChoiceY(number);
            boardController.getBoard().setFinishPlayeropposite();
        } else {
            boardController.getBoard().setPlayerChoicenumTile(number);
            boardController.isSelectable(boardController.getBoard().getBoardBox(boardController.getBoard().getPlayerChoiceX(), boardController.getBoard().getPlayerChoiceY()), boardController.getBoard().getPlayerChoicenumTile());
            if (playerController.maxTileOrFinish() == boardController.getBoard().getPlayerChoicenumTile()) {
                playerController.getGame().getTurnPlayer().setSelectedItems(boardController.selected());
                //Inserisce nella bookshelf
                //calcola punteggio...
                //Chiama il metodo bookshelf piena
                playerController.setNextPlayer(playerController.getGame().getTurnPlayer());
                if (boardController.checkRefill()) {
                    boardController.refill();
                }

            }
        }
        resetPlayerChoice();
        boardController.getBoard().setFinishPlayeropposite();
    }
    private void resetPlayerChoice(){
        boardController.getBoard().setPlayerChoiceX(-1);
        boardController.getBoard().setPlayerChoiceY(-1);
        boardController.getBoard().setPlayerChoicenumTile(-1);
    }
    public void startGame() {


    }
    private void endGame(int number) {
        for(Player p:playerController.getGame().getPlayers()){
            if(p.getNickname().equals(playerController.getGame().getTurnPlayer())){
                turnPlayer(number);
                resetPlayerChoice();
                boardController.getBoard().setFinishPlayeropposite();
                playerController.setNextPlayer(playerController.getGame().getTurnPlayer());
            }
        }
    }
}