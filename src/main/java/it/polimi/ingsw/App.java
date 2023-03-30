package it.polimi.ingsw;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.controller.*;
public class App
{
    public static void main( String[] args ) {
        Board board=new Board();
        View1 view1=new View1();
        Game game=new Game(board);
        View2 view2=new View2();

        Controller controller = new Controller(game);

        game.addObserver(view1);
        board.addObserver(view2);


        view1.addObserver(controller);
        view2.addObserver(controller);

        view1.askPlayerNickname();

    view2.askPlayer();

    }


}
