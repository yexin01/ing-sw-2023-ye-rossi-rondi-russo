package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.server.ServerView;

//TODO manage it differently by adding the network part, it was needed to see how the message exchange works
public class ClientView {
    private BoardView boardView;
    private CommonGoalView[] commonGoalViews;
    private int[] commonGoalPoints;
    //TODO initialization will be inserted in the method related to the start of the game
    private int[] freeShelves=new int[5];
    private String[] players;
    private ItemTileView[][] bookshelfView;
    private ItemTileView[] tilesSelected;
    private PlayerPointsView playerPoints;
    private PersonalGoalCard playerPersonalGoal;
    private final String nickname;
    private final GameController gameController;
    private final ServerView serverView;
    private final Client client;
    public ClientView(String nickname, GameController gameController, ServerView serverView, Client client) {
        this.nickname = nickname;
        this.gameController = gameController;
        this.serverView = serverView;
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
                case DATA ->receiveData(messageServer);
                case RECEIVE -> System.out.println("Server has received the sent message ");
                /*
                case END_GAME ->{
                    System.out.println("END GAME");
                    for (Player s : (List<Player>)messageServer.getMessagePayload().get(PayloadKeyServer.RANKING)) {
                        System.out.println(s.getNickname());
                    }
                }

                 */
            }

        }catch(Exception e){
        }
    }
    public void receiveData(MessageFromServer mes) throws Exception {
        switch(mes.getMessagePayload().getEvent()){
            case BOARD_SELECTION->boardSelection(mes);
            case END_TURN->endTurn(mes);
            case WIN_TOKEN->{
                if(getMessageWhoChange(mes)==nickname)
                    winToken(mes);
                else loseToken(mes);
            }
            case ALL_INFO->allInfo(mes);
            case TILES_SELECTED->tilesSelected(mes);
        }
    }
    public void boardSelection(MessageFromServer mes){
        setBoardView((BoardView) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        System.out.println(getMessageWhoChange(mes)+" change board");
        printMatrixBoard();
    }
    public void endTurn(MessageFromServer mes) throws Exception {
        setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        printPlayerPoints();
        setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));;
        printMatrixBookshelf();
        throw new Exception();
    }

    public void winToken(MessageFromServer mes){
        int index=(int)mes.getMessagePayload().get(PayloadKeyServer.INDEX_TOKEN);
        setCommonGoalPoints((int)mes.getMessagePayload().get(PayloadKeyServer.POINTS),index);
        setCommonGoalViews((CommonGoalView) mes.getMessagePayload().get(PayloadKeyServer.TOKEN),index);
        printCommonGoalPoints();
        System.out.println("YOU WON TOKEN n:"+(index+1)+" .Points that remain: "+commonGoalViews[index].getLastPointsLeft());
    }
    public void loseToken(MessageFromServer mes){
        int index=(int)mes.getMessagePayload().get(PayloadKeyServer.INDEX_TOKEN);
        setCommonGoalViews((CommonGoalView) mes.getMessagePayload().get(PayloadKeyServer.TOKEN),index);
        System.out.println("YOU LOSE TOKEN n:"+(index+1)+" .Points that remain: "+commonGoalViews[index].getLastPointsLeft());
    }


    public void allInfo(MessageFromServer mes){
        setBoardView((BoardView) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        printMatrixBoard();
        setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));;
        printMatrixBookshelf();
        setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        printPlayerPoints();
        setCommonGoalViews((CommonGoalView[]) mes.getMessagePayload().get(PayloadKeyServer.TOKEN));
        setPlayerPersonalGoal((PersonalGoalCard) mes.getMessagePayload().get(PayloadKeyServer.PERSONAL_GOAL));
        printCommonGoalPoints();
        printPersonalGoal();

    }
    public void tilesSelected(MessageFromServer mes){
        setBoardView((BoardView) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        printMatrixBoard();
        setTilesSelected((ItemTileView[]) mes.getMessagePayload().get(PayloadKeyServer.TILES_SELECTED));
        printItemTilesSelected();
    }
    public String getMessageWhoChange(MessageFromServer mes){
        return (String)mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE);

    }

    public void askClient(){
        int num=client.ask();
        MessageFromClient mes;
        switch(num){
            case 1:
                printMatrixBoard();
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
                printItemTilesSelected();
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
                computeFreeShelves();
                printFreeShelves();
                printMatrixBookshelf();
                int column = client.number();
                mes=new MessageFromClient(DataClientType.COLUMN,nickname,new int[]{column});
                gameController.receiveMessageFromClient(mes);
                break;
            case 6:
                mes=new MessageFromClient(DataClientType.INSERT_TILE_AND_POINTS,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            case 7:
                mes=new MessageFromClient(DataClientType.ASK_INFO_GAME,nickname,null);
                serverView.sendInfo(nickname);
                break;
            case 8:
                mes=new MessageFromClient(DataClientType.INSERT_TILE_AND_POINTS,nickname,null);
                gameController.receiveMessageFromClient(mes);
                break;
            default:
                System.err.println("INVALID COMMAND");
                client.ask();
                break;
        }
    }
    public void printMatrixBoard(){
        System.out.println("BOARD ");
        BoardBoxView[][] matrix= boardView.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getItemTileView().getTypeView()!=null) {
                    System.out.printf("%-10s",+j+""+matrix[i][j].getItemTileView().getTypeView());
                } else {
                    if(matrix[i][j].isOccupiable()){
                        System.out.printf("%-10s",+j+"SELECTED");
                    }else System.out.printf("%-10s",+j+"EMPTY");
                }
            }
            System.out.println("");
        }
    }
    public void printMatrixBookshelf(){
        System.out.println("BOOKSHELF "+nickname);
        for (int i = 0; i < bookshelfView.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < bookshelfView[i].length; j++) {
                if (bookshelfView[i][j].getTileID() != -1) {
                    System.out.printf("%-10s",+j+""+bookshelfView[i][j].getTypeView());
                } else {
                    System.out.printf("%-10s",+j+" EMPTY");
                }
            }
            System.out.println("");
        }
    }
    public void printItemTilesSelected(){
        int j=0;
        for(ItemTileView t:tilesSelected){
            System.out.printf("%-10s",+(j++)+" "+t.getTypeView());
        }
        System.out.println("");
    }
    public void printPersonalGoal() {
        System.out.println("PERSONAL GOAL "+nickname);
        for (PersonalGoalBox p : playerPersonalGoal.getCells()) {
            System.out.printf("%-10s", "row "+p.getX());
            System.out.printf("%-10s", "column "+p.getY());
            System.out.printf(p.getTypePersonal().toString());
            System.out.println("");
        }
        for (int i = 0; i < bookshelfView.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < bookshelfView[0].length; j++) {
                boolean found = false;
                for (PersonalGoalBox p : playerPersonalGoal.getCells()) {
                    if (p.getX() == i && p.getY() == j) {
                        System.out.printf("%-10s",j+" "+p.getTypePersonal().toString());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.printf("%-10s",j+" EMPTY");
                }
            }
            System.out.println("");
        }
    }
    public void printCommonGoalPoints(){
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("COMMONGOALCARDS "+nickname);
        String whoChange;

        for(CommonGoalView commonGoalp:commonGoalViews){
            if(commonGoalp.getWhoWonLastToken()==null){
                whoChange="NO ONE HAS WON A TOKEN";
            }else whoChange=commonGoalp.getWhoWonLastToken()+" ONE HAS WON A TOKEN";
            System.out.println(commonGoalp.getLastPointsLeft()+" This are TOKEN points that remain,"+whoChange);
        }
    }
    public void printPlayerPoints(){
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("END TURN "+this.nickname);
    }
    public void printFreeShelves() {
        for (int i = 0; i < freeShelves.length; i++) {
            System.out.print(freeShelves[i] + "  ");
        }
        System.out.println("");
    }
    public void computeFreeShelves() {
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public CommonGoalView[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(CommonGoalView[] commonGoalViews) {
        this.commonGoalViews = commonGoalViews;
    }
    public void setCommonGoalViews(CommonGoalView commonGoalViews,int index) {
        this.commonGoalViews[index] = commonGoalViews;
    }
    public void setCommonGoalPoints(int points,int index) {
        this.commonGoalPoints[index] = points;
    }


    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public ItemTileView[][] getBookshelfView() {
        return bookshelfView;
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView) {
        this.bookshelfView = bookshelfView;
    }

    public PlayerPointsView getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(PlayerPointsView playerPoints) {
        this.playerPoints = playerPoints;
    }

    public PersonalGoalCard getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }

    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal) {
        this.playerPersonalGoal = playerPersonalGoal;
    }


    public ItemTileView[] getTilesSelected() {
        return tilesSelected;
    }

    public void setTilesSelected(ItemTileView[] tilesSelected) {
        this.tilesSelected = tilesSelected;
    }
}
