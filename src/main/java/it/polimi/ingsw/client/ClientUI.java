package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listeners.EventType;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.modelView.*;

//TODO manage it differently by adding the network part, it was needed to see how the message exchange works
public class ClientUI {
    private final String nickname;
    private final GameController gameController;
    private final Client client;
    public ClientUI(String nickname, GameController gameController, Client client) {
        this.nickname = nickname;
        this.gameController = gameController;
        this.client = client;
    }

    public String getNickname(){
        return nickname;
    }

    public void receiveMessageFromServer(String nickname, MessageFromServer messageServer) {
        try{
            System.out.println(nickname + " RECEIVE MESSAGE");
            switch (messageServer.getServerMessageHeader().getMessageFromServer()) {
                case ERROR ->System.out.println("ERROR "+((ErrorType)messageServer.getMessagePayload().get(PayloadKeyServer.ERRORMESSAGE)).getErrorMessage()) ;
                case START_TURN ->System.out.println(nickname + " START TURN ");
                case DATA -> receiveData(messageServer);
                //TODO differentiate types of message reception
                case RECEIVE -> System.out.println("Server has received the sent message( coordinates, or column, or reset)");
                /*
                case END_GAME ->{
                    System.out.println("END GAME");
                    for (Player s : (List<Player>)messageServer.getMessagePayload().get(PayloadKeyServer.RANKING)) {
                        System.out.println(s.getNickname());
                    }
                }

                 */
            }
            askClient();
            //ask();
        }catch(Exception e){
        }
    }

    public void receiveData(MessageFromServer mes) throws Exception {
        switch(mes.getMessagePayload().getEvent()){
            case BOARD_SELECTION:
                System.out.println(mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE));
                printMatrixBoard((BoardView) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
                break;
            case END_TURN:
                printMatrixBookshelf((BookshelfView) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));
                PlayerPointsView points=(PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS);
                System.out.println(points.getPoints()+" This are new points of "+mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)+" YOU ARE"+this.nickname);
                System.out.println(" AdjacentPoint "+points.getAdjacentPoints()+" How many token you have:"+points.getHowManyTokenYouHave()+" PersonalGoalPoint "+points.getPersonalGoalPoints());
                System.out.println("END TURN "+this.nickname);
                throw new Exception();
            case REMOVE_TOKEN:
                CommonGoalView commonGoal=(CommonGoalView) mes.getMessagePayload().get(PayloadKeyServer.TOKEN);
                System.out.println(commonGoal.getLastPointsLeft()+"This are TOKEN points that remain, who won last token is"+commonGoal.getWhoWonLastToken()+" YOU ARE"+this.nickname);
                break;
        }

    }

    public void askClient(){
        int num=client.ask();
        MessageFromClient mes;
        switch(num){
            case 1:
                gameController.getModel().getBoard().printMatrix();
                int[] coordinates = new int[2];
                System.out.println("Insert row:");
                coordinates[0] = client.number();
                System.out.println("Insert column:");
                coordinates[1]= client.number();
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
                int[] orderTiles=new int[gameController.getModel().getTurnPlayer().getSelectedItems().size()];
                for(int i=0;i<gameController.getModel().getTurnPlayer().getSelectedItems().size();i++){
                    System.out.println("Insert number:");
                    orderTiles[i]= client.number();
                }
                mes=new MessageFromClient(DataClientType.ORDER_TILE,nickname,orderTiles);
                gameController.receiveMessageFromClient(mes);
                break;
            case 5:
                System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
                gameController.getModel().getTurnPlayer().getBookshelf().printFreeShelves();
                gameController.getModel().getTurnPlayer().getBookshelf().printBookshelf();
                int column = client.number();
                mes=new MessageFromClient(DataClientType.COLUMN,nickname,new int[]{column});
                gameController.receiveMessageFromClient(mes);
                break;
            case 6:
                mes=new MessageFromClient(DataClientType.INSERT_TILE_AND_POINTS,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            default:
                System.err.println("INVALID COMMAND");
                client.ask();
                break;
        }
    }
    public void printMatrixBoard(BoardView board){
        BoardBoxView[][] matrix= board.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getItemTileView().getType()!=null) {
                    System.out.printf("%-10s",+j+""+matrix[i][j].getItemTileView().getType());
                } else {
                    System.out.printf("%-10s",+j+"EMPTY");
                }
            }
            System.out.println("");
        }
    }
    public void printMatrixBookshelf(BookshelfView bookshelf){
        ItemTileView[][] matrix= bookshelf.getBookshelfView();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("row" + i + " ");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getTileID() != -1) {
                    System.out.printf("%-10s", +j + "" + matrix[i][j].getType());
                } else {
                    System.out.printf("%-10s", +j + " EMPTY");
                }
            }
            System.out.println("");
        }
    }

}