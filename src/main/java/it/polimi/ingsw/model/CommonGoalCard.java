package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * CommonGoalCard is the abstract class that will be extended by the 12 different types of CommonGoalCard
 */

public abstract class CommonGoalCard {
    private ArrayList<Integer> points;

    /**
     * constructor of CommonGoalCard
     */
    public CommonGoalCard(){
        points=new ArrayList<>();
    }

    /**
     * Abstract method to check if the goal is reached or not and will be implemented by the 12 different types of CommonGoalCard
     * @param matrix matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    public abstract boolean checkGoal(ItemTile[][] matrix);

    /**
     * @return the last point of the ArrayList points
     */
    public int getLastPoint(){
        if(points.size()==0){
            return 0;
        }else return points.get(points.size()-1);
    }

    /**
     * Method to remove the last point of the ArrayList points
     * @return the point removed
     */
    public int removeToken(){
        if(points.size()>0){
            int point=points.get(points.size()-1);
            points.remove(points.size()-1);
            return point;
        }
        return 0;
    }

    /**
     * @return the ArrayList points
     */
    public ArrayList<Integer> getPoints() {
        return points;
    }

    /**
     * Method to set the ArrayList points
     * @param points ArrayList of points
     */
    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }

}