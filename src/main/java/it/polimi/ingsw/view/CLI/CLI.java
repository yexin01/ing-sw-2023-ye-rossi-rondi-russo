package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends ClientInterface {
    private String nickname;

    private Scanner scanner;
    private PrinterBoard printerBoard;
    private PrinterBookshelfAndPersonal printerBookshelfAndPersonal;



    public CLI(){
        this.scanner= new Scanner(System.in);
        setClientView(new ClientView());
        printerBoard=new PrinterBoard();
        printerBookshelfAndPersonal=new PrinterBookshelfAndPersonal();
    }
    private static final int MAX_SELECTABLE_TILES = 3;
    public void allCommands(int phase) throws Exception {
        System.out.println();
        String[] commandsPhase=new String[]{"select_from_board","order_tiles","column","print" };
        String option="Select a command: ";
        Colors.colorize(Colors.GAME_INSTRUCTION,option );
        System.out.println();
        int i=0;
        for (Commands command : Commands.values()) {
            String commandString = command.toString();
            while(!commandString.toLowerCase().startsWith(commandsPhase[i%commandsPhase.length])){
                String noCommands=" ";
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,noCommands, 30+5);
                Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
                i++;
            }
            if (commandString.toLowerCase().startsWith(commandsPhase[phase]) || commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])) {
                Colors.colorizeSize(Colors.GAME_INSTRUCTION, "•["+(command.ordinal()+1)+"]",5);
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,command.getCommand(), 30);
            }else{
                Colors.colorizeSize(Colors.BLACK_CODE, "•["+(command.ordinal()+1)+"]",5);
                Colors.colorizeSize(Colors.BLACK_CODE,command.getCommand(), 30);
            }
            i++;
            Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
            if(commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])){
                System.out.println();
            }
        }
        Colors.colorize(Colors.GAME_INSTRUCTION,"Insert command: ");

    }
    @Override
    public int[] askCoordinates() throws Exception {

        getClientView().setCoordinatesSelected(new ArrayList<>());
        printerBoard.printMatrixBoard(getClientView());

        //TODO aggiungere attributo che indica il numero di tile massimo
        Scanner scanner=new Scanner(System.in);
        //printerBookshelfAndPersonal.printMatrixBookshelf(getClientView().getBookshelfView(), 5,2,20,false);
        boolean continueToAsk = true;
        int input;
        Commands commands;

        while (continueToAsk) {

            allCommands(0);
            input = scanner.nextInt();
            input--;
            scanner.nextLine();

          if(!(input >= 0 && input < Commands.values().length)){
              Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.INVALID_INPUT.getErrorMessage());
              continue;
          }
          System.out.println();
          commands=Commands.values()[input];

          switch (commands) {
              case SELECT_FROM_BOARD1:
                  selectTile(scanner);
                  break;
              case SELECT_FROM_BOARD2:
                  resetLastChoice();
                  break;
              case SELECT_FROM_BOARD3:
                  resetAllChoice();
                  break;
              case SELECT_FROM_BOARD4:
                 if (getClientView().getCoordinatesSelected().isEmpty()) {
                 //printerBoard.printMatrixBoard(getClientView());
                    Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_TILES_SELECTED.getErrorMessage());
                    System.out.println();
                    continue;
                 } else {
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Confirmation successful.");
                    continueToAsk = false;
                 }
                 break;
              default:
                String commandString = commands.toString();
                if (commandString.toLowerCase().startsWith("print")){
                   printCommands(commands);
                }else Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.ILLEGAL_PHASE.getErrorMessage());
                continue;
            }

            if (getClientView().getCoordinatesSelected().isEmpty()) {
                //printerBoard.printMatrixBoard(getClientView());
                Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.NOT_TILES_SELECTED.getErrorMessage());
            } else {
                //printerBoard.printMatrixBoard(getClientView());
                Colors.colorize(Colors.GAME_INSTRUCTION,"Your current selections: ");
                for (int i = 0; i < getClientView().getCoordinatesSelected().size(); i += 2) {
                    int x = getClientView().getCoordinatesSelected().get(i);
                    int y = getClientView().getCoordinatesSelected().get(i + 1);
                    Colors.colorize(Colors.GAME_INSTRUCTION,"(" + x + ", " + y + ") ");
                    System.out.print(Colors.printTiles(getClientView().getBoardView()[x][y].getType(),3));
                    Colors.colorize(Colors.GAME_INSTRUCTION,"; ");
                }
                System.out.println();
            }
        }
        askOrder();
        return getClientView().getCoordinatesSelected().stream().mapToInt(Integer::intValue).toArray();
    }

    private void printCommands(Commands commands) throws Exception {
        switch (commands){
            case PRINT1 -> printerBoard.printMatrixBoard(getClientView());
            case PRINT2 -> printerBookshelfAndPersonal.printMatrixBookshelf(getClientView().getBookshelfView(), 4,1,40,true);
            case PRINT3 -> printerBookshelfAndPersonal.printPersonal(getClientView().getBookshelfView(),2,40);
            //TODO qua vanno inserite anche le common e i punti
        }
    }

    private void selectTile(Scanner scanner) {
        int x, y;

        if (getClientView().getCoordinatesSelected().size() >= MAX_SELECTABLE_TILES *2) {
            Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.TOO_MANY_TILES.getErrorMessage());
            System.out.println();
        }else{
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert row",16);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(x): ");
            x = scanner.nextInt();
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert column",16);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(y): ");
            y = scanner.nextInt();
            scanner.nextLine();
            ErrorType error= checkCoordinates(x, y);
            if(error==null){
                error= checkSelectable(x, y);
            }
            if (error!=null) {
                printerBoard.printMatrixBoard(getClientView());
                Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                System.out.println();
            }else {
                printerBoard.printMatrixBoard(getClientView());
            }
        }
    }
    private  void resetAllChoice() {
        getClientView().getCoordinatesSelected().clear();
        printerBoard.printMatrixBoard(getClientView());
        Colors.colorize(Colors.GAME_INSTRUCTION, "All choices have been reset. ");
    }
    private void resetLastChoice() {
        if (!getClientView().getCoordinatesSelected().isEmpty()) {
            int lastIndex = getClientView().getCoordinatesSelected().size() - 1;
            getClientView().getCoordinatesSelected().remove(lastIndex);
            getClientView().getCoordinatesSelected().remove(lastIndex - 1);
            printerBoard.printMatrixBoard(getClientView());
            Colors.colorize(Colors.GAME_INSTRUCTION, "Last choice has been reset. ");

        }/* else {
            printerBoard.printMatrixBoard(getClientView());
            Colors.colorize(Colors.ERROR_MESSAGE, "There are no choices to reset.");
        }
        */

    }
    /*
    private void confirmChoice() {
        if (!getClientView().getCoordinatesSelected().isEmpty()) {
            printerBoard.printMatrixBoard(getClientView());
            Colors.colorize(Colors.GAME_INSTRUCTION, "You have confirmed the following choices:");
            for (int i = 0; i < getClientView().getCoordinatesSelected().size(); i += 2) {
                int x = getClientView().getCoordinatesSelected().get(i);
                int y = getClientView().getCoordinatesSelected().get(i + 1);
                System.out.println("(" + x + ", " + y + ")");
            }
            Colors.colorize(Colors.GAME_INSTRUCTION, "Confirmation successful.");
            System.out.println();
        } else {
            printerBoard.printMatrixBoard(getClientView());
            Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_RECEIVED_TILES.getErrorMessage());
            System.out.println();
        }
    }

     */
    private int sizetile=3;
    private int sizeLenghtFromBordChoiceItem = 20;
    private int distanceBetweenTilesChoice = 4;
    @Override
    public int[] askOrder() throws Exception {
        createItemTileView();
        //insertTiles(2);
        Colors.colorize(Colors.GAME_INSTRUCTION,"ORDER TILES " );
        printerBookshelfAndPersonal.printMatrixBookshelf(getClientView().getBookshelfView(), 3,2,40,true);
        Colors.colorize(Colors.GAME_INSTRUCTION, "Insert numbers from 0 to "+(getClientView().getCoordinatesSelected().size()/2-1)+"\n");
        System.out.println();
        Colors.colorize(Colors.GAME_INSTRUCTION,"THIS IS YOUR BOOKSHELF now " );
        System.out.println();
        //Colors.colorize(Colors.GAME_INSTRUCTION, "For example, if you have selected 2 tiles and want to insert the second selected first,\n just insert: 1,then 0.\n");
        Colors.colorize(Colors.GAME_INSTRUCTION,"These are the tiles selected by YOU: " );
        ErrorType error=ErrorType.INVALID_ORDER_TILE_NUMBER;
        int[] orderTiles = new int[getClientView().getCoordinatesSelected().size()/2];
        while(error!=null){
            int j=0;
            for (ItemTileView t:getClientView().getTilesSelected()) {
                Colors.colorize(Colors.RED_CODE,Integer.toString(j++)+" ");
                System.out.print(Colors.printTiles(t.getTypeView(),sizetile));
                Colors.colorize(Colors.GAME_INSTRUCTION,"; ");
            }
            System.out.println();
            for (int i = 0; i < getClientView().getCoordinatesSelected().size()/2; i++) {
                Colors.colorize(Colors.GAME_INSTRUCTION,"Insert number: " );
                orderTiles[i] = scanner.nextInt();
            }
            error=checkPermuteSelection(orderTiles);
            if(error!=null){
                Colors.colorize(Colors.ERROR_MESSAGE,error.getErrorMessage());
                System.out.println();
            }
        }
        getClientView().setOrderTiles(orderTiles);
        permuteSelection();
        askColumn();
        return orderTiles;
    }

    @Override
    public int[] askColumn() {
        ErrorType error=ErrorType.INVALID_COLUMN;
        int[] column = new int[1];
        while(error!=null){
            System.out.println("These are the free shelves, to select a column write a number from 0 to "+(getClientView().getBookshelfView()[0].length-1));
            column[0] =scanner.nextInt();
            error=checkBookshelf(column[0]);
            if(error!=null){
                Colors.colorize(Colors.ERROR_MESSAGE,error.getErrorMessage());
            }
        }
        insertTiles(column[0]);
        try {
            printerBookshelfAndPersonal.printMatrixBookshelf(getClientView().getBookshelfView(), sizetile,2,20,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public PrinterBookshelfAndPersonal getPrinterBookshelfAndPersonal() {
        return printerBookshelfAndPersonal;
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
    /*
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

     */
}


