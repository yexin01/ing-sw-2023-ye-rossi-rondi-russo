package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *Class represents the player's personalGoal (immutable)
 */

public class PersonalGoalCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 1644368757037203142L;

    private final int idPersonal;
    private final ArrayList<PersonalGoalBox> cells;

    /**
     * Constructs a PersonalGoalCard object with the specified ID and personal goal boxes.
     * @param idPersonal The ID of the personal goal card.
     * @param cells The list of PersonalGoalBox objects representing the cells of the personal goal.
     */
    public PersonalGoalCard(int idPersonal, ArrayList<PersonalGoalBox> cells) {
        this.idPersonal = idPersonal;
        this.cells = cells;
    }
    /**
     * Returns the list of PersonalGoalBox objects representing the cells of the personal goal.
     * @return The list of PersonalGoalBox objects.
     */
    public ArrayList<PersonalGoalBox> getCells() {
        return cells;
    }
    /**
     * Returns the ID of the personal goal card.
     * @return The ID of the personal goal card.
     */
    public int getIdPersonal() {
        return idPersonal;
    }
}



