package it.polimi.ingsw.view;

import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.listeners.ListenerManager;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientListener;
import it.polimi.ingsw.network.messages.MessageFromServer;
import it.polimi.ingsw.network.messages.PayloadKeyServer;

import java.util.ArrayList;
import java.util.Scanner;

//TODO una volta terminati gli handler finire di implementare i metodi
public class CLI implements ClientListener {
    private String nickname;
    private Scanner scanner;
    private ClientView clientView;
    private ListenerManager listenerManager;
    public CLI(){
        this.listenerManager=new ListenerManager();
        this.scanner= new Scanner(System.in);
    }
    @Override
    public void askCoordinates(BoardBoxView[][] board) {
        //TODO change 3,insert parameter
        printMatrixBoard(board);
        ArrayList<Integer> coordinates=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            System.out.println("(If you want to select less than 3 tiles insert -1 after you last tile)\nInsert row:");
            int row=scanner.nextInt();
            if(row==-1){
                break;
            }
            coordinates.add(row);
            System.out.println("Insert column:");
            coordinates.add(scanner.nextInt());
        }
        listenerManager.fireEvent("BOARD_SELECTION",nickname,coordinates.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public void askOrder(ItemTileView[] tileSelected) {
        System.out.println("Insert numbers from 0 to max selected tiles-1.\nFor example, if you have selected 2 tiles and want to insert the second selected first, just insert: 1,then 0.");
        printItemTilesSelected(tileSelected);
        int[] orderTiles = new int[tileSelected.length];
        for (int i = 0; i < tileSelected.length; i++) {
            System.out.println("Insert number:");
            orderTiles[i] = scanner.nextInt();
        }
        listenerManager.fireEvent("ORDER_TILES",nickname,orderTiles);
    }

    @Override
    public void askColumn(ItemTileView[][] bookshelf) {
        System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
        computeFreeShelves(bookshelf);
        printMatrixBookshelf(bookshelf);
        int column = scanner.nextInt();
        listenerManager.fireEvent("COLUMN",nickname,column);

    }
    @Override
    public void fireEvent(String event, String playerNickname, Object newValue) {
        switch (event){
            case "StartTurn"->{
                askCoordinates((BoardBoxView[][]) ((MessageFromServer)newValue).getMessagePayload().get(PayloadKeyServer.NEWBOARD));
            }
            case "TileSelected"->{
                BoardBoxView[][] newBoard= (BoardBoxView[][]) ((MessageFromServer)newValue).getMessagePayload().get(PayloadKeyServer.NEWBOARD);
                printMatrixBoard(newBoard);
                if( nickname.equals(((String)((MessageFromServer)newValue).getMessagePayload().get(PayloadKeyServer.WHO_CHANGE)))){
                    ItemTileView[] tilesSelected=(ItemTileView[])((MessageFromServer)newValue).getMessagePayload().get(PayloadKeyServer.TILES_SELECTED);
                    printTilesSelected(tilesSelected);
                    askOrder(tilesSelected);
                }
            }

        }
    }


    public void printMatrixBoard(BoardBoxView[][] matrix){
        System.out.println("BOARD ");
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
    public void printMatrixBookshelf(ItemTileView[][] bookshelfView){
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
    public void printItemTilesSelected(ItemTileView[] tilesSelected){
        int j=0;
        for(ItemTileView t:tilesSelected){
            System.out.printf("%-10s",+(j++)+" "+t.getTypeView());
        }
        System.out.println("");
    }
    public void printPersonalGoal(PersonalGoalCard playerPersonalGoal, int rows, int columns) {

        System.out.println("PERSONAL GOAL "+nickname);
        for (PersonalGoalBox p : playerPersonalGoal.getCells()) {
            System.out.printf("%-10s", "row "+p.getX());
            System.out.printf("%-10s", "column "+p.getY());
            System.out.printf(p.getTypePersonal().toString());
            System.out.println("");
        }
        for (int i = 0; i < rows; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < columns; j++) {
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
    //TODO GAME CONTROLLER VERRA INSERITO NEL MESSAGGIO DI ENDTURN
    public void printCommonGoalPoints(CommonGoalView[]){
        PlayerPointsView playerPoints=clientView.getPlayerPoints();
        CommonGoalView[] commonGoalViews=clientView.getCommonGoalViews();
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("COMMONGOALCARDS "+nickname);
        java.lang.String whoChange;

        for(CommonGoalView commonGoalp:commonGoalViews){
            if(commonGoalp.getWhoWonLastToken()==null){
                whoChange="NO ONE HAS WON A TOKEN";
            }else whoChange=commonGoalp.getWhoWonLastToken()+" HAS WON A TOKEN";
            System.out.println(commonGoalp.getLastPointsLeft()+" This are TOKEN points that remain,"+whoChange);
        }
    }
    public void printPlayerPoints(PlayerPointsView playerPoints){
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("END TURN "+this.nickname);
    }
    public void printFreeShelves(int[] freeShelves) {
        for (int i = 0; i < freeShelves.length; i++) {
            System.out.print(freeShelves[i] + "  ");
        }
        System.out.println("");
    }
    public void computeFreeShelves(ItemTileView[][] bookshelfView) {
        int[] freeShelves=new int[bookshelfView[0].length];
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        printFreeShelves(freeShelves);
    }

    public void addListener (String string, EventListener listener){
        this.listenerManager.addListener(string, listener);
    }

}
