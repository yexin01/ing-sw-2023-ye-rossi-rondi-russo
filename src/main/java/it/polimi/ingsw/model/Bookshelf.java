package it.polimi.ingsw.model;


import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;


import java.util.*;
/**
 * The Bookshelf class represents a player's bookshelf in the game.
 */
public class Bookshelf {

    private ItemTile[][] matrix;
    private int[] freeShelves; // The number of free shelves in each column
    /**
     * Constructs a new Bookshelf with the specified number of rows and columns.
     * @param rows              The number of rows in the bookshelf.
     * @param columns           The number of columns in the bookshelf.
     */
    public void matrix(int rows, int columns) {
        freeShelves = new int[columns];
        matrix = new ItemTile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new ItemTile(null, -1);
            }
        }
    }
    /**
     * Returns the array of free shelves in each column.
     * @return The array of free shelves.
     */
    public int[] getFreeShelves() {
        return freeShelves;
    }
    /**
     * Computes the number of free shelves in each column and updates the freeShelves array.
     */
    public void computeFreeShelves() {
        for (int j = 0; j < matrix[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < matrix.length && matrix[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
    }
    /**
     * Creates a clone of the bookshelf matrix as an array of ItemTileViews.
     * @return The cloned bookshelf matrix.
     */
    public ItemTileView[][] cloneBookshelf(){
        ItemTileView[][] bookshelfView=new ItemTileView[matrix.length][matrix[0].length];
        ItemTile itemTile;
        for (int i = matrix.length-1; i >=0; i--) {
            for (int j = 0; j < matrix[i].length; j++) {
                itemTile=matrix[i][j];
                bookshelfView[i][j]=new ItemTileView(itemTile.getType(),itemTile.getTileID());
            }
        }
        return bookshelfView;
    }

    /**
     * Returns the maximum number of free shelves among all columns.
     * @return The maximum number of free shelves.
     */
    public int maxFreeShelves() {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        return max;
    }
    /**
     * Returns the number of free shelves in the specified column.
     * @param i The index of the column.
     * @return The number of free shelves in the column.
     */
    public int getMaxTilesColumn(int i) {
        return freeShelves[i];
    }
    /**
     * Computes the number of selectable tiles based on the current free shelves and maximum selectable tiles.
     * @return The number of selectable tiles.
     */
    public int numSelectableTiles() {
        computeFreeShelves();
        int max = maxFreeShelves();
        return (max > ModelView.MAX_SELECTABLE_TILES) ? ModelView.MAX_SELECTABLE_TILES: max;
    }

    /**
     * Returns the matrix representing the bookshelf.
     * @return The bookshelf matrix.
     */

    public ItemTile[][] getMatrix() {
        return matrix;
    }
    /**
     * Sets the matrix representing the bookshelf.
     * @param matrix The bookshelf matrix to set.
     */
    public void setMatrix(ItemTile[][] matrix) {
        this.matrix = matrix;
    }


    /**
     * Sets the tile at the specified position in the bookshelf matrix.
     * @param tile The tile to set.
     * @param x    The x-coordinate of the tile.
     * @param y    The y-coordinate of the tile.
     */
    public void setTile(ItemTile tile, int x, int y) {
        matrix[x][y] = new ItemTile(tile.getType(), tile.getTileID());
    }
    /**
     * Checks if the bookshelf is full.
     * @return true if the bookshelf is full, false otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i].getTileID() == -1) {
                return false;
            }
        }
        return true;
    }
    /**
     * Inserts the selected tiles into the bookshelf at the specified column.
     * @param selectedItemTiles The list of selected tiles to insert.
     * @param columnSelected    The column where the tiles should be inserted.
     * @throws Error If there is an error during the insertion process.
     */
    public void insertTiles(ArrayList<ItemTile> selectedItemTiles,int columnSelected) throws Error {
        int j = 0;
        for (int i = getMatrix().length - 1; j < selectedItemTiles.size(); i--) {
            if (getMatrix()[i][columnSelected].getTileID() == -1) {
                setTile(selectedItemTiles.get(j++), i,columnSelected);
            }
        }
    }
    /**
     * Checks the bookshelf for errors at the specified column and number of selected tiles.
     *
     * @param column          The column to check.
     * @param numSelectedTiles The number of selected tiles.
     * @return The type of error encountered:if the specified column is outside the valid range or the selected
     * column does not have enough free cells for the selected tiles.
     */
    public ErrorType checkBookshelf(int column,int numSelectedTiles) {
        if (column < 0 || column > getMatrix()[0].length-1 ) {
            return ErrorType.INVALID_COLUMN;
        }
        if(numSelectedTiles > getMaxTilesColumn(column)){
            return ErrorType.NOT_ENOUGH_FREE_CELLS_COLUMN;
        }
        return null;
    }

    /**
     * Sets the array of free shelves on the bookshelf.
     * @param freeShelves The array of free shelves to set.
     */
    public void setFreeShelves(int[] freeShelves) {
        this.freeShelves = freeShelves;
    }

    /**
     * @return a list with the cardinalities of the groups of adjacent tiles found by the algorithm
     */
    public List<Integer> findAdjacentTilesGroups() {
        ItemTile startingTile = null;
        List<Integer> groupsSizes = new ArrayList<>();
        Set<ItemTile> visited = new HashSet<>();
        int lastRow = matrix.length - 1;
        int lastColumn = matrix[0].length - 1;
        int startingRow = lastRow;
        int startingColumn = 0;

        //find startingTile in the last row:
        for (int j = 0; j < matrix[0].length; j++) {
            ItemTile currentTile = matrix[lastRow][j];
            if (currentTile != null && (!Objects.equals(currentTile.getTileID(), -1))) {
                startingTile = currentTile;
                startingColumn = j;
                break;
            }
            //algorithm can stop if last row is empty
            if (j == lastColumn) { //every element in the last row is null
                return groupsSizes;
            }
        }

        assert (startingTile != null && (!Objects.equals(startingTile.getTileID(), -1))): "startingTile is null!";
        Type startingType = startingTile.getType();
        int groupSize = dfs(startingTile, visited, startingType, startingRow, startingColumn);
        groupsSizes.add(groupSize);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ItemTile currentTile = matrix[i][j];
                if (!visited.contains(currentTile) && currentTile.getType() != null && (!Objects.equals(currentTile.getTileID(), -1))) {
                    Type currentType = currentTile.getType();
                    groupSize = dfs(currentTile, visited, currentType, i, j);
                    groupsSizes.add(groupSize);
                }
            }
        }
        return groupsSizes;
    }


    private static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; //possible directions towards adjacent tiles
    /**
     * Performs a depth-first search (DFS) starting from the given currentTile on the bookshelf matrix.
     * It counts the number of connected tiles of the specified type and returns the size of the connected component.
     * @param currentTile The current tile to start the DFS from.
     * @param visited A set of visited tiles to avoid revisiting.
     * @param type The type of tiles to count in the connected component.
     * @param row The current row index.
     * @param column The current column index.
     * @return The size of the connected component.
     */
    private int dfs(ItemTile currentTile, Set<ItemTile> visited, Type type, int row, int column) {
        visited.add(currentTile);
        int size = 1;
        for (int[] dir : DIRECTIONS) {
            int x = row + dir[0];
            int y = column + dir[1];
            if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) {
                continue; //proceeding in other adjacent directions
            }
            ItemTile nextBox = matrix[x][y];
            if (!visited.contains(nextBox) && nextBox.getType() == type) {
                size += dfs(nextBox, visited, type, x, y);
            }
        }
        return size;
    }


}
