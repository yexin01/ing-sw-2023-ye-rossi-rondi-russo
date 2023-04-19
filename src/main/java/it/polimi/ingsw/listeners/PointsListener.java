package it.polimi.ingsw.listeners;

public class PointsListener implements EventListener{
   /*
    @Override
    public void onEvent(EventType eventType, EventValue eventValue, String nickname) {
        int points= (int) eventValue.getValue();
        System.out.println(nickname +" changed  POINTS: "+points);
    }
*/


    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        int points=(Integer)newValue;
        System.out.println(nickname +" changed  POINTS: "+points);
    }


}
