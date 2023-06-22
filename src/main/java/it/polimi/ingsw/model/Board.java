package it.polimi.ingsw.model;



import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;

import java.util.ArrayList;
import java.util.Random;

public class Board{
    private ModelView modelView;

    private BoardBox[][] matrix;

    public Board(ModelView modelView) {
        this.modelView=modelView;
    }


    /**
     * Gets the matrix of BoardBox objects representing the game board.
     * @return The matrix of BoardBox objects.
     */
    public BoardBox[][] getMatrix() {return matrix;}
    /**
     * Sets the matrix of BoardBox objects representing the game board.
     * @param matrix The matrix of BoardBox objects to be set.
     */
    public void setMatrix(BoardBox[][] matrix) {
        this.matrix = matrix;

    }
    /**
     * Gets the BoardBox object at the specified coordinates on the game board.
     * @param x The row index.
     * @param y The column index.
     * @return The BoardBox object at the specified coordinates.
     */
    public BoardBox getBoardBox(int x,int y) {
        return matrix[x][y];
    }


    private ArrayList<ItemTile> tiles;
    /**
     * Gets the list of ItemTile objects.
     * @return The list of ItemTile objects.
     */
    public ArrayList<ItemTile> getTiles() {
        return tiles;
    }

    /**

     Sets the list of ItemTile objects.
     @param tiles The list of ItemTile objects to be set.
     */
    public void setTiles(ArrayList<ItemTile> tiles) {
        this.tiles = tiles;
    }

    private ArrayList<BoardBox> selectedBoard=new ArrayList<BoardBox>();

    /**
     * Gets the list of selected BoardBox objects.
     * @return The list of selected BoardBox objects.
     */
    public ArrayList<BoardBox> getSelectedBoard() {
        return selectedBoard;
    }

    /**
     * Sets the list of selected BoardBox objects.
     * @param selectedBoard The list of selected BoardBox objects to be set.
     */
    public void setSelectedBoard(ArrayList<BoardBox> selectedBoard) {
        this.selectedBoard = selectedBoard;

    }

