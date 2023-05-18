package it.polimi.ingsw.message;

/**
 * Enum that represents the key of the payload for information inputs from the client during the game
 */
public enum Data implements KeyAbstractPayload {
    ERROR,RANKING,CONTENT,SELECTED_ITEMS,PHASE, PERSONAL_POINTS,
    WHO_CHANGE,NEW_BOARD,NEW_BOOKSHELF,POINTS,TOKEN,PERSONAL_GOAL_CARD,VALUE_CLIENT,COMMON_GOAL,
}
