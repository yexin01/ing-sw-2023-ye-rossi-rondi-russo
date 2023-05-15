package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.view.ClientView;

public class PrinterCommonGoalAndPoints {
    private String[] commonGoalCard1  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + "                " + "┃" + "      Due gruppi separati di 4 tessere dello",
        "┃" + "                " + "┃" + "      stesso tipo che formano un quadrato 2x2.",
        "┃" + "      ▇▇ ▇▇     " + "┃" + "      Le tessere dei due gruppi devono essere",
        "┃" + "   2x ▇▇ ▇▇     " + "┃" + "      dello stesso tipo.",
        "┃" + "                " + "┃",
        "┃" + "                " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard2  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + "       ▇▇       " + "┃",
        "┃" + "       ▇▇       " + "┃",
        "┃" + "       ▇▇       " + "┃" + "      Due colonne formate ciascuna",
        "┃" + "    2x ▇▇       " + "┃" + "      da 6 diversi tipi di tessere.",
        "┃" + "       ▇▇       " + "┃",
        "┃" + "       ▇▇       " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard3  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + "                " + "┃" + "      Quattro gruppi separati formati ciascuno",
        "┃" + "                " + "┃" + "      da quattro tessere adiacenti dello stesso",
        "┃" + "      ▇▇        " + "┃" + "      tipo (non necessariamente come mostrato",
        "┃" + "   4x ▇▇ ▇▇     " + "┃" + "      in figura). Le tessere di un gruppo possono",
        "┃" + "         ▇▇     " + "┃" + "      essere diverse da quelle di un altro gruppo.",
        "┃" + "                " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard4  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + "                " + "┃" + "      Sei gruppi separati formati ciascuno",
        "┃" + "                " + "┃" + "      da due tessere adiacenti dello stesso tipo",
        "┃" + "                " + "┃" + "      (non necessariamente come mostrato in",
        "┃" + "       ▇▇       " + "┃" + "      figura). Le tessere di un gruppo possono",
        "┃" + "    6x ▇▇       " + "┃" + "      essere diverse da quelle di un altro gruppo.",
        "┃" + "                " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard5  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + " ▇▇             " + "┃",
        "┃" + " ▇▇             " + "┃" + "      Tre colonne formate ciascuna da",
        "┃" + " ▇▇  max 3 ≠    " + "┃" + "      6 tessere di uno, due o tre tipi differenti.",
        "┃" + " ▇▇    x3       " + "┃" + "      Colonne diverse possono avere",
        "┃" + " ▇▇             " + "┃" + "      combinazioni diverse di tipi di tessere.",
        "┃" + " ▇▇             " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard6  = {
        "┏" + "━━━━━━━━━━━━━━━━" + "┓",
        "┃" + "                " + "┃",
        "┃" + "                " + "┃",
        "┃" + "       2x       " + "┃" + "      Due righe formate ciascuna",
        "┃" + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + "┃" + "      da 6 diversi tipi di tessere.",
        "┃" + "                " + "┃",
        "┃" + "                " + "┃",
        "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard7  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + "                " + "┃",
            "┃" + "                " + "┃" + "      Quattro righe formate ciascuna",
            "┃" + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + "┃" + "      da 5 tessere di uno, due o tre tipi",
            "┃" + "     max 3 ≠    " + "┃" + "      differenti. Righe diverse possono avere",
            "┃" + "       x4       " + "┃" + "      combinazioni diverse di tipi di tessere.",
            "┃" + "                " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard8  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + " ▇▇          ▇▇ " + "┃",
            "┃" + "                " + "┃",
            "┃" + "                " + "┃" + "      Quattro tessere dello stesso tipo",
            "┃" + "                " + "┃" + "      ai quattro angoli della Libreria.",
            "┃" + "                " + "┃",
            "┃" + " ▇▇          ▇▇ " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard9  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + "                " + "┃",
            "┃" + "                " + "┃" + "      Otto tessere dello stesso tipo. Non ci",
            "┃" + "     ▇▇  ▇▇     " + "┃" + "      sono restrizioni sulla posizione di",
            "┃" + "   ▇▇  ▇▇  ▇▇   " + "┃" + "      queste tessere.",
            "┃" + "   ▇▇  ▇▇  ▇▇   " + "┃",
            "┃" + "                " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard10  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + "                " + "┃",
            "┃" + "                " + "┃",
            "┃" + "    ▇▇    ▇▇    " + "┃" + "      Cinque tessere dello stesso tipo",
            "┃" + "       ▇▇       " + "┃" + "      che formano una X.",
            "┃" + "    ▇▇    ▇▇    " + "┃",
            "┃" + "                " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard11  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + "                " + "┃",
            "┃" + " ▇▇             " + "┃",
            "┃" + "    ▇▇          " + "┃" + "      Cinque tessere dello stesso tipo che",
            "┃" + "       ▇▇       " + "┃" + "      formano una diagonale.",
            "┃" + "          ▇▇    " + "┃",
            "┃" + "             ▇▇ " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[] commonGoalCard12  = {
            "┏" + "━━━━━━━━━━━━━━━━" + "┓",
            "┃" + "                " + "┃" + "      Cinque colonne di altezza crescente o",
            "┃" + " ▇▇             " + "┃" + "      decrescente: a partire dalla prima colonna",
            "┃" + " ▇▇ ▇▇          " + "┃" + "      a sinistra o a destra, ogni colonna successiva",
            "┃" + " ▇▇ ▇▇ ▇▇       " + "┃" + "      deve essere formata da una tessera in più.",
            "┃" + " ▇▇ ▇▇ ▇▇ ▇▇    " + "┃" + "      Le tessere possono essere di qualsiasi tipo.",
            "┃" + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + "┃",
            "┗" + "━━━━━━━━━━━━━━━━" + "┛"
    };

    private String[][] cgcFigures = {commonGoalCard1, commonGoalCard2, commonGoalCard3, commonGoalCard4, commonGoalCard5,
                             commonGoalCard6, commonGoalCard7, commonGoalCard8, commonGoalCard9, commonGoalCard10,
                             commonGoalCard11, commonGoalCard12};

    public String[] getCommonGoalCard (int i){
        return cgcFigures[i-1];
    }

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

    //TODO: aggiusta il related problem
    public  void printCommonGoalCards(ClientView clientView){
        int cgc1=1;
        int cgc2=2;
        for (int i=0; i<commonGoalCard1.length; i++){
            Colors.colorizeSize(Colors.BLUE_CODE, getCommonGoalCard(cgc1)[i], 70);
            Colors.colorizeSize(Colors.BLUE_CODE, getCommonGoalCard(cgc2)[i], 70);
            System.out.println("");
        }
    }

    /*
    public  void printCommonGoalCards(ClientView clientView){
        CommonGoalView[] commonGoalViews= clientView.getCommonGoalViews();
        //TODO finire aggiungere qualche disegno o cornice si potrebbe aggiungere la descrizione della common goal sulla destra mentre la common sulla sinistra
        //TODO idea per stampare direttamente la common che serve o passo l'indice dell'identita o utilizziamo pattern strategy

    }

     */

}
