package it.polimi.ingsw.model;


import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.ItemTileView;


import java.util.*;

public class Bookshelf {

    private ItemTile[][] matrix;
    private int[] freeShelves; //freeShelves[i] = # celle libere nell'i-esima colonna
    private int maxSelectableTiles;

/*
    public Bookshelf(int rows, int columns, int maxSelectableTiles) {
        freeShelves = new int[columns];
        this.maxSelectableTiles = maxSelectableTiles;
        matrix = new ItemTile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new ItemTile(null, -1);
            }
        }
    }

 */
    public void matrix(int rows, int columns, int maxSelectableTiles) {
        freeShelves = new int[columns];
        this.maxSelectableTiles = maxSelectableTiles;
        matrix = new ItemTile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new ItemTile(null, -1);
            }
        }
    }

    public int[] getFreeShelves() {
        return freeShelves;
    }

    public void computeFreeShelves() {
        for (int j = 0; j < matrix[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < matrix.length && matrix[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
    }
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
    public void setMaxSelectableTiles(int maxSelectableTiles){
        this.maxSelectableTiles=maxSelectableTiles;
    }

    public int maxFreeShelves() {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        System.out.println(max);
        return max;
    }

    public int getMaxTilesColumn(int i) {
        return freeShelves[i];
    }

    public int numSelectableTiles() {
        computeFreeShelves();
        int max = maxFreeShelves();
        return (max > maxSelectableTiles) ? maxSelectableTiles : max;
    }



    public ItemTile[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(ItemTile[][] matrix) {
        this.matrix = matrix;
    }

    public Type getTileType(int x, int y) {
        return matrix[x][y].getType();
    }

    public int getTileValue(int x, int y) {
        return matrix[x][y].getTileID();
    }

    public void setTile(ItemTile tile, int x, int y) {
        matrix[x][y] = new ItemTile(tile.getType(), tile.getTileID());
    }

    public boolean isFull() {
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i].getTileID() == -1) {
                return false;
            }
        }
        return true;
    }

    public void insertTiles(ArrayList<ItemTile> selectedItemTiles,int columnSelected) throws Error {
        int j = 0;
        for (int i = getMatrix().length - 1; j < selectedItemTiles.size(); i--) {
            if (getMatrix()[i][columnSelected].getTileID() == -1) {
                setTile(selectedItemTiles.get(j++), i,columnSelected);
            }
        }
    }

    public ErrorType checkBookshelf(int column,int numSelectedTiles) throws Error {
        if (column < 0 || column > getMatrix()[0].length-1 ) {
            return ErrorType.INVALID_COLUMN;
        }
        if(numSelectedTiles > getMaxTilesColumn(column)){
            return ErrorType.NOT_ENOUGH_FREE_CELLS_COLUMN;
        }
        return null;
    }

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
