package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Type;

import java.util.HashMap;
import java.util.Map;

public class Colors {

    public final static String ERROR_MESSAGE = "\033[31m";
    public final static String YELLOW_CODE = "\033[33m";
    public final static String GAME_INSTRUCTION ="\033[97m";;
    public final static String WHITE_CODE ="\033[97m";;
    public final static String BLACK_CODE = "\033[30m";

    public final static String GREEN_CODE = "\033[32m";
    ;
    public final static String LIGHT_BLUE_CODE = "\033[36m";
    public final static String OCHRE_YELLOW_CODE = "\033[93m";
    public final static String ORANGE_CODE = "\033[33m";
    public final static String BLUE_CODE = "\033[34m";
    public final static String PINK_CODE = "\033[95m";
    public final static String BEIGE_CODE = "\033[38;2;139;69;19m";
    public final static String GOLD_CODE = "\u001B[38;2;255;215;0m";
    public final static String SILVER_CODE = "\u001B[38;2;192;192;192m";
    public final static String BRONZE_CODE = "\u001B[38;2;205;127;50m";

    public final static String RESET_CODE = "\u001B[0m";


    private static final Map<Object, String> TYPE_COLORS = new HashMap<>();

    static {
        TYPE_COLORS.put(Type.CAT, GREEN_CODE);
        TYPE_COLORS.put(Type.BOOK, OCHRE_YELLOW_CODE);
        TYPE_COLORS.put(Type.GAME, ORANGE_CODE);
        TYPE_COLORS.put(Type.FRAME, BLUE_CODE);
        TYPE_COLORS.put(Type.TROPHY, LIGHT_BLUE_CODE);
        TYPE_COLORS.put(Type.PLANT, PINK_CODE);
        TYPE_COLORS.put("SELECTED", ERROR_MESSAGE);
    }
    /**
     * Prints the type with color formatting.
     *
     * @param type the object representing the type
     * @param size the size of the printed output
     */
   public static void printTypeWithTypeColor(Object type, int size) {
        if (TYPE_COLORS.containsKey(type)) {
            System.out.print(TYPE_COLORS.get(type));
            System.out.print(String.format("%-"+size+"s", type));
            System.out.print("\033[0m");

        }
    }

    /**
     * Prints the specified text with the given color.
     *
     * @param color the color code or ANSI escape sequence
     * @param text the text to be printed
     */
    public static void colorize(String color, String text) {
        System.out.print(color + text + "\033[0m");
    }


    /**
     * Prints the specified text with the given color and adjusts the output size.
     *
     * @param color the color code or ANSI escape sequence
     * @param text the text to be printed
     * @param size the desired output size
     */
    public static void colorizeSize(String color, String text, int size) {
        int lengthDifference = size - text.length();
        String space = "";
        for (int i = 0; i < lengthDifference; i++) {
            space += " ";
        }
        System.out.printf("%s%s%s\033[0m", color, text, space);
    }


    /**
     * Generates a string representation of tiles using the specified character.
     *
     * @param size the number of tiles to generate
     * @return the string representation of tiles
     */
    public static String printTiles(Object type, int size) {
        String square = TYPE_COLORS.get(type) + "█";
        String coloredSquares = square.repeat(size);
        return coloredSquares;
    }
    /**
     * Prints a specified number of free spaces.
     *
     * @param count the number of spaces to print
     */
    public static void printFreeSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }
    /**
     * Retrieves the color associated with the specified type.
     *
     * @param type the object representing the type
     * @return the color associated with the type, or null if not found
     */

    public static String getColor(Object type) {
        if (TYPE_COLORS.containsKey(type)) {
            return TYPE_COLORS.get(type);

        }
        return null;
    }
    /**
     * Prints a specified character with the given size and color code.
     *
     * @param character the character to be printed
     * @param size the number of times the character should be repeated
     * @param colorCode the color code or ANSI escape sequence
     */
    public static void printCharacter(String character, int size, String colorCode) {
        String coloredCharacter = colorCode + character.repeat(size) + "\033[0m";
        System.out.print(coloredCharacter);
    }

    public static String paint(String color, String text) {
        return (color + text + "\033[0m");
    }

    public static void printSize2(String text, int size) {
        int lengthDifference = size - text.length();
        String space = "";
        for (int i = 0; i < lengthDifference; i++) {
            space += " ";
        }
        System.out.printf("%s%s", text, space);
    }

}


