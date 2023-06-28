package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.CLI.PrinterBoard;
import it.polimi.ingsw.view.CLI.PrinterBookshelfAndPersonal;
import it.polimi.ingsw.view.ClientView;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * The ModelView class represents a set of immutable objects that are sent to the client.
 * It provides a snapshot of the game state that can be used for rendering and displaying information to the players.
 */
public class ModelView implements Serializable {
    @Serial
    private static final long serialVersionUID = -5158808756179690476L;
    private int turnPlayer;
    private Boolean[] activePlayers;
    public static int MAX_SELECTABLE_TILES;

    private int[][] commonGoalView;
    private int[] token;
    private int[] personalPoints;
    private String bookshelfFullPoints;

    private BoardBoxView[][] boardView;

    private TurnPhase turnPhase;

    private ItemTileView[][][] bookshelfView;
    private PlayerPointsView[] playerPoints;
    private PersonalGoalCard[] playerPersonalGoal;
    private ItemTileView[] selectedItems;

    /**
     * Constructs a ModelView object.
     * @param numPlayers The number of players in the associated game.
     * @param gameRules Used to read the game rules from a JSON file.
     *                 It provides the rules and settings for the game.
     */

    public ModelView(int numPlayers,GameRules gameRules){
        bookshelfView=new ItemTileView[numPlayers][gameRules.getRowsBookshelf()][gameRules.getColumnsBookshelf()];
        playerPoints=new PlayerPointsView[numPlayers];
        playerPersonalGoal=new PersonalGoalCard[numPlayers];
        activePlayers=new Boolean[numPlayers];
        Arrays.fill(activePlayers, true);
        commonGoalView =new int[gameRules.getNumOfCommonGoals()][2];
        token=new int[gameRules.getNumOfCommonGoals()];
        personalPoints=new int[numPlayers];
        MAX_SELECTABLE_TILES= gameRules.getMaxSelectableTiles();
    }
    /**
     * Constructs a new instance of the ModelView class.
     * This constructor creates a ModelView object without any parameters.
     */
    public ModelView() {

    }


    /**
     * Gets the index of a player based on their nickname.
     * @param nickname The nickname of the player.
     * @return The index of the player. Returns -1 if the player is not found.
     */
    public int getIntegerValue(String nickname) {
        OptionalInt index = IntStream.range(0, playerPoints.length)
                .filter(i -> playerPoints[i].getNickname().equals(nickname))
                .findFirst();
        return index.orElse(-1);
    }

    /**
     * Sorts the player points and personal points to determine the winner at the end of the game.
     */

