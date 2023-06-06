package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

import java.io.Serial;
import java.io.Serializable;


/**

 Class representing an immutable cell on the board.
 */
public class BoardBoxView implements Serializable {
    private final boolean occupiable;
    private final ItemTileView itemTileView;
    private final int x;
    private final int y;
    private final int freeEdges;

    @Serial
    private static final long serialVersionUID = -3213024846906965897L;

    /**
     * Constructs a BoardBoxView.
     * @param occupiable Whether the cell is occupiable based on the matrix read from the JSON file,which depends on the number of players.
     * @param itemTileView The ItemTileView located at the position [x][y] on the board.
     * @param x The row of the board.
     * @param y The column of the board.
     * @param freeEdges The number of free edges of the cell.
     */
    public BoardBoxView(boolean occupiable, ItemTileView itemTileView, int x, int y, int freeEdges) {
        this.occupiable = occupiable;
        this.itemTileView = itemTileView;
        this.x = x;
        this.y = y;
        this.freeEdges = freeEdges;
    }
    /**
     * Checks if the cell is occupiable.
     * @return true if the cell is occupiable, false otherwise.
     */
    public boolean isOccupiable() {
        return occupiable;
    }
    /**
     * Gets the ItemTileView associated with the cell.
     * @return The ItemTileView.
     */
    public ItemTileView getItemTileView() {
        return itemTileView;
    }
    /**
     * Gets the type of the cell's item.
     * @return The type of the item.
     */
    public Type getType() {
        return itemTileView.getTypeView();
    }
    /**
     * Gets the ID of the cell's item.
     * @return The ID of the item.
     */
    public int getId() {
        return itemTileView.getTileID();
    }
    /**
     * Gets the row index of the cell on the board.
     * @return The row index.
     */
    public int getX() {
        return x;
    }
    /**
     * Gets the column index of the cell on the board.
     * @return The column index.
     */
    public int getY() {
        return y;
    }
    /**
     * Gets the number of free edges of the cell.
     * @return The number of free edges.
     */
    public int getFreeEdges() {
        return freeEdges;
    }
}

