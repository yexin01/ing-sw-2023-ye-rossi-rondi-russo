package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.client.handlers.*;
import it.polimi.ingsw.view.ClientView;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO una volta terminati gli handler finire di implementare i metodi
//TODO branch CLI correggo gli errori e cambio la stampa degli oggetti

public class CLI implements ClientInterface {
    private String nickname;
    private Colors colors=new Colors();
    private Scanner scanner;
    private ClientView clientView;

    public CLI(){
        this.scanner= new Scanner(System.in);
    }



    @Override
    public int[] askCoordinates() {
        clientView.setCoordinatesSelected(new ArrayList<>());
        printMatrixBoard();
        //TODO aggiungere attributo che indica il numero di tile massimo
        for (int i = 0; i < 3; i++) {
            System.out.println("If you want to reset your selection write -2 before inserting row");
            System.out.println("If you want to select less than 3 tiles insert -1 after you last tile\nInsert row:");
            int row=scanner.nextInt();
            if(row==-1){
                break;
            }
            if(row==-2){
                clientView.setCoordinatesSelected(new ArrayList<>());
                printMatrixBoard();
                i=-1;
                continue;
            }
            clientView.getCoordinatesSelected().add(row);
            System.out.println("Insert column:");
            clientView.getCoordinatesSelected().add(scanner.nextInt());
            printMatrixBoard();
        }
        System.out.println("You selected your tiles, if you want to confirm the choice write -1,otherwise -2");
        if(scanner.nextInt()==-2){
            askCoordinates();
        }
        return clientView.getCoordinatesSelected().stream().mapToInt(Integer::intValue).toArray();
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
        ArrayList<Integer> coordinates=clientView.getCoordinatesSelected();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getItemTileView().getTypeView()!=null) {
                    System.out.printf("%-6s","("+i+","+j+")");
                    boolean selected=false;
                    for (int k = 0;coordinates.size()>0&& k < coordinates.size(); k += 2) {
                        if (coordinates.get(k).equals(i) && coordinates.get(k + 1).equals(j)) {
                            colors.colorize(Colors.RED_CODE,"SELECTED");
                            selected=true;
                            break;
                        }
                    }
                    if(!selected){
                        colors.printTypeWithTypeColor(matrix[i][j].getItemTileView().getTypeView());
                    }
                } else {
                    if(matrix[i][j].isOccupiable()){
                        System.out.printf("%-6s","("+i+","+j+")");
                        colors.colorize(Colors.RED_CODE,"SELECTED");
                    }else System.out.printf("%-14s","");
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
        computeFreeShelves();
        for (int i = 0; i < bookshelfView.length; i++) {
            for (int j = 0; j < bookshelfView[i].length; j++) {
                if (bookshelfView[i][j].getTileID() != -1) {
                    colors.printTypeWithTypeColor(bookshelfView[i][j].getTypeView());
                } else {
                    colors.colorize(Colors.BLACK_CODE,"EMPTY");
                }
            }
            System.out.println("");
        }
    }
    public void printItemTilesSelected(){
        int j=0;
        for(ItemTileView t: clientView.getTilesSelected()){
            System.out.printf("%-2s",(j++));
            colors.printTypeWithTypeColor(t.getTypeView());
            System.out.printf("%-2s",(j++));
        }
        System.out.println("");
    }
    public void printPersonalGoal() {
        ItemTileView[][] bookshelfView=clientView.getBookshelfView();
        PersonalGoalCard personalGoalCard=clientView.getPlayerPersonalGoal();
        System.out.println("PERSONAL GOAL "+nickname);
        for (int i = 0; i < bookshelfView.length; i++) {
            for (int j = 0; j < bookshelfView[0].length; j++) {
                System.out.printf("%-6s","("+i+","+j+")");
                boolean found = false;
                for (PersonalGoalBox p : personalGoalCard.getCells()) {
                    if (p.getX() == i && p.getY() == j) {
                        colors.printTypeWithTypeColor(p.getTypePersonal());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    colors.colorize(Colors.BLACK_CODE,"EMPTY");
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
    public String askNickname() {
        return null;
    }


    public void printFreeShelves(int[] freeShelves) {
        System.out.println("these are the free cells for each column ");
        for (int i = 0; i < freeShelves.length; i++) {
            System.out.printf("%-8d", freeShelves[i]);
        }
        System.out.println("");
    }
    public void computeFreeShelves() {
        ItemTileView[][] bookshelfView=clientView.getBookshelfView();
        int[] freeShelves=new int[bookshelfView[0].length];
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i <bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        printFreeShelves(freeShelves);

    }

    public ClientView getClientView() {
        return clientView;
    }


    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void start() throws IOException {
        //TODO verra aggiunto l'inserimento della porta...e il nickname
        System.out.print("PROVA: ");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Inserisci un numero: ");
            int num = input.nextInt();

            if (num == 1) {
                break;
            }
        }
//TODO creare client socket con la porta ...
        ClientSocket socket=new ClientSocket("username", "ip", 3, "token",this);
        socket.setHandlerUpdater(new HandlerUpdater(socket,this));
        socket.startConnection();

    }
}


