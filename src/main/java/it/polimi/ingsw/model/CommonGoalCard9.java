package it.polimi.ingsw.model;

public class CommonGoalCard9 extends CommonGoalCard {

    /**
     * Goal9: "Eight tiles of the same type. There’s no restriction about the position of these tiles."
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int[] seen = new int[Type.values().length]; //array of counters for each Type of tile seen
        //counts the occurrences of each tile type
        for (ItemTile[] itemTiles : mat) {
            for (int j=0; j<mat[0].length; j++) {
                if (itemTiles[j].getTileID()!=-1) {
                    Type type=itemTiles[j].getType();
                    seen[type.ordinal()]++;
                    if(seen[type.ordinal()]>=8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
