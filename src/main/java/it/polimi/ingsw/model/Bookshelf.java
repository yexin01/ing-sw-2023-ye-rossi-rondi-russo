package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bookshelf {
    private BookshelfBox[][] matrix;
    private int[] freeShelves; //freeShelves[i] = # celle libere nell'i-esima colonna

    public BookshelfBox[][] getMatrix() {
        return matrix;
    }

    public int[] getFreeShelves() {
        return freeShelves;
    }

    public void computeFreeShelves(){
        for (int j=0; j<matrix[0].length; j++){
            for (int i=0; i<matrix.length && matrix[i][j]==null; i++){
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
        return max;
    }

    public boolean isFull (){ //looking at the first row is sufficient
        for(int j=0; j<matrix[0].length; j++){
            if (matrix[0][j]==null) return false;
        }
        return true;
    }

    //each node of the returned list contains the cardinality of the correspondent group found by the algorithm
    public List<Integer> findAdjacentTilesGroups() {
        BookshelfBox startingBox = null;
        List<Integer> groupsSizes = new ArrayList<>();
        Set<BookshelfBox> visited = new HashSet<>();

        //find startingBox in last row:
        int lastRow = matrix.length - 1;
        for (int j=0; j<matrix[0].length; j++){
            BookshelfBox currentBox = matrix[lastRow][j];
            if(currentBox != null){
                startingBox = currentBox;
                break;
            }
            //algorithm can stop if last row is empty
            if (j== matrix[0].length-1){ //every element in the last row is null
                return groupsSizes;
            }
        }

        assert startingBox != null : "startingBox is null!";
        Type startingType = startingBox.getItemTile().getType();
        int groupSize = dfs(startingBox, visited, startingType);
        groupsSizes.add(groupSize);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                BookshelfBox currentBox = matrix[i][j];
                if (!visited.contains(currentBox) && currentBox.getItemTile().getType() != null) {
                    Type currentType = currentBox.getItemTile().getType();
                    groupSize = dfs(currentBox, visited, currentType);
                    groupsSizes.add(groupSize);
                }
            }
        }

        return groupsSizes;
    }

    private static final int[][] DIRECTIONS = {{-1,0}, {0,-1}, {0,1}, {1,0}}; //possible directions towards adjacent tiles
    private int dfs(BookshelfBox currentBox, Set<BookshelfBox> visited, Type type) {
        visited.add(currentBox);
        int size = 1;
        for (int[] dir : DIRECTIONS) {
            int x = currentBox.getX() + dir[0];
            int y = currentBox.getY() + dir[1];
            if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) {
                continue; //proceeding in other adjacent directions
            }
            BookshelfBox nextBox = matrix[x][y];
            if (!visited.contains(nextBox) && nextBox.getItemTile().getType() == type) {
                size += dfs(nextBox, visited, type);
            }
        }
        return size;
    }

    /* attribute checkable is no longer useful

    public void resetCheckable(){
    }

     */



}
