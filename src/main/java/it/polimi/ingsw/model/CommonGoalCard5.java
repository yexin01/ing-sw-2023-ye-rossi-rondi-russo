package it.polimi.ingsw.model;

public class CommonGoalCard5 extends CommonGoalCard{
    /**
     * Goal5: "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     *        (as requested by professor Cugola in Slack.channel-requirements)
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int goals;
        int [] seen = new int [Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen

        // check the goal
        goals=0;
        for(int j=0; j<mat[0].length && goals<3; j++){
            // for each column initializes the seen types as 0
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            for (ItemTile[] itemTiles : mat) {
                switch (itemTiles[j].getType()) {
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
            if(notseen>=3){
                goals++;
            }
        }
        return goals >= 3;
    }
}
