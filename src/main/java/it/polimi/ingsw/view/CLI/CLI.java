package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.ItemTileView;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;


import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

public class CLI implements ClientInterface {

    private Scanner in = new Scanner(System.in);
    private ClientView clientView;

    private Scanner scanner;
    private PrinterBoard printerBoard;
    private PrinterBookshelfAndPersonal printerBookshelfAndPersonal;
    private PrinterCommonGoalAndPoints printerCommonGoalAndPoints;

    public CLI(){
        this.scanner= new Scanner(System.in);
        this.clientView=new ClientView();
        printerBoard=new PrinterBoard();
        printerBookshelfAndPersonal=new PrinterBookshelfAndPersonal();
        printerCommonGoalAndPoints=new PrinterCommonGoalAndPoints();
    }
    //LOBBY PHASE
    /**
     * Asks the user to make a decision in the lobby phase.
     */
    @Override
    public void askLobbyDecision() {
        PrinterLogo.printGlobalLobbyPhase();
        out.println();
        boolean continueToAsk = true;
        CommandsLobby commandsLobby = null;
        while (continueToAsk) {
            commandsLobby = (CommandsLobby) checkCommand(-1);
            if (commandsLobby == null) {
                continue;
            }
            Colors.colorize(Colors.LIGHT_BLUE_CODE, "You choose " + commandsLobby.getCommand() + " ");
            out.println();
            switch (commandsLobby) {
                case CREATE_GAME_LOBBY -> {
                    Colors.colorize(Colors.WHITE_CODE, "Insert number of players for the new Lobby: ");
                    int num = in.nextInt();
                    clientView.lobby(KeyLobbyPayload.CREATE_GAME_LOBBY,num);
                    continueToAsk=false;
                }
                case JOIN_SPECIFIC_GAME_LOBBY -> {
                    Colors.colorize(Colors.WHITE_CODE, "Insert id Lobby you want to join: ");
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

    /**
     * Prints CommandsLobby.
     *
     * @param <T>        the type of the commands enumeration
     * @param enumClass  the class of the commands enumeration
     */
    public synchronized <T extends Enum<T> & Commands> void printLobbyCommands(Class<T> enumClass){
        T[] enumValues = enumClass.getEnumConstants();
        out.println();
        for(T enumValue : enumValues){
            Colors.colorizeSize(Colors.WHITE_CODE, "·["+(enumValue.ordinal()+1)+"]",5);
            Colors.colorizeSize(Colors.WHITE_CODE,enumValue.getCommand(), 28);
            Colors.colorize(Colors.WHITE_CODE, "│ ");
        }
        out.println();
        Colors.colorize(Colors.WHITE_CODE,"Insert command: ");
    }
    /**
     * Prints CommandsTurn based on the given phase.
     * The commands of the specified phase will be printed in a different color.
     *@param phase the phase number
     */
    public synchronized void allCommands(int phase){
        boolean firstPrint=false;
        out.println();
        String[] commandsPhase=new String[]{"select_from_board","order_tiles","column","print" };
        String[] titlePhase=new String[]{"BOARD","ORDER","COLUMN","INFO PLAYER" };
        out.println();
        for(int i=0;i<titlePhase.length;i++){
            if(phase==i || i==commandsPhase.length-1){
                Colors.colorizeSize(Colors.LIGHT_BLUE_CODE,titlePhase[i], 28+5);
                //TODO
            }else Colors.colorizeSize(Colors.WHITE_CODE,titlePhase[i], 28+5);
            Colors.printFreeSpaces(2);

        }
        out.println();
        int i=0;
        int typeCommand=0;
        for (CommandsTurn command : CommandsTurn.values()) {
            String commandString = command.toString();
            while(!commandString.toLowerCase().startsWith(commandsPhase[typeCommand%(commandsPhase.length+1)])){
                String noCommands=" ";
                Colors.colorizeSize(Colors.LIGHT_BLUE_CODE,noCommands, 28+5);
                Colors.colorize(Colors.WHITE_CODE, "│ ");
                i++;
                typeCommand++;
            }
            if(commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])){
                Colors.colorizeSize(Colors.LIGHT_BLUE_CODE, "·["+(command.ordinal()+1)+"]",5);
                Colors.colorizeSize(Colors.LIGHT_BLUE_CODE,command.getCommand(), 15);
                if(!firstPrint){
                    firstPrint=true;
                    Colors.colorize(Colors.LIGHT_BLUE_CODE, "  ");
                    if(command.equals(CommandsTurn.values()[CommandsTurn.values().length-1])){
                        out.println();
                    }
                }else {
                    firstPrint=false;
                    Colors.colorize(Colors.WHITE_CODE, "│ ");
                    out.println();
                    typeCommand=0;
                }
            }else{
                if (commandString.toLowerCase().startsWith(commandsPhase[phase])/* || commandString.toLowerCase().startsWith(commandsPhase[commandsPhase.length-1])*/) {
                    Colors.colorizeSize(Colors.LIGHT_BLUE_CODE, "·["+(command.ordinal()+1)+"]",5);
                    Colors.colorizeSize(Colors.LIGHT_BLUE_CODE,command.getCommand(), 28);
                }else{
                    Colors.colorizeSize(Colors.WHITE_CODE, "·["+(command.ordinal()+1)+"]",5);
                    Colors.colorizeSize(Colors.WHITE_CODE,command.getCommand(), 28);
                }
                i++;
                typeCommand++;
                Colors.colorize(Colors.WHITE_CODE, "│ ");
            }
        }
        Colors.colorize(Colors.LIGHT_BLUE_CODE,"Insert command: ");

    }
    /**
     * Prints the corresponding view based on the provided command.
     * @param commandsTurn the command to determine the view to print
     */
    public void printCommands(CommandsTurn commandsTurn) {
        switch (commandsTurn){
            case PRINT1 ->{
                PrinterLogo.printBoardLogo();
                printerBoard.printMatrixBoard(getClientView().getBoardView(),null);
            }
            case PRINT2 ->{
                PrinterLogo.printBookshelfLogo();
                printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3,1,52,false,false,0);

            }
            case PRINT3 -> {
                printerBookshelfAndPersonal.printPersonal(getClientView(),2,35);
            }
            case PRINT4 ->{
                PrinterLogo.printBookshelfLogo();
                printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(),3,1,52,true,false,0);

            }
            case PRINT5 -> printerCommonGoalAndPoints.printPoints(getClientView());
            case PRINT6 -> printerCommonGoalAndPoints.printCommonGoalCards(getClientView());
            case PRINT7 -> PrinterLogo.printGamesRulesLogo();
            case PRINT9 -> System.exit(0);
        }
    }

