package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Type;

import java.util.HashMap;
import java.util.Map;

public class Colors {

    public final static String RED_CODE = "\u001B[38;2;255;0;0m";
    //public final static String GREEN_CODE = "\u001B[38;2;0;255;0m";
    //public final static String BLUE_CODE = "\u001B[38;2;0;0;255m";
    public final static String YELLOW_CODE = "\u001B[38;2;255;255;0m";
    public final static String BLACK_CODE = "\u001B[38;2;0;0;0m";
    public final static String WHITE_CODE = "\u001B[38;2;255;255;255m";
    public final static String GREEN_CODE = "\u001B[38;2;0;255;0m";
    public final static String LIGHT_BLUE_CODE = "\u001B[38;2;197;225;247m";
    public final static String OCHRE_YELLOW_CODE = "\u001B[38;2;204;119;34m";
    public final static String ORANGE_CODE = "\u001B[38;2;255;165;0m";
    public final static String BLUE_CODE = "\u001B[38;2;133;163;220m";
    public final static String PINK_CODE = "\u001B[38;2;255;192;203m";

    private static final Map<Type, String> TYPE_COLORS = new HashMap<>();

    static {
        TYPE_COLORS.put(Type.CAT, GREEN_CODE);
        TYPE_COLORS.put(Type.BOOK, OCHRE_YELLOW_CODE);
        TYPE_COLORS.put(Type.GAME, ORANGE_CODE);
        TYPE_COLORS.put(Type.FRAME, BLUE_CODE);
        TYPE_COLORS.put(Type.TROPHY, LIGHT_BLUE_CODE);
        TYPE_COLORS.put(Type.PLANT, PINK_CODE);
    }


    public void printTypeWithTypeColor(Type type) {
        if (TYPE_COLORS.containsKey(type)) {
            System.out.print(TYPE_COLORS.get(type));
            System.out.print(String.format("%-7s", type.name()));
            System.out.print("\u001B[0m");
        }
    }
}

