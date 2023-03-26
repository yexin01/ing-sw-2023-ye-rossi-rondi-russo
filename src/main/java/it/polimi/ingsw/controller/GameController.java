package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class GameController {
    private PlayerController playerController;
    private BoardController boardController;
    private BookshelfController bookshelfController;
    private Game game;


    public GameController(PlayerController playerController, BoardController boardController, BookshelfController bookshelfController, Board board, Game game){
        this.boardController = boardController;
        this.playerController=playerController;
        this.bookshelfController=bookshelfController;
        this.game = game;
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
