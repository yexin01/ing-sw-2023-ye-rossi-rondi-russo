package it.polimi.ingsw.model;

public class CheckGoal9 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal9(mat);
    }

    private boolean checkGoal9(ItemTile[][] mat) {
        int [] seen = new int [Type.values().length]; // array of counters for each Type of tile seen

        // check the goal
        // initializes the seen types as 0
        for(int a=0; a<Type.values().length; a++){
            seen[a]=0;
        }
        for (ItemTile[] itemTiles : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                switch (itemTiles[j].getType()) {
                    case CAT -> seen[0]++;
                    case BOOK -> seen[1]++;
                    case GAME -> seen[2]++;
                    case FRAME -> seen[3]++;
                    case TROPHY -> seen[4]++;
                    case PLANT -> seen[5]++;
                    default -> System.out.println("type_tile not valid!");
                }
                for (int a = 0; a < Type.values().length; a++) {
                    if (seen[a] >= 8) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
