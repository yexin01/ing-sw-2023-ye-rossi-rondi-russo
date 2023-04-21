package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.List;
import java.util.Scanner;

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
                case RECEIVE -> System.out.println("ACK il server ha rivevuto il messaggio inviato(o le coordinate,o la colonna,o il reset)");
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
                //if(!mes.getMessagePayload().get(PayloadKeyServer.NICKNAME_CHANGE).equals(this.nickname))
                System.out.println(mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE));
                ((Board) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD)).printMatrix();
                break;
            case END_TURN:
                //System.out.println(mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE));
                ((Bookshelf) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF)).printBookshelf();
                System.out.println((Integer) mes.getMessagePayload().get(PayloadKeyServer.POINTS)+" This are new points of "+mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)+" YOU ARE"+this.nickname);
                System.out.println("END TURN "+this.nickname);
                throw new Exception();
            case REMOVE_TOKEN:
                //System.out.println(mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE));
                System.out.println((Integer) mes.getMessagePayload().get(PayloadKeyServer.POINTS)+"This are TOKEN points of"+mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)+" YOU ARE"+this.nickname);
                break;
        }



}

/*
        Map<PayloadKeyServer, Object> payload = mes.getMessagePayload().getAll();
        for (Map.Entry<PayloadKeyServer, Object> entry : payload.entrySet()) {
            PayloadKeyServer key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case NEWBOARD:
                    System.out.println(nickname+"YOU changedBoard this is the new Board");
                    ((Board) value).printMatrix();
                    break;
                case SOMEBODYCHANGEBOARD:
                    System.out.println(nickname+"YOU DON'T change board ");
                    ((Board) value).printMatrix();
                    break;
                case NEWBOOKSHELF:
                    System.out.println(nickname+"YOU change bookshelf ");
                    ((Bookshelf) value).printBookshelf();
                    break;
                case POINTS:
                    System.out.println(nickname+" your score is ");
                    break;
                case REMOVE_TOKEN:
                    System.out.println(nickname+"YOU DON'T WIN token of "+((int) value));
                    ((Board) value).printMatrix();
                    break;
                default:
                    // handle other keys
                    break;
            }
        }
    }

 */

    public void ask(){
        int i=1;
        for(DataClientType element : DataClientType.values()) {
            System.out.print(i+" "+element+"  ");
            i++;
        }
        System.out.println("");
        System.err.println(gameController.getTurnController().getCurrentPhase()+" Questa é quella del controller: ");
        int num = scanner.nextInt();
        MessageFromClient mes;
        //ClientMessageHeader header;
        //MessagePayload payload;
        switch(num){
            case 1:
                game.getBoard().printMatrix();
                int[] coordinates = new int[2];
                System.out.println("Insert row:");
                coordinates[0] = scanner.nextInt();
                System.out.println("Insert column:");
                coordinates[1]= scanner.nextInt();
                mes=new MessageFromClient(DataClientType.COORDINATES,nickname,coordinates);
                //header=new ClientMessageHeader(MessageFromClientType.SELECTION_BOARD,nickname);
                //payload=new MessagePayload<>(coordinates);
                //mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 2:
                //header=new ClientMessageHeader(DataClientType.RESET_BOARD_CHOICE,nickname);
                mes=new MessageFromClient(DataClientType.RESET_BOARD_CHOICE,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 3:
                mes=new MessageFromClient(DataClientType.FINISH_SELECTION,nickname,null);
                //header=new ClientMessageHeader(DataClientType.FINISH_SELECTION,nickname);
                //mes=new MessageFromClient(header,null);
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
                //header=new ClientMessageHeader(DataClientType.SELECT_ORDER_TILES,nickname);
                //payload=new MessagePayload<>(orderTiles);
                //mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 5:
                System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
                game.getTurnPlayer().getBookshelf().printFreeShelves();
                game.getTurnPlayer().getBookshelf().printBookshelf();
                int column = scanner.nextInt();
                mes=new MessageFromClient(DataClientType.COLUMN,nickname,new int[]{column});
                //header=new ClientMessageHeader(DataClientType.SELECT_COLUMN,nickname);
                //payload=new MessagePayload<>(column);
                //mes=new MessageFromClient(header,payload);
                gameController.receiveMessageFromClient(mes);
                break;
            case 6:
                mes=new MessageFromClient(DataClientType.INSERT_TILE_AND_POINTS,nickname,null);
                //header=new ClientMessageHeader(DataClientType.INSERT_BOOKSHELF,nickname);
                //mes=new MessageFromClient(header,null);
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
