package it.polimi.ingsw.model;

public class CommonGoalCard11 extends CommonGoalCard{
    /**
     * Goal11: "Five tiles of the same type forming a diagonal."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        boolean verified;
        // check the goal
        for (int i=0; i<=mat.length-mat[0].length; i++) {
            if(mat[i][0].getTileID()!=-1){
                verified=true;
                for (int a=1; a<mat[0].length && verified; a++) {
                    if(mat[i+a][a].getTileID()==-1){
                        verified=false;
                    }
                    if ( verified && !mat[i][0].getType().equals(mat[i+a][a].getType())) {
                        verified=false;
                    }
                }
            }else{
                verified=false;
            }
            if(verified){
                return true;
            }
        }
        for (int i=0; i<=mat.length-mat[0].length; i++) {
            if(mat[i][mat[0].length-1].getTileID()!=-1){
                verified=true;

                for (int a=mat[0].length-1, b=0; a>1 && b<mat.length-1 && verified; b++, a--) {
                    if(mat[i+b][a].getTileID()==-1){
                        verified=false;
                    }
                    if ( verified && !mat[i][mat[0].length-1].getType().equals(mat[i+b][a].getType()) ) {
                        verified=false;
                    }
                }
            }else{
                verified=false;
            }
            if(verified){
                return true;
            }
        }
        return false;
    }
}
