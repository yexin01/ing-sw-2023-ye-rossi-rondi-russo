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
import java.util.concurrent.Semaphore;

import static java.lang.System.out;

//TODO molti comandi scritti qua li sposterò sugli handler li ho utilizzati per vedere come venivano stampate le varie fasi del turno
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
    public <T extends Enum<T> & Commands> void printLobbyCommands(Class<T> enumClass) throws Exception {
        T[] enumValues = enumClass.getEnumConstants();
        out.println();
        for(T enumValue : enumValues){
            Colors.colorizeSize(Colors.GAME_INSTRUCTION, "•["+(enumValue.ordinal()+1)+"]",5);
            Colors.colorizeSize(Colors.GAME_INSTRUCTION,enumValue.getCommand(), 30);
            Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");
            if((enumValue.ordinal()+1)%2==0){
                out.println();
            }
            //Colors.colorize(Colors.GAME_INSTRUCTION, "┃ ");

        }
        out.println();
        Colors.colorize(Colors.GAME_INSTRUCTION,"Insert command: ");
    }

    public void allCommands(int phase) throws Exception {
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
        Commands commands;
        int enumSize=-1;
        if(phase==-2){
            printLobbyCommands(CommandsEndGame.class);
            enumSize=CommandsEndGame.values().length;
        }else if(phase==-1){
            printLobbyCommands(CommandsLobby.class);
            enumSize=CommandsLobby.values().length;
        }else{
            allCommands(phase);
            enumSize=CommandsTurn.values().length;
        }
        input = scanner.nextInt();
        input--;
        scanner.nextLine();

        if(!(input >= 0 && input < enumSize)){
            Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.INVALID_INPUT.getErrorMessage());
            return null;
        }
        out.println();
        if(phase==-2){
            return CommandsEndGame.values()[input];
        }
        if(phase==-1){
            return CommandsLobby.values()[input];
        }else return CommandsTurn.values()[input];
    }


    @Override
    public void askCoordinates() throws Exception {
        PrinterLogo.printYourTurnPhase();
        out.println();
        //Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: SELECT FROM BOARD");
        PrinterLogo.printBoardPhase();
        out.println();
        ArrayList<Integer> selection=new ArrayList<>();
        //getClientView().setCoordinatesSelected(new ArrayList<>());
        printerBoard.printMatrixBoard(getClientView().getBoardView(),null);
        //TODO aggiungere attributo che indica il numero di tile massimo
        Scanner scanner=new Scanner(System.in);
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
/*
            if (selection.isEmpty()) {
                printerBoard.printMatrixBoard(getClientView().getBoardView(),selection);
                Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
            } else {

 */         if (!selection.isEmpty()) {
                //printerBoard.printMatrixBoard(getClientView().getBoardView(),selection);
                Colors.colorize(Colors.GAME_INSTRUCTION, "Your current selections: ");
                for (int i = 0; i < selection.size(); i += 2) {
                    int x = selection.get(i);
                    int y = selection.get(i + 1);
                    Colors.colorize(Colors.GAME_INSTRUCTION, "(" + x + ", " + y + ") ");
                    out.print(Colors.printTiles(getClientView().getBoardView()[x][y].getType(), 3));
                    Colors.colorize(Colors.GAME_INSTRUCTION, "; ");
                    // }
                    out.println();
                }
            }
        }

    }


    private void printCommands(CommandsTurn commandsTurn) throws Exception {
        switch (commandsTurn){
            case PRINT1 -> printerBoard.printMatrixBoard(getClientView().getBoardView(),null);
            case PRINT2 -> printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3,1,60,false,false,0);
            case PRINT3 -> printerBookshelfAndPersonal.printPersonal(getClientView(),2,35);
            case PRINT4 -> printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(),3,1,60,true,false,0);
            case PRINT5 -> printerCommonGoalAndPoints.printPoints(getClientView());
            //TODO poi gli passero i valori delle common
            case PRINT6 -> printerCommonGoalAndPoints.printCommonGoalCards(1,2);
            case PRINT7 -> printerStartAndEndTurn.rulesGame();
            //case PRINT8 -> clientView.somethingWrong();
        }
    }

    private ArrayList<Integer> selectTile(ArrayList<Integer> selection) {
        int x, y;
        ErrorType error= Check.checkNumTilesSelectedBoard(selection,clientView.getBookshelfView());
        if (error!=null) {
            Colors.colorize(Colors.ERROR_MESSAGE,ErrorType.TOO_MANY_TILES.getErrorMessage());
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
                Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                return selection;
            }
            selection.add(x);
            selection.add(y);
            error= Check.checkSelectable(selection, getClientView().getBoardView());
            if (error!=null) {
                //printerBoard.printMatrixBoard(getClientView().getBoardView(),selection);
                Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
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
        } else Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.ILLEGAL_PHASE.getErrorMessage());
        return false;
    }

    private int sizetile=3;
    private int sizeLenghtFromBordChoiceItem = 20;
    private int distanceBetweenTilesChoice = 4;
    @Override
    public void askOrder() throws Exception {
        out.println();
        //Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: ORDER TILES");
        PrinterLogo.printOrderPhase();
        out.println();
        //clientView.setTilesSelected(Check.createItemTileView(clientView.getCoordinatesSelected(), clientView.getBoardView()));
        //insertTiles(2);
        //Colors.colorize(Colors.GAME_INSTRUCTION,"ORDER TILES " );
        boolean continueToAsk = true;
        int[] orderTiles = new int[getClientView().getCoordinatesSelected().size() / 2];
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

                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert numbers from 0 to " + (getClientView().getCoordinatesSelected().size() / 2 - 1) + "\n");
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

                        for (int i = 0; i < getClientView().getCoordinatesSelected().size() / 2; i++) {
                            Colors.colorize(Colors.GAME_INSTRUCTION, "Insert number: ");
                            orderTiles[i] = scanner.nextInt();
                        }
                        error = Check.checkPermuteSelection(orderTiles,clientView.getTilesSelected());
                        if (error != null) {
                            Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                            out.println();
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
    public void askColumn() throws Exception {
        out.println();
        //Colors.colorize(Colors.ERROR_MESSAGE, "PHASE: COLUMN");
        PrinterLogo.printColumnPhase();
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
                            Colors.colorize(Colors.ERROR_MESSAGE, error.getErrorMessage());
                            out.println();
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
        Colors.colorize(Colors.ERROR_MESSAGE, error);
        out.println();
    }

    @Override
    public void displayMessage(String error) {
        Colors.colorize(Colors.GREEN_CODE, error);
    }


    @Override
    public void askNicknameAndConnection() throws Exception {
        //Colors.colorize(Colors.YELLOW_CODE,"QUESTA E LA TUA NUOVA LA CLI");
        PrinterLogo.printMyShelfieLogo();
        initialLobby();
    }


    @Override
    public void askLobbyDecision() throws Exception {
        PrinterLogo.printGlobalLobbyPhase();
        out.println();
       // Colors.colorize(Colors.ERROR_MESSAGE,"Welcome to Lobby what do you want to do?\n");
        boolean continueToAsk = true;
        MessageHeader header=new MessageHeader(MessageType.LOBBY, getClientView().getNickname());
        MessagePayload payload = null;
        Message message;
        CommandsLobby commandsLobby = null;
        while (continueToAsk) {
            commandsLobby = (CommandsLobby) checkCommand(-1);
            if (commandsLobby == null) {
                Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.INVALID_INPUT.getErrorMessage());
                continue;
            }
            Colors.colorize(Colors.BLUE_CODE, "You choose " + commandsLobby.getCommand() + " ");
            out.println();
            switch (commandsLobby) {
                case CREATE_GAME_LOBBY -> {

                    payload = new MessagePayload(KeyLobbyPayload.CREATE_GAME_LOBBY);
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert number of players for the new Lobby: ");
                    int num = in.nextInt();
                    clientView.lobby(KeyLobbyPayload.CREATE_GAME_LOBBY,num);
                    //out.printf(String.valueOf(num));
                    /*
                    payload = new MessagePayload(KeyLobbyPayload.CREATE_GAME_LOBBY);
                    payload.put(Data.VALUE_CLIENT, num);

                     */
                    continueToAsk=false;


                }
                case JOIN_SPECIFIC_GAME_LOBBY -> {
                    Colors.colorize(Colors.GAME_INSTRUCTION, "Insert id Lobby you want to join: ");
                    int num = in.nextInt();
                    clientView.lobby(KeyLobbyPayload.JOIN_SPECIFIC_GAME_LOBBY,num);
                    continueToAsk=false;
                    //out.printf(String.valueOf(num));
                    //payload = new MessagePayload(KeyLobbyPayload.JOIN_SPECIFIC_GAME_LOBBY);
                    //payload.put(Data.VALUE_CLIENT, num);
                }
                case JOIN_RANDOM_GAME_LOBBY -> {
                    clientView.lobby(KeyLobbyPayload.JOIN_RANDOM_GAME_LOBBY,-1);
                    continueToAsk=false;
                    //payload = new MessagePayload(KeyLobbyPayload.JOIN_RANDOM_GAME_LOBBY);
                }
                /*
                case RESET_CHOICE ->{
                    if(payload == null){
                        Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                    }else{
                        payload=null;
                        Colors.colorize(Colors.BLUE_CODE,"Your choice has been reset");
                    }
                }
                case CONFIRM_CHOICE -> {
                    if (payload == null) {
                        Colors.colorize(Colors.ERROR_MESSAGE, ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                    } else continueToAsk = false;
                }

                 */
            }
        }
        //clientView.setLobbyDecision(new Message(header,payload));


        message=new Message(header,payload);
        //return message;
    }

    @Override
    public boolean endGame() throws Exception {
        CommandsEndGame commandsEndGame=(CommandsEndGame) checkCommand(-2);
        switch(commandsEndGame){
            case QUIT_GAME -> {
                out.println("you choose quit");
                clientView.endGame(1);
            }
            case JOIN_NEW_GAME ->{
                out.println("you choose new game");
                clientView.endGame(0);
            }

        }
        printerStartAndEndTurn.endGame(getClientView());
        return false;
    }


    public void Setup() {
        //printerStartAndEndTurn.initialLobby();
    }



    public PrinterBookshelfAndPersonal getPrinterBookshelfAndPersonal() {
        return printerBookshelfAndPersonal;
    }



    public void initialLobby(){
        //printMyShelfieLogo();
        doConnection();
    }

    private void doConnection(){
        int connectionType = -1;

        String nickname = askNickname();
        out.println("Hi "+ nickname +"!");

        connectionType = askConnection();
        if (connectionType == 0) {
            out.println("You chose Socket connection\n");
        } else if (connectionType == 1){
            out.println("You chose RMI connection\n");
        } else {
            out.println("Invalid connection");
            doConnection();
        }


        String ip = askIp();
        int port = askPort(connectionType);

        out.println("Server Ip Address: " + ip);
        out.println("Server Port: " + port + "\n");
        ClientHandler clientHandler=new ClientHandler();
        try{
            //metodo di Clienthanlder (la cli estende ClientHandler)
            clientHandler.createConnection(connectionType, ip, port,this);
            out.println("Connection created");
        } catch (Exception e){
            out.println("Error in creating connection. Please try again.\n");
            doConnection();
        }


    }

    private String askNickname(){
        Colors.colorize(Colors.WHITE_CODE,"Enter your username: ");
        String nickname=in.nextLine().toLowerCase();
        getClientView().setNickname(nickname);
        return nickname;
    }

    private int askConnection(){
        int connectionType = -1;
        do{
            out.println("Enter your connection type: (0 for Socket, 1 for RMI) ");
            connectionType = in.nextInt();
            in.nextLine(); // aggiungo questa riga per consumare il carattere di fine riga rimanente
        } while (connectionType != 0 && connectionType != 1);
        return connectionType;
    }
    //TODO questo i stess come quello sotto poi ci accordiamo su dove metterlo
    private String askIp() {
        String defaultIp = "127.0.0.1";
        out.println("Enter the server Ip Address (default " + defaultIp + "): (press Enter button to choose default)");
        in.reset();

        do {
            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return defaultIp;
                } else {
                    try {
                        InetAddress address = InetAddress.getByName(line);
                        return address.getHostAddress();
                    } catch (UnknownHostException e) {
                        out.println("Invalid IP address. Please enter a valid IP address or press Enter to choose the default.");
                    }
                }
            } else {
                in.nextLine();
                out.println("Invalid input. Please enter a valid IP address or press Enter to choose the default.");
            }
        } while (true);
    }
    //TODO poi questa funzione la spostamo sulla CLI ho il metodo che crea la connessione chiede il soprannome, crea gli handler...
    private int askPort(int connectionType) {
        int defaultPort = (connectionType == 0 ? 51634 : 51633);
        out.println("Enter the server port (default " + defaultPort + "): (press Enter button to choose default)");
        in.reset();

        do {
            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return defaultPort;
                } else {
                    try {
                        int port = Integer.parseInt(line);
                        if (port >= 1024 && port <= 65535) {
                            return port;
                        } else {
                            out.println("Invalid port number. Please enter a port number between 1024 and 65535.");
                        }
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Please enter a valid port number.");
                    }
                }
            } else {
                in.nextLine();
                out.println("Invalid input. Please enter a valid port number.");
            }
        } while (true);
    }
/*
    @Override
    public void endTurn(boolean phase) throws Exception {
        // printerBoard.printMatrixBoard(getClientView());
        //Colors.colorize(Colors.ERROR_MESSAGE, "FINE TURNO");
        PrinterLogo.printWaitingTurnPhase();
        while(phase){
            CommandsTurn commandsTurn = (CommandsTurn)checkCommand(3);
            if (commandsTurn == null) {
                continue;
            }
            handleInvalidPhase(commandsTurn);
        }
    }

 */

    boolean continueToAskEndTurn;

    @Override
    public void start() throws Exception {
        this.continueToAskEndTurn=continueToAskEndTurn;
        PrinterLogo.printWaitingTurnPhase();
        /*
        this.continueToAskEndTurn=continueToAskEndTurn;
        while (continueToAskEndTurn) {
            CommandsTurn commandsTurn = (CommandsTurn)checkCommand(3);
        }

         */



    }



    @Override
    public void stop() {
        semaphore.release();
    }
    private Semaphore semaphore = new Semaphore(0);

    public ClientView getClientView() {
        return clientView;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
}


