package it.polimi.ingsw;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.controller.*;
public class App
{
    public static void main( String[] args ) {
        Board board=new Board();
        View1 view1=new View1();
        Game game=new Game();
        View2 view2=new View2();

        BookshelfController bookshelfController=new BookshelfController();
        BoardController boardController=new BoardController(board);
        PlayerController playerController=new PlayerController(game);
        GameController gameController = new GameController(playerController,boardController,bookshelfController);

        game.addObserver(view1);
        board.addObserver(view2);

        view1.addObserver(gameController);
        view2.addObserver(gameController);

        view1.askPlayerNickname();
        view2.askPlayer();

    }


}
