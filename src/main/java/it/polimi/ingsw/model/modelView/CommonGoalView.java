package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.CommonGoalCard;

public class CommonGoalView {
    private final CommonGoalCard commonGoalCard;

    public CommonGoalView(CommonGoalCard commonGoalCard) {
        this.commonGoalCard = commonGoalCard;
    }

    public int getLastPointRemain() {
        return commonGoalCard.getLastPoint();
    }
/*
    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

 */
}
