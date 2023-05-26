package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.ItemTileView;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class CLI implements ClientInterface {

    private Scanner in = new Scanner(System.in);
    private ClientView clientView;

    private Scanner scanner;
    private PrinterBoard printerBoard;
    private PrinterBookshelfAndPersonal printerBookshelfAndPersonal;
    private PrinterStartAndEndTurn printerStartAndEndTurn;
    private PrinterCommonGoalAndPoints printerCommonGoalAndPoints;

    public CLI(){
        this.scanner= new Scanner(System.in);
        this.clientView=new ClientView();
        printerBoard=new PrinterBoard();
        printerBookshelfAndPersonal=new PrinterBookshelfAndPersonal();
        printerStartAndEndTurn =new PrinterStartAndEndTurn();
        printerCommonGoalAndPoints=new PrinterCommonGoalAndPoints();
    }



    public synchronized <T extends Enum<T> & Commands> void printLobbyCommands(Class<T> enumClass) throws Exception {
        T[] enumValues = enumClass.getEnumConstants();
        out.println();
        for(T enumValue : enumValues){
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "•["+(enumValue.ordinal()+1)+"]",5);
            Colors.colorizeSize(Colors.GAME_INSTRUCTION,enumValue.getCommand(), 30);
            Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
        }
        out.println();
        Colors.colorize(Colors.GAME_INSTRUCTION,"Insert command: ");
    }

    public synchronized void allCommands(int phase) throws Exception {
        boolean firstPrint=false;
        out.println();
        String[] commandsPhase=new String[]{"select_from_board","order_tiles","column","print" };
        String[] titlePhase=new String[]{"BOARD","ORDER","COLUMN","INFO PLAYER" };
        //String option="Select a command: ";
        //Colors.colorize(Colors.GAME_INSTRUCTION,option );
        out.println();
        for(int i=0;i<titlePhase.length;i++){
            if(phase==i || i==commandsPhase.length-1){
                Colors.colorizeSize(Colors.GAME_INSTRUCTION,titlePhase[i], 30+5);
            }else Colors.colorizeSize(Colors.BLACK_CODE,titlePhase[i], 30+5);
            Colors.printFreeSpaces(2);
            //Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");

        }
        out.println();
        int i=0;
        int typeCommand=0;
        for (CommandsTurn command : CommandsTurn.values()) {
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
                    if(command.equals(CommandsTurn.values()[CommandsTurn.values().length-1])){
                        out.println();
                    }
                }else {
                    firstPrint=false;
                    Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
                    out.println();
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
        int enumSize = -1;

        if (phase == -1) {
            printLobbyCommands(CommandsLobby.class);
            enumSize = CommandsLobby.values().length;
        } else {
            allCommands(phase);
            enumSize = CommandsTurn.values().length;
        }

        String userInput = scanner.nextLine();

        try {
            input = Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException e) {
            displayError(ErrorType.INVALID_INPUT.getErrorMessage());
            return null;
        }

        if (!(input >= 0 && input < enumSize)) {
            displayError(ErrorType.INVALID_INPUT.getErrorMessage());
            return null;
        }

        out.println();

        if (phase == -1) {
            return CommandsLobby.values()[input];
        } else {
            return CommandsTurn.values()[input];
        }
    }


    @Override
    public  synchronized  void askCoordinates() throws Exception {
        out.println();
        PrinterLogo.printBoardPhase(50);
        out.println();
        ArrayList<Integer> selection=new ArrayList<>();
        printerBoard.printMatrixBoard(getClientView().getBoardView(),null);
        boolean continueToAsk = true;

        while (continueToAsk) {
            CommandsTurn commandsTurn =(CommandsTurn) checkCommand(0);
            if(commandsTurn ==null){
                continue;
            }
            switch (commandsTurn) {
                case SELECT_FROM_BOARD1:
                    selection=selectTile(selection );
                    break;
                case SELECT_FROM_BOARD2:
                    resetChoice(0,selection);
                    break;
                case SELECT_FROM_BOARD3:
                    resetChoice(1,selection);
                    break;
                case SELECT_FROM_BOARD4:
                    if(!selection.isEmpty()){
                        Colors.colorize(Colors.GAME_INSTRUCTION, "Confirmation successful.");
                        clientView.setCoordinatesSelected(selection);;
                        clientView.setTilesSelected(Check.createItemTileView(selection, clientView.getBoardView()));
                        continueToAsk = false;
                    }
                    break;

                default:
                    if(handleInvalidPhase(commandsTurn)){
                        continueToAsk = false;
                    }
                    continue;
            }
            if (!selection.isEmpty()) {
                Colors.colorize(Colors.GAME_INSTRUCTION, "Your current selections: ");
                for (int i = 0; i < selection.size(); i += 2) {
                    int x = selection.get(i);
                    int y = selection.get(i + 1);
                    Colors.colorize(Colors.GAME_INSTRUCTION, "(" + x + ", " + y + ") ");
                    out.print(Colors.printTiles(getClientView().getBoardView()[x][y].getType(), 3));
                    Colors.colorize(Colors.GAME_INSTRUCTION, "; ");
                    out.println();
                }
            }
        }

    }


    public void printCommands(CommandsTurn commandsTurn) throws Exception {
        switch (commandsTurn){
            case PRINT1 ->{
                PrinterLogo.printBoardLogo(10);
                printerBoard.printMatrixBoard(getClientView().getBoardView(),null);
            }
            case PRINT2 ->{
                PrinterLogo.printBookshelfLogo(10);
                printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3,1,60,false,false,0);

            }
            case PRINT3 -> {
                printerBookshelfAndPersonal.printPersonal(getClientView(),2,35);
            }
            case PRINT4 ->{
                PrinterLogo.printBookshelfLogo(10);
                printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(),3,1,60,true,false,0);

            }
            case PRINT5 -> printerCommonGoalAndPoints.printPoints(getClientView());
            case PRINT6 -> printerCommonGoalAndPoints.printCommonGoalCards(getClientView());
            case PRINT7 -> printerStartAndEndTurn.rulesGame();
            case PRINT9 -> System.exit(0);
        }
    }

    private ArrayList<Integer> selectTile(ArrayList<Integer> selection) {
        int x, y;
        ErrorType error= Check.checkNumTilesSelectedBoard(selection,clientView.getBookshelfView());
        if (error!=null) {
            displayError(error.getErrorMessage());
            //Colors.colorize(Colors.ERROR_MESSAGE,
            out.println();
        }else{
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert row",14);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(x): ");
            x = scanner.nextInt();
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "Insert column",14);
            Colors.colorize(Colors.GAME_INSTRUCTION, "(y): ");
            y = scanner.nextInt();
            scanner.nextLine();
            error= Check.checkCoordinates(x, y, clientView.getBoardView());
            if(error!=null){
                displayError(error.getErrorMessage());
                //Colors.colorize(Colors.ERROR_MESSAGE,
                return selection;
            }
            selection.add(x);
            selection.add(y);
            error= Check.checkSelectable(selection, getClientView().getBoardView());
            if (error!=null) {
                //printerBoard.printMatrixBoard(getClientView().getBoardView(),selection);
                displayError(error.getErrorMessage());
                //Colors.colorize(Colors.ERROR_MESSAGE,
                selection.remove(selection.size()-1);
                selection.remove(selection.size()-1);
                out.println();
            }else {
                printerBoard.printMatrixBoard(getClientView().getBoardView(),selection);
            }
        }
        return selection;
    }

    private void resetChoice(int lastOrAll,ArrayList<Integer> coordinatesSelected) {
        ErrorType error=Check.resetChoiceBoard(lastOrAll,coordinatesSelected);
        if (error==null) {
            printerBoard.printMatrixBoard(getClientView().getBoardView(),coordinatesSelected);
            Colors.colorize(Colors.GAME_INSTRUCTION, "Reset successful\n");
        }
    }
    public boolean handleInvalidPhase(CommandsTurn commandsTurn) throws Exception {
        String commandString = commandsTurn.toString();
        if (commandString.toLowerCase().startsWith("print")) {
            if(commandsTurn.equals(CommandsTurn.PRINT8)){
                clientView.somethingWrong();
                return true;
            }else{
                printCommands(commandsTurn);

            }
        } else displayError(ErrorType.ILLEGAL_PHASE.getErrorMessage());
            //Colors.colorize(Colors.ERROR_MESSAGE,
        return false;
    }

    private int sizetile=3;
    @Override
    public  synchronized void askOrder() throws Exception {
        out.println();
        PrinterLogo.printOrderPhase(50);
        out.println();
        boolean continueToAsk = true;
        int[] orderTiles = new int[getClientView().getTilesSelected().length];
        orderTiles[0]=-1;
        ErrorType error = ErrorType.INVALID_ORDER_TILE_NUMBER;
        while (continueToAsk) {
            CommandsTurn commandsTurn =(CommandsTurn) checkCommand(1);
            if (commandsTurn == null) {
                continue;
            }
            switch (commandsTurn) {
                case ORDER_TILES1:
                    //printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, true, false, 0);

                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert numbers from 0 to " + (getClientView().getTilesSelected().length - 1) + "\n");
                    out.println();
                    Colors.colorize(Colors.GAME_INSTRUCTION, "These are the tiles selected by YOU: ");
                    int j = 0;
                    for (ItemTileView t : getClientView().getTilesSelected()) {
                        Colors.colorize(Colors.RED_CODE, Integer.toString(j++) + " ");
                        out.print(Colors.printTiles(t.getTypeView(), sizetile));
                        Colors.colorize(Colors.GAME_INSTRUCTION, "; ");
                    }
                    out.println();

                    while (error != null) {

                        for (int i = 0; i < getClientView().getTilesSelected().length; i++) {
                            Colors.colorize(Colors.GAME_INSTRUCTION, "Insert number: ");
                            orderTiles[i] = scanner.nextInt();
                        }
                        error = Check.checkPermuteSelection(orderTiles,clientView.getTilesSelected());
                        if (error != null) {
                            displayError(error.getErrorMessage());
                            //Colors.colorize(Colors.ERROR_MESSAGE,
                            out.println();
                        }
                    }

                    continue;
                case ORDER_TILES2:
                    if(error!=null){
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        //Colors.colorize(Colors.ERROR_MESSAGE,
                    }
                    Colors.colorize(Colors.WHITE_CODE, "Choice has been reset");
                    error = ErrorType.INVALID_ORDER_TILE_NUMBER;
                    continue;
                case ORDER_TILES3:
                    if(error!=null){
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        //Colors.colorize(Colors.ERROR_MESSAGE,
                        continue;
                    }
                    continueToAsk=false;
                    getClientView().setOrderTiles(orderTiles);
                    clientView.setTilesSelected(Check.permuteSelection(clientView.getTilesSelected(),clientView.getOrderTiles()));
                    break;
                default:
                    if(handleInvalidPhase(commandsTurn)){
                        continueToAsk = false;
                    }
            }

        }
    }

    @Override
    public  synchronized void askColumn() throws Exception {
        out.println();
        //Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: COLUMN");
        PrinterLogo.printColumnPhase(50);
        out.println();
        ErrorType error = ErrorType.INVALID_COLUMN;
        int column=-1;
        boolean continueToAsk = true;
        while (continueToAsk) {
            CommandsTurn commandsTurn = (CommandsTurn)checkCommand(2);
            if (commandsTurn == null) {
                continue;
            }
            switch (commandsTurn) {
                case COLUMN1:

                    //printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 2, 40, false, true, 50);
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, false, true, 50);
                    while (error != null) {
                        Colors.colorize(Colors.GAME_INSTRUCTION, "To select a column write a number from 0 to " + (getClientView().getBookshelfView()[0].length - 1) + ": ");
                        column = scanner.nextInt();
                        error = Check.checkBookshelf(column,clientView.getBookshelfView(),clientView.getTilesSelected());
                        if (error != null) {
                            displayError(error.getErrorMessage());
                            //Colors.colorize(Colors.ERROR_MESSAGE,
                            out.println();
                            error=ErrorType.INVALID_COLUMN;
                        }
                    }
                    continue;
                case COLUMN2:
                    if (error!=null) {
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        //Colors.colorize(Colors.ERROR_MESSAGE,
                        continue;
                    }
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Choice has been reset");
                    error=ErrorType.INVALID_COLUMN;
                    continue;
                case COLUMN3:
                    if (error!=null) {
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        //Colors.colorize(Colors.ERROR_MESSAGE,
                        continue;
                    }
                    //continueToAsk = false;
                    getClientView().setColumn(column);
                    Check.insertTiles(column,clientView.getBookshelfView(),clientView.getTilesSelected());
                    continueToAsk=false;
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, false, false, 0);
                    continue;
                default:
                    if(handleInvalidPhase(commandsTurn)){
                        continueToAsk = false;
                    }
            }
        }

    }

    @Override
    public void displayError(String error) {
        out.println();
        Colors.printCharacter("✘  ",1,Colors.ERROR_MESSAGE);
        Colors.colorize(Colors.ERROR_MESSAGE, error);
        Colors.printCharacter("  ✘",1,Colors.ERROR_MESSAGE);
        out.println();
    }

    @Override
    public void displayMessage(String string) {
        out.println();
        Colors.printCharacter("► ",1,Colors.WHITE_CODE);
        Colors.colorize(Colors.BLUE_CODE, string);
        Colors.printCharacter(" ◄",1,Colors.WHITE_CODE);
        out.println();
    }


    @Override
    public void askNicknameAndConnection() throws Exception {
        PrinterLogo.printMyShelfieLogo();
        initialLobby();
    }


    @Override
    public void askLobbyDecision() throws Exception {
        PrinterLogo.printGlobalLobbyPhase(50);
        out.println();
        boolean continueToAsk = true;
        CommandsLobby commandsLobby = null;
        while (continueToAsk) {
            commandsLobby = (CommandsLobby) checkCommand(-1);
            if (commandsLobby == null) {
                //printError(ErrorType.INVALID_INPUT.getErrorMessage());
                //Colors.colorize(Colors.ERROR_MESSAGE,
                continue;
            }
            Colors.colorize(Colors.BLUE_CODE, "You choose " + commandsLobby.getCommand() + " ");
            out.println();
            switch (commandsLobby) {
                case CREATE_GAME_LOBBY -> {
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert number of players for the new Lobby: ");
                    int num = in.nextInt();
                    clientView.lobby(KeyLobbyPayload.CREATE_GAME_LOBBY,num);
                    continueToAsk=false;


                }
                case JOIN_SPECIFIC_GAME_LOBBY -> {
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert id Lobby you want to join: ");
                    int num = in.nextInt();
                    clientView.lobby(KeyLobbyPayload.JOIN_SPECIFIC_GAME_LOBBY,num);
                    continueToAsk=false;
                }
                case JOIN_RANDOM_GAME_LOBBY -> {
                    clientView.lobby(KeyLobbyPayload.JOIN_RANDOM_GAME_LOBBY,-1);
                    continueToAsk=false;
                }
                case QUIT_SERVER -> {
                    clientView.lobby(KeyLobbyPayload.QUIT_SERVER,-1);
                    System.exit(0);
                }
            }
        }
    }


    @Override
    public void endGame(int[] personalPoints) throws Exception {
        PrinterLogo.printWinnerLogo(50);
        printerCommonGoalAndPoints.printEndGame(clientView,personalPoints);
        clientView.receiveEndGame();
        boolean continueToAsk=true;

        while(continueToAsk){
            out.println();
            Colors.colorize(Colors.GAME_INSTRUCTION, "Write |ok| to return to lobby:  ");
            String input = scanner.next();
            if (input.equals("ok")){
                continueToAsk=false;
            }
            scanner.nextLine();
        }
        out.println();
        askLobbyDecision();

    }

    @Override
    public void onlyPlayer() {
        displayError(ErrorType.ONLY_PLAYER.getErrorMessage());
        PrinterLogo.onlyPlayer(10);
    }

    public PrinterBookshelfAndPersonal getPrinterBookshelfAndPersonal() {
        return printerBookshelfAndPersonal;
    }

    boolean continueToAskEndTurn;

    @Override
    public synchronized void waitingRoom() throws Exception {
        Colors.colorize(Colors.GAME_INSTRUCTION, "Turn player: "+clientView.getTurnPlayer());
        PrinterLogo.printWaitingTurnPhase(50);
        Colors.colorize(Colors.GAME_INSTRUCTION, "This is the board\n ");
        printCommands(CommandsTurn.PRINT1);
        printCommands(CommandsTurn.PRINT4);
    }

    @Override
    public void displayToken(int num, String nickname) {
        printerCommonGoalAndPoints.printToken(num,nickname);
    }


    public ClientView getClientView() {
        return clientView;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    public void initialLobby(){
        doConnection();
    }

    private void doConnection(){
        int connectionType = -1;

        String nickname = askNickname();
        Colors.colorize(Colors.BLUE_CODE,"\nHi "+ nickname +"!\n" );
        connectionType = askConnection();
        if (connectionType == 0) {
            Colors.colorize(Colors.BLUE_CODE,"\nYou chose Socket connection\n");

        } else Colors.colorize(Colors.BLUE_CODE,"\nYou chose RMI connection\n");


        String ip = askIp();
        int port = askPort(connectionType);
        Colors.colorize(Colors.WHITE_CODE,"Server Ip Address: " + ip);
        Colors.colorize(Colors.WHITE_CODE,"Server Port: " + port + "\n");
        ClientHandler clientHandler=new ClientHandler();
        try{ //metodo di Clienthanlder (la cli estende ClientHandler)
            clientHandler.createConnection(connectionType, ip, port,this);
            Colors.colorize(Colors.WHITE_CODE,"Connection created");
        } catch (Exception e){
            e.printStackTrace();
            //displayError("Error in creating connection. Please try again.\n");
            //doConnection();
        }
    }

    private String askNickname() {
        String nickname = "";

        do {
            Colors.colorize(Colors.WHITE_CODE, "\nEnter your username >> ");
            nickname = in.nextLine().toLowerCase();

            if (nickname.length() < 2) {
                displayError("Username should have at least 2 characters.");
            }
        } while (nickname.length() < 2);

        getClientView().setNickname(nickname);
        return nickname;
    }

    private int askConnection(){
        int connectionType = -1;

        do {
            Colors.colorize(Colors.WHITE_CODE, "\nEnter your connection type: (0 for Socket, 1 for RMI) >> ");
            try {
                connectionType = Integer.parseInt(in.next());
                in.nextLine();
            } catch (NumberFormatException e) {
                displayError(ErrorType.INVALID_INPUT.getErrorMessage());
                in.nextLine();
            }
        } while (connectionType != 0 && connectionType != 1);
        return connectionType;
    }

    private String askIp() {
        String defaultIp = "127.0.0.1";

        String ipAddress = defaultIp;
        boolean validInput = false;

        do {
            Colors.colorize(Colors.WHITE_CODE, "\nEnter the server IP Address (default " + defaultIp + ") \nPress ENTER button to choose default >> ");
            String line = in.nextLine();

            if (line.equals("")) {
                return defaultIp;
            }

            if (isValidIpAddress(line)) {
                ipAddress = line;
                validInput = true;
            } else {
                displayError("Invalid IP address. Please enter a valid IP address or press Enter to choose the default.");
            }
        } while (!validInput);

        return ipAddress;
    }

    private boolean isValidIpAddress(String ipAddress) {
        String[] components = ipAddress.split("\\.");

        if (components.length != 4) {
            return false;
        }

        for (String component : components) {
            try {
                int value = Integer.parseInt(component);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private int askPort(int connectionType) {
        int defaultPort = (connectionType == 0 ? 1100 : 1099);

        int port = defaultPort;
        boolean validInput = false;

        do {
            Colors.colorize(Colors.WHITE_CODE,"\nEnter the server port (default " + defaultPort + ") \nPress ENTER button to choose default >> ");
            String line = in.nextLine();

            if (line.equals("")) {
                return defaultPort;
            }

            try {
                port = Integer.parseInt(line);
                if (port >= 1024 && port <= 65535) {
                    validInput = true;
                } else {
                    displayError("Invalid port number. Please enter a port number between 1024 and 65535.");
                }
            } catch (NumberFormatException e) {
                displayError("Invalid input. Please enter a valid port number.");
            }
        } while (!validInput);

        return port;
    }

}


