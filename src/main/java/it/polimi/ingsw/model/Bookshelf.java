package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bookshelf {
    //private ItemTile[][] matrix;
    private ItemTile[][] matrix;
    private int[] freeShelves; //freeShelves[i] = # celle libere nell'i-esima colonna

    //TODO: rows e columns come parametri del costruttore, presi da Json
    public Bookshelf(){
        int rows=6;
        int columns=5;
        freeShelves=new int[columns];
        matrix=new ItemTile[rows][columns];
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                matrix[i][j]=new ItemTile(null, -1);
            }
        }
    }
    public int[] getFreeShelves() {
        return freeShelves;
    }
    public void computeFreeShelves(){
        for (int j=0; j<matrix[0].length; j++){
            freeShelves[j]=0;
            for (int i = 0; i<matrix.length && matrix[i][j].getTileID()==-1; i++){
                freeShelves[j]++;
            }
        }
    }
    public int maxFreeShelves(){
        int max = 0;
        for (int i=0; i<freeShelves.length; i++){
            if (freeShelves[i]>max){
                max = freeShelves[i];
            }
        }
        System.out.println(max);
        return max;
    }
    //TODO: rename --> freeShelvesAtColumn, actually you could just use getFreeShelves[i]
    public int getMaxTilesColumn(int i){return freeShelves[i];}
    public int numSelectableTiles(){
        computeFreeShelves();
        int max=maxFreeShelves();
        return (max > 3) ? 3 : max;
    }
    public void printFreeShelves(){
        for(int i=0;i<freeShelves.length;i++){
            System.out.print(freeShelves[i]+"  ");
        }
    }
    public ItemTile[][] getMatrix(){ return matrix;}
    public void setMatrix(ItemTile[][] matrix){ this.matrix=matrix;}
    public Type getTileType(int x,int y){ return matrix[x][y].getType();}
    public int getTileValue(int x,int y){return matrix[x][y].getTileID();}
    public void setTile(ItemTile tile,int x,int y){
        matrix[x][y]=new ItemTile(tile.getType(),tile.getTileID());
    }
    public boolean isFull(){
        for(int i=0;i< matrix[0].length;i++){
            if(matrix[0][i].getTileID()==-1){
                return false;
            }
        }
        return true;
    }

    //TODO rename without BookshelfBox

    /**
     *
     * @return a list with the cardinalities of the groups of adjacent tiles found by the algorithm
     */
    public List<Integer> findAdjacentTilesGroups() {
        ItemTile startingTile = null;
        List<Integer> groupsSizes = new ArrayList<>();
        Set<ItemTile> visited = new HashSet<>();
        int lastRow = matrix.length - 1;
        int lastColumn = matrix[0].length-1;
        int startingRow = lastRow;
        int startingColumn = 0;

        //find startingTile in the last row:
        for (int j=0; j<matrix[0].length; j++){
            ItemTile currentTile = matrix[lastRow][j];
            if(currentTile != null){
                startingTile = currentTile;
                startingColumn = j;
                break;
            }
            //algorithm can stop if last row is empty
            if (j==lastColumn){ //every element in the last row is null
                return groupsSizes;
            }
        }

        assert startingTile != null : "startingTile is null!";
        Type startingType = startingTile.getType();
        int groupSize = dfs(startingTile, visited, startingType, startingRow, startingColumn);
        groupsSizes.add(groupSize);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ItemTile currentTile = matrix[i][j];
                if (!visited.contains(currentTile) && currentTile.getType() != null) {
                    Type currentType = currentTile.getType();
                    groupSize = dfs(currentTile, visited, currentType, i, j);
                    groupsSizes.add(groupSize);
                }
            }
        }

        return groupsSizes;
    }

    private static final int[][] DIRECTIONS = {{-1,0}, {0,-1}, {0,1}, {1,0}}; //possible directions towards adjacent tiles
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
