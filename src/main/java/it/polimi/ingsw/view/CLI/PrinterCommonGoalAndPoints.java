package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.view.ClientView;

public class PrinterCommonGoalAndPoints {
    private String borderColor = Colors.BEIGE_CODE;
    private int lineLength = 16;

    String tlc = borderColor+"┏"+"\u001B[0m"; //topLeftCorner
    String trc = borderColor+"┓"+"\u001B[0m"; //topRightCorner
    String blc = borderColor+"┗"+"\u001B[0m"; //bottomLeftCorner
    String brc = borderColor+"┛"+"\u001B[0m"; //bottomRightCorner
    String hd = borderColor+"━"+"\u001B[0m"; //horizontalDash
    String vd = borderColor+"┃"+"\u001B[0m"; //verticalDash

    private String[] commonGoalCard1 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "      ▇▇ ▇▇     " + vd,
            vd + "   2x ▇▇ ▇▇     " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };
    private String[] commonGoalCard1Description = {
            "",
            "      Due gruppi separati di 4 tessere dello",
            "      stesso tipo che formano un quadrato 2x2.",
            "      Le tessere dei due gruppi devono essere",
            "      dello stesso tipo.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard2 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "       ▇▇       " + vd,
            vd + "       ▇▇       " + vd,
            vd + "       ▇▇       " + vd,
            vd + "    2x ▇▇       " + vd,
            vd + "       ▇▇       " + vd,
            vd + "       ▇▇       " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard2Description = {
            "",
            "",
            "",
            "      Due colonne formate ciascuna",
            "      da 6 diversi tipi di tessere.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard3 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "      ▇▇        " + vd,
            vd + "   4x ▇▇ ▇▇     " + vd,
            vd + "         ▇▇     " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard3Description = {
            "",
            "      Quattro gruppi separati formati ciascuno",
            "      da quattro tessere adiacenti dello stesso",
            "      tipo (non necessariamente come mostrato",
            "      in figura). Le tessere di un gruppo possono",
            "      essere diverse da quelle di un altro gruppo.",
            "",
            ""
    };

    private String[] commonGoalCard4 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "       ▇▇       " + vd,
            vd + "    6x ▇▇       " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard4Description = {
            "",
            "      Sei gruppi separati formati ciascuno",
            "      da due tessere adiacenti dello stesso tipo",
            "      (non necessariamente come mostrato in",
            "      figura). Le tessere di un gruppo possono",
            "      essere diverse da quelle di un altro gruppo.",
            "",
            ""
    };

    private String[] commonGoalCard5 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + " ▇▇             " + vd,
            vd + " ▇▇             " + vd,
            vd + " ▇▇  max 3 ≠    " + vd,
            vd + " ▇▇    x3       " + vd,
            vd + " ▇▇             " + vd,
            vd + " ▇▇             " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard5Description = {
            "",
            "",
            "      Tre colonne formate ciascuna da",
            "      6 tessere di uno, due o tre tipi differenti.",
            "      Colonne diverse possono avere",
            "      combinazioni diverse di tipi di tessere.",
            "",
            ""
    };

    private String[] commonGoalCard6 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "       2x       " + vd,
            vd + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard6Description = {
            "",
            "",
            "",
            "      Due righe formate ciascuna",
            "      da 6 diversi tipi di tessere.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard7 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + vd,
            vd + "     max 3 ≠    " + vd,
            vd + "       x4       " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard7Description = {
            "",
            "",
            "      Quattro righe formate ciascuna",
            "      da 5 tessere di uno, due o tre tipi",
            "      differenti. Righe diverse possono avere",
            "      combinazioni diverse di tipi di tessere.",
            "",
            ""
    };

    private String[] commonGoalCard8 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + " ▇▇          ▇▇ " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + " ▇▇          ▇▇ " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard8Description = {
            "",
            "",
            "",
            "      Quattro tessere dello stesso tipo",
            "      ai quattro angoli della Libreria.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard9 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "     ▇▇  ▇▇     " + vd,
            vd + "   ▇▇  ▇▇  ▇▇   " + vd,
            vd + "   ▇▇  ▇▇  ▇▇   " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard9Description = {
            "",
            "",
            "      Otto tessere dello stesso tipo. Non ci",
            "      sono restrizioni sulla posizione di",
            "      queste tessere.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard10 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "    ▇▇    ▇▇    " + vd,
            vd + "       ▇▇       " + vd,
            vd + "    ▇▇    ▇▇    " + vd,
            vd + "                " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard10Description = {
            "",
            "",
            "",
            "      Cinque tessere dello stesso tipo",
            "      che formano una X.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard11 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + " ▇▇             " + vd,
            vd + "    ▇▇          " + vd,
            vd + "       ▇▇       " + vd,
            vd + "          ▇▇    " + vd,
            vd + "             ▇▇ " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard11Description = {
            "",
            "",
            "",
            "      Cinque tessere dello stesso tipo che",
            "      formano una diagonale.",
            "",
            "",
            ""
    };

    private String[] commonGoalCard12 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + " ▇▇             " + vd,
            vd + " ▇▇ ▇▇          " + vd,
            vd + " ▇▇ ▇▇ ▇▇       " + vd,
            vd + " ▇▇ ▇▇ ▇▇ ▇▇    " + vd,
            vd + " ▇▇ ▇▇ ▇▇ ▇▇ ▇▇ " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard12Description = {
            "",
            "      Cinque colonne di altezza crescente o",
            "      decrescente: a partire dalla prima colonna",
            "      a sinistra o a destra, ogni colonna successiva",
            "      deve essere formata da una tessera in più.",
            "      Le tessere possono essere di qualsiasi tipo.",
            "",
            ""
    };

    private String[][] cgcFigures = {commonGoalCard1, commonGoalCard2, commonGoalCard3, commonGoalCard4, commonGoalCard5,
                             commonGoalCard6, commonGoalCard7, commonGoalCard8, commonGoalCard9, commonGoalCard10,
                             commonGoalCard11, commonGoalCard12};

    private String[][] cgcDescriptions = {commonGoalCard1Description, commonGoalCard2Description, commonGoalCard3Description, commonGoalCard4Description, commonGoalCard5Description, commonGoalCard6Description,
            commonGoalCard7Description, commonGoalCard8Description, commonGoalCard9Description, commonGoalCard10Description,
            commonGoalCard11Description, commonGoalCard12Description};



    public String[] getCommonGoalCard (int i){
        return cgcFigures[i-1];
    }

    public String[] getCommonGoalCardDescription(int i) {
        return cgcDescriptions[i-1];
    }
    public void printToken(int num,String player){

    }

    public void printPoints(ClientView clientView){
        /*
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

         */

    }

    public  void printCommonGoalCards(ClientView clientView){
        /*
        //int numOfCGC = clientView.getIdCommonGoals().length;
        int numOfRows = getCommonGoalCard(clientView.getIdCommonGoals()[0]).length;
        int[] cgcIndexes = clientView.getIdCommonGoals();

        for (int i=0; i<numOfRows; i++){
            if(i==0 || i==numOfRows-1){
                Colors.printSize2(getCommonGoalCard(cgcIndexes[0])[i]+getCommonGoalCardDescription(cgcIndexes[0])[i], 432+60);
                Colors.printSize2(getCommonGoalCard(cgcIndexes[1])[i]+getCommonGoalCardDescription(cgcIndexes[1])[i], 432+60);
                System.out.println("");
            }
            else {
                Colors.printSize2(getCommonGoalCard(cgcIndexes[0])[i]+getCommonGoalCardDescription(cgcIndexes[0])[i], 124);
                Colors.printSize2(getCommonGoalCard(cgcIndexes[1])[i]+getCommonGoalCardDescription(cgcIndexes[1])[i], 124);
                System.out.println("");

            }
        }

         */
    }

    /*
    public  void printCommonGoalCards(ClientView clientView){
        CommonGoalView[] commonGoalViews= clientView.getCommonGoalViews();
        //TODO finire aggiungere qualche disegno o cornice si potrebbe aggiungere la descrizione della common goal sulla destra mentre la common sulla sinistra
        //TODO idea per stampare direttamente la common che serve o passo l'indice dell'identita o utilizziamo pattern strategy

    }

     */

}
