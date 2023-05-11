package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.view.ClientView;

public class PrinterCommonGoalAndPoints {
    public void printPoints(ClientView clientView){
        //TODO pensavo di inserire una cornice con i vari punteggi e qualche disegno tipo di due tiles vicine se sono gli adjacentPoints...
        PlayerPointsView playerPoints=clientView.getPlayerPoints();
        System.out.println("POINTS ");
        System.out.println("AdjacentPoint "+playerPoints.getAdjacentPoints()+" How many token you have:"+playerPoints.getHowManyTokenYouHave()+" PersonalGoalPoint "+playerPoints.getPersonalGoalPoints());
        System.out.println("END TURN ");
        String whoChange;
        CommonGoalView[] commonGoalViews= clientView.getCommonGoalViews();
        for(CommonGoalView commonGoalp:commonGoalViews){
            if(commonGoalp.getWhoWonLastToken()==null){
                whoChange="NO ONE HAS WON A TOKEN";
            }else whoChange=commonGoalp.getWhoWonLastToken()+" ONE HAS WON A TOKEN";
            System.out.println(commonGoalp.getLastPointsLeft()+" This are TOKEN points that remain,"+whoChange);
        }

    }
    public  void printCommonGoalCards(ClientView clientView){
        CommonGoalView[] commonGoalViews= clientView.getCommonGoalViews();
        //TODO finire aggiungere qualche disegno o cornice si potrebbe aggiungere la descrizione della common goal sulla destra mentre la common sulla sinistra
        //TODO idea per stampare direttamente la common che serve o passo l'indice dell'identita o utilizziamo pattern strategy

    }

}
