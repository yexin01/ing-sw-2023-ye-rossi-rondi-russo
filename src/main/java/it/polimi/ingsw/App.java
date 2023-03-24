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

        Controller1 controller1 = new Controller1(board,view1,view2,game);

        board.addObserver(view1);
        game.addObserver(view2);

        view1.addObserver(controller1);
        view2.addObserver(controller1);

        view1.askPlayerNickname();
        view2.askPlayer();

    }


}