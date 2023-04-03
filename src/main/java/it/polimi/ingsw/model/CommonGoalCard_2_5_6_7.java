package it.polimi.ingsw.model;

public class CommonGoalCard_2_5_6_7 extends CommonGoalCard{

    public int[] settingCase(int[] settings) {throw new IllegalArgumentException("Something wrong");}
    public boolean internalCondition1(int i,int j,ItemTile[][] mat,Type type) {throw new IllegalArgumentException("Something wrong");}
    public boolean internalCondition2(int i,int j,ItemTile[][] mat) {throw new IllegalArgumentException("Something wrong");}
    public boolean updateGoalsGroupsFound (int notseen){throw new IllegalArgumentException("Something wrong");};

    public static class local_2_5 extends CommonGoalCard_2_5_6_7 {
        @Override
        public boolean internalCondition1(int i,int j,ItemTile[][] mat,Type type){
            return mat[i][j].getTileID()!=-1 && mat[i][j].getType().equals(Type.values()[type.ordinal()]);
        }
        @Override
        public boolean internalCondition2(int i,int j,ItemTile[][] mat){
            return mat[i][j].getTileID()==-1;
        }

        public static class local2 extends local_2_5{
            @Override
            public int[] settingCase(int[] settings){
                settings[0] = 2;
                return settings;
            }

            @Override
            public boolean updateGoalsGroupsFound (int notseen){
                return notseen==0;
            }

        }
        public static class local5 extends local_2_5{
            @Override
            public int[] settingCase(int[] settings){
                settings[0] = 3;
                return settings;
            }
            @Override
            public boolean updateGoalsGroupsFound (int notseen){
                return notseen>=3;
            }

        }

    }
    public static class local_6_7 extends CommonGoalCard_2_5_6_7 {
        @Override
        public boolean internalCondition1(int i,int j,ItemTile[][] mat,Type type){
            return mat[j][i].getTileID()!=-1 && mat[j][i].getType().equals(Type.values()[type.ordinal()]);
        }
        @Override
        public boolean internalCondition2(int i,int j,ItemTile[][] mat){
            return mat[j][i].getTileID()==-1;
        }
        public int[] swap(int[] settings){
            int temp;
            temp = settings[1];
            settings[1] = settings[2];
            settings[2] = temp;
            return settings;
        }

        public static class local6 extends local_6_7{
            @Override
            public int[] settingCase(int[] settings){
                settings[0] = 2;
                return swap(settings);
            }
            @Override
            public boolean updateGoalsGroupsFound (int notseen){
                return notseen<=1;
            }

        }
        public static class local7 extends local_6_7{
            @Override
            public int[] settingCase(int[] settings){
                int temp;
                settings[0]  = 4;
                return swap(settings);
            }
            @Override
            public boolean updateGoalsGroupsFound (int notseen){
                return notseen>=3;
            }

        }

    }

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
        System.out.println("uncheck tutte");
        int goals;
        int [] seen = new int[Type.values().length]; // array of counters for each Type of tile seen
        int notseen; // counter of types not seen
        int [] settings = new int[3]; // 0: goalsToReach, 1: dim of lines, 2: dim of columns
        boolean notnull=true; // the column/line should be full to be counted as a goal

        // initializes goalsToReach, lines, columns according to numCommonGoalCard selected
        settings[1]=mat.length;
        settings[2]=mat[0].length;
        settings=settingCase(settings);

        // check the goal
        goals=0;
        for(int j = 0; j<settings[2] && goals<settings[0]; j++){
            // for each column (cases:2,5) or line (cases:6,7), it resets the array seen[]
            notnull=true;
            for(int a=0; a<Type.values().length; a++){
                seen[a]=0;
            }
            for(int i=0; i<settings[1]; i++){
                for(Type type : Type.values()){
                    if( internalCondition1(i,j,mat,type)){
                        seen[type.ordinal()]++;
                    } else if (internalCondition2(i,j,mat)) {
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
            if(notnull && updateGoalsGroupsFound(notseen)){
                goals++;
            }
        }
        return goals>=settings[0];
    }
}