    public synchronized void winnerEndGame() {
        Integer[] indices = new Integer[playerPoints.length];
        for (int i = 0; i < playerPoints.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, Comparator.comparingInt(index -> {
            if (index == getIntegerValue(bookshelfFullPoints)) {
                return playerPoints[index].getPoints() + personalPoints[index] + 1;
            } else {
                return playerPoints[index].getPoints() + personalPoints[index];
            }
        }));
        PlayerPointsView[] sortedPlayerPoints = new PlayerPointsView[playerPoints.length];
        int[] sortedPersonalPoints = new int[personalPoints.length];

        for (int i = 0; i < playerPoints.length; i++) {
            sortedPlayerPoints[i] = playerPoints[indices[i]];
            sortedPersonalPoints[i] = personalPoints[indices[i]];
        }

        playerPoints = sortedPlayerPoints;
        personalPoints = sortedPersonalPoints;
    }
    /**
     * Sets the turn to the next active player in the game.
     * If the current player is the last active player, the turn is set to the first active player.
     */
    public synchronized void setNextPlayer() {
        do{

            if(turnPlayer>= (playerPoints.length - 1))
                setTurnPlayer(0);
            else setTurnPlayer(turnPlayer+1);
        }while(!activePlayers[turnPlayer]);
    }
    /**
     * Finds the previous active player in the game.
     * @return The PlayerPointsView object representing the previous active player.
     */
    public PlayerPointsView findPreviousPlayer() {
        int currentIndex =turnPlayer;
         do{
            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = activePlayers.length- 1;
            }
            if (currentIndex == turnPlayer) {
                break;
            }
        }while (!activePlayers[currentIndex]);
        return playerPoints[currentIndex];
    }

    /**
     * Retrieves the bookshelf view for a specific player.
     * @param nickname The nickname of the player.
     * @return The bookshelf view for the player.
     */

    public ItemTileView[][] getBookshelfView(String nickname) {
        return bookshelfView[getIntegerValue(nickname)];
    }
    /**
     * Sets the bookshelf view for a specific player.
     * @param bookshelfView The bookshelf view to set.
     * @param nickname The nickname of the player.
     */
    public synchronized void setBookshelfView(ItemTileView[][] bookshelfView,String nickname) {
        this.bookshelfView[getIntegerValue(nickname)] = bookshelfView;
    }
    /**
     * Retrieves the bookshelf view.
     *
     * @return The bookshelf view represented as a three-dimensional array of ItemTileView objects.
     *         The first dimension represents the player index,
     *         the second dimension represents the rows of the bookshelf, and
     *         the third dimension represents the columns of the bookshelf.
     */
    public ItemTileView[][][] getBookshelfView(){
        return bookshelfView;
    }
    /**
     * Sets the bookshelf view.
     *
     * @param bookshelfView The new bookshelf view to be set.
     */
    public void setBookshelfView(ItemTileView[][][] bookshelfView){
        this.bookshelfView = bookshelfView;
    }
    /**

     Retrieves the player points for all players.
     @return An array of PlayerPointsView objects representing the player points.
     */
    public PlayerPointsView[] getPlayerPoints() {
        return playerPoints;
    }
    /**
     * Sets the player points for a specific player.
     * @param playerPoints The PlayerPointsView object representing the player points.
     * @param index The index of the player.
     */

    public synchronized void setPlayerPoints(PlayerPointsView playerPoints,int index) {
        this.playerPoints[index] = playerPoints;
    }
    /**
     * Sets the player points views for all players.
     *
     * @param playerPoints An array of PlayerPointsView objects representing the points views for each player.
     *                     The index of each element in the array corresponds to the player index.
     */
    public synchronized void setPlayerPoints(PlayerPointsView[] playerPoints){
        this.playerPoints = playerPoints;
    }
    /**
     * Retrieves the personal points of a player.
     * @param nickname The nickname of the player.
     * @return The personal points of the player.
     */
    public int getPersonalPoint(String nickname) {
        return personalPoints[getIntegerValue(nickname)];
    }

    /**
     * Retrieves the personal goal card of a player.
     * @param nickname The nickname of the player.
     * @return The personal goal card of the player.
     */

    public PersonalGoalCard getPlayerPersonalGoal(String nickname) {
        return playerPersonalGoal[getIntegerValue(nickname)];
    }
    /**
     * Retrieves the personal goal cards for each player.
     * @return An array of PersonalGoalCard objects representing the personal goal cards for each player.
     *         The index of each element in the array corresponds to the player index.
     */
    public PersonalGoalCard[] getPlayerPersonalGoal(){
        return playerPersonalGoal;
    }
    /**
     * Sets the personal goal card for a specific player.
     * @param playerPersonalGoal The PersonalGoalCard to set.
     * @param nickname The nickname of the player.
     */
    public synchronized void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal, String nickname) {
        this.playerPersonalGoal[getIntegerValue(nickname)] = playerPersonalGoal;
    }

    /**
     * Checks the winner based on player points.
     * @return An array of PlayerPointsView objects sorted in ascending order of points.
     */
    public PlayerPointsView[] checkWinner() {
        PlayerPointsView[] sortedObjects = Arrays.copyOf(playerPoints, playerPoints.length);

        Arrays.sort(sortedObjects, Comparator.comparingInt(PlayerPointsView::getPoints));

        return sortedObjects;
    }
    /**
     * Retrieves the nickname of the player whose turn it is.
     * @return The nickname of the current player.
     */
    public String getTurnNickname(){
        return playerPoints[turnPlayer].getNickname();
    }
    /**
     * Retrieves the nickname of the player with a full bookshelf.
     * @return The nickname of the player with a full bookshelf.
     */
    public String getBookshelfFullPoints(){
        return bookshelfFullPoints;
    }
    /**
     * Sets the nickname of the player with a full bookshelf.
     * @param nickname The nickname of the player with a full bookshelf.
     */
    public synchronized void setBookshelfFullPoints(String nickname){
        bookshelfFullPoints=nickname;
    }

    /**
     * Retrieves the selected items.
     * @return An array of ItemTileView representing the selected items.
     */
    public ItemTileView[] getSelectedItems() {
        return selectedItems;
    }
    /**
     * Sets the selected items.
     * @param selectedItems An array of ItemTileView representing the selected items.
     */
    public synchronized void setSelectedItems(ItemTileView[] selectedItems) {
        this.token=new int[commonGoalView[0].length];
        this.selectedItems = selectedItems;
    }
    /**
     * Retrieves the board view.
     * @return A 2D array of BoardBoxView representing the board view.
     */
    public BoardBoxView[][] getBoardView() {
        return boardView;
    }
    /**
     * Sets the board view.
     * @param boardView A 2D array of BoardBoxView representing the board view.
     */
    public synchronized void setBoardView(BoardBoxView[][] boardView) {
        this.boardView = boardView;
    }
    /**
     * Retrieves the turn phase.
     * @return The current turn phase.
     */

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }
    /**
     * Sets the turn phase.
     * @param turnPhase The turn phase to set.
     */
    public synchronized void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }


    /**
     * Retrieves the common goal view.
     * @return A 2D array of int representing the common goal view.
     */

    public int[][] getCommonGoalView() {
        return commonGoalView;
    }
    /**
     * Sets the points left for a specific common goal.
     * @param row The row index of the common goal.
     * @param index The index within the common goal.
     * @param pointsLeft The points left to set.
     */
    public synchronized void setIdCommon(int row,int index,int pointsLeft) {
        this.commonGoalView[row][index]= pointsLeft;
    }
    /**
     * Updates the connection status of a player.
     * @param nickname The nickname of the player.
     * @param discOrRec Indicates whether the player is being reconnected (true) or disconnected (false).
     */
    public void disconnectionAndReconnectionPlayer(String nickname,boolean discOrRec) {
        for(int i=0;i<activePlayers.length;i++){
            if(playerPoints[i].getNickname().equals(nickname)){
                activePlayers[i]=discOrRec;
                break;
            }
        }
    }



    /**
     * Retrieves the index of the current turn player.
     * @return The index of the current turn player.
     */

    public int getTurnPlayer() {
        return turnPlayer;
    }
    /**
     * Sets the index of the current turn player.
     * @param turnPlayer The index of the current turn player.
     */
    public synchronized void setTurnPlayer(int turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    /**
     * Sets the personal goal cards for all players.
     * @param playerPersonalGoal An array of PersonalGoalCard representing the personal goal cards for all players.
     */
    public synchronized void setPlayerPersonalGoal(PersonalGoalCard[] playerPersonalGoal){
        this.playerPersonalGoal=playerPersonalGoal;
    }

    /**
     * Retrieves the token values.
     * @return An array of int representing the token values.
     */
    public int[] getToken() {
        return token;
    }
    /**
     * Sets the token values.
     * @param token An array of int representing the token values.
     */
    public synchronized void setToken(int[] token) {
        this.token = token;
    }
    /**
     * Retrieves the personal points for all players.
     * @return An array of int representing the personal points for all players.
     */
    public int[] getPersonalPoints() {
        return personalPoints;
    }
    /**
     * Sets the personal points for all players.
     * @param personalPoints An array of int representing the personal points for all players.
     */
    public synchronized void setPersonalPoints(int[] personalPoints) {
        this.personalPoints = personalPoints;
    }
    /**
     * Retrieves the active players status.
     * @return An array of Boolean representing the active players status.
     */
    public Boolean[] getActivePlayers() {
        return activePlayers;
    }

    /**
     * Sets the active players status.
     * @param activePlayers An array of Boolean representing the active players status.
     */

    public synchronized void setActivePlayers(Boolean[] activePlayers) {
        this.activePlayers = activePlayers;
    }
    /**
     * Retrieves the maximum number of selectable tiles.
     * @return The maximum number of selectable tiles.
     */
    public int getMAX_SELECTABLE_TILES() {
        return MAX_SELECTABLE_TILES;
    }
    /**
     * Sets the maximum number of selectable tiles.
     * @param MAX_SELECTABLE_TILES The maximum number of selectable tiles.
     */
    public void setMAX_SELECTABLE_TILES(int MAX_SELECTABLE_TILES) {
        this.MAX_SELECTABLE_TILES = MAX_SELECTABLE_TILES;
    }
    /**
     * Returns the maximum number of selectable tiles.
     * @return The maximum number of selectable tiles.
     */
    public int getMaxSelectableTiles() {
        return MAX_SELECTABLE_TILES;
    }

    /**
     * Sets the common goal view of the board.
     * @param commonGoalView The common goal view to set.
     */
    public synchronized void setCommonGoalView(int[][] commonGoalView) {
        this.commonGoalView = commonGoalView;
    }


    /**
     * Restores the board based on the saved state.
     * @return The restored Board object.
     */
    public Board restoreBoard() {
        Board board = new Board(this);
        int row= boardView.length;
        int column=boardView[0].length;
        BoardBox[][] boardBox = new BoardBox[row][column];
        for(int i=0; i< boardView.length; i++){
            for(int j=0; j<boardView[0].length; j++){
                boardBox[i][j]= restoreBoardBox(i,j);
            }
        }
        board.setMatrix(boardBox);
        PrinterBoard printerBoard=new PrinterBoard();
        printerBoard.printMatrixBoard(board.cloneBoard(),null);
        return board;
    }
    /**
     * Restores the board box based on the saved game state.
     * @param x The x-coordinate of the board box.
     * @param y The y-coordinate of the board box.
     * @return The restored board box.
     */
    public BoardBox restoreBoardBox(int x,int y){
        BoardBoxView boardBoxView=boardView[x][y];
        BoardBox boardBox = new BoardBox(x, y);
        boardBox.setTile(restoreItemTile(boardBoxView.getItemTileView()));
        boardBox.setOccupiable(boardBoxView.isOccupiable());
        boardBox.setFreeEdges(boardBoxView.getFreeEdges());
        return boardBox;
    }
    /**
     * Restores the item tile based on the saved game state.
     * @param itemTileView The item tile view to restore.
     * @return The restored item tile.
     */
    public ItemTile restoreItemTile(ItemTileView itemTileView){
        ItemTile itemTile = new ItemTile(itemTileView.getTypeView(), itemTileView.getTileID());
        return itemTile;
    }
    /**
     * Restores the bookshelf associated with a specific player based on the saved state.
     * @param player The player whose bookshelf is being restored.
     * @return The restored Bookshelf object.
     */
    public Bookshelf restoreBookshelf(Player player){
        int intPlayer=getIntegerValue(player.getNickname());
        int row= bookshelfView[intPlayer].length;
        int column= bookshelfView[intPlayer][0].length;
        ItemTile[][] itemTiles=new ItemTile[row][column];
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                ItemTile tile=restoreItemTile(bookshelfView[intPlayer][i][j]);
                itemTiles[i][j]=tile;
            }
        }
        Bookshelf bookshelf=new Bookshelf();
        bookshelf.setMatrix(itemTiles);
        bookshelf.setFreeShelves(new int[column]);
        ClientView clientView=new ClientView();
        clientView.setBookshelfView(bookshelf.cloneBookshelf());
        PrinterBookshelfAndPersonal printerBookshelfAndPersonal=new PrinterBookshelfAndPersonal();
        printerBookshelfAndPersonal.printMatrixBookshelf(clientView,3,1,10,false,false,0);
        return bookshelf;
    }
}

