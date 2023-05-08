package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.network.client.handlers.*;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends ClientInterface {
    private String nickname;

    private Scanner scanner;
    private PrinterBoard printerBoard;


    public CLI(){
        this.scanner= new Scanner(System.in);
        setClientView(new ClientView());
        printerBoard=new PrinterBoard();
    }

    @Override
    public int[] askCoordinates() {
        getClientView().setCoordinatesSelected(new ArrayList<>());
        printerBoard.printMatrixBoard(getClientView());
        //TODO aggiungere attributo che indica il numero di tile massimo
        Scanner scanner=new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            int row;
            int column;
            Colors.colorize(Colors.GAME_INSTRUCTION, "Insert row and column, of the tile you want to select ('x,y'): ");
            //System.out.print("Insert row and column, of the tile you want to select ('x,y'): ");
            String input = scanner.nextLine();

            String[] numeri = input.split(",");
            if (numeri.length == 2) {
                try {
                    row = Integer.parseInt(numeri[0].trim());
                    column = Integer.parseInt(numeri[1].trim());

                    System.out.println();
                    getClientView().getCoordinatesSelected().add(row);
                    getClientView().getCoordinatesSelected().add(column);

                } catch (NumberFormatException e) {
                    System.err.println("Invalid input. Make sure you enter two whole numbers separated by a comma.");
                    askCoordinates();
                }
            } else {
                System.err.println("Invalid input. Make sure you enter two whole numbers separated by a comma.");
            }

            Colors.colorize(Colors.GAME_INSTRUCTION, "Write:\n•1 -> RESET your selection \n•2 -> CONFIRM the choice\n");
            row = scanner.nextInt();
            scanner.nextLine();
            if (row == 1) {
                getClientView().setCoordinatesSelected(new ArrayList<>());
                printerBoard.printMatrixBoard(getClientView());
                i = -1;
                continue;
            }
            if (row == 2) {
                printerBoard.printMatrixBoard(getClientView());
            }
        }
        return getClientView().getCoordinatesSelected().stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int[] askOrder() {
        System.out.println("Insert numbers from 0 to max selected tiles-1.\nFor example, if you have selected 2 tiles and want to insert the second selected first, just insert: 1,then 0.");
        //printItemTilesSelected();
        ItemTileView[] tileSelected= getClientView().getTilesSelected();
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
        //printMatrixBookshelf();
        int[] column =new int[]{scanner.nextInt()};
        return column;
    }


    /*
    public void printMatrixBoard(){
        System.out.println("BOARD");
        BoardBoxView[][] matrix = getClientView().getBoardView();
        ArrayList<Integer> coordinates=getClientView().getCoordinatesSelected();
        Colors.upperBoard(Colors.OCHRE_YELLOW_CODE);
        Colors.mediumBoard(Colors.OCHRE_YELLOW_CODE);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getItemTileView().getTypeView()!=null) {
                    //System.out.printf("%-6s","("+i+","+j+")");
                    boolean selected=false;
                    for (int k = 0;coordinates.size()>0&& k < coordinates.size(); k += 2) {
                        if (coordinates.get(k).equals(i) && coordinates.get(k + 1).equals(j)) {
                           // colors.colorize(Colors.RED_CODE,"SELECTED");
                            colors.colorize(Colors.RED_CODE,"SELE");
                            selected=true;
                            break;
                        }
                    }
                    if(!selected){
                        Colors.printTiles(matrix[i][j].getItemTileView().getTypeView());
                        //colors.printTypeWithTypeColor(matrix[i][j].getItemTileView().getTypeView());
                    }
                } else {
                    if(matrix[i][j].isOccupiable()){
                        //System.out.printf("%-6s","("+i+","+j+")");
                        colors.colorize(Colors.RED_CODE,"SELE");
                    }else colors.colorize(Colors.RED_CODE,"SELE");
                }
            }
            Colors.lowerBoard(Colors.OCHRE_YELLOW_CODE);
            System.out.println("");
        }

     */


        /*
        BoardBoxView[][] matrix = getClientView().getBoardView();
        ArrayList<Integer> coordinates=getClientView().getCoordinatesSelected();
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

         */





    public void printMatrixBookshelf(){
        /*
        ItemTileView[][] bookshelfView= getClientView().getBookshelfView();
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

         */
    }
    public void printItemTilesSelected(){
        /*
        System.out.printf("These are the tiles you have selected: ");
        int j=0;
        ItemTileView[] tilesSelected=new ItemTileView[3];
        tilesSelected[0]=new ItemTileView(Type.CAT,0);
        tilesSelected[1]=new ItemTileView(Type.CAT,0);
        tilesSelected[2]=new ItemTileView(Type.CAT,0);
        for(ItemTileView t: tilesSelected){
        //for(ItemTileView t: getClientView().getTilesSelected()){
        //for(int i=0;i<3;i++){
            System.out.printf("%-2s",(j++));
            //colors.printTypeWithTypeColor(t.getTypeView());
            Colors.printTiles(t.getTypeView());
            System.out.printf("%-2s","");
        }
        System.out.println("");

         */
    }
    public void printPersonalGoal() {
        /*
        ItemTileView[][] bookshelfView=getClientView().getBookshelfView();
        PersonalGoalCard personalGoalCard=getClientView().getPlayerPersonalGoal();
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

         */
    }

    public void printCommonGoalPoints(){
      String whoChange;
        CommonGoalView[] commonGoalViews= getClientView().getCommonGoalViews();
        for(CommonGoalView commonGoalp:commonGoalViews){
            if(commonGoalp.getWhoWonLastToken()==null){
                whoChange="NO ONE HAS WON A TOKEN";
            }else whoChange=commonGoalp.getWhoWonLastToken()+" ONE HAS WON A TOKEN";
            System.out.println(commonGoalp.getLastPointsLeft()+" This are TOKEN points that remain,"+whoChange);
        }
    }
    public void printPlayerPoints(){
        PlayerPointsView playerPoints=getClientView().getPlayerPoints();
        System.out.println("POINTS "+nickname);
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("END TURN "+this.nickname);
    }

    @Override
    public String getNickname() {
        return null;
    }




    public void printFreeShelves(int[] freeShelves) {
        System.out.println("these are the free cells for each column ");
        for (int i = 0; i < freeShelves.length; i++) {
            System.out.printf("%-8d", freeShelves[i]);
        }
        System.out.println("");
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


