package it.polimi.ingsw.model;

public class CommonGoalCard5 extends CommonGoalCard{

    /**
     * Goal5: "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal (ItemTile[][] mat){
        int goals;
        int [] seen = new int[Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen
        int [] settings = new int[3]; // 0: goalsToReach, 1: dim of lines, 2: dim of columns
        boolean notnull=true; // the column should be full to be counted as a goal

        // initializes goalsToReach, lines, columns according to the goal, it searches by columns
        settings[0]=3;
        settings[1]=mat.length;
        settings[2]=mat[0].length;

        // check the goal
        goals=0;
        for(int j=0; j<settings[2] && goals<settings[0]; j++){
            // for each column it resets the array seen[]
            notnull=true;
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            for(int i=0; i<settings[1]; i++){
                for(Type types : Type.values()){
                    if( mat[i][j].getTileID()!=-1 && mat[i][j].getType().equals(Type.values()[types.ordinal()]) ){
                        seen[types.ordinal()]++;
                    } else if (mat[i][j].getTileID()==-1) {
                        notnull=false;
                    }
                }
            }
            notseen=0;
            for(int a=0; a<Type.values().length; a++){
                if(seen[a]==0){
                    notseen++;
                }
            }
            if(notnull && notseen>=3){
                goals++;
            }
        }
        return goals>=settings[0];
    }

}