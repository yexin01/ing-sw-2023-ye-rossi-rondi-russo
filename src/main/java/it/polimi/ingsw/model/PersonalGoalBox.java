package it.polimi.ingsw.model;

import java.io.Serializable;

public class PersonalGoalBox implements Serializable {
    private final int x;
    private final int y;
    private final Type type;


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

    public Type getType() {
        return type;
    }
}
