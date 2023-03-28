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


        BoardController boardController=new BoardController(board);

        PlayerController playerController=new PlayerController(game);

        Controller controller = new Controller(playerController,boardController);

        game.addObserver(view1);
        board.addObserver(view2);


        view1.addObserver(controller);
        view2.addObserver(controller);

        view1.askPlayerNickname();
        view2.askPlayer();

    }


}
