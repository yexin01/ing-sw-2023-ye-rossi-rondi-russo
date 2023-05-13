package it.polimi.ingsw.view;

import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.util.ArrayList;

public class Check {
    public static ItemTileView[] createItemTileView(ArrayList<Integer> coordinates,BoardBoxView[][] boardBoxViews) throws Error {
        if(coordinates!=null){
            ItemTileView[] itemTileViews=new ItemTileView[coordinates.size()/2];
            //BoardBoxView[][] boardView= boardBoxViews.getBoardView();
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
    public static ItemTileView[][] insertTiles(int columnSelected,ItemTileView[][] bookshelf,ItemTileView[] selectedItemTiles) throws Error {
        //ItemTileView[][] bookshelf=clientView.getBookshelfView();
        //ItemTileView[] selectedItemTiles= clientView.getTilesSelected();
        int j = 0;
        for (int i = bookshelf.length - 1; j < selectedItemTiles.length; i--) {
            if (bookshelf[i][columnSelected].getTileID() == -1) {
                bookshelf[i][columnSelected] = new ItemTileView(selectedItemTiles[j].getTypeView(), selectedItemTiles[j++].getTileID());
            }
        }
        return bookshelf;
    }

    public static ItemTileView[] permuteSelection(ItemTileView[] tilesSelected,int[] orderSelected){
        ItemTileView[] temp = new ItemTileView[tilesSelected.length];
        int j=0;
        for(int i : orderSelected){
            System.out.println(i);
            temp[j++]=tilesSelected[i];
        }
        return temp ;
    }
    /**
     * @return check that each ItemTile of selectedBoard is adjacent to the previous one
     */
    public static boolean allAdjacent(ArrayList<Integer> coordinatesSelected, int selectedCount) {
        for (int i = 2; i <= selectedCount * 2; i = i + 2) {
            if (Math.abs(coordinatesSelected.get(i) - coordinatesSelected.get(i - 2)) != 1 && Math.abs(coordinatesSelected.get(i + 1) - coordinatesSelected.get(i - 1)) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return check that all the ItemTiles in the selectedBoard array are on the same row or column
     */
    public static boolean allSameRowOrSameColumn(ArrayList<Integer> coordinatesSelected,int selectedCount) {
        int firstX = coordinatesSelected.get(0);
        int firstY = coordinatesSelected.get(1);
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i <= selectedCount * 2; i = i + 2) {
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
    public static ErrorType checkCoordinates(int x, int y,BoardBoxView[][] board) {
        //BoardBoxView[][] board= clientView.getBoardView();
        if (x < 0 || y<0 || x> board.length-1 || y> board[0].length-1 || !board[x][y].isOccupiable() ) {
            return ErrorType.INVALID_COORDINATES;
        }
        return null;
    }

    /**
     * adjacent, in the same row or column and adjacent
     *
     * @return
     */

    public static ErrorType checkSelectable(int x, int y,ArrayList<Integer> selection,BoardBoxView[][] board) throws Error {
        //TODO AGGIUNGERE 3 COME PARAMETRO
        //TODO ricontrollare ill metodo
        ArrayList<Integer> coordinatesSelected =selection;

        int selectedCount=coordinatesSelected.size()/2;
        if (selectedCount >= (3)) {
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox = board[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_ENOUGH_FREE_EDGES;
        }
        coordinatesSelected.add(x);
        coordinatesSelected.add(y);

        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent(coordinatesSelected,selectedCount) || !allSameRowOrSameColumn(coordinatesSelected,selectedCount)) {
            coordinatesSelected.remove(coordinatesSelected.size() - 1);
            coordinatesSelected.remove(coordinatesSelected.size() - 1);
            return ErrorType.NOT_SAME_ROW_OR_COLUMN;
        }
        return null;
    }

    public static ErrorType checkNumTilesSelectedBoard(ArrayList<Integer> coordinatesSelected,ItemTileView[][] bookshelf){
        if(coordinatesSelected!=null){
            if (coordinatesSelected.size() >= numSelectableTiles(bookshelf)*2) {
                return ErrorType.TOO_MANY_TILES;
            }else return null;

        }else return null;
    }
    //TODO questi li cambiero
    public static ErrorType resetChoiceBoard(int lastOrAll,ArrayList<Integer> coordinatesSelected) throws Error{
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
    public static ErrorType checkBookshelf(int column,ItemTileView[][] bookshelfView,ItemTileView[] tilesSelected) throws Error {
        //int numSelectedTiles=clientView.getCoordinatesSelected().size()/2;
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
    public static int numSelectableTiles(ItemTileView[][] bookshelfView) {
        int[] freeShelves=computeFreeShelves(bookshelfView);
        //TODO importare 3 come parametro attraverso gamerules
        int max = maxFreeShelves(freeShelves);
        return (max > 3) ? 3 : max;
    }
    public static int[] computeFreeShelves(ItemTileView[][] bookshelfView) {
        //ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        int[] freeShelves=new int[bookshelfView[0].length];
        for (int j = 0; j < bookshelfView[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < bookshelfView.length && bookshelfView[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        return freeShelves;
    }
    public static int maxFreeShelves(int[] freeShelves) {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        return max;
    }

    public static ErrorType checkPermuteSelection(int[] order,ArrayList<Integer> coordinatesSelected) throws Error {
        int maxIndex = coordinatesSelected.size()/2 - 1;
        for (int i = 0; i < order.length; i++) {
            int curIndex = order[i];
            if (curIndex > maxIndex || curIndex < 0) {
                return ErrorType.INVALID_ORDER_TILE_NUMBER;
                //throw new Error(ErrorType.INVALID_ORDER_TILE);
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