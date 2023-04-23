package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.server.ServerView;

import java.util.ArrayList;

public abstract class CommonGoalCard {
   // private GameInfo gameInfo;
    private ServerView serverView;

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
            MessagePayload payloadWinner=new MessagePayload(EventType.WIN_TOKEN);
            CommonGoalView common=new CommonGoalView(getLastPoint(),nickname);
            payloadWinner.put(PayloadKeyServer.TOKEN,common);
            serverView.setCommonGoalViews(common,index);
            serverView.firePlayer(payloadWinner, MessageFromServerType.DATA,nickname);
            MessagePayload payloadLoser=new MessagePayload(EventType.LOSE_TOKEN);
            payloadLoser.put(PayloadKeyServer.WHO_CHANGE,nickname);
            payloadLoser.put(PayloadKeyServer.TOKEN,new CommonGoalView(getLastPoint(),nickname));
            serverView.fireEvent(payloadLoser, MessageFromServerType.DATA,false,nickname);
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

    public ServerView getServerView() {
        return serverView;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }

}