package it.polimi.ingsw.model;

public class CommonGoalCard12 extends CommonGoalCard{
    /**
     * Goal12: "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right,
     *          each next column must be made of exactly one more tile. Tiles can be of any type."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        boolean verified;
        int i;

        // used only for debugging to see occupied matrix in bookshelf
        /*
        System.out.println("Matrice occupied: ");
        for (int x=0; x<mat.length; x++) {
            for (int j=0; j<mat[x].length; j++) {
                if(mat[x][j].getTileID()!=-1){
                    System.out.println("1 ");
                }else{
                    System.out.println("0 ");
                }
            }
            System.out.println(" ");
        }
        */

        // check the goal

        // case1: decreasing height
        i=0;
        if (mat[0][0].getTileID()==-1){
            i++;
        }
        verified=true;
        for (int a=0; a<mat[i].length && verified; a++) {
            if (mat[i+a][a].getTileID()==-1){
                verified=false;
            } else if (a+1<mat[i].length && mat[i+a][a+1].getTileID()!=-1){
                verified=false;
            }
        }
        if(verified){
            return true;
        }

        // case2: increasing height
        i=0;
        if (mat[0][mat[i].length-1].getTileID()==-1){
            i++;
        }
        verified=true;
        for (int a=mat[i].length-1; a>=0 && verified; a--) {
            if (mat[i-a+mat[i].length-1][a].getTileID()==-1){
                verified=false;
            } else if (a-1>=0 && mat[i-a+mat[i].length-1][a-1].getTileID()!=-1) {
                verified=false;
            }
        }
        return verified;
    }
}
