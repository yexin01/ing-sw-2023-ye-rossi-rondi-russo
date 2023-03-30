package it.polimi.ingsw.model;

public class CommonGoalCard2 extends CommonGoalCard{
    /**
     * Goal2: "Two columns each formed by 6 different types of tiles."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    //TODO delete
    @Override
    public boolean checkGoal(int numCommonGoalCard, ItemTile[][] mat){
        int goals;
        int [] seen = new int [Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen

        // check the goal
        goals=0;
        for(int j=0; j<mat[0].length && goals<2; j++){
            // for each column initializes the seen types as 0
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            for (ItemTile[] bookshelfBoxes : mat) {
                switch (bookshelfBoxes[j].getType()) {
                    case CAT -> seen[0]++;
                    case BOOK -> seen[1]++;
                    case GAME -> seen[2]++;
                    case FRAME -> seen[3]++;
                    case TROPHY -> seen[4]++;
                    case PLANT -> seen[5]++;
                    default -> System.out.println("type_tile not valid!");
                }
            }
            notseen=0;
            for(int a=0; a<Type.values().length; a++){
                if(seen[a]==0){
                    notseen++;
                }
            }
            if(notseen==0){
                goals++;
            }
        }
        return goals >= 2;
    }
}
