package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.ErrorType;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.List;
import java.util.Scanner;

//TODO this class will changed, it defines how to handle message from Server once received
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

    public void receiveMessageFromClient(String nickname,MessageFromServer messageServer) {
        try{
            System.out.println(nickname + " RECEIVE MESSAGE");
            switch (messageServer.getServerMessageHeader().getMessageFromServer()) {
                case ERROR ->System.out.println("ERROR "+((ErrorType)messageServer.getMessagePayload().get(PayloadKeyServer.ERRORMESSAGE)).getErrorMessage()) ;
                case START_TURN ->System.out.println(nickname + " START TURN ");
                case DATA -> messageEndPhase(messageServer);
                //TODO differentiate types of message reception
                case RECEIVE -> System.out.println("Server has received the sent message( coordinates, or column, or reset)");
                case END_GAME ->{
                    System.out.println("END GAME");
                    for (Player s : (List<Player>)messageServer.getMessagePayload().get(PayloadKeyServer.RANKING)) {
                        System.out.println(s.getNickname());
                    }
                }
            }
            ask();
        }catch(Exception e){
        }
    }
    public void messageEndPhase(MessageFromServer mes) throws Exception {
        switch(mes.getMessagePayload().getEvent()){
            case BOARD_SELECTION:
               System.out.println(mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE));
                ((Board) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD)).printMatrix();
                break;
            case END_TURN:
               ((Bookshelf) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF)).printBookshelf();
                System.out.println((Integer) mes.getMessagePayload().get(PayloadKeyServer.POINTS)+" This are new points of "+mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)+" YOU ARE"+this.nickname);
                System.out.println("END TURN "+this.nickname);
                throw new Exception();
            case REMOVE_TOKEN:
               System.out.println((Integer) mes.getMessagePayload().get(PayloadKeyServer.POINTS)+"This are TOKEN points of"+mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)+" YOU ARE"+this.nickname);
                break;
        }

}

    public void ask(){
        int i=1;
        for(DataClientType element : DataClientType.values()) {
            System.out.print(i+" "+element+"  ");
            i++;
        }
        System.out.println("");
        System.err.println(gameController.getTurnController().getCurrentPhase()+" controller phase: ");
        int num = scanner.nextInt();
        MessageFromClient mes;
        switch(num){
            case 1:
                game.getBoard().printMatrix();
                int[] coordinates = new int[2];
                System.out.println("Insert row:");
                coordinates[0] = scanner.nextInt();
                System.out.println("Insert column:");
                coordinates[1]= scanner.nextInt();
                mes=new MessageFromClient(DataClientType.COORDINATES,nickname,coordinates);
                gameController.receiveMessageFromClient(mes);
                break;
            case 2:
                mes=new MessageFromClient(DataClientType.RESET_BOARD_CHOICE,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 3:
                mes=new MessageFromClient(DataClientType.FINISH_SELECTION,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 4:
                System.out.println("insert numbers from 0 to max selected tiles-1.\nFor example, if you have selected 2 tiles and want to insert the second selected first, just insert: 1,then 0.");
                int[] orderTiles=new int[game.getTurnPlayer().getSelectedItems().size()];
                for(i=0;i<game.getTurnPlayer().getSelectedItems().size();i++){
                    System.out.println("Insert number:");
                    orderTiles[i]= scanner.nextInt();
                }
                mes=new MessageFromClient(DataClientType.ORDER_TILE,nickname,orderTiles);
                gameController.receiveMessageFromClient(mes);
                break;
            case 5:
                System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
                game.getTurnPlayer().getBookshelf().printFreeShelves();
                game.getTurnPlayer().getBookshelf().printBookshelf();
                int column = scanner.nextInt();
                mes=new MessageFromClient(DataClientType.COLUMN,nickname,new int[]{column});
                gameController.receiveMessageFromClient(mes);
                break;
            case 6:
                mes=new MessageFromClient(DataClientType.INSERT_TILE_AND_POINTS,nickname,null);
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
