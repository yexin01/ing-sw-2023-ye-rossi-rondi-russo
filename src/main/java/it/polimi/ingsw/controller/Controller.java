package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private PlayerController playerController;
    private BoardController boardController;
    private BookshelfController bookshelfController;

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String nickname) {
            if (!playerController.insertNickname(nickname)) {
                try {
                    inizializeGame();
                    //   playerController.getGame().getPlayers().get(0).getBookshelf().computeFreeShelves();
                    //   playerController.getGame().getPlayers().get(0).getBookshelf().maxFreeShelves();
                    //  playerController.getGame().getPlayers().get(0).getBookshelf().computeFreeShelves();
                    //  playerController.getBookshelfControllers().get(0).computeFreeShelves();
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

    public Controller(PlayerController playerController, BoardController boardController) {
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
        if (number == -1) {
            resetPlayerChoice();
            boardController.getBoard().setFinishPlayeropposite();
        }
        if(number==-2){
            System.out.println("Hai selezionato le tessere : freeshelves, inserisce la colonna");
            boardController.getBoard().setPlayerChoiceColumn(-2);
            playerController.getGame().getTurnPlayer().getBookshelf().computeFreeShelves();
            playerController.getGame().getTurnPlayer().getBookshelf().printFreeShelves();
            playerController.getGame().getTurnPlayer().setSelectedItems(boardController.selected());
            boardController.getBoard().setFinishPlayeropposite();
        }
        if(boardController.getBoard().getPlayerChoiceColumn()==-2){
            if(playerController.getGame().getTurnPlayer().getSelectedItems().size()<=playerController.getGame().getTurnPlayer().getBookshelf().getMaxTilesColumn(number)){
                System.out.println("le inserisce");
                playerController.getGame().getTurnPlayer().insert(number);
                System.out.println("Ha inserito");
                //Inserisce nella bookshelf
                //calcola punteggio...
                //Chiama il metodo bookshelf piena
                playerController.setNextPlayer(playerController.getGame().getTurnPlayer());
                if (boardController.checkRefill()) {
                    boardController.refill();
                }
                resetPlayerChoice();
                boardController.getBoard().setPlayerChoiceColumn(-1);
                boardController.getBoard().setFinishPlayeropposite();
            } else {
                System.out.println("Hai inserito in una colonna con un minore numero di tessere libere");
                boardController.getBoard().setFinishPlayeropposite();
            }
        }
        if (boardController.getBoard().getPlayerChoiceX() == -1) {
            boardController.getBoard().setPlayerChoiceX(number);
            boardController.getBoard().setFinishPlayeropposite();

        } else if (boardController.getBoard().getPlayerChoiceY() == -1) {
            boardController.getBoard().setPlayerChoiceY(number);
            boardController.getBoard().setFinishPlayeropposite();

        } else {
            boardController.getBoard().setPlayerChoicenumTile(number);
            boardController.isSelectable(boardController.getBoard().getBoardBox(boardController.getBoard().getPlayerChoiceX(), boardController.getBoard().getPlayerChoiceY()), boardController.getBoard().getPlayerChoicenumTile());
            resetPlayerChoice();
            boardController.getBoard().setFinishPlayeropposite();
        }
    }
    private void resetPlayerChoice(){
        boardController.getBoard().setPlayerChoiceX(-1);
        boardController.getBoard().setPlayerChoiceY(-1);
        boardController.getBoard().setPlayerChoicenumTile(-1);
        //boardController.getBoard().setPlayerChoiceColumn(-1);
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