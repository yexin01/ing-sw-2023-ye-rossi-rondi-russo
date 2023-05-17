package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ModelView;

import java.util.ArrayList;

public abstract class CommonGoalCard {
   // private GameInfo gameInfo;


    private ModelView modelView;

    public int getLastPoint(){
        if(points.size()==0){
            return 0;
        }else return points.get(points.size()-1);
    }

    private ArrayList<Integer> points;
    public CommonGoalCard(){
        points=new ArrayList<>();
    }
    public int removeToken(String nickname,int index){
        if(points.size()>0){
            int point=points.get(points.size()-1);
            points.remove(points.size()-1);
            int pointsArray[]= points.stream().mapToInt(Integer::intValue).toArray();
            //CommonGoalView common=new CommonGoalView(nickname, point, pointsArray);
            modelView.getPointsLeftCommon()[index]=getLastPoint();
            return point;
        }
        return 0;
    }


    /**
     * when checkGoal() function is called, you give in input bookshelf.getmatrix()
     * this function is abstract and will be implemented in different versions according to the CommonGoalCard
     * @param matrix matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    public abstract boolean checkGoal(ItemTile[][] matrix);

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }


    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }
}