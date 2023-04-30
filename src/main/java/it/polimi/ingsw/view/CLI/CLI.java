package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.util.ArrayList;
import java.util.Scanner;

//TODO una volta terminati gli handler finire di implementare i metodi
//TODO branch CLI correggo gli errori e cambio la stampa degli oggetti
public class CLI implements ClientInterface {
    private String nickname;
    private Colors colors=new Colors();
    private Scanner scanner;
    private ClientView clientView;

    public CLI(String nickname, ClientView clientView){
        this.nickname = nickname;
        this.clientView = clientView;
        this.scanner= new Scanner(System.in);
    }



    @Override
    public int[] askCoordinates() {
        //TODO change 3,insert parameter
        printMatrixBoard();
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
        return coordinates.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int[] askOrder() {
        System.out.println("Insert numbers from 0 to max selected tiles-1.\nFor example, if you have selected 2 tiles and want to insert the second selected first, just insert: 1,then 0.");
        printItemTilesSelected();
        ItemTileView[] tileSelected= clientView.getTilesSelected();
        int[] orderTiles = new int[tileSelected.length];
        for (int i = 0; i < tileSelected.length; i++) {
            System.out.println("Insert number:");
            orderTiles[i] = scanner.nextInt();
        }
        return orderTiles;
    }

    @Override
    public int[] askColumn() {
        System.out.println("These are the free shelves, to select a column write a number from 0 to 4");
        computeFreeShelves();
        printMatrixBookshelf();
        int[] column =new int[]{scanner.nextInt()};
        return column;
    }

    public void printMatrixBoard(){
        System.out.println("BOARD");
        BoardBoxView[][] matrix = clientView.getBoardView();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getItemTileView().getTypeView()!=null) {
                    System.out.printf("%-6s","("+i+","+j+")");
                    colors.printTypeWithTypeColor(matrix[i][j].getItemTileView().getTypeView());
                } else {
                    if(matrix[i][j].isOccupiable()){
                        System.out.printf("%-13s",+j+"SELECTED");
                    }else System.out.printf("%-13s","");
                }
            }
            System.out.println("");
        }
    }



    @Override
    public ClientView getCurrentView() {
        return null;
    }

    @Override
    public void shutdown() {

    }

    public void printMatrixBookshelf(){
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
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
        for(ItemTileView t: clientView.getTilesSelected()){
            System.out.printf("%-10s",+(j++)+" "+t.getTypeView());
        }
        System.out.println("");
    }
    public void printPersonalGoal() {
        System.out.println("PERSONAL GOAL "+nickname);
        PersonalGoalCard personalGoalCard=clientView.getPlayerPersonalGoal();
        for (PersonalGoalBox p :personalGoalCard.getCells()) {
            System.out.printf("%-10s", "row "+p.getX());
            System.out.printf("%-10s", "column "+p.getY());
            System.out.printf(p.getTypePersonal().toString());
            System.out.println("");
        }
        ItemTileView[][] bookshelfView=clientView.getBookshelfView();
        int rows=bookshelfView.length;
        int columns=bookshelfView[0].length;
        for (int i = 0; i < rows; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j <columns; j++) {
                boolean found = false;
                for (PersonalGoalBox p : personalGoalCard.getCells()) {
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
      String whoChange;
        CommonGoalView[] commonGoalViews= clientView.getCommonGoalViews();
        for(CommonGoalView commonGoalp:commonGoalViews){
            if(commonGoalp.getWhoWonLastToken()==null){
                whoChange="NO ONE HAS WON A TOKEN";
            }else whoChange=commonGoalp.getWhoWonLastToken()+" ONE HAS WON A TOKEN";
            System.out.println(commonGoalp.getLastPointsLeft()+" This are TOKEN points that remain,"+whoChange);
        }
    }
    public void printPlayerPoints(){
        PlayerPointsView playerPoints=clientView.getPlayerPoints();
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("END TURN "+this.nickname);
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public void askNickname() {

    }



    public void printFreeShelves(int[] freeShelves) {
      for (int i = 0; i < freeShelves.length; i++) {
            System.out.print(freeShelves[i] + "  ");
        }
        System.out.println("");
    }
    public void computeFreeShelves() {
        ItemTileView[][] bookshelfView=clientView.getBookshelfView();
        int[] freeShelves=new int[bookshelfView[0].length];
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        printFreeShelves(freeShelves);

    }

    public ClientView getClientView() {
        return clientView;
    }


}
