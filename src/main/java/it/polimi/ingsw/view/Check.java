package it.polimi.ingsw.view;

import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.util.ArrayList;
/**
 * The Check class provides a set of static methods used by the client application for various checks and validations.
 * These methods are designed to be used by both the GUI and CLI.
 */
public class Check {
    public static int MAX_SELECTABLE_TILES;
    /**
     * Creates an array of ItemTileView objects based on the given coordinates and board box views.
     *
     * @param coordinates the list of coordinates representing the positions on the board
     * @param boardBoxViews the 2D array of board box views representing the board
     * @return an array of ItemTileView objects created from the given coordinates and board box views
     */
    public static ItemTileView[] createItemTileView(ArrayList<Integer> coordinates,BoardBoxView[][] boardBoxViews) {
        if(coordinates!=null){
            ItemTileView[] itemTileViews=new ItemTileView[coordinates.size()/2];
            int j=0;
            for (int i = 0; i < coordinates.size(); i += 2) {
                int x = coordinates.get(i);
                int y = coordinates.get(i + 1);
                itemTileViews[j++]=new ItemTileView(boardBoxViews[x][y].getType(),boardBoxViews[x][y].getId());
            }
            return itemTileViews;
        }
        return null;
    }
    /**
     * Inserts the selected item tiles into the specified column of the bookshelf.
     *
     * @param columnSelected the index of the column where the tiles should be inserted
     * @param bookshelf the 2D array representing the bookshelf
     * @param selectedItemTiles the array of selected item tiles to be inserted
     * @return the updated bookshelf with the tiles inserted
     */
    public static ItemTileView[][] insertTiles(int columnSelected,ItemTileView[][] bookshelf,ItemTileView[] selectedItemTiles){
        int j = 0;
        for (int i = bookshelf.length - 1; j < selectedItemTiles.length; i--) {
            if (bookshelf[i][columnSelected].getTileID() == -1) {
                bookshelf[i][columnSelected] = new ItemTileView(selectedItemTiles[j].getTypeView(), selectedItemTiles[j++].getTileID());
            }
        }
        return bookshelf;
    }
    /**
     * Permutes the order of the selected item tiles based on the specified order.
     *
     * @param tilesSelected the array of selected item tiles
     * @param orderSelected the array representing the desired order of the tiles
     * @return the array of selected item tiles with the permuted order
     */
    public static ItemTileView[] permuteSelection(ItemTileView[] tilesSelected,int[] orderSelected){
        ItemTileView[] temp = new ItemTileView[tilesSelected.length];
        int j=0;
        for(int i : orderSelected){
            temp[j++]=tilesSelected[i];
        }
        return temp ;
    }
    /**
     * Checks if all the coordinates in the list are adjacent to each other.
     *
     * @param coordinatesSelected the list of selected coordinates
     * @return true if all coordinates are adjacent, false otherwise
     */
    public static boolean allAdjacent(ArrayList<Integer> coordinatesSelected) {
        for (int i = 2; i < coordinatesSelected.size(); i = i + 2) {
            if (Math.abs(coordinatesSelected.get(i) - coordinatesSelected.get(i-2)) != 1 && Math.abs(coordinatesSelected.get(i+1) - coordinatesSelected.get(i-1)) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all the coordinates in the list are in the same row or same column.
     *
     * @param coordinatesSelected the list of selected coordinates
     * @return true if all coordinates are in the same row or same column, false otherwise
     */
    public static boolean allSameRowOrSameColumn(ArrayList<Integer> coordinatesSelected) {
        int firstX = coordinatesSelected.get(0);
        int firstY = coordinatesSelected.get(1);
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i < coordinatesSelected.size(); i = i + 2) {
            if (coordinatesSelected.get(i) != firstX) {
                allSameRow = false;
            }
            if (coordinatesSelected.get(i + 1) != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }

        return false;
    }
    /**
     * Checks if the given coordinates are valid on the board.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param board the 2D array representing the board
     * @return an ErrorType if the coordinates are invalid, null otherwise
     */
    public static ErrorType checkCoordinates(int x, int y,BoardBoxView[][] board) {
        if (x < 0 || y<0 || x> board.length-1 || y> board[0].length-1 || board[x][y].getItemTileView().getTileID()==-1) {
            return ErrorType.INVALID_COORDINATES;
        }
        return null;
    }


    /**
     * Checks if the selected coordinates on the board are valid for selection.
     *
     * @param selection the list of selected coordinates
     * @param board     the 2D array representing the board
     * @return an ErrorType if the selection is invalid, null otherwise
     */
    public static ErrorType checkSelectable(ArrayList<Integer> selection,BoardBoxView[][] board) {
        int x=selection.get(selection.size()-2);
        int y=selection.get(selection.size()-1);
        BoardBoxView boardBox = board[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_ENOUGH_FREE_EDGES;
        }
        if ( selection.size()/2 == 1) {
            return null;
        }
        if (!allAdjacent( selection) || !allSameRowOrSameColumn( selection)) {
            return ErrorType.NOT_SAME_ROW_OR_COLUMN;
        }
        return null;
    }
    /**
     * Checks the number of selected tiles on the board against the maximum number of selectable tiles.
     *
     * @param coordinatesSelected the list of selected coordinates
     * @param bookshelf           the 2D array representing the bookshelf
     * @return an ErrorType if there are too many tiles selected, null otherwise
     */
    public static ErrorType checkNumTilesSelectedBoard(ArrayList<Integer> coordinatesSelected,ItemTileView[][] bookshelf){
        if(coordinatesSelected.size()>0){
            if (coordinatesSelected.size() >= numSelectableTiles(bookshelf)*2) {
                return ErrorType.TOO_MANY_TILES;
            }
        }
        return null;
    }
    /**
     * Resets the selected coordinates on the board based on the lastOrAll parameter.
     *
     * @param lastOrAll the flag indicating whether to reset the last selection or all selections
     *                  0: reset the last selection
     *                  1: reset all selections
     * @param coordinatesSelected the list of selected coordinates
     * @return an ErrorType if there are no coordinates selected, null otherwise
     */

    public static ErrorType resetChoiceBoard(int lastOrAll,ArrayList<Integer> coordinatesSelected){
        if (!(coordinatesSelected==null) && !coordinatesSelected.isEmpty()) {
            if(lastOrAll==0){
                int lastIndex = coordinatesSelected.size() - 1;
                coordinatesSelected.remove(lastIndex);
                coordinatesSelected.remove(lastIndex - 1);
            }else coordinatesSelected.clear();
            return null;
        }
        return ErrorType.NOT_VALUE_SELECTED;
    }
    /**
     * Checks if the selected tiles can be placed in the specified column of the bookshelf.
     *
     * @param column           the column index to check
     * @param bookshelfView    the 2D array representing the bookshelf view
     * @param tilesSelected    the array of selected tiles
     * @return an ErrorType if the column is invalid or there are not enough free cells in the column, null otherwise
     */
    public static ErrorType checkBookshelf(int column,ItemTileView[][] bookshelfView,ItemTileView[] tilesSelected) {
        int numSelectedTiles=tilesSelected.length;
        if (column < 0 || column > bookshelfView[0].length-1 ) {
            return ErrorType.INVALID_COLUMN;
        }
        int[] freeShelves=computeFreeShelves(bookshelfView);
        if(!(numSelectedTiles <= freeShelves[column])){
            return ErrorType.NOT_ENOUGH_FREE_CELLS_COLUMN;
        }
        return null;
    }
    /**
     * Calculates the maximum number of selectable tiles from the bookshelf.
     *
     * @param bookshelfView    the 2D array representing the bookshelf view
     * @return the number of selectable tiles, capped at a maximum value
     */
    public static int numSelectableTiles(ItemTileView[][] bookshelfView) {
        int[] freeShelves=computeFreeShelves(bookshelfView);
        int max = maxFreeShelves(freeShelves);
        return (max > MAX_SELECTABLE_TILES) ? MAX_SELECTABLE_TILES : max;
    }
    /**
     * Computes the number of free shelves in each column of the bookshelf.
     *
     * @param bookshelfView    the 2D array representing the bookshelf view
     * @return an array containing the number of free shelves in each column
     */
    public static int[] computeFreeShelves(ItemTileView[][] bookshelfView) {
        int[] freeShelves=new int[bookshelfView[0].length];
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        return freeShelves;
    }
    /**
     * Finds the maximum number of free shelves among the given array of shelves.
     *
     * @param freeShelves    an array representing the number of free shelves in each column
     * @return the maximum number of free shelves
     */
    public static int maxFreeShelves(int[] freeShelves) {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        return max;
    }
    /**
     * Checks if the given order of indices is valid for permuting the item tile views.
     *
     * @param order         an array representing the order of indices
     * @param itemTileViews an array of item tile views
     * @return an ErrorType indicating any errors, or null if the order is valid
     */
    public static ErrorType checkPermuteSelection(int[] order,ItemTileView[] itemTileViews)  {
        int maxIndex = itemTileViews.length - 1;
        for (int i = 0; i < order.length; i++) {
            int curIndex = order[i];
            if (curIndex > maxIndex || curIndex < 0) {
                return ErrorType.INVALID_ORDER_TILE_NUMBER;
            }
            for (int j = i + 1; j < order.length; j++) {
                if (order[j] == curIndex) {
                    return ErrorType.INVALID_ORDER_TILE_REPETITION;
                }
            }
        }
        return null;
    }

}
