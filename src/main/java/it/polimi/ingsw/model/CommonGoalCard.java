package it.polimi.ingsw.model;

import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.listeners.EventType;
import it.polimi.ingsw.listeners.ListenerManager;

import java.util.ArrayList;

public abstract class CommonGoalCard {


    private ListenerManager listenerManager;
    public void addListener(EventType eventType, EventListener listener) {
        listenerManager=new ListenerManager();
        this.listenerManager.addListener(eventType, listener);
    }

    public void removeListener(EventType eventType, EventListener listener) {
        this.listenerManager.removeListener(eventType, listener);
    }


    private ArrayList<Integer> points;
    public CommonGoalCard(){
        points=new ArrayList<>();
    }
    public int removeToken(String nickname){
        if(points.size()>0){
            int point=points.get(points.size()-1);
            points.remove(points.size()-1);
            listenerManager.fireEvent(EventType.REMOVE_TOKEN, point, nickname);
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
}