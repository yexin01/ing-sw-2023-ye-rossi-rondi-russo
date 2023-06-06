package it.polimi.ingsw.model;

/**
 *Class representing the cell of the Board.
 */

public class BoardBox {

    private int x;
    private int y;
    private ItemTile tile;
    private boolean occupiable;
    private int freeEdges;

    /**
     * Constructs a BoardBox object with the specified coordinates.
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     */
    public BoardBox(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the x-coordinate of the cell.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }
    /**
     * Sets the x-coordinate of the cell.
     * @param x The x-coordinate to be set.
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Returns the y-coordinate of the cell.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
    /**
     * Sets the y-coordinate of the cell.
     * @param y The y-coordinate to be set.
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Returns the tile placed on the cell.
     * @return The ItemTile object representing the tile.
     */
    public ItemTile getTile() {
        return tile;
    }
    /**
     * Sets the tile on the cell.
     * @param tile The ItemTile object to be set.
     */
    public void setTile(ItemTile tile) {
        this.tile = tile;
    }
    /**
     * Checks if the cell is occupiable.
     * @return true if the cell is occupiable, false otherwise.
     */
    public boolean isOccupiable() {
        return occupiable;
    }
    /**
     * Sets the occupiable status of the cell.
     * @param occupiable The occupiable status to be set.
     */
    public void setOccupiable(boolean occupiable) {
        this.occupiable = occupiable;
    }
    /**
     * Returns the number of free edges on the cell.
     * @return The number of free edges.
     */
    public int getFreeEdges() {
        return freeEdges;
    }
    /**
     * Sets the number of free edges on the cell.
     * @param freeEdges The number of free edges to be set.
     */
    public void setFreeEdges(int freeEdges) {
        this.freeEdges = freeEdges;
    }
    /**
     * Increases the number of free edges on the cell by 1.
     */
    public void increasefreeEdges() {
        freeEdges++;
    }
}
