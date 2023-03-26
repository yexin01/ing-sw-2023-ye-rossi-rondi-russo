package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer {
    private PlayerController playerController;
    private BoardController boardController;
    private BookshelfController bookshelfController;

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String nickname){
            if(!playerController.insertNickname(nickname)){
                boardController.fillBag(playerController.getGame().getNumPlayers());
                playerController.firstPlayer();
            }else playerController.getGame().setFinishopposite();
        }
        if(arg instanceof Integer number){
            if(boardController.getBoard().getPlayerChoiceX()==-1){
                boardController.getBoard().setPlayerChoiceX(number);
                boardController.getBoard().setFinishPlayeropposite();
            }else if (boardController.getBoard().getPlayerChoiceY()==-1) {
                boardController.getBoard().setPlayerChoiceY(number);
                boardController.getBoard().setFinishPlayeropposite();
            }else {
                boardController.getBoard().setPlayerChoicenumTile(number);
                boardController.isSelectable(boardController.getBoard().getBoardBox(boardController.getBoard().getPlayerChoiceX(), boardController.getBoard().getPlayerChoiceY()), boardController.getBoard().getPlayerChoicenumTile());
                if(playerController.maxTileOrFinish() == boardController.getBoard().getPlayerChoicenumTile()) {
                    playerController.getGame().getTurnPlayer().setSelectedItems(boardController.selected());
                    //Inserisce nella bookshelf
                    //calcola punteggio...
                    //Chiama il metodo bookshelf piena
                    playerController.setNextPlayer(playerController.getGame().getTurnPlayer());
                    if(boardController.checkRefill()){
                        boardController.refill();
                    }

                }
            }
            boardController.getBoard().setPlayerChoiceX(-1);
            boardController.getBoard().setPlayerChoiceY(-1);
            boardController.getBoard().setPlayerChoicenumTile(-1);
            boardController.getBoard().setFinishPlayeropposite();
        }
    }
    public GameController(PlayerController playerController, BoardController boardController, BookshelfController bookshelfController){
        this.boardController = boardController;
        this.playerController=playerController;
        this.bookshelfController=bookshelfController;
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

    public BookshelfController getBookshelfController() {
        return bookshelfController;
    }

    public void setBookshelfController(BookshelfController bookshelfController) {
        this.bookshelfController = bookshelfController;
    }


    public void startGame(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void endGame(){
        throw new UnsupportedOperationException("Not implemented yet");
    }



}