    /**
     * Creates a deep copy of the BoardBoxView matrix.
     * @return The cloned BoardBoxView matrix.
     */
    public BoardBoxView[][] cloneBoard(){
        BoardBoxView[][] boardView=new BoardBoxView[matrix.length][matrix[0].length];
        ItemTile itemTile;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                itemTile=matrix[i][j].getTile();
                if(itemTile!=null){
                    boardView[i][j]=new BoardBoxView(matrix[i][j].isOccupiable(), new ItemTileView(itemTile.getType(), itemTile.getTileID()), i, j, matrix[i][j].getFreeEdges());
                }else boardView[i][j]=new BoardBoxView(matrix[i][j].isOccupiable(), new ItemTileView(null,-1), i, j, matrix[i][j].getFreeEdges());
            }
        }
        return boardView;
    }
    /**
     * Fills the board initially with tiles based on the number of players and game rules.
     * @param numPlayers The number of players.
     * @param gameRules Used to read the game rules from a JSON file.
     *                  It provides the rules and settings for the game.
     @throws Exception if an error occurs during the reading of game rules from the JSON file.
     */
    public void firstFillBoard(int numPlayers, GameRules gameRules) throws Exception {
        //MAX_SELECTABLE_TILES= gameRules.getMaxSelectableTiles();
        int[][] matrix = gameRules.getMatrix(numPlayers);
        this.matrix=new BoardBox[matrix.length][matrix[0].length];
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    randomNumber = random.nextInt(tiles.size());
                    this.matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                    this.matrix[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if(this.matrix[i][j].getTile()!=null){
                    setFreeEdges(i,j);
                }
            }
        }
        modelView.setBoardView(cloneBoard());
    }
    /**
     * Fills the bag with item tiles based on the specified game rules.
     * @param gameRules Used to read the game rules from a JSON file.
     *                  It provides the rules and settings for the game.
     */
    public void fillBag(GameRules gameRules) {
        int[] numTilesOfType = gameRules.getNumTilesPerType();
        int j = 0;
        tiles=new ArrayList<ItemTile>();
        for (Type t : Type.values()) {
            for (int i = 0; i < numTilesOfType[t.ordinal()]; i++) {
                tiles.add(new ItemTile(t, j++));
            }
        }
    }

    /**
     * Calculates the number of free edges of the cell having x and y coordinates of the Board.
     * @param x:board.matrix row
     * @param y: board.matrix column
     */
    public void setFreeEdges(int x, int y){
        if(x>0) {
            //up
            if (matrix[x - 1][y].getTile()==null || !matrix[x - 1][y].isOccupiable())
                matrix[x][y].increaseFreeEdges();
        }else matrix[x][y].increaseFreeEdges();
        if(x< matrix.length-1) {
            //down
            if (matrix[x+1][y].getTile()==null|| !matrix[x + 1][y].isOccupiable())
                matrix[x][y].increaseFreeEdges();
        }else matrix[x][y].increaseFreeEdges();
        if(y>0) {
            //left
            if (matrix[x][y-1].getTile()==null|| !matrix[x][y-1].isOccupiable())
                matrix[x][y].increaseFreeEdges();
        }else matrix[x][y].increaseFreeEdges();
        if(y< matrix.length-1) {
            //right
            if (matrix[x][y+1].getTile()==null|| !matrix[x][y+1].isOccupiable())
                matrix[x][y].increaseFreeEdges();
        }else matrix[x][y].increaseFreeEdges();
    }

    /**
     * Updates the free edges of the adjacent cells after the tile
     * in row x and column y of the board is chosen by the user.
     * @param x:board.matrix row
     * @param y: board.matrix column
     */

    public void increaseNear(int x, int y){
        if(x>0) {
            //up
            if (matrix[x-1][y].getTile()!=null)
                matrix[x-1][y].increaseFreeEdges();
        }
        if(x< matrix.length-1) {
            //down
            if (matrix[x+1][y].getTile()!=null)
                matrix[x+1][y].increaseFreeEdges();
        }
        if(y>0) {
            //left
            if (matrix[x][y-1].getTile()!=null)
                matrix[x][y-1].increaseFreeEdges();
        }
        if(y< matrix.length-1) {
            //right
            if (matrix[x][y+1].getTile()!=null)
                matrix[x][y+1].increaseFreeEdges();
        }
    }
    /**
     * Checks if all the specified coordinates are adjacent to each other.
     * @param coordinatesSelected The array of coordinates to be checked. The coordinates should be in the format [x1, y1, x2, y2, ..., xn, yn].
     * @return True if all the coordinates are adjacent to each other, false otherwise.
     */
    public boolean allAdjacent(int[] coordinatesSelected) {
        for (int i = 2; i < coordinatesSelected.length; i = i + 2) {
            if (Math.abs(coordinatesSelected[i] - coordinatesSelected[i-2]) != 1 && Math.abs(coordinatesSelected[i+1] - coordinatesSelected[i-1]) != 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if all the specified coordinates are in the same row or the same column.
     * @param coordinatesSelected The array of coordinates to be checked. The coordinates should be in the format [x1, y1, x2, y2, ..., xn, yn].
     * @return True if all the coordinates are in the same row or the same column, false otherwise.
     */
    public boolean allSameRowOrSameColumn(int[] coordinatesSelected) {
        int firstX = coordinatesSelected[0];
        int firstY = coordinatesSelected[1];
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i < coordinatesSelected.length; i = i + 2) {
            if (coordinatesSelected[i] != firstX) {
                allSameRow = false;
            }
            if (coordinatesSelected[i+1]!= firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }

        return false;
    }
    /**
     * Checks if the specified coordinates are valid and if there is any error associated with them.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The error type associated with the coordinates, or null if the coordinates are valid.
     */
    public ErrorType checkCoordinates(int x, int y) {
        if (x < 0 || y<0 || x> matrix.length-1 || y> matrix[0].length-1 ||matrix[x][y].getTile()==null) {
            return ErrorType.INVALID_COORDINATES;
        }
        return null;
    }
    /**
     * Checks if the specified error type indicates an error.
     * @param error The error type to be checked.
     * @return True if the error type indicates an error, false otherwise.
     */
    public boolean checkError(ErrorType error){
        if(error!=null){
            return true;
        }
        else return false;
    }

    /**
     * Checks if the specified selection is selectable and adds the corresponding BoardBox to the selectedBoard list.
     * @param selection The array of selection coordinates.
     * @param maxSelectableTile The maximum number of selectable tiles, which can vary depending on the occupancy of the bookshelf.
     * @return The error type associated with the selection, or null if the selection is selectable.
     */
    public ErrorType checkSelectable(int[] selection,int maxSelectableTile) throws Error {
        selectedBoard=new ArrayList<>();
        ErrorType error;
        if(selection==null || selection.length%2!=0){
            return ErrorType.INVALID_INPUT;
        }
        if (selection.length/2 > (maxSelectableTile)) {
            return ErrorType.TOO_MANY_TILES;
        }
        if(matrix[0][1].getFreeEdges()<=0){
            BoardBox boardBox=new BoardBox(selection[0],selection[1]);
            ItemTile tile=new ItemTile(matrix[selection[0]][selection[1]].getTile().getType(),matrix[selection[0]][selection[1]].getTile().getTileID());
            boardBox.setTile(tile);
            selectedBoard.add(boardBox);
            if(selection.length==2){
                return null;
            }
        }
        for(int k=2;k<selection.length;k+=2){
            int x=selection[k];
            int y=selection[k+1];
            error=checkCoordinates(x,y);
            if(checkError(error)){
                return error;
            }
            if (matrix[x][y].getFreeEdges() <= 0) {
                return ErrorType.NOT_ENOUGH_FREE_EDGES;
            }

            BoardBox boardBox=new BoardBox(x,y);
            ItemTile tile=new ItemTile(matrix[x][y].getTile().getType(),matrix[x][y].getTile().getTileID());
            boardBox.setTile(tile);
            selectedBoard.add(boardBox);
        }

        if (!allAdjacent(selection) || !allSameRowOrSameColumn(selection)){
            return ErrorType.NOT_SAME_ROW_OR_COLUMN;
        }
        return null;
    }

    /**
     * Retrieves the list of selected ItemTiles from the selectedBoard.
     * @return The list of selected ItemTiles.
     */
    public ArrayList<ItemTile> selected(){
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i = 0; i< selectedBoard.size(); i++ ){
            selectedItems.add(selectedBoard.get(i).getTile());
        }
        return selectedItems;
    }
    /**
     * Resets the board by clearing the selected tiles and updating the neighboring cells.
     * The neighboring cells of the selected tiles have their attributes modified accordingly.
     * After resetting, the board view in the model is updated to reflect the changes.
     */
    public void changeBoardAfterUserChoice(){
        for(int i = 0; i< selectedBoard.size(); i++ ){
            increaseNear(selectedBoard.get(i).getX(), selectedBoard.get(i).getY());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setFreeEdges(0);
        }
        selectedBoard=new ArrayList<>();
        modelView.setBoardView(cloneBoard());
    }


    /**
     * Checks if there are any isolated cells on the board.
     * @return true if there are only isolated cells, false otherwise.
     */
    public boolean checkRefill(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    if(matrix[i][j].getFreeEdges()!=4)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Refills the board by adding isolated tiles to the occupiable cells.
     * The method performs the following steps:
     * 1)Collects all the isolated tiles and adds them to the ArrayList.
     * 2)Randomly extracts tiles from the ArrayList.
     * 3)Inserts the extracted tiles onto the board in correspondence with the occupiable cells.
     */
    public void refill(){
        //add the board tiles to the arraylist Tiles
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    tiles.add(matrix[i][j].getTile());
                    matrix[i][j].setTile(null);
                }
            }
        }
        //randomly extract ItemTile from arraylist Tiles and place them on the board
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].isOccupiable()){
                    randomNumber = random.nextInt(tiles.size());
                    matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                }
            }
        }
        //calculate and set the number of free edges for each tile
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    matrix[i][j].setFreeEdges(0);
                    setFreeEdges(i,j);
                }
            }
        }
        modelView.setBoardView(cloneBoard());
    }
}
