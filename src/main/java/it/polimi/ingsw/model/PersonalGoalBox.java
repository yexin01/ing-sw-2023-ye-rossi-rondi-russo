package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents the single cell of the PersonalGoalCard having type
 * belonging to the enum Type. (immutable)
 */
public class PersonalGoalBox implements Serializable {

    @Serial
    private static final long serialVersionUID = 545291495180279995L;

    private final int x;
    private final int y;
    private final Type type;

    /**
     * Constructs a PersonalGoalBox object with the specified type, row, and column.
     * @param type The type of the personal goal.
     * @param x The row of the bookshelf.
     * @param y The column of the bookshelf.
     */
    public PersonalGoalBox(Type type, int x, int y) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    /**
     * Returns the row of the bookshelf.
     * @return The row of the bookshelf.
     */
    public int getX() {
        return x;
    }
    /**
     * Returns the column of the bookshelf.
     * @return The column of the bookshelf.
     */
    public int getY() {
        return y;
    }
    /**
     * Returns the type of the personal goal.
     * @return The type of the personal goal.
     */
    public Type getTypePersonal() {
        return type;
    }
}
