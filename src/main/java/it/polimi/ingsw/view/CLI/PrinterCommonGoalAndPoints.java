package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.view.ClientView;

import java.util.Arrays;

/**
 * This class contains a method used to print each common goal card with its description and topmost token,
 * as well as the methods to visualize the points of each player and the final ranking.
 * The characters defined at the beginning of the class are used to draw the cards' borders and tiles disposition.
 * Every card is constructed line by line, together with its description.
 * The attribute terminalWidth is initialized with a default value of 70 and is used to center the output in the terminal.
 */
public class PrinterCommonGoalAndPoints {
    private String borderColor = Colors.WHITE_CODE;
    private int lineLength = 16;
    private int terminalWidth = 70;
    private String margin = " ".repeat(50);

    String tlc = borderColor+"┌"+"\u001B[0m"; //topLeftCorner
    String trc = borderColor+"┐"+"\u001B[0m"; //topRightCorner
    String blc = borderColor+"└"+"\u001B[0m"; //bottomLeftCorner
    String brc = borderColor+"┘"+"\u001B[0m"; //bottomRightCorner
    String hd = borderColor+"─"+"\u001B[0m"; //horizontalDash
    String vd = borderColor+"│"+"\u001B[0m"; //verticalDash
    String cr = borderColor+"┼"+"\u001B[0m"; //cross
    String t = borderColor+"┬"+"\u001B[0m"; //t
    String ut = borderColor+"┴"+"\u001B[0m"; //upside down t
    String ri = borderColor+"┤"+"\u001B[0m"; //right intersection
    String li = borderColor+"├"+"\u001B[0m"; //left intersection
    String oc = Colors.WHITE_CODE +"▄▄"+"\u001B[0m"; //yellow
    String or = Colors.ORANGE_CODE+"▄▄"+"\u001B[0m"; //orange
    String g = Colors.GREEN_CODE+"▄▄"+"\u001B[0m"; //green
    String l = Colors.LIGHT_BLUE_CODE+"▄▄"+"\u001B[0m"; //lightblue
    String b = Colors.BLUE_CODE+"▄▄"+"\u001B[0m"; //blue
    String p = Colors.PINK_CODE+"▄▄"+"\u001B[0m"; //pink

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
            "      Two groups each containing 4 tiles of",
            "      the same type in a 2x2 square. The ",
            "      tiles of one square can be different ",
            "      from those of the other square.",
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
            "      Two columns each formed by 6",
            "      different types of tiles.",
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
            "      Four groups each containing at least",
            "      4 tiles of the same type (not necessarily",
            "      in the depicted shape).",
            "      The tiles of one group can be different",
            "      from those of another group.",
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
            "      Six groups each containing at least",
            "      2 tiles of the same type (not necessarily",
            "      in the depicted shape).",
            "      The tiles of one group can be different",
            "      from those of another group.",
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
            "      Three columns each formed by 6 tiles",
            "      of maximum three different types. One",
            "      column can show the same or a different",
            "      combination of another column.",
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
            "      Two lines each formed by 5 different",
            "      types of tiles. One line can show the",
            "      same or a different combination of the",
            "      other line.",
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
            "      Four lines each formed by 5 tiles of",
            "      maximum three different types. One",
            "      line can show the same or a different",
            "      combination of another line.",
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
            "      Four tiles of the same type in the four",
            "      corners of the bookshelf.",
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
            "      Eight tiles of the same type. There’s no",
            "      restriction about the position of these",
            "      tiles.",
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
            "      Five tiles of the same type forming an X.",
            "",
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
            "      Five tiles of the same type forming a diagonal.",
            "",
            "",
            "",
            ""
    };

    private String[] commonGoalCard12 = {
            tlc + hd.repeat(lineLength) + trc,
            vd + "                " + vd,
            vd + " ▄▄             " + vd,
            vd + " ▄▄ ▄▄          " + vd,
            vd + " ▄▄ ▄▄ ▄▄       " + vd,
            vd + " ▄▄ ▄▄ ▄▄ ▄▄    " + vd,
            vd + " ▄▄ ▄▄ ▄▄ ▄▄ ▄▄ " + vd,
            blc + hd.repeat(lineLength) + brc
    };

    private String[] commonGoalCard12Description = {
            "",
            "      Five columns of increasing or decreasing",
            "      height. Starting from the first column on",
            "      the left or on the right, each next column",
            "      must be made of exactly one more tile.",
            "      Tiles can be of any type.",
            "",
            ""
    };

    /**
     * This method builds the token according to the points passed as parameter
     * @param x: token points
     */
    public String[] buildToken (int x){
        String [] token ={"┌"+"───"+"┐",
                          "│ "+ x +" │",
                          "└"+"───"+"┘"};
        return token;
    }

    private String[][] cgcFigures = {commonGoalCard1, commonGoalCard2, commonGoalCard3, commonGoalCard4, commonGoalCard5,
                             commonGoalCard6, commonGoalCard7, commonGoalCard8, commonGoalCard9, commonGoalCard10,
                             commonGoalCard11, commonGoalCard12};

    private String[][] cgcDescriptions = {commonGoalCard1Description, commonGoalCard2Description, commonGoalCard3Description, commonGoalCard4Description, commonGoalCard5Description, commonGoalCard6Description,
            commonGoalCard7Description, commonGoalCard8Description, commonGoalCard9Description, commonGoalCard10Description,
            commonGoalCard11Description, commonGoalCard12Description};

    /**
     * Retrieves the common goal card figures for a given index.
     * @param i The index of the common goal card.
     * @return The common goal card figures at the specified index.
     */
    public String[] getCommonGoalCard (int i){
        return cgcFigures[i];
    }
    /**
     * Retrieves the common goal card descriptions for a given index.
     * @param i The index of the common goal card.
     * @return The common goal card descriptions at the specified index.
     */
    public String[] getCommonGoalCardDescription(int i) {
        return cgcDescriptions[i];
    }

    /**
     * Prints the name and token for a given player.
     * @param num       The number of tokens won by the player.
     * @param nickname  The nickname of the player.
     */
    public void printNameAndToken(int num, String nickname){
        Colors.printFreeSpaces(5);
        String str = nickname+" has won the following common goal card token!";
        System.out.println(" ".repeat((terminalWidth-str.length()/2))+str);
        printToken(num);
    }

    /**
     * This method prints the token according to the points passed as parameter
     * @param num : token points
     */
    public void printToken(int num){
        String [] token = {"┌"+"───"+"┐","│ "+ num +" │","└"+"───"+"┘"};
        for(String s:token){
            System.out.println(" ".repeat((terminalWidth-s.length()/2))+s);
        }
        System.out.println("");
    }

    /**
     * Prints the game ranking whenever a player asks for it.
     * While everyone can see each other's common and adjacent points, the only personal points visualized are
     * the ones scored by the player who asked for the ranking.
     * The turn player is highlighted in yellow.
     * @param clientView
     */
    public void printPoints(ClientView clientView){
        PlayerPointsView[] playerPoints=clientView.getPlayerPointsViews();
        int nickLenght = 10;
        for(int i=playerPoints.length-1;i>=0;i--){
            nickLenght = Math.max(playerPoints[i].getNickname().length(), nickLenght);
        }
        String shift = " ".repeat(terminalWidth-(nickLenght+42)/2);
        System.out.println(shift+tlc+hd.repeat(nickLenght+2)+t+hd.repeat(8)+t+hd.repeat(8)+t+hd.repeat(10)+t+hd.repeat(10)+trc);
        System.out.println(shift+vd+" Nickname"+" ".repeat(nickLenght-7)+vd+" Points "+vd+" Common "+vd+" Adjacent "+vd+" Personal "+vd);
        System.out.println(shift+li+hd.repeat(nickLenght+2)+cr+hd.repeat(8)+cr+hd.repeat(8)+cr+hd.repeat(10)+cr+hd.repeat(10)+ri);
        for (int i=playerPoints.length-1;i>=0;i--) {
            System.out.printf(shift+vd+" %s"+" ".repeat(nickLenght-playerPoints[i].getNickname().length()+1)+vd+" %6d "+vd+" %6d "+vd+" %8d "+vd+" %8s "+vd+"%n", playerPoints[i].getNickname().equals(clientView.getTurnPlayer())? Colors.paint(Colors.YELLOW_CODE, playerPoints[i].getNickname()): playerPoints[i].getNickname(), playerPoints[i].getPoints(), Arrays.stream(playerPoints[i].getPointsToken()).sum(), playerPoints[i].getAdjacentPoints(), playerPoints[i].getNickname().equals(clientView.getNickname())? Integer.toString(clientView.getPersonalPoints()) : "?");
        }
        System.out.println(shift+blc+hd.repeat(nickLenght+2)+ut+hd.repeat(8)+ut+hd.repeat(8)+ut+hd.repeat(10)+ut+hd.repeat(10)+brc);

    }

    /**
     * Prints the final ranking at game end. A table is shown with the total amounts of points for each player,
     * categorized in common, adjacent and personal points. Podium players are higlighted in gold, silver and bronze.
     * @param clientView
     * @param personalPoints
     * @param nickWhoFilledBookshelf
     */
    public  void printEndGame(ClientView clientView,int[] personalPoints, String nickWhoFilledBookshelf){
        PrinterLogo.printWinnerLogo(terminalWidth-(43)/2); //old parameter: terminalWidth-(43)/2
        PlayerPointsView[] playersRanking= clientView.getPlayerPointsViews();
        int nickLenght = 10;
        for(int i=playersRanking.length-1;i>=0;i--){
            nickLenght = Math.max(playersRanking[i].getNickname().length(), nickLenght);
        }
        String tab = " ".repeat(terminalWidth-(nickLenght+42)/2);
        System.out.println(tab+tlc+hd.repeat(nickLenght+2)+t+hd.repeat(8)+t+hd.repeat(8)+t+hd.repeat(10)+t+hd.repeat(10)+trc);
        System.out.println(tab+vd+" Nickname"+" ".repeat(nickLenght-7)+vd+" Points "+vd+" Common "+vd+" Adjacent "+vd+" Personal "+vd);
        System.out.println(tab+li+hd.repeat(nickLenght+2)+cr+hd.repeat(8)+cr+hd.repeat(8)+cr+hd.repeat(10)+cr+hd.repeat(10)+ri);
        for (int i=playersRanking.length-1;i>=0;i--) {
            String symbol = new String();
            String color = new String();
            System.out.printf(tab+vd+" %s"+" ".repeat(nickLenght-playersRanking[i].getNickname().length()+1)+vd+" %6d "+vd+" %6d "+vd+" %8d "+vd+" %8s "+vd+"  "+symbol+"%n", Colors.paint(color, playersRanking[i].getNickname()), playersRanking[i].getPoints()+personalPoints[i]+(playersRanking[i].getNickname().equals(nickWhoFilledBookshelf)?1:0), Arrays.stream(playersRanking[i].getPointsToken()).sum(), playersRanking[i].getAdjacentPoints(), clientView.getPersonalPoints());
        }
        System.out.println(tab+blc+hd.repeat(nickLenght+2)+ut+hd.repeat(8)+ut+hd.repeat(8)+ut+hd.repeat(10)+ut+hd.repeat(10)+brc);

    }

    /**
     * print the common goal cards int the middle of the screen.
     * @param clientView
     */
    public  void printCommonGoalCards(ClientView clientView){
        int pointsLeft;
        int numOfCommonGoalCards = clientView.getCommonGoalView()[0].length;
        for(int i=0; i<numOfCommonGoalCards; i++){
            int index =  clientView.getCommonGoalView()[0][i];
            int rows = getCommonGoalCard(index).length;
            for(int j=1; j<rows-1; j++){
                if(j==0 || j==rows-1){
                    Colors.colorizeSize(Colors.WHITE_CODE,margin+(getCommonGoalCard(index)[j])+getCommonGoalCardDescription(index)[j], 432+60);
                    System.out.println("");
                }
                else {
                    Colors.colorizeSize(Colors.WHITE_CODE,margin+(getCommonGoalCard(index)[j])+getCommonGoalCardDescription(index)[j], 124);
                    System.out.println("");

                }
            }
            pointsLeft=clientView.getCommonGoalView()[1][i];
            System.out.println("");
            String str = "Topmost token for this common goal card: ";
            System.out.println(" ".repeat((terminalWidth-str.length()/2))+str);
            printToken(pointsLeft);
            System.out.println("");
        }

    }

}
