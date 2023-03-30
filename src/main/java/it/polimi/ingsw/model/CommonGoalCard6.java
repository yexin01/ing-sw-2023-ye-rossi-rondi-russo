package it.polimi.ingsw.model;

public class CommonGoalCard6 extends CommonGoalCard{
    /**
     * Goal6: "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    // TODO delete
    @Override
    public boolean checkGoal(int numCommonGoalCard, ItemTile[][] mat){
        int goals;
        int [] seen = new int [Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen

        // check the goal
        goals=0;
        for(int i=0; i<mat.length && goals<2; i++){
            // for each line initializes the seen types as 0
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            for(int j=0; j<mat[0].length; j++){
                switch (mat[i][j].getType()) {
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
            if(notseen<=1){
                goals++;
            }
        }
        return goals >= 2;
    }
}
