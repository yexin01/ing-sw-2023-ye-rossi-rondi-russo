package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.ItemTileView;

import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.util.ArrayList;
import java.util.Scanner;
//TODO molti comandi scritti qua li sposterò sugli handler li ho utilizzati per vedere come venivano stampate le varie fasi del turno
public class CLI extends ClientInterface {
    private String nickname;

    private Scanner scanner;
    private PrinterBoard printerBoard;
    private PrinterBookshelfAndPersonal printerBookshelfAndPersonal;
    private PrinterStartAndEndTurn printerStartAndEndTurn;
    private PrinterCommonGoalAndPoints printerCommonGoalAndPoints;

    public CLI(){
        this.scanner= new Scanner(System.in);
        setClientView(new ClientView());
        printerBoard=new PrinterBoard();
        printerBookshelfAndPersonal=new PrinterBookshelfAndPersonal();
        printerStartAndEndTurn =new PrinterStartAndEndTurn();
        printerCommonGoalAndPoints=new PrinterCommonGoalAndPoints();
    }

    public void allCommands(int phase) throws Exception {
        boolean firstPrint=false;
        System.out.println();
        String[] commandsPhase=new String[]{"select_from_board","order_tiles","column","print" };
        String[] titlePhase=new String[]{"BOARD","ORDER","COLUMN","INFO PLAYER" };
        //String option="Select a command: ";
        //Colors.colorize(Colors.GAME_INSTRUCTION,option );
        System.out.println();
        for(int i=0;i<titlePhase.length;i++){
            if(phase==i || i==commandsPhase.length-1){
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,titlePhase[i], 30+5);
            }else Colors.colorizeSize(Colors.BLACK_CODE,titlePhase[i], 30+5);
            Colors.printFreeSpaces(2);
            //Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");

        }
        System.out.println();
        int i=0;
        int typeCommand=0;
        for (Commands command : Commands.values()) {
            String commandString = command.toString();
            while(!commandString.toLowerCase().startsWith(commandsPhase[typeCommand%(commandsPhase.length+1)])){
                String noCommands=" ";
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,noCommands, 30+5);
                Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
                i++;
                typeCommand++;
            }
            if(commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])){
                Colors.colorizeSize(Colors.GAME_INSTRUCTION, "•["+(command.ordinal()+1)+"]",5);
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,command.getCommand(), 15);
                //System.out.println();

                if(!firstPrint){
                    firstPrint=true;
                    Colors.colorize(Colors.GAME_INSTRUCTION, "  ");
                    if(command.equals(Commands.values()[Commands.values().length-1])){
                        System.out.println();
                    }
                }else {
                    firstPrint=false;
                    Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
                    System.out.println();
                    typeCommand=0;
                }
            }else{
                if (commandString.toLowerCase().startsWith(commandsPhase[phase])/* || commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])*/) {
                    Colors.colorizeSize(Colors.GAME_INSTRUCTION, "•["+(command.ordinal()+1)+"]",5);
                    Colors.colorizeSize(Colors.GAME_INSTRUCTION,command.getCommand(), 30);
                }else{
                    Colors.colorizeSize(Colors.BLACK_CODE, "•["+(command.ordinal()+1)+"]",5);
                    Colors.colorizeSize(Colors.BLACK_CODE,command.getCommand(), 30);
                }
                i++;
                typeCommand++;
                Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
            }
        }
        Colors.colorize(Colors.GAME_INSTRUCTION,"Insert command: ");

    }
    public Commands checkCommand(int phase) throws Exception {
        int input;
        Commands commands;
        allCommands(phase);
        input = scanner.nextInt();
        input--;
        scanner.nextLine();

        if(!(input >= 0 && input < Commands.values().length)){
            Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.INVALID_INPUT.getErrorMessage());
            return null;
        }
        System.out.println();
        return Commands.values()[input];
    }


    @Override
    public int[] askCoordinates() throws Exception {
        System.out.println();
        Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: SELECT FROM BOARD");
        System.out.println();
        getClientView().setCoordinatesSelected(new ArrayList<>());
        printerBoard.printMatrixBoard(getClientView());
        //TODO aggiungere attributo che indica il numero di tile massimo
        Scanner scanner=new Scanner(System.in);
        boolean continueToAsk = true;

        while (continueToAsk) {
          Commands commands=checkCommand(0);
          if(commands==null){
            continue;
          }
          switch (commands) {
              case SELECT_FROM_BOARD1:
                  selectTile();
                  break;
              case SELECT_FROM_BOARD2:
                  resetChoice(0);
                  break;
              case SELECT_FROM_BOARD3:
                  resetChoice(1);
                  break;
              case SELECT_FROM_BOARD4:
                  if(!getClientView().getCoordinatesSelected().isEmpty()){
                      Colors.colorize(Colors.GAME_INSTRUCTION, "Confirmation successful.");
                      continueToAsk = false;
                  }
                 break;
              default:
                  handleInvalidPhase(commands);
                continue;
            }

            if (getClientView().getCoordinatesSelected().isEmpty()) {
                //printerBoard.printMatrixBoard(getClientView());
                Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
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
            case PRINT2 -> printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3,1,60,false,false,0);
            case PRINT3 -> printerBookshelfAndPersonal.printPersonal(getClientView(),2,35);
            case PRINT4 -> printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(),3,1,60,true,false,0);
            case PRINT5 -> printerCommonGoalAndPoints.printPoints(getClientView());
            case PRINT6 -> printerCommonGoalAndPoints.printCommonGoalCards(getClientView());
            case PRINT7 -> printerStartAndEndTurn.rulesGame();
            //TODO qua vanno inserite anche le common e i punti
        }
    }

    private void selectTile() {
        int x, y;
        ErrorType error=checkNumTilesSelectedBoard();
        if (error!=null) {
            Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.TOO_MANY_TILES.getErrorMessage());
            System.out.println();
        }else{
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert row",14);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(x): ");
            x = scanner.nextInt();
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert column",14);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(y): ");
            y = scanner.nextInt();
            scanner.nextLine();
            error= checkCoordinates(x, y);
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

    private void resetChoice(int lastOrAll) {
        ErrorType error=resetChoiceBoard(lastOrAll);
        if (error==null) {
            printerBoard.printMatrixBoard(getClientView());
            Colors.colorize(Colors.GAME_INSTRUCTION, "Reset successful\n");
        }
    }
    public void handleInvalidPhase(Commands commands) throws Exception {
        String commandString = commands.toString();
        if (commandString.toLowerCase().startsWith("print")) {
            printCommands(commands);
        } else Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.ILLEGAL_PHASE.getErrorMessage());
    }

    private int sizetile=3;
    private int sizeLenghtFromBordChoiceItem = 20;
    private int distanceBetweenTilesChoice = 4;
    @Override
    public int[] askOrder() throws Exception {
        System.out.println();
        Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: ORDER TILES");
        System.out.println();
        createItemTileView();
        //insertTiles(2);
        //Colors.colorize(Colors.GAME_INSTRUCTION,"ORDER TILES " );
        boolean continueToAsk = true;
        int[] orderTiles = new int[getClientView().getCoordinatesSelected().size() / 2];
        orderTiles[0]=-1;
        ErrorType error = ErrorType.INVALID_ORDER_TILE_NUMBER;
        while (continueToAsk) {
            Commands commands = checkCommand(1);
            if (commands == null) {
                continue;
            }
            switch (commands) {
                case ORDER_TILES1:
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, true, false, 0);

                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert numbers from 0 to " + (getClientView().getCoordinatesSelected().size() / 2 - 1) + "\n");
                    System.out.println();
                    Colors.colorize(Colors.GAME_INSTRUCTION, "These are the tiles selected by YOU: ");
                    int j = 0;
                    for (ItemTileView t : getClientView().getTilesSelected()) {
                        Colors.colorize(Colors.RED_CODE, Integer.toString(j++) + " ");
                        System.out.print(Colors.printTiles(t.getTypeView(), sizetile));
                        Colors.colorize(Colors.GAME_INSTRUCTION, "; ");
                    }
                    System.out.println();

                    while (error != null) {

                        for (int i = 0; i < getClientView().getCoordinatesSelected().size() / 2; i++) {
                            Colors.colorize(Colors.GAME_INSTRUCTION, "Insert number: ");
                            orderTiles[i] = scanner.nextInt();
                        }
                        error = checkPermuteSelection(orderTiles);
                        if (error != null) {
                            Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                            System.out.println();
                        }
                    }

                    continue;
                case ORDER_TILES2:
                    if(error!=null){
                        Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                    }
                    Colors.colorize(Colors.WHITE_CODE, "Choice has been reset");
                    error = ErrorType.INVALID_ORDER_TILE_NUMBER;
                    continue;
                case ORDER_TILES3:
                    if(error!=null){
                        Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        continue;
                    }
                    continueToAsk=false;
                    getClientView().setOrderTiles(orderTiles);
                    permuteSelection();
                    break;
                default:
                    handleInvalidPhase(commands);
                    continue;
            }

            askColumn();
        }
        return orderTiles;
    }

    @Override
    public int[] askColumn() throws Exception {
        System.out.println();
        Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: COLUMN");
        System.out.println();
        ErrorType error = ErrorType.INVALID_COLUMN;
        int[] column = new int[1];
        boolean continueToAsk = true;
        while (continueToAsk) {
            Commands commands = checkCommand(2);
            if (commands == null) {
                continue;
            }
            switch (commands) {
                case COLUMN1:

                    //printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 2, 40, false, true, 50);
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, false, true, 50);
                    while (error != null) {
                        Colors.colorize(Colors.GAME_INSTRUCTION, "To select a column write a number from 0 to " + (getClientView().getBookshelfView()[0].length - 1) + ": ");
                        column[0] = scanner.nextInt();
                        error = checkBookshelf(column[0]);
                        if (error != null) {
                            Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                            System.out.println();
                            error=ErrorType.INVALID_COLUMN;
                        }
                    }
                    continue;
                case COLUMN2:
                    if (error!=null) {
                        Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        continue;
                    }
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Choice has been reset");
                    error=ErrorType.INVALID_COLUMN;
                    continue;
                case COLUMN3:
                    if (error!=null) {
                        Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        continue;
                    }
                    //continueToAsk = false;
                    getClientView().setColumn(column[0]);
                    permuteSelection();
                    insertTiles(column[0]);
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, false, false, 0);
                    continue;
                default:
                    handleInvalidPhase(commands);
                    break;
            }
        }
        return column;
    }

    @Override
    public boolean endGame() {
        printerStartAndEndTurn.endGame(getClientView());
        return false;
    }

    @Override
    public void Setup() {
        printerStartAndEndTurn.initialLobby();
    }

    @Override
    public String getNickname() {
        return null;
    }
    public PrinterBookshelfAndPersonal getPrinterBookshelfAndPersonal() {
        return printerBookshelfAndPersonal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void start()  {
        Colors.colorize(Colors.YELLOW_CODE,"QUESTA E LA CLI");
        printerStartAndEndTurn.initialLobby();
    }

}


