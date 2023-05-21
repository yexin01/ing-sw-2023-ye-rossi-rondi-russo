package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;
import java.util.Arrays;

public class PrinterCommonGoalAndPoints {
    private String borderColor = Colors.BEIGE_CODE;
    private int lineLength = 16;

    String tlc = borderColor+"┏"+"\u001B[0m"; //topLeftCorner
    String trc = borderColor+"┓"+"\u001B[0m"; //topRightCorner
    String blc = borderColor+"┗"+"\u001B[0m"; //bottomLeftCorner
    String brc = borderColor+"┛"+"\u001B[0m"; //bottomRightCorner
    String hd = borderColor+"━"+"\u001B[0m"; //horizontalDash
    String vd = borderColor+"┃"+"\u001B[0m"; //verticalDash

    String oc = Colors.OCHRE_YELLOW_CODE+"▇▇"+"\u001B[0m"; //yellow
    String or = Colors.ORANGE_CODE+"▇▇"+"\u001B[0m"; //orange
    String g = Colors.GREEN_CODE+"▇▇"+"\u001B[0m"; //green
    String l = Colors.LIGHT_BLUE_CODE+"▇▇"+"\u001B[0m"; //lightblue
    String b = Colors.BLUE_CODE+"▇▇"+"\u001B[0m"; //blue
    String p = Colors.PINK_CODE+"▇▇"+"\u001B[0m"; //pink

    private String[] commonGoalCard1 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "      "+p+" "+p+"     " + vd,
            vd + "   2x "+p+" "+p+"     " + vd,
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
            vd + "       "+or+"       " + vd,
            vd + "       "+oc+"       " + vd,
            vd + "       "+p+"       " + vd,
            vd + "    2x "+l+"       " + vd,
            vd + "       "+b+"       " + vd,
            vd + "       "+g+"       " + vd,
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
            vd + "      "+p+"        " + vd,
            vd + "   4x "+p+" "+p+"     " + vd,
            vd + "         "+p+"     " + vd,
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
            vd + "       "+p+"       " + vd,
            vd + "    6x "+p+"       " + vd,
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
            vd + " "+oc+"             " + vd,
            vd + " "+or+"             " + vd,
            vd + " "+p+"  "+Colors.YELLOW_CODE+"max"+"\u001B[0m"+" 3 ≠    " + vd,
            vd + " "+p+"    x3       " + vd,
            vd + " "+p+"             " + vd,
            vd + " "+p+"             " + vd,
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
            vd + " "+or+" "+oc+" "+p+" "+l+" "+g+" "+ vd,
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
            vd + " "+or+" "+oc+" "+p+" "+p+" "+p+" " + vd,
            vd + "     "+Colors.YELLOW_CODE+"max"+"\u001B[0m"+" 3 ≠    " + vd,
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
            vd + " "+b+"          "+b+" " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + "                " + vd,
            vd + " "+b+"          "+b+" " + vd,
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
            vd + "       "+or+"       " + vd,
            vd + "     "+or+"  "+or+"     " + vd,
            vd + "   "+or+"  "+or+"  "+or+"   " + vd,
            vd + "     "+or+"  "+or+"     " + vd,
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
            vd + "    "+or+"    "+or+"    " + vd,
            vd + "       "+or+"       " + vd,
            vd + "    "+or+"    "+or+"    " + vd,
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
            vd + " "+g+"             " + vd,
            vd + "    "+g+"          " + vd,
            vd + "       "+g+"       " + vd,
            vd + "          "+g+"    " + vd,
            vd + "             "+g+" " + vd,
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

    private String[] token2 = {
            "┏"+"━"+"┓",
            "┃"+"2"+"┃",
            "┗"+"━"+"┛"
    };

    String[] token4 = {
            "┏"+"━━━"+"┓",
            "┃"+" 4 "+"┃",
            "┗"+"━━━"+"┛"
    };

    String[] token6 = {
            "┏"+"━━━"+"┓",
            "┃"+" 6 "+"┃",
            "┗"+"━━━"+"┛"
    };

    String[] token8 = {
            "┏"+"━━━"+"┓",
            "┃"+" 8 "+"┃",
            "┗"+"━━━"+"┛"
    };

    private String[][] cgcFigures = {commonGoalCard1, commonGoalCard2, commonGoalCard3, commonGoalCard4, commonGoalCard5,
                             commonGoalCard6, commonGoalCard7, commonGoalCard8, commonGoalCard9, commonGoalCard10,
                             commonGoalCard11, commonGoalCard12};

