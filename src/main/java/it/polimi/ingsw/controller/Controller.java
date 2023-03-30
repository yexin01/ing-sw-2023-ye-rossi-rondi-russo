package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    private GameandPlayerController gameandPlayerController;
    private BoardController boardController;

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String nickname) {
            if (!gameandPlayerController.insertNickname(nickname)) {
                try {
                    inizializeGame();
                    gameBoard().printMatrix();
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else gameandPlayerController.getGame().setFinishopposite();
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

    public Controller(Game game) {
        this.gameandPlayerController=new GameandPlayerController(game);
        this.boardController=new BoardController(game.getBoard());
    }

    public GameandPlayerController getGameandPlayerController() {
        return gameandPlayerController;
    }

    public void setGameandPlayerController(GameandPlayerController gameandPlayerController) {
        this.gameandPlayerController = gameandPlayerController;
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    public void inizializeGame() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        boardController.fillBag(gameandPlayerController.getGame().getNumPlayers());
        gameandPlayerController.firstPlayer();
        gameandPlayerController.createCommonGoalCard();
    }

    private void turnPlayer(int number) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(gameBoard().isEndGame()&&firstPlayer().getNickname().equals(turnPlayer().getNickname())){
            System.out.println("FINE PARTITA");
            //TODO add methods to reset all
            //resettare
            //inizializeGame();
            gameandPlayerController.getGame().setFinishopposite();
        }
        if (number == -1) {
            resetPlayerChoice();
            changeFlag();
        }
        if(number==-2){
            if(gameBoard().getSelectedBoard().size()!=0){
                System.out.println("You selected "+gameBoard().getSelectedBoard().size()+" tiles");
                System.out.println("These are the free cells for each column of your bookshelf enter a number from 0 to 4 to insert them into");
                gameBoard().setFinishPlayerChoice(-2);
                turnPlayer().getBookshelf().computeFreeShelves();
                turnBookshelf().printFreeShelves();
                turnPlayer().setSelectedItems(boardController.selected());
            }else{
                System.err.println("You have not selected tiles");
                resetPlayerChoice();
            }
         changeFlag();
        }

        if(boardController.getBoard().getFinishPlayerChoice()==-2){
            try{
                //TODO ricontrollare l'eccezione cambiando il valore base di x e y senno inserendo -1 il programma va avanti
                if (number < 0 || number >= turnBookshelf().getMatrix().length-1) {
                    throw new IllegalArgumentException("Column value must be between 0 and " + (turnBookshelf().getMatrix()[0].length - 1));
                }
                if(gameandPlayerController.insertBookshelf(number)){
                    System.out.println("The tiles have been inserted");
                    //TODO AGGIUNGERE L'AGGIORNAMENTO DEI PUNTEGGI
                    gameandPlayerController.setNextPlayer(turnPlayer());
                    if (boardController.checkRefill()) {
                        boardController.refill();
                    }
                    if(turnBookshelf().isFull()){
                        gameBoard().setEndGame(true);
                    }
                    resetPlayerChoice();
                } else {
                    System.err.println("You have inserted in a column with a number of free cells lower than those that can be inserted, insert another column");
                }

            }catch (IllegalArgumentException e) {
                System.err.println("Invalid column value: " + e.getMessage());
            }
            changeFlag();

        }
        if (gameBoard().getPlayerChoiceX() == -1) {
            gameBoard().setPlayerChoiceX(number);
            changeFlag();
            System.out.println("You inserted the row now insert the column");
        } else {
            gameBoard().setPlayerChoiceY(number);
            if (gameBoard().getSelectedBoard() != null && gameBoard().getSelectedBoard().size() > turnBookshelf().numSelectableTiles()) {
                System.out.println("You have selected a greater number of tiles that you can select, if you want to reset the choice enter -1");
                System.out.println("If you want to confirm your choice except for the last tile, enter -2");
                resetPlayerChoice();
            }else if (!boardController.checkSelectable(gameBoard().getBoardBox(gameBoard().getPlayerChoiceX(), gameBoard().getPlayerChoiceY()))) {
                    System.out.println("The tile you selected is not a selectable tile select another one \nIf you want to reset the choice enter -1");
                    System.out.println("If you want to continue without this tile write the row of the next card");
                    System.out.println("If you want to confirm your choice except for the last tile, enter -2");
                }else{
                System.out.println("You selected the row tile: "+gameBoard().getPlayerChoiceX()+" column: "+gameBoard().getPlayerChoiceY() +"\nIf you want to select others write the line");
                System.out.println("If you have finished the choice write -2\nIf you want to change the tiles to select-1");
                }
            gameBoard().setPlayerChoiceX(-1);
            gameBoard().setPlayerChoiceY(-1);
            changeFlag();
        }

    }


    private void resetPlayerChoice(){
        gameBoard().setPlayerChoiceX(-1);
        gameBoard().setPlayerChoiceX(-1);
        gameBoard().setFinishPlayerChoice(-1);
        gameBoard().setSelectedBoard(new ArrayList<>());
        gameBoard().printMatrix();
    }


    public Player turnPlayer(){return gameandPlayerController.getGame().getTurnPlayer();}

    public Player firstPlayer(){return gameandPlayerController.getGame().getFirstPlayer();}

    public Bookshelf turnBookshelf(){return gameandPlayerController.getGame().getTurnPlayer().getBookshelf();}

    public Board gameBoard(){return boardController.getBoard();}

    public void changeFlag(){boardController.getBoard().setFinishPlayeropposite();}
}