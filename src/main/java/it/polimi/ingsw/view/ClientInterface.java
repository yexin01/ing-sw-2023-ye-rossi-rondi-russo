package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.view.ClientView;

import javax.swing.*;
import java.util.ArrayList;

//TODO will change

public abstract class ClientInterface extends JPanel {
    private ClientView clientView;

    public abstract String getNickname();

   // String askNickname();

    public abstract int[] askCoordinates();
    public abstract int[] askOrder();
    public abstract int[] askColumn();

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
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        coordinatesSelected.add(x);
        coordinatesSelected.add(y);

        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent(coordinatesSelected,selectedCount) || !allSameRowOrSameColumn(coordinatesSelected,selectedCount)) {
            coordinatesSelected.remove(coordinatesSelected.size() - 1);
            coordinatesSelected.remove(coordinatesSelected.size() - 1);
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        return null;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    public ClientView getClientView() {
        return clientView;
    }
    public ErrorType checkBookshelf(int column) throws Error {
        int numSelectedTiles=clientView.getCoordinatesSelected().size()/2;
        if (column < 0 || column > clientView.getBookshelfView()[0].length-1 ) {
            return ErrorType.INVALID_COLUMN;
        }
        if(!(numSelectedTiles <= numSelectableTiles())){
            return ErrorType.NOT_ENOUGH_FREE_CELLS_COLUMN;
        }
        return null;
    }
    public int numSelectableTiles() {
        //TODO importare 3 come parametro attraverso gamerules
        int max = maxFreeShelves(computeFreeShelves());
        return (max > 3) ? 3 : max;
    }
    public int[] computeFreeShelves() {
        ItemTileView[][] matrix= clientView.getBookshelfView();
        int[] freeShelves=new int[matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++) {
            freeShelves[j] = 0;
            for (int i = 0; i < matrix.length && matrix[i][j].getTileID() == -1; i++) {
                freeShelves[j]++;
            }
        }
        return freeShelves;
    }
    private int maxFreeShelves(int[] freeShelves) {
        int max = 0;
        for (int i = 0; i < freeShelves.length; i++) {
            if (freeShelves[i] > max) {
                max = freeShelves[i];
            }
        }
        System.out.println(max);
        return max;
    }

}
