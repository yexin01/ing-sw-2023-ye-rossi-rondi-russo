package it.polimi.ingsw.controller;
import it.polimi.ingsw.json.GameRules;
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
            try {
                //if it is false the game isn't already started
                if (!gameandPlayerController.insertNickname(nickname)) {
                    try {
                        initializeGame();
                        System.out.println("TURN PLAYER: "+turnPlayer().getNickname());
                        gameBoard().printMatrix();
                        System.out.println("\n");
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
            }catch (IllegalArgumentException e){
                System.err.println("Invalid name");
                gameandPlayerController.getGame().setFinishopposite();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
            } catch (Exception e) {
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

    public void initializeGame() throws Exception {
        GameRules gameRules =new GameRules();

        boardController.firstFill(gameandPlayerController.getGame().getNumPlayers(), gameRules);
        gameandPlayerController.firstPlayer();
        gameandPlayerController.createCommonGoalCard(gameRules);
        gameandPlayerController.createPersonalGoalCard(gameRules);

    }

    private void turnPlayer(int number) throws Exception {

        if(gameBoard().isEndGame()&&firstPlayer().getNickname().equals(turnPlayer().getNickname())){
            System.out.println("END GAME");
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
            finishChoicePlayer();
        }
        if(boardController.getBoard().getFinishPlayerChoice()==-2){
            insertInBookshelfAndPoints(number);
        }
        playerIsSelecting(number);
    }

    private void insertInBookshelfAndPoints(int number) {
        try{
            //TODO ricontrollare l'eccezione cambiando il valore base di x e y senno inserendo -1 il programma va avanti
            if (number < 0 || number >= turnBookshelf().getMatrix().length-1) {
                throw new IllegalArgumentException("Column value must be between 0 and " + (turnBookshelf().getMatrix()[0].length - 1));
            }
            if(gameandPlayerController.insertAsSelected(number)){
                System.out.println("The tiles have been inserted");
                //TODO AGGIUNGERE L'AGGIORNAMENTO DEI PUNTEGGI QUANDO I CHECK GOAL SARANNO TERMINATI
                //gameandPlayerController.updateAllPoints();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        changeFlag();

    }

    private void finishChoicePlayer() {
        if(gameBoard().getSelectedBoard().size()!=0){
            System.out.println("You selected "+gameBoard().getSelectedBoard().size()+" tiles");
            System.out.println("These are the free cells for each column of your bookshelf enter a number from 0 to 4 to insert them into");
            gameBoard().setFinishPlayerChoice(-2);
            turnPlayer().getBookshelf().computeFreeShelves();
            turnPlayer().getBookshelf().printFreeShelves();
            turnBookshelf().printBookshelf();
            turnPlayer().setSelectedItems(boardController.selected());
        }else{
            System.err.println("You have not selected tiles");
            resetPlayerChoice();
        }
        changeFlag();
    }
    public void playerIsSelecting(int number) throws Exception {
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
        System.out.println("TURN PLAYER: "+turnPlayer().getNickname()+" can select MAX "+turnBookshelf().numSelectableTiles());
        gameBoard().printMatrix();
    }


    public Player turnPlayer(){return gameandPlayerController.getGame().getTurnPlayer();}

    public Player firstPlayer(){return gameandPlayerController.getGame().getFirstPlayer();}

    public Bookshelf turnBookshelf(){return gameandPlayerController.getGame().getTurnPlayer().getBookshelf();}

    public Board gameBoard(){return boardController.getBoard();}

    public void changeFlag(){boardController.getBoard().setFinishPlayeropposite();}




}






















/*package it.polimi.ingsw.controller;
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
            try {
                //if it is false the game isn't already started
                if (!gameandPlayerController.insertNickname(nickname)) {
                    try {
                        inizializeGame();
                        System.out.println("TURN PLAYER: "+turnPlayer().getNickname());
                        gameBoard().printMatrix();
                        System.out.println("\n");
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else gameandPlayerController.getGame().setFinishopposite();
            }catch (IllegalArgumentException e){
                System.err.println("Invalid name");
                gameandPlayerController.getGame().setFinishopposite();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
            } catch (Exception e) {
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

    public void inizializeGame() throws Exception {
        boardController.firstFill(gameandPlayerController.getGame().getNumPlayers());
        gameandPlayerController.firstPlayer();
        gameandPlayerController.createCommonGoalCard();
        gameandPlayerController.createPersonalGoalCard();
    }

    private void turnPlayer(int number) throws Exception {

        if(gameBoard().isEndGame()&&firstPlayer().getNickname().equals(turnPlayer().getNickname())){
            System.out.println("END GAME");
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
            finishChoicePlayer();
        }
        if (number == -3) {
            gameandPlayerController.insertAsSelected(gameBoard().getColumnSelected());
            updatePoints();
        }
        if(boardController.getBoard().getFinishPlayerChoice()==-2){
            insertInBookshelf(number);
        }
        playerIsSelecting(number);
    }
    private void updatePoints() {
        System.out.println("The tiles have been inserted");
        //TODO AGGIUNGERE L'AGGIORNAMENTO DEI PUNTEGGI sia check goal che personal goal
        gameandPlayerController.setNextPlayer(turnPlayer());
        if (boardController.checkRefill()) {
            boardController.refill();
        }
        if(turnBookshelf().isFull()){
            gameBoard().setEndGame(true);
        }
        resetPlayerChoice();
        changeFlag();

    }
    private void insertInBookshelf(int number) {

        //TODO ricontrollare l'eccezione cambiando il valore base di x e y senno inserendo -1 il programma va avanti

        if (gameBoard().getColumnSelected() == -1) {
            if(gameandPlayerController.checkBookshelf(number)) {
                gameBoard().setColumnSelected(number);
            }
            else {
                System.err.println("You have inserted in a column with a number of free cells lower than those that can be inserted, insert another column");
                changeFlag();
            }
            System.out.println(turnPlayer().getSelectedItems().size());
            gameBoard().setNumOfTile(turnPlayer().getSelectedItems().size());
            System.out.println("write -3 if you want to insert the tiles in the bookshelf as you selected them. \nOtherwise write a number from 0 to" + (turnPlayer().getSelectedItems().size() - 1) + " select the order");
            System.out.println("You selected"+turnPlayer().getSelectedItems().size());
            System.out.println("");
            for (ItemTile t : turnPlayer().getSelectedItems()) {
                System.out.print(t.getType() + " ");
            }
            changeFlag();
        } else {
            if (gameBoard().getnumOfTile() == 1) {
                gameandPlayerController.insertOnceATime(number, gameBoard().getColumnSelected());
                updatePoints();
            }
            System.out.println("write a number from 0 to " + (gameBoard().getnumOfTile()-2 ) + " select the order");
            System.out.println("You selected");
            gameandPlayerController.insertOnceATime(number, gameBoard().getColumnSelected());

            for (ItemTile t : turnPlayer().getSelectedItems()) {
                System.out.print(t.getType() + " ");
            }
            System.out.println("");
           gameBoard().setNumOfTile(gameBoard().getnumOfTile() - 1);
            changeFlag();

        }


    }

    private void finishChoicePlayer() {
        if(gameBoard().getSelectedBoard().size()!=0){
            System.out.println("You selected "+gameBoard().getSelectedBoard().size()+" tiles");
            System.out.println("These are the free cells for each column of your bookshelf enter a number from 0 to 4 to insert them into");
            gameBoard().setFinishPlayerChoice(-2);
            turnPlayer().getBookshelf().computeFreeShelves();
            turnPlayer().getBookshelf().printFreeShelves();
            turnBookshelf().printBookshelf();
            turnPlayer().setSelectedItems(boardController.selected());
        }else{
            System.err.println("You have not selected tiles");
            resetPlayerChoice();
        }
        changeFlag();
    }
    public void playerIsSelecting(int number) throws Exception {
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
        gameBoard().setColumnSelected(-1);
        gameBoard().setNumOfTile(-1);
        gameBoard().setSelectedBoard(new ArrayList<>());
        System.out.println("TURN PLAYER: "+turnPlayer().getNickname()+" can select MAX "+turnBookshelf().numSelectableTiles());
        gameBoard().printMatrix();
    }


    public Player turnPlayer(){return gameandPlayerController.getGame().getTurnPlayer();}

    public Player firstPlayer(){return gameandPlayerController.getGame().getFirstPlayer();}

    public Bookshelf turnBookshelf(){return gameandPlayerController.getGame().getTurnPlayer().getBookshelf();}

    public Board gameBoard(){return boardController.getBoard();}

    public void changeFlag(){boardController.getBoard().setFinishPlayeropposite();}
}*/