package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;

import java.util.Scanner;

import static it.polimi.ingsw.messages.MessageFromServerType.NEW_BOARD;

public class Client {
    private final String nickname;
    private GameController gameController;
    private Scanner scanner;
    private final Game game;

    public Client(String nickname, GameController gameController) {
        this.nickname = nickname;
        this.gameController = gameController;
        this.scanner=new Scanner(System.in);
        this.game=gameController.getModel();
    }

    public void receiveMessageFromClient(String nickname, Object newValue, MessageFromServerType messageFromServerType){
        System.out.println(nickname+" RECEIVE MESSAGE");
        switch(messageFromServerType) {
            case START_TURN -> System.out.println(nickname+" START TURN ");
            case NEW_BOARD-> ((Board)newValue).printMatrix();
            case END_TURN->{
                ((Bookshelf)newValue).printBookshelf();
                System.out.println(nickname+" FINISH TURN ");
                return;
            }
            case COMMONGOALCARD_AND_PERSONAL->System.out.println("SETUP");
            case POINTS->System.out.println(nickname+" NEW score "+(int)newValue);
            case REMOVE_TOKEN-> System.out.println(nickname+" win token, not you "+this.nickname);
            case WIN_TOKEN->System.out.println(nickname+" (YOU) WIN token, GOOD JOB!!! ");
            case END_PHASE ->{
                System.out.println(nickname+" FINISH PHASE ");
            }
            case ERROR ->{
                System.out.println(nickname+" ERROR "+(String)newValue);
            }
        }
        ask();
    }

    public void ask(){
        int i=1;
        for(MessageFromClientType element : MessageFromClientType.values()) {
            System.out.print(i+" "+element+"  ");
            i++;
        }
        System.out.println("");
        System.err.println(gameController.getTurnController().getCurrentPhase()+" Questa é quella del controller: ");
        int num = scanner.nextInt();
        MessageFromClient mes;
        ClientMessageHeader header;
        MessagePayload payload;
        switch(num){
            case 1:
                game.getBoard().printMatrix();
                int[] coordinates = new int[2];
                System.out.println("Insert row:");
                coordinates[0] = scanner.nextInt();
                System.out.println("Insert column:");
                coordinates[1]= scanner.nextInt();
                header=new ClientMessageHeader(MessageFromClientType.SELECTION_BOARD,nickname);
                payload=new MessagePayload(coordinates);
                mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 2:
                header=new ClientMessageHeader(MessageFromClientType.RESET_BOARD_CHOICE,nickname);
                mes=new MessageFromClient(header,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 3:
                header=new ClientMessageHeader(MessageFromClientType.FINISH_SELECTION,nickname);
                mes=new MessageFromClient(header,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 4:
                System.out.println("insert numbers from 0 to max selected tiles-1.\nFor example, if you have selected 2 tiles and want to insert the second selected first, just insert: 1,then 0.");
                int[] orderTiles=new int[game.getTurnPlayer().getSelectedItems().size()];
                for(i=0;i<game.getTurnPlayer().getSelectedItems().size();i++){
                    System.out.println("Insert number:");
                    orderTiles[i]= scanner.nextInt();
                }
                header=new ClientMessageHeader(MessageFromClientType.SELECT_ORDER_TILES,nickname);
                payload=new MessagePayload(orderTiles);
                mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 5:
                System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
                game.getTurnPlayer().getBookshelf().printFreeShelves();
                game.getTurnPlayer().getBookshelf().printBookshelf();
                int column = scanner.nextInt();
                header=new ClientMessageHeader(MessageFromClientType.SELECT_COLUMN,nickname);
                payload=new MessagePayload(column);
                mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 6:
                header=new ClientMessageHeader(MessageFromClientType.INSERT_BOOKSHELF,nickname);
                mes=new MessageFromClient(header,null);
                gameController.receiveMessageFromClient(mes);
                break;
            default:
                System.err.println("INVALID COMMAND");
                ask();
                break;
        }

    }


    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public String getNickname() {
        return nickname;
    }
}
