package it.polimi.ingsw.model;

public class CommonGoalCard9 extends CommonGoalCard{
    /**
     * Goal9: "Eight tiles of the same type. There’s no restriction about the position of these tiles."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int [] seen = new int [Type.values().length]; // array of counters for each Type of tile seen

        // check the goal
        // initializes the seen types as 0
        for(int a=0; a<Type.values().length; a++){
            seen[a]=0;
        }
        for (ItemTile[] itemTiles : mat) {
            for (int j=0; j<mat[0].length; j++) {
                for (Type types : Type.values()) {
                    if ( itemTiles[j].getTileID()!=-1 && itemTiles[j].getType().equals(Type.values()[types.ordinal()]) ) {
                        seen[types.ordinal()]++;
                    }
                }
                for (int a=0; a<Type.values().length; a++) {
                    if (seen[a] >= 8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
