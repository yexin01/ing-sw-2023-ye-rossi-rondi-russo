package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class that represents the single cell of the PersonalGoalCard having type
 * belonging to the enum Type.
 */
public class PersonalGoalBox implements Serializable {
    private final int x;
    private final int y;
    private final Type type;

    /**
     *Constructor of the PersonalGoalBox
     * @param type:cell type;
     * @param x:bookshelf row;
     * @param y:bookshelf column;
     */
    public PersonalGoalBox(Type type, int x, int y) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getTypePersonal() {
        return type;
    }
}