    /**
     * Checks the user input for a command based on the given phase.
     *
     * @param phase the phase number
     * @return the command corresponding to the user input, or null if the input is invalid or out of range
     */
    public Commands checkCommand(int phase) {
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

        if (userInput.isEmpty()) {
            while (userInput.isEmpty()) {
                userInput = scanner.nextLine();
            }
        }

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
    /**
     * Handles an invalid phase for the given command.
     *
     * @param commandsTurn the command that triggered the invalid phase
     * @return true if the user has selected the "somethingWrong", false otherwise
     *
     * If the provided command is "somethingWrong", the server will be contacted to sets
     * all attributes in the client view.
     */
    public boolean handleInvalidPhase(CommandsTurn commandsTurn)  {
        String commandString = commandsTurn.toString();
        if (commandString.toLowerCase().startsWith("print")) {
            if(commandsTurn.equals(CommandsTurn.PRINT8)){
                clientView.somethingWrong();
                return true;
            }else{
                printCommands(commandsTurn);

            }
        } else displayError(ErrorType.ILLEGAL_PHASE.getErrorMessage());
        return false;
    }
    //SELECT FROM BOARD PHASE
    /**
     * Asks the user to enter coordinates for tile selection.
     * Displays the board and prompts for commands to select and confirm tile coordinates.
     * Continues asking for commands until a valid confirmation command is received.
     */
    @Override
    public  synchronized  void askCoordinates() {
        out.println();
        PrinterLogo.printBoardPhase();
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
                        Colors.colorize(Colors.WHITE_CODE, "Confirmation successful.");
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
                Colors.colorize(Colors.WHITE_CODE, "Your current selections: ");
                for (int i = 0; i < selection.size(); i += 2) {
                    int x = selection.get(i);
                    int y = selection.get(i + 1);
                    Colors.colorize(Colors.WHITE_CODE, "(" + x + ", " + y + ") ");
                    out.print(Colors.printTiles(getClientView().getBoardView()[x][y].getType(), 3));
                    Colors.colorize(Colors.WHITE_CODE, "; ");

                }
            }
            out.println();
        }

    }


    /**
     * Allows the user to select a tile on the board by specifying the row and column coordinates.
     *
     * @param selection the current list of selected tiles
     * @return the updated list of selected tiles after the user's successful selection, or the same
     * list if an error occurs during the selection process
     */
    private ArrayList<Integer> selectTile(ArrayList<Integer> selection) {
        int x, y;
        ErrorType error = Check.checkNumTilesSelectedBoard(selection, clientView.getBookshelfView());

        if (error != null) {
            displayError(error.getErrorMessage());
            out.println();
        } else {
            while (true) {
                try {
                    Colors.colorizeSize(Colors.WHITE_CODE, "Insert row", 14);
                    Colors.colorize(Colors.WHITE_CODE, "(x): ");
                    x = Integer.parseInt(scanner.nextLine());

                    Colors.colorizeSize(Colors.WHITE_CODE, "Insert column", 14);
                    Colors.colorize(Colors.WHITE_CODE, "(y): ");
                    y = Integer.parseInt(scanner.nextLine());

                    error = Check.checkCoordinates(x, y, clientView.getBoardView());
                    if (error != null) {
                        displayError(error.getErrorMessage());
                        out.println();
                        return selection;
                    }

                    selection.add(x);
                    selection.add(y);

                    error = Check.checkSelectable(selection, getClientView().getBoardView());
                    if (error != null) {
                        displayError(error.getErrorMessage());
                        selection.remove(selection.size() - 1);
                        selection.remove(selection.size() - 1);
                        out.println();
                    } else {
                        printerBoard.printMatrixBoard(getClientView().getBoardView(), selection);
                        break;
                    }
                } catch (NumberFormatException e) {
                    displayError("Invalid input! Please enter valid integer values for row and column.");
                    out.println();
                }
            }
        }

        return selection;
    }


    /**
     * Resets the selected tiles on the board.
     * @param lastOrAll the reset option (0 for the last selected tile, 1 for all selected tiles)
     * @param coordinatesSelected the list of currently selected tile coordinates
     */
    private void resetChoice(int lastOrAll,ArrayList<Integer> coordinatesSelected) {
        ErrorType error=Check.resetChoiceBoard(lastOrAll,coordinatesSelected);
        if (error==null) {
            printerBoard.printMatrixBoard(getClientView().getBoardView(),coordinatesSelected);
            Colors.colorize(Colors.WHITE_CODE, "Reset successful\n");
        }
    }