    private String[][] cgcDescriptions = {commonGoalCard1Description, commonGoalCard2Description, commonGoalCard3Description, commonGoalCard4Description, commonGoalCard5Description, commonGoalCard6Description,
            commonGoalCard7Description, commonGoalCard8Description, commonGoalCard9Description, commonGoalCard10Description,
            commonGoalCard11Description, commonGoalCard12Description};

    private String[][] tokens = {token2, token4, token6, token8};

    public String[] getCommonGoalCard (int i){
        return cgcFigures[i];
    }

    public String[] getCommonGoalCardDescription(int i) {
        return cgcDescriptions[i];
    }

    public String[] getToken(int i){
        return tokens[i];
    }
    public void printToken(int num,String player){
        System.out.println("il token "+num+"   nome  "+player);
    }

    public void printPoints(ClientView clientView){
        //i playerpoints view della clientView saranno ordinati dal piu basso al piu alto leggerli al contrario
        //durante il turno per i personal point degli altri andrebbe messo ?, in modo tale che all utente non venga mostrato i punteggi personal degli altri
        //la classifica si basa solo sui common e adjacent mentre i personal sono mandati singolarmente , cosi da non vedere quelli degli altri
        PlayerPointsView[] playerPoints=clientView.getPlayerPointsViews();
        for(int i=playerPoints.length-1;i>=0;i--){
            System.out.print(playerPoints[i].getNickname()+"  ha ottenuto "+(playerPoints[i].getPoints()));
            for(int j=0;j<playerPoints[i].getPointsToken().length;j++){
                System.out.print("Punteggi token "+playerPoints[i].getPointsToken()[j]);
            }
            System.out.print("Punteggi adjacent "+playerPoints[i].getAdjacentPoints());
            if(playerPoints[i].getNickname().equals(clientView.getNickname())){
                System.out.print("Punteggi personal solo del piocatore "+clientView.getPersonalPoints());
            }
        }

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
    public  void printEndGame(ClientView clientView,int[] personalPoints){
        //i playerpoints saranno ordinati dal basso verso l alto leggerli al contrario e per il punteggio totale sommare quello in posizione index dell array personal
        //sono gia associati ai rispettivi giocatori ma non potevo mostrare i personal points prima
        PlayerPointsView[] playersRanking= clientView.getPlayerPointsViews();
        for(int i=playersRanking.length-1;i>=0;i--){
            System.out.print(playersRanking[i].getNickname()+"  ha ottenuto "+(playersRanking[i].getPoints()+personalPoints[i]));
        }

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

    public  void printCommonGoalCards(ClientView clientView){
        int numOfCommonGoalCards = clientView.getCommonGoalView()[0].length;
        for(int i=0; i<numOfCommonGoalCards; i++){
            int index =  clientView.getCommonGoalView()[0][i];
            int rows = getCommonGoalCard(index).length;
            for(int j=0; j<rows; j++){
                if(j==0 || j==rows-1){
                    Colors.printSize2(getCommonGoalCard(index)[j]+getCommonGoalCardDescription(index)[j], 432+60);
                    //Colors.printSize2(getCommonGoalCard(index)[j]+getCommonGoalCardDescription(index)[j], 432+60);
                    System.out.println("");
                }
                else {
                    Colors.printSize2(getCommonGoalCard(index)[j]+getCommonGoalCardDescription(index)[j], 124);
                    //Colors.printSize2(getCommonGoalCard(index)[j]+getCommonGoalCardDescription(index)[j], 124);
                    System.out.println("");

                }
            }
            System.out.println("Tokens left: ");
            printTokensLeft(i, clientView);
            System.out.println("");
        }
    }

    public void printTokensLeft(int indexCGC, ClientView clientView){
        ArrayList<String[]> tokensToPrint = new ArrayList<>();
        switch (clientView.getPlayerPointsViews().length) {
            case 2: tokensToPrint.addAll(Arrays.asList(token4, token8));
                break;
            case 3: tokensToPrint.addAll(Arrays.asList(token4, token6, token8));
                break;
            case 4: tokensToPrint.addAll(Arrays.asList(token2, token4, token6, token8));
                break;
        }

        for(int j=0; j<token2.length; j++){
            for (int i=0; i<tokensToPrint.size(); i++){
                int points = Integer.parseInt(tokensToPrint.get(i)[1].replaceAll("[^0-9]", ""));
                if (points<= clientView.getCommonGoalView()[1][indexCGC]) Colors.colorize(Colors.WHITE_CODE, tokensToPrint.get(i)[j]);
                else Colors.colorize(Colors.BLACK_CODE, tokensToPrint.get(i)[j]);
                //System.out.println("");
            }
            System.out.println("");
        }

    }

}
