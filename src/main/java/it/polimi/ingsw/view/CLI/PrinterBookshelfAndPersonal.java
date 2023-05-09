package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.ItemTileView;

public class PrinterBookshelfAndPersonal {

    //private int sizetile=3;
    //private int spaceBetweenTiles=2;
    //private int lineLength=sizetile+2*spaceBetweenTiles+3;


    public void printMatrixBookshelf(ItemTileView[][] bookshelfView,int sizetile,int spaceBetweenTiles,int sizeLenghtFromBord,boolean personalGoal ) throws Exception {
        System.out.println("BOOKSHELF ");
        int lineLength=sizetile+2*spaceBetweenTiles;
        int personalLine;
        if(!personalGoal){
            personalLine=bookshelfView.length+1;
        }else personalLine=0;
        int charForSide = 3;
        int size = (lineLength + 1) * bookshelfView[0].length + 1;


        Colors.printFreeSpaces(sizeLenghtFromBord);
        for (int i = 0; i < bookshelfView.length-1; i++) {
            Colors.printFreeSpaces((lineLength+2)/2);
            Colors.colorize(Colors.RED_CODE, String.valueOf(i));
            Colors.printFreeSpaces((lineLength+2-(lineLength+2)/2)-2);
        }
        System.out.println();
        Colors.printFreeSpaces(sizeLenghtFromBord);
        for (int i = 0; i < bookshelfView.length-1; i++) {
            Colors.printFreeSpaces((lineLength+2)/2);
            System.out.print("↓");
            Colors.printFreeSpaces((lineLength+2-(lineLength+2)/2)-2);
        }


        String[] lineRepresentations = {
                "┳" + "━".repeat(lineLength),
                "┳" + "━".repeat(lineLength),
                "┳" + "━".repeat(lineLength) + "┳",
                "┣" + "━".repeat(lineLength),
                "╋" + "━".repeat(lineLength),
                "╋" + "━".repeat(lineLength) + "┫",
                " ".repeat(lineLength - 2 * spaceBetweenTiles),
        };


        //Colors.printCharacter("▄", size, Colors.BEIGE_CODE);
        System.out.println();
        int index = 0;
        //for(int i=0;i<bookshelfView.length+1;i++){
        for (int i = 0; i < bookshelfView.length; i++) {
            personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,bookshelfView,spaceBetweenTiles,false,sizeLenghtFromBord,0);

            //Colors.printFreeSpaces(sizeLenghtFromBord);
            for (int j = 0; j < bookshelfView[0].length; j++) {
                if (j == 0) {
                    index = (i == 0 && j == 0) ? 0 : 3;
                }
                if (j > 0 && j < bookshelfView[0].length - 1) {
                    index = (i == 0) ? 1 : 4;

                }
                if (j == bookshelfView[0].length - 1) {
                    index = (i == 0) ? 2 : 5;
                }

                System.out.print(Colors.BEIGE_CODE + lineRepresentations[index] + "\u001B[0m");
            }

            if (i != bookshelfView.length) {
                System.out.println();
                personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,bookshelfView,spaceBetweenTiles,false,sizeLenghtFromBord,0);
                for (int j = 0; j < bookshelfView[0].length; j++) {
                    Colors.colorize(Colors.BEIGE_CODE, "┃");
                    Colors.printFreeSpaces(spaceBetweenTiles);
                    if (bookshelfView[i][j].getTileID() != -1) {
                        System.out.print(Colors.printTiles(bookshelfView[i][j].getTypeView(), sizetile));

                    } else System.out.print(lineRepresentations[6]);
                    Colors.printFreeSpaces(spaceBetweenTiles);
                }
                Colors.colorize(Colors.BEIGE_CODE, "┃");

            }
            System.out.println();

        }

        Colors.printFreeSpaces(sizeLenghtFromBord - charForSide);
        Colors.printCharacter("▓", size + 2 * charForSide, Colors.BEIGE_CODE);

        System.out.println();
    }
    private boolean frame=true;
    public int printPersonalGoalWithOrWithoutFrame(int personalLine, ItemTileView[][] bookshelfView, int spaceBetweenTiles, boolean mediumSpaces, int fromBoardBookshelf, int sizeLenghtFromBord) throws Exception {
        GameRules gameRules = new GameRules();
        PersonalGoalCard q = gameRules.getPersonalGoalCard(0);
        int lineLength=1+spaceBetweenTiles*2;
        String[] lineRepresentations = {
                "┏" + "━".repeat(lineLength),
                "┳" + "━".repeat(lineLength),
                "┳" + "━".repeat(lineLength) + "┓",
                "┣" + "━".repeat(lineLength),
                "╋" + "━".repeat(lineLength),
                "╋" + "━".repeat(lineLength) + "┫",
                "┗" + "━".repeat(lineLength),
                "┻" + "━".repeat(lineLength),
                "┻" + "━".repeat(lineLength) + "┛",
                " ".repeat(lineLength - 2 * spaceBetweenTiles),
        };
        int index = 0;
        int limit;
        if(mediumSpaces){
            limit=(bookshelfView.length+1)*2;
        }else limit=bookshelfView.length;
        if(personalLine<limit){
        //for (int i = 0; i < bookshelfView.length + 1; i++) {
            if(mediumSpaces && frame){
                //System.out.println();
                Colors.printFreeSpaces(sizeLenghtFromBord);

                for (int j = 0; j < bookshelfView[0].length; j++) {
                    if (j == 0) {
                        index = (personalLine == 0 && j == 0) ? 0 : (personalLine < bookshelfView.length) ? 3 : 6;
                    }
                    if (j > 0 && j < bookshelfView[0].length) {
                        index = (personalLine == 0) ? 1 : (personalLine < bookshelfView.length) ? 4 : 7;

                    }
                    if (j == bookshelfView[0].length - 1) {
                        index = (personalLine == 0) ? 2 : (personalLine < bookshelfView.length) ? 5 : 8;
                    }
                    System.out.print(Colors.BEIGE_CODE + lineRepresentations[index] + "\u001B[0m");

                }
                fromBoardBookshelf=fromBoardBookshelf-sizeLenghtFromBord-lineRepresentations[index].length();
                frame=false;
                //System.out.println();
            }else{
                Colors.printFreeSpaces(sizeLenghtFromBord);
                fromBoardBookshelf=fromBoardBookshelf-sizeLenghtFromBord;
                if(personalLine!= bookshelfView.length){
                    for(int j=0;j<bookshelfView[0].length;j++){
                        boolean found = false;
                        Colors.colorize(Colors.BEIGE_CODE,"┃");
                        fromBoardBookshelf--;
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        fromBoardBookshelf=fromBoardBookshelf-spaceBetweenTiles;
                        for (PersonalGoalBox p : q.getCells()) {
                            if (p.getX() == personalLine && p.getY() == j) {
                                Colors.printCharacter("■",1,Colors.getColor(p.getTypePersonal()));
                                fromBoardBookshelf--;
                                Colors.printFreeSpaces(spaceBetweenTiles);
                                fromBoardBookshelf=fromBoardBookshelf-spaceBetweenTiles;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Colors.printCharacter("■",1,Colors.BLACK_CODE);
                            fromBoardBookshelf--;
                            // System.out.print(lineRepresentations[9]);
                            Colors.printFreeSpaces(spaceBetweenTiles);
                            fromBoardBookshelf=fromBoardBookshelf-spaceBetweenTiles;
                        }
                        //printFreeSpaces(1);
                        //Colors.colorize(Colors.WHITE_CODE,"║");
                        //printFreeSpaces(spaceBetweenTiles);
                    }
                    Colors.colorize(Colors.BEIGE_CODE,"┃");
                    fromBoardBookshelf--;
                    Colors.printFreeSpaces(spaceBetweenTiles);
                    fromBoardBookshelf=fromBoardBookshelf-spaceBetweenTiles;

                }
                frame=true;
            }
            Colors.printFreeSpaces(fromBoardBookshelf);
            personalLine++;
        }
        else Colors.printFreeSpaces(fromBoardBookshelf+sizeLenghtFromBord);
        return personalLine;
    }

    public void printPersonal(ItemTileView[][] bookshelfView,int spaceBetweenTiles,int sizeLengthFromBord) throws Exception {
        int personalLine=0;
        for(int i=0;i<bookshelfView.length+1;i++){
            //se si vuole stampare la personal goal senza frame camcellare le prime due righe é mettere falso al flag
            printPersonalGoalWithOrWithoutFrame(personalLine,bookshelfView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            System.out.println();
            personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,bookshelfView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            System.out.println();
        }
    }


            /*

    public int printPersonal(int personalLine, ItemTileView[][] bookshelfView,int sizeLenghtFromBord ) throws Exception {
        GameRules gameRules = new GameRules();
        int distanceFromBord=3;

        PersonalGoalCard q = gameRules.getPersonalGoalCard(0);
        if(personalLine<bookshelfView.length){
            sizeLenghtFromBord=sizeLenghtFromBord-distanceFromBord;
            Colors.printFreeSpaces(distanceFromBord);
            for(int j=0;j<bookshelfView[0].length;j++){
                boolean found = false;
                Colors.colorize(Colors.BEIGE_CODE,"│");
                sizeLenghtFromBord--;
                for (PersonalGoalBox p : q.getCells()) {
                    if (p.getX() == personalLine && p.getY() == j) {
                        Colors.printCharacter("■",1,Colors.getColor(p.getTypePersonal()));
                        sizeLenghtFromBord--;
                        //printFreeSpaces(spaceBetweenTiles);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Colors.printCharacter("■",1,Colors.BLACK_CODE);
                    sizeLenghtFromBord--;
                    // System.out.print(lineRepresentations[9]);
                    // printFreeSpaces(spaceBetweenTiles);
                }
            }
            Colors.colorize(Colors.BEIGE_CODE,"│");
            sizeLenghtFromBord--;
            //System.out.println();
            Colors.printFreeSpaces(sizeLenghtFromBord);
            personalLine++;
        }
        else Colors.printFreeSpaces(sizeLenghtFromBord+distanceFromBord);
                return personalLine;
            //printFreeSpaces(1);
            //Colors.colorize(Colors.WHITE_CODE,"║");
            //printFreeSpaces(spaceBetweenTiles);
    }


    public void printPersonalGoal(ItemTileView[][] bookshelfView) throws Exception {
        System.out.println("PERSONAL GOAL ");
        GameRules gameRules = new GameRules();
        PersonalGoalCard q = gameRules.getPersonalGoalCard(0);
        //┖┏┓┛
        /*
        Colors.printCharacter("┍",1,Colors.BEIGE_CODE);
        Colors.printCharacter("ˍ",bookshelfView[0].length*2,Colors.BEIGE_CODE);
        Colors.printCharacter("┓",1,Colors.BEIGE_CODE);

        System.out.println();
        for(int i=0;i<bookshelfView.length;i++){
            Colors.printFreeSpaces(3);
            for(int j=0;j<bookshelfView[0].length;j++){
                boolean found = false;
                Colors.colorize(Colors.BEIGE_CODE,"│");
                for (PersonalGoalBox p : q.getCells()) {
                    if (p.getX() == i && p.getY() == j) {
                        Colors.printCharacter("■",1,Colors.getColor(p.getTypePersonal()));
                        //printFreeSpaces(spaceBetweenTiles);
                        found = true;
                        break;
                    }

                }
                if (!found) {
                    Colors.printCharacter("■",1,Colors.BLACK_CODE);
                    // System.out.print(lineRepresentations[9]);
                    // printFreeSpaces(spaceBetweenTiles);
                }

                //printFreeSpaces(1);
                //Colors.colorize(Colors.WHITE_CODE,"║");
                //printFreeSpaces(spaceBetweenTiles);
            }
            Colors.colorize(Colors.BEIGE_CODE,"│");
            System.out.println();
        }
        int lineLengthp=3;

        int sizeLenghtFromBord=40;
        //printFreeSpaces(sizeLenghtFromBord);
        //Colors.printCharacter("┉",(bookshelfView[0].length*3),Colors.BEIGE_CODE);

        //Colors.printCharacter("▄", size, Colors.BEIGE_CODE);
        System.out.println();
        int index = 0;
        //for(int i=0;i<bookshelfView.length+1;i++){

        for(int i=0;i<bookshelfView.length;i++){
            Colors.printFreeSpaces(sizeLenghtFromBord);
            for(int j=0;j<bookshelfView[0].length;j++){
                Colors.colorize(Colors.BEIGE_CODE,"┊");
                //printFreeSpaces(spaceBetweenTiles);
                boolean found = false;
                for (PersonalGoalBox p : q.getCells()) {
                    if (p.getX() == i && p.getY() == j) {
                        Colors.printCharacter("■",1,Colors.getColor(p.getTypePersonal()));
                        //printFreeSpaces(spaceBetweenTiles);
                        found = true;
                        break;
                    }

                }
                if (!found) {
                    Colors.printFreeSpaces(1);
                    // System.out.print(lineRepresentations[9]);
                    // printFreeSpaces(spaceBetweenTiles);
                }

                //Colors.colorize(Colors.WHITE_CODE,"║");
                //printFreeSpaces(spaceBetweenTiles);
            }
            Colors.colorize(Colors.BEIGE_CODE,"┊");
            System.out.println();
            // printFreeSpaces(sizeLenghtFromBord);
            //Colors.printCharacter("┉",bookshelfView[0].length,Colors.BEIGE_CODE);
            //System.out.println();
        }


    }

             */
}