//ORDER PHASE
    private int sizetile=3;
    /**
     * Asks the user to input the order of the selected tiles.
     * The method displays the order phase and prompts the user to enter numbers from 0 to N-1,
     * where N is the number of selected tiles.
     * The user's input represents the desired order of the tiles.
     */
    @Override
    public  synchronized void askOrder() {
        out.println();
        PrinterLogo.printOrderPhase();
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
                    Colors.colorize(Colors.WHITE_CODE, "Insert numbers from 0 to " + (getClientView().getTilesSelected().length - 1) + "\n");
                    out.println();
                    Colors.colorize(Colors.WHITE_CODE, "These are the tiles selected by YOU: ");
                    int j = 0;
                    for (ItemTileView t : getClientView().getTilesSelected()) {
                        Colors.colorize(Colors.ERROR_MESSAGE, Integer.toString(j++) + " ");
                        out.print(Colors.printTiles(t.getTypeView(), sizetile));
                        Colors.colorize(Colors.WHITE_CODE, "; ");
                    }
                    out.println();

                    while (error != null) {
                        try {
                            for (int i = 0; i < getClientView().getTilesSelected().length; i++) {
                                Colors.colorize(Colors.WHITE_CODE, "Insert number: ");
                                orderTiles[i] = Integer.parseInt(scanner.nextLine());
                            }
                            error = Check.checkPermuteSelection(orderTiles, clientView.getTilesSelected());

                            if (error != null) {
                                displayError(error.getErrorMessage());
                                out.println();
                            }
                        } catch (NumberFormatException e) {
                            displayError("Invalid input! Please enter valid integers for each number.");
                            out.println();
                        }
                    }

                    continue;
                case ORDER_TILES2:
                    if(error!=null){
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                    }
                    Colors.colorize(Colors.WHITE_CODE, "Choice has been reset");
                    error = ErrorType.INVALID_ORDER_TILE_NUMBER;
                    continue;
                case ORDER_TILES3:
                    if(error!=null){
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
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
    //COLUMN PHASE
    /**
     * Asks the user to select a column for placing the selected tiles.
     * The method displays the column phase and prompts the user to enter a number from 0 to N-1,
     * where N is the number of columns in the bookshelf.
     * The user's input represents the desired column for tile placement.
     */

    @Override
    public  synchronized void askColumn(){
        out.println();
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
                    printerBookshelfAndPersonal.printMatrixBookshelf(getClientView(), 3, 1, 60, false, true, 50);
                    while (error != null) {
                        try {
                            Colors.colorize(Colors.WHITE_CODE, "To select a column, write a number from 0 to " + (getClientView().getBookshelfView()[0].length - 1) + ": ");
                            column = Integer.parseInt(scanner.nextLine());
                            error = Check.checkBookshelf(column, clientView.getBookshelfView(), clientView.getTilesSelected());

                            if (error != null) {
                                displayError(error.getErrorMessage());
                                out.println();
                                error = ErrorType.INVALID_COLUMN;
                            }
                        } catch (NumberFormatException e) {
                            displayError("Invalid input! Please enter a valid integer for the column.");
                            out.println();
                        }
                    }
                    continue;
                case COLUMN2:
                    if (error!=null) {
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        continue;
                    }
                    Colors.colorize(Colors.WHITE_CODE, "Choice has been reset");
                    error=ErrorType.INVALID_COLUMN;
                    continue;
                case COLUMN3:
                    if (error!=null) {
                        displayError(ErrorType.NOT_VALUE_SELECTED.getErrorMessage());
                        continue;
                    }
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
    /**
     * Displays an error message to the user.
     * @param error the error message to display
     */
    @Override
    public void displayError(String error) {
        out.println();
        Colors.printCharacter("x  ",1,Colors.ERROR_MESSAGE);
        Colors.colorize(Colors.ERROR_MESSAGE, error);
        Colors.printCharacter("  x",1,Colors.ERROR_MESSAGE);
        out.println();
    }
    /**
     * Displays a message to the user.
     * @param string the message to display
     */
    @Override
    public void displayMessage(String string) {
        out.println();
        Colors.printCharacter("» ", 1, Colors.WHITE_CODE);
        Colors.colorize(Colors.LIGHT_BLUE_CODE, string);
        Colors.printCharacter(" «", 1,Colors.WHITE_CODE);
        out.println();
    }



    /**
     * Ends the game and displays the end game results.
     * @param personalPoints an array containing the personal points of each player
     * @param playerBookshelfFull the nickname indicating the player who completed their bookshelf first
     * After displaying the end game results, the client view will receive the end game notification
     * The user will be prompted to enter "ok" to return to the lobby.
     * Once the user enters "ok", the function will exit and control will return to the lobby.
     */
    @Override
    public void endGame(int[] personalPoints, String playerBookshelfFull) {
        printerCommonGoalAndPoints.printEndGame(clientView,personalPoints,playerBookshelfFull);
        clientView.receiveEndGame();
        boolean continueToAsk=true;

        while(continueToAsk){
            out.println();
            Colors.colorize(Colors.WHITE_CODE, "Write |ok| to return to lobby:  ");
            String input = scanner.next();
            if (input.equals("ok")){
                continueToAsk=false;
            }
            scanner.nextLine();
        }
        out.println();
    }


    /**
     * Displays an error message indicating that the current player is the only player connected to the game,
     * and they need to wait for another player to join before they can continue.
     * Additionally, it prints a logo indicating the "Only Player" status.
     */

    @Override
    public void onlyPlayer() {
        displayError(ErrorType.ONLY_PLAYER.getErrorMessage());
        PrinterLogo.onlyPlayer(10);
    }
    //WAITING TURN PHASE

    /**
     * Displays the waiting room phase, showing the current turn player, the board, and the current ranking.
     * This phase is displayed while the player is waiting for his/her turn.
     */
    @Override
    public synchronized void waitingRoom() {
        PrinterLogo.printWaitingTurnPhase();
        printCommands(CommandsTurn.PRINT1);
        printCommands(CommandsTurn.PRINT5);
        Colors.colorize(Colors.WHITE_CODE, "Turn player: "+clientView.getTurnPlayer());
    }
    /**
     * Displays the player who won a token and the associated score.
     * @param num the token number
     * @param nickname the nickname of the player who won the token
     */
    @Override
    public void displayToken(int num, String nickname) {
        printerCommonGoalAndPoints.printToken(num, nickname);
        Colors.colorize(Colors.WHITE_CODE, "\nPress ENTER to continue...>> ");
        scanner.nextLine();
    }

    /**
     * Returns the current client view.
     * @return the client view
     */
    public ClientView getClientView() {
        return clientView;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }


    //ASK CONNECTION

    /**
     * Establishes a connection with the game server based on the user's input for connection type,
     * nickname, IP address, and port.
     */
    @Override
    public void askNicknameAndConnection(){
        PrinterLogo.printMyShelfieLogo();
        int connectionType = -1;

        String nickname = askNickname();
        Colors.colorize(Colors.LIGHT_BLUE_CODE,"\nHi "+ nickname +"!\n" );
        connectionType = askConnection();
        if (connectionType == 0) {
            Colors.colorize(Colors.LIGHT_BLUE_CODE,"\nYou chose Socket connection\n");

        } else Colors.colorize(Colors.LIGHT_BLUE_CODE,"\nYou chose RMI connection\n");


        String ip = askIp();
        int port = askPort(connectionType);
        Colors.colorize(Colors.LIGHT_BLUE_CODE,"\nYou choose: -Server Ip Address: " + ip+" -Server Port: " + port + "\n");
        ClientHandler clientHandler=new ClientHandler();
        try{
            clientHandler.createConnection(connectionType, ip, port,this);
        } catch (Exception e){
            displayError(ErrorType.ERROR_CONNECTION.getErrorMessage());
            askNicknameAndConnection();

        }
    }

    /**
     * Asks the user to enter their nickname and returns the entered nickname.
     * @return the user's nickname
     */
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
    /**
     * Asks the user to enter the connection type (0 for Socket, 1 for RMI) and returns the entered connection type.
     * @return the connection type (0 for Socket, 1 for RMI)
     */
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
    /**
     * Asks the user to enter the server IP address and returns the entered IP address.
     * If no input is provided, the default IP address "127.0.0.1" is returned.
     * @return the server IP address
     */
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
    /**
     * Checks if the given IP address is valid.
     * @param ipAddress the IP address to check
     * @return true if the IP address is valid, false otherwise
     */
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
    /**
     * Asks the user for the server port number.
     * @param connectionType the type of connection (0 for Socket, 1 for RMI)
     * @return the server port number entered by the user, or the default port number if no valid input is provided
     */

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


