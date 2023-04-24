package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;

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
            CommonGoalView common=new CommonGoalView(nickname, point, pointsArray);
            modelView.setCommonGoalViews(common,index,nickname);
            return point;
            /*
            MessagePayload payloadWinner=new MessagePayload(EventType.WIN_TOKEN);

            payloadWinner.put(PayloadKeyServer.TOKEN,common);
            payloadWinner.put(PayloadKeyServer.POINTS,point);
            payloadWinner.put(PayloadKeyServer.WHO_CHANGE,nickname);
            payloadWinner.put(PayloadKeyServer.INDEX_TOKEN,index);

            serverView.fireEvent(payloadWinner, MessageFromServerType.DATA,true,nickname);
            /*

            MessagePayload payloadLoser=new MessagePayload(EventType.LOSE_TOKEN);
            payloadLoser.put(PayloadKeyServer.WHO_CHANGE,nickname);
            payloadLoser.put(PayloadKeyServer.TOKEN,common);
            payloadLoser.put(PayloadKeyServer.POINTS,point);
            payloadLoser.put(PayloadKeyServer.INDEX_TOKEN,index);
            serverView.fireEvent(payloadLoser, MessageFromServerType.DATA,false,nickname);

             */

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