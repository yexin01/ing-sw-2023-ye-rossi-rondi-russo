package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.view.CLI.Colors;
import it.polimi.ingsw.view.ClientView;

import javax.swing.*;
import java.util.ArrayList;

//TODO will change

public abstract class ClientInterface extends JPanel {
    private ClientView clientView;

    public abstract String getNickname();
    private int[] freeShelves;

   // String askNickname();

    public abstract int[] askCoordinates() throws Exception;
    public abstract int[] askOrder() throws Exception;
    public abstract int[] askColumn() throws Exception;
    //booleano corrisponde alla partecipazione ad una nuova partita se é true vuole terminare il gioco altrimenti no
    public abstract boolean endGame();
    public abstract void Setup();

    public void createItemTileView() throws Error {
        ItemTileView[] itemTileViews=new ItemTileView[getClientView().getCoordinatesSelected().size()/2];
        BoardBoxView[][] boardView= getClientView().getBoardView();
        int j=0;
        for (int i = 0; i < getClientView().getCoordinatesSelected().size(); i += 2) {
            int x = getClientView().getCoordinatesSelected().get(i);
            int y = getClientView().getCoordinatesSelected().get(i + 1);
            itemTileViews[j++]=new ItemTileView(boardView[x][y].getType(),boardView[x][y].getId());
        }
        getClientView().setTilesSelected(itemTileViews);
    }
    public void insertTiles(int columnSelected) throws Error {
        ItemTileView[][] bookshelf=clientView.getBookshelfView();
        ItemTileView[] selectedItemTiles= clientView.getTilesSelected();
        int j = 0;
        for (int i = bookshelf.length - 1; j < selectedItemTiles.length; i--) {
            if (bookshelf[i][columnSelected].getTileID() == -1) {
                bookshelf[i][columnSelected] = new ItemTileView(selectedItemTiles[j].getTypeView(), selectedItemTiles[j++].getTileID());
            }
        }
    }
    public void permuteSelection(){
        ItemTileView[] temp = new ItemTileView[clientView.getTilesSelected().length];
        int j=0;
        for(int i : clientView.getOrderTiles()){
            temp[j++]=clientView.getTilesSelected()[i];
        }
        clientView.setTilesSelected(temp) ;
    }
    /**
     * @return check that each ItemTile of selectedBoard is adjacent to the previous one
     */
    public boolean allAdjacent(ArrayList<Integer> coordinatesSelected,int selectedCount) {
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
    public boolean allSameRowOrSameColumn(ArrayList<Integer> coordinatesSelected,int selectedCount) {
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
    public ErrorType checkCoordinates(int x, int y) {
        BoardBoxView[][] board= clientView.getBoardView();
        if (x < 0 || y<0 || x> board.length-1 || y> board[0].length-1 || !board[x][y].isOccupiable() ) {
            return ErrorType.INVALID_COORDINATES;
            //throw new Error(ErrorType.INVALID_COORDINATES);
        }
        return null;
    }

    /**
     * adjacent, in the same row or column and adjacent
     *
     * @return
     */

    public ErrorType checkSelectable(int x, int y) throws Error {
        //TODO AGGIUNGERE 3 COME PARAMETRO
        //TODO ricontrollare ill metodo
        ArrayList<Integer> coordinatesSelected =clientView.getCoordinatesSelected();

        int selectedCount=coordinatesSelected.size()/2;
        if (selectedCount >= (3)) {
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox = clientView.getBoardView()[x][y];
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
    public ErrorType checkNumTilesSelectedBoard(){
        if (getClientView().getCoordinatesSelected().size() >= numSelectableTiles()*2) {
            return ErrorType.TOO_MANY_TILES;
        }else return null;
    }
    public ErrorType resetChoiceBoard(int lastOrAll) throws Error{
        if (!getClientView().getCoordinatesSelected().isEmpty()) {
            if(lastOrAll==0){
                int lastIndex = getClientView().getCoordinatesSelected().size() - 1;
                getClientView().getCoordinatesSelected().remove(lastIndex);
                getClientView().getCoordinatesSelected().remove(lastIndex - 1);
            }else getClientView().getCoordinatesSelected().clear();
            return null;
        }
        return ErrorType.NOT_VALUE_SELECTED;
    }


    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    public ClientView getClientView() {
        return clientView;
    }
    public ErrorType checkBookshelf(int column) throws Error {
        //int numSelectedTiles=clientView.getCoordinatesSelected().size()/2;
        int numSelectedTiles=clientView.getTilesSelected().length;
        if (column < 0 || column > clientView.getBookshelfView()[0].length-1 ) {
            return ErrorType.INVALID_COLUMN;
        }
        computeFreeShelves();
        if(!(numSelectedTiles <= freeShelves[column])){
            return ErrorType.NOT_ENOUGH_FREE_CELLS_COLUMN;
        }
        return null;
    }
    public int numSelectableTiles() {
        computeFreeShelves();
        //TODO importare 3 come parametro attraverso gamerules
        int max = maxFreeShelves();
        return (max > 3) ? 3 : max;
    }
    public int[] computeFreeShelves() {
        ItemTileView[][] matrix= clientView.getBookshelfView();
        freeShelves=new int[matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < matrix.length && matrix[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        return freeShelves;
    }
    private int maxFreeShelves() {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        return max;
    }

    public ErrorType checkPermuteSelection(int[] order) throws Error {
        int maxIndex = clientView.getCoordinatesSelected().size()/2 - 1;
        for (int i = 0; i < order.length; i++) {
            int curIndex = order[i];
            if (curIndex > maxIndex || curIndex < 0) {
                return ErrorType.INVALID_ORDER_TILE_NUMBER;
                //throw new Error(ErrorType.INVALID_ORDER_TILE);
            }
            for (int j = i + 1; j < order.length; j++) {
                if (order[j] == curIndex) {
                    return ErrorType.INVALID_ORDER_TILE_REPETITION;
                    // throw new Error(ErrorType.INVALID_ORDER_TILE);
                }
            }
        }
        return null;
    }

    public int[] getFreeshelves() {
        return freeShelves;
    }

    public void setFreeshelves(int[] freeshelves) {
        this.freeShelves = freeshelves;
    }
}
