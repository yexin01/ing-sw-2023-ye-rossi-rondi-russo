package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private PlayerController playerController;
    private BoardController boardController;

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

       if (arg instanceof Integer number){
           try {
               turnPlayer(number);
           } catch (InvocationTargetException e) {
               throw new RuntimeException(e);
           } catch (NoSuchMethodException e) {
               throw new RuntimeException(e);
           } catch (InstantiationException e) {
               throw new RuntimeException(e);
           } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
           }
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

    private void turnPlayer(int number) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(gameBoard().isEndGame()&&firstPlayer().getNickname().equals(turnPlayer().getNickname())){
            System.out.println("FINE PARTITA, ne inizia una nuova");
            //resettare
            //inizializeGame();

            playerController.getGame().setFinishopposite();
        }
        if (number == -1) {
            resetPlayerChoice();
            changeFlag();
        }
        if(number==-2){
            System.out.println("Hai selezionato le tessere : freeshelves, inserisce la colonna");
            gameBoard().setFinishPlayerChoice(-2);
            turnPlayer().getBookshelf().computeFreeShelves();
            turnBookshelf().printFreeShelves();
            turnPlayer().setSelectedItems(boardController.selected());
            changeFlag();
        }
        if(boardController.getBoard().getFinishPlayerChoice()==-2){
            if(playerController.insert(number)){
                System.out.println("le inserisce");
               //AGGIUNGERE L'AGGIORNAMENTO DEI PUNTEGGI
                playerController.setNextPlayer(turnPlayer());
                if (boardController.checkRefill()) {
                    boardController.refill();
                }
                if(turnBookshelf().isFull()){
                    gameBoard().setEndGame(true);
                }
                gameBoard().setFinishPlayerChoice(-1);
                resetPlayerChoice();
                changeFlag();
            } else {
                System.out.println("Hai inserito in una colonna con un minore numero di tessere libere");
                changeFlag();
            }
        }
        if (gameBoard().getPlayerChoiceX() == -1) {
            gameBoard().setPlayerChoiceX(number);
            changeFlag();

        } else if (gameBoard().getPlayerChoiceY() == -1) {
            gameBoard().setPlayerChoiceY(number);
            changeFlag();

        } else if (number< turnBookshelf().numSelectableTiles()) {
            gameBoard().setPlayerChoicenumTile(number);
            if(!boardController.isSelectable(gameBoard().getBoardBox(gameBoard().getPlayerChoiceX(), gameBoard().getPlayerChoiceY()), gameBoard().getPlayerChoicenumTile())){
                System.out.println("non é una tessera selezionabile");
            }
            resetPlayerChoice();
            gameBoard().printMatrix();
            changeFlag();
        }else{
            System.out.println("Hai selezionato un numero maggiore di tesser selezionabili");
            System.out.println("Digita -1 oppure scrivi direttamente le coordinate della nuova tessera");
            gameBoard().printMatrix();
            resetPlayerChoice();
            changeFlag();
        }
    }
    private void resetPlayerChoice(){
        gameBoard().setPlayerChoiceX(-1);
        gameBoard().setPlayerChoiceY(-1);
        gameBoard().setPlayerChoicenumTile(-1);
    }


    public Player turnPlayer(){return playerController.getGame().getTurnPlayer();}

    public Player firstPlayer(){return playerController.getGame().getFirstPlayer();}

    public Bookshelf turnBookshelf(){return playerController.getGame().getTurnPlayer().getBookshelf();}

    public Board gameBoard(){return boardController.getBoard();}

    public void changeFlag(){boardController.getBoard().setFinishPlayeropposite();}
}