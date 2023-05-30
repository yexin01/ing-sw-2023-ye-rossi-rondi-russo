package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Type;

import java.util.HashMap;
import java.util.Map;

public class Colors {

    public final static String RED_CODE = "\u001B[38;2;255;0;0m";
    //public final static String GREEN_CODE = "\u001B[38;2;0;255;0m";
    //public final static String BLUE_CODE = "\u001B[38;2;0;0;255m";
    public final static String YELLOW_CODE = "\u001B[38;2;255;255;0m";
    public final static String GAME_INSTRUCTION ="\u001B[38;2;255;255;255m";;
    public final static String BLACK_CODE = "\u001B[38;2;0;0;0m";
    public final static String WHITE_CODE = "\u001B[38;2;255;255;255m";
    public final static String GREEN_CODE = "\u001B[38;2;0;128;0m";
    ;
    public final static String LIGHT_BLUE_CODE = "\u001B[38;2;197;225;247m";
    public final static String OCHRE_YELLOW_CODE = "\u001B[38;2;204;119;34m";
    public final static String ORANGE_CODE = "\u001B[38;2;255;165;0m";
    public final static String BLUE_CODE = "\u001B[38;2;133;163;220m";
    public final static String PINK_CODE = "\u001B[38;2;255;192;203m";
    public final static String BEIGE_CODE = "\u001B[38;2;210;180;140m";
    public final static String RESET_CODE = "\u001B[0m";
    public final static String ERROR_MESSAGE = "\u001B[38;2;255;20;147m";

    private static final Map<Object, String> TYPE_COLORS = new HashMap<>();

    static {
        TYPE_COLORS.put(Type.CAT, GREEN_CODE);
        TYPE_COLORS.put(Type.BOOK, OCHRE_YELLOW_CODE);
        TYPE_COLORS.put(Type.GAME, ORANGE_CODE);
        TYPE_COLORS.put(Type.FRAME, BLUE_CODE);
        TYPE_COLORS.put(Type.TROPHY, LIGHT_BLUE_CODE);
        TYPE_COLORS.put(Type.PLANT, PINK_CODE);
        TYPE_COLORS.put("SELECTED",RED_CODE);
    }

    public static void printTypeWithTypeColor(Object type, int size) {
        if (TYPE_COLORS.containsKey(type)) {
            System.out.print(TYPE_COLORS.get(type));
            System.out.print(String.format("%-"+size+"s", type));
            //System.out.print(String.format("%-8s", type.name()));
            System.out.print("\u001B[0m");

        }
    }


    public static void colorize(String color, String text) {
        System.out.print(color + text + "\u001B[0m");


        /*
        int lengthDifference = 8 - text.length();
        String space = "";
        for(int i=0; i<lengthDifference; i++) {
            space += " ";
        }
        System.out.printf("%s%s%s\u001B[0m", color, text, space);

         */
    }

    public static String paint(String color, String text) {
        return (color + text + "\u001B[0m");
    }
    public static void colorizeSize(String color, String text, int size) {
        int lengthDifference = size - text.length();
        String space = "";
        for (int i = 0; i < lengthDifference; i++) {
            space += " ";
        }
        System.out.printf("%s%s%s\u001B[0m", color, text, space);
    }

    public static void printSize2(String text, int size) {
        int lengthDifference = size - text.length();
        String space = "";
        for (int i = 0; i < lengthDifference; i++) {
            space += " ";
        }
        System.out.printf("%s%s", text, space);
    }

    public static String printTiles(Object type, int size) {
        String square = TYPE_COLORS.get(type) + "█";
        String coloredSquares = square.repeat(size);
        return coloredSquares;
    }
    public static void printFreeSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }


    }
    public static String getColor(Object type) {
        if (TYPE_COLORS.containsKey(type)) {
            return TYPE_COLORS.get(type);

        }
        return null;
    }
    public static void printCharacter(String character, int size, String colorCode) {
        String coloredCharacter = colorCode + character.repeat(size) + "\u001B[0m";
        System.out.print(coloredCharacter);
    }

}


    /*

    public static void upperBoard(String color,int column, BoardBoxView[][] board) {
        String text;

        if(board[0].length==1){
            text="╔══════╗";
        }else{
            if (board[0][column].isOccupiable() == false) {
                text="       ";
            }else{
                if((column==0 && board[0][column].isOccupiable()==true) ||(column!=0 && board[0][column-1].isOccupiable()==false)){
                    text="╔══════";

                }else if(column!=0 && column!= board[0].length-1 && board[0][column-1].isOccupiable()==true && board[0][column+1].isOccupiable()==true ){
                    text="╦══════";
                }else{ //if((column==board[0].length && board[0][column].isOccupiable()==true)||(column!=0 && column!=board[0].length && board[0][column-1].isOccupiable()==false)){
                    text="╦══════╗";
                }
            }
        }

        System.out.print(color + text + "\u001B[0m");

    }

    public static void downBoard(String color,int column, BoardBoxView[][] board) {
        String text;
        int row=board.length-1;
        if(board[row].length==1){
            text="╚══════╝";
        }else{
            if (board[row][column].isOccupiable() == false) {
                text="       ";
            }else{
                if((column==0 && board[row][column].isOccupiable()==true) ||(column!=0 && board[row][column-1].isOccupiable()==false)){
                    text="╚══════";

                }else if(column!=0 && column!= board[row].length-1 && board[row][column-1].isOccupiable()==true && board[row][column+1].isOccupiable()==true ){
                    text="╩══════";
                }else{ //if((column==board[0].length && board[0][column].isOccupiable()==true)||(column!=0 && column!=board[0].length && board[0][column-1].isOccupiable()==false)){
                    text="╩══════╝";
                }
            }
        }

        System.out.print(color + text + "\u001B[0m");

    }
    public static void printFreeSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    public static boolean finishLine;

    public static boolean mediumBoard(String color,int row,int column, BoardBoxView[][] board,boolean waitingFirst) {
        String text;
        if (board[row][column].isOccupiable() == false) {
            if (board[row + 1][column].isOccupiable() == false) text = "       ";
            else if (!waitingFirst) {
                //if (board[row - 1][column].isOccupiable() == true) text = "╔══════";
                //else text = "╠══════";
                text = "╔══════";
                waitingFirst=true;
            } else {
                if(column== board[0].length-1){
                    if (board[row][column - 1].isOccupiable() == true) text = "╬══════╗";
                    else text = "╦══════╗";
                    finishLine=true;
                    /*
                    if (board[row+1][column - 1].isOccupiable() == true) text = "╬══════╗";
                    else text = "╦══════╗";
                    finishLine=true;

                     */
    /*


                }
                else{
                    if(board[row][column - 1].isOccupiable() == true){
                        if(board[row+1][column + 1].isOccupiable() == true )text = "╬══════";
                        else {
                            text = "╬══════╗";
                            finishLine=true;
                        }
                    }
                    else {
                        if(board[row+1][column + 1].isOccupiable() == true)text = "╦══════";
                        else {
                            text = "╦══════╗";
                            finishLine=true;
                        }

                    }

                }

            }
        }else {
            if(!waitingFirst) {
                if(board[row + 1][column].isOccupiable() == true) text="╠══════";
                else text="╚══════";
                waitingFirst=true;
            }
            else {
                if(column== board[0].length-1){
                    if(board[row+1][column].isOccupiable() == true)text = "╬══════╣";
                    else if(board[row+1][column-1].isOccupiable() == true) text = "╬══════╝";
                    else text = "╩══════╝";
                    finishLine=true;
                }else {
                    if(board[row+1][column].isOccupiable() == true){
                        if( board[row+1][column+1].isOccupiable()==true)text="╬══════";
                        else{
                            if( board[row][column+1].isOccupiable()==true)text="╬══════";
                            else{
                                text = "╬══════╣";
                                finishLine=true;
                            }

                        }
                    }
                    else{
                        if(board[row][column+1].isOccupiable() == true){
                            if(board[row+1][column-1].isOccupiable() == true)text = "╬══════";
                            else text = "╩══════";
                        }
                        else{
                            if(board[row+1][column-1].isOccupiable() == true)text = "╬══════╝";
                            else text = "╩══════╝";
                            finishLine=true;
                        }
                    }

                }

            }
        }

        System.out.print(color + text + "\u001B[0m");
        return waitingFirst;

    }

     */
/*  public static void upperOneBoard(String color) {
        String text = "      0      1      2      3      4      5      6      7      8\n" +
                "╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗";
        System.out.println(color + text + "\u001B[0m");
    }

 */


    /*
    public static void mediumBoard(String color,int row,int column, BoardBoxView[][] board) {
        String text;

        row++;
            if (board[row][column].isOccupiable() == false) {

                if((column==0 && board[row-1][column].isOccupiable()==true) ||(column!=0 && board[row-1][column].isOccupiable()==true && board[row-1][column-1].isOccupiable()==false))
                    text="╚══════";
                else if(board[row-1][column].isOccupiable()==true && board[row-1][column-1].isOccupiable()==true && board[row][column-1].isOccupiable()==true)
                    text="╬══════╝";
                else if(board[row-1][column].isOccupiable()==true && board[row-1][column-1].isOccupiable()==true && board[row][column-1].isOccupiable()==false)
                    text="╩══════╝";

                //}else if((column== board[0].length-1 && board[row-1][column-1].isOccupiable()==true) ||(column!=0 && board[row-1][column].isOccupiable()==true && board[row-1][column-1].isOccupiable()==false)){




                else text="       ";
            }else{
                if((column==0 && board[row][column].isOccupiable()==true) ||(column!=0 && board[row][column-1].isOccupiable()==false)){
                   if(board[row-1][column].isOccupiable()==true) text="╠══════";
                   else text="╔══════";

                }else if(column!=0 && column!= board[0].length-1 && board[row][column-1].isOccupiable()==true && board[row][column+1].isOccupiable()==true ){
                    if(column!=0 && board[row-1][column].isOccupiable()==true){
                        text="╬══════";
                    }else if(column!=0 && board[row-1][column-1].isOccupiable()==false) text="╦══════";
                    else text="╬══════";
                }else{ //if((column==board[0].length && board[0][column].isOccupiable()==true)||(column!=0 && column!=board[0].length && board[0][column-1].isOccupiable()==false)){
                    if(board[row-1][column].isOccupiable()==true) text="╬══════╣";
                    else /*if(column!=0 && board[row-1][column-1].isOccupiable()==false) text="╦══════╗";
                }
            }
        System.out.print(color + text + "\u001B[0m");

    }

     */
/*
    public static void mediumBoard(String color) {
        String text="╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣";
        System.out.println(color + text + "\u001B[0m");
    }

    public static void lowerBoard(String color) {
        String text= "╚══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╝";
        System.out.println(color + text + "\u001B[0m");
    }
    public static void colorfulOutput(Type oks) {

        String colorCode = "\u001B[38;2;197;225;247m"; // Codice ANSI per il colore specifico
        String square = "\u2588"; // Carattere "█" rappresentato con Unicode

        // Stampa il carattere con il colore specifico
        System.out.println(colorCode + square);

        // Ripristina il colore predefinito
        System.out.print("\u001B[0m");
    }

*/


/*
    public String getTypeWithColor(Type type) {
        if (TYPE_COLORS.containsKey(type)) {
            return TYPE_COLORS.get(type) + type.name() + "\u001B[0m";
        } else {
            return type.name();
        }
    }

 */
    /*
    public static void printfColored(String text, Color color) {
        System.out.printf("\033[38;2;%d;%d;%dm%s\033[0m", color.getRed(), color.getGreen(), color.getBlue(), text);
    }



    public static void printlnColored(String text, Color color) {
        System.out.println("\033[38;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m" + text + "\033[0m");
    }
/*
    public static void printType(String type) {
        Color color = TYPE_COLORS.getOrDefault(type, BLACK);
        printfColored(type, color);
    }

 */



