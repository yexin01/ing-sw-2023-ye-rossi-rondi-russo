package it.polimi.ingsw.model;

public class CommonGoalCard2567 extends CommonGoalCard{
    /**
     * This checkGoal implements the algorithms for CommonGoalCards 2,5,6,7
     *      Goal2: "Two columns each formed by 6 different types of tiles."
     *      Goal5: "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
     *              The tiles of one group can be different from those of another group."
     *      Goal6: "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line."
     *      Goal7: "Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line."
     * @param numCommonGoalCard
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    // TODO how to handle numCommonGoalCard
    @Override
    public boolean checkGoal (int numCommonGoalCard, ItemTile[][] mat){
        int goals;
        int [] seen = new int[Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen
        int [] settings = new int[3]; // 0: goalsToReach, 1: dim of lines, 2: dim of columns

        // TODO create exception ?
        /*
        if(numCommonGoalCard!=2 && numCommonGoalCard!=5 && numCommonGoalCard!=6 && numCommonGoalCard!=7){
            System.out.println("cannot use this function for commonGoalCards that are not 2,5,6,7!");
            return false;
        }*/

        // initializes goalsToReach, lines, columns according to numCommonGoalCard selected
        settings[1]=mat.length;
        settings[2]=mat[0].length;
        settingsCase(numCommonGoalCard, settings);

        // check the goal
        goals=0;
        for(int j = 0; j<settings[2] && goals<settings[0]; j++){
            // for each column (cases:2,5) or line (cases:6,7), it resets the array seen[]
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            switch (numCommonGoalCard) {
                case 2, 5:
                    for(int i=0; i<settings[1]; i++){
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
                    break;
                case 6, 7:
                    for(int i=0; i<settings[1]; i++){
                        switch (mat[j][i].getType()) {
                            case CAT -> seen[0]++;
                            case BOOK -> seen[1]++;
                            case GAME -> seen[2]++;
                            case FRAME -> seen[3]++;
                            case TROPHY -> seen[4]++;
                            case PLANT -> seen[5]++;
                            default -> System.out.println("type_tile not valid!");
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
            goals=updateGoalsGroupsFound(numCommonGoalCard,notseen,goals);
        }
        return goals>=settings[0];
    }

    private void settingsCase (int numCommonGoalCard, int[] settings){
        int temp;
        switch (numCommonGoalCard) {
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

    private int updateGoalsGroupsFound (int numCommonGoalCard, int notseen, int goals){
        switch (numCommonGoalCard) {
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



