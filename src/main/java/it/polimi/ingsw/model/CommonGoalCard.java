package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class CommonGoalCard {

    private ArrayList<Integer> points;
    public int removeToken(){
        if(points.size()>0){
            int point=points.get(points.size()-1);
            points.remove(points.size()-1);
            return point;
        }
        return 0;
    }

  /*
    /**
     * when checkGoal() function is called, you give in input bookshelf.getmatrix()
     * this function is abstract and will be implemented in different versions according to the CommonGoalCard
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    /**
     *
     * @param bookshelf
     * @return
     */

    public abstract boolean checkGoal(ItemTile[][] matrix);

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}