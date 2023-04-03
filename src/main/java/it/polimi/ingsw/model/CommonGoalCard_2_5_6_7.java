package it.polimi.ingsw.model;

public class CommonGoalCard_2_5_6_7 extends CommonGoalCard{

    //TODO change the constructor to set this ID
    private final int CommonGoalCardID=7;

    /**
     * This checkGoal implements the algorithms for CommonGoalCards 2,5,6,7
     *      Goal2: "Two columns each formed by 6 different types of tiles."
     *      Goal5: "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column."
     *      Goal6: "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line."
     *      Goal7: "Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    @Override
    public boolean checkGoal (ItemTile[][] mat){
        int goals;
        int [] seen = new int[Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen
        int [] settings = new int[3]; // 0: goalsToReach, 1: dim of lines, 2: dim of columns
        boolean notnull=true; // the column/line should be full to be counted as a goal

        // initializes goalsToReach, lines, columns according to numCommonGoalCard selected
        settings[1]=mat.length;
        settings[2]=mat[0].length;
        settingsCase(settings);

        // check the goal
        goals=0;
        for(int j = 0; j<settings[2] && goals<settings[0]; j++){
            // for each column (cases:2,5) or line (cases:6,7), it resets the array seen[]
            notnull=true;
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            switch (CommonGoalCardID) {
                case 2, 5:
                    for(int i=0; i<settings[1]; i++){
                        for(Type types : Type.values()){
                            if( mat[i][j].getTileID()!=-1 && mat[i][j].getType().equals(Type.values()[types.ordinal()]) ){
                                seen[types.ordinal()]++;
                            } else if (mat[i][j].getTileID()==-1) {
                                notnull=false;
                            }
                        }
                    }
                    break;
                case 6, 7:
                    for(int i=0; i<settings[1]; i++){
                        for(Type types : Type.values()){
                            if( mat[j][i].getTileID()!=-1 && mat[j][i].getType().equals(Type.values()[types.ordinal()]) ){
                                seen[types.ordinal()]++;
                            } else if (mat[j][i].getTileID()==-1) {
                                notnull=false;
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("cannot use this function for commonGoalCards that are not 2,5,6,7!");
            }
            notseen=0;
            for(int a=0; a<Type.values().length; a++){
                if(seen[a]==0){
                    notseen++;
                }
            }
            if(notnull){
                goals=updateGoalsGroupsFound(notseen,goals);
            }
        }
        return goals>=settings[0];
    }

    public void settingsCase (int[] settings){
        int temp;
        switch (CommonGoalCardID) {
            case 2 -> settings[0] = 2;
            case 5 -> settings[0] = 3;
            // in cases:6,7 it switches dim of lines with dim of columns because the algorithm checks per lines and not per columns
            case 6 -> {
                settings[0] = 2;
                temp = settings[1];
                settings[1] = settings[2];
                settings[2] = temp;
            }
            case 7 -> {
                settings[0]  = 4;
                temp = settings[1];
                settings[1] = settings[2];
                settings[2] = temp;
            }
            default -> {
                settings[0]  = -1;
                System.out.println("cannot use this function for commonGoalCards that are not 2,5,6,7!");
            }
        }
    }

    private int updateGoalsGroupsFound (int notseen, int goals){
        switch (CommonGoalCardID) {
            case 2:
                if(notseen==0){
                    goals++;
                }
                break;
            case 5, 7:
                if(notseen>=3){
                    goals++;
                }
                break;
            case 6:
                if(notseen<=1){
                    goals++;
                }
                break;
            default:
                System.out.println("cannot use this function for commonGoalCards that are not 2,5,6,7!");
        }
        return goals;
    }
}



