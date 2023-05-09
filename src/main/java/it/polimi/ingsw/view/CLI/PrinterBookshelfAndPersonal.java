package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.ItemTileView;

public class PrinterBookshelfAndPersonal {

    private int sizetile=5;
    private int spaceBetweenTiles=3;
    private int lineLength=sizetile+2*spaceBetweenTiles;


    public void printMatrixBookshelf(ItemTileView[][] bookshelfView) {
        System.out.println("BOOKSHELF ");

        int charForSide = 3;
        int size = (lineLength + 1) * bookshelfView[0].length + 1;

        int sizeLenghtFromBord = 40;
        Colors.printFreeSpaces(sizeLenghtFromBord);
        for (int i = 0; i < bookshelfView.length - 1; i++) {
            Colors.printFreeSpaces(((spaceBetweenTiles + sizetile / 2) + 1));
            System.out.print("↓");
            if (((spaceBetweenTiles + sizetile / 2) + 1) % 2 == 0) {
                Colors.printFreeSpaces(((spaceBetweenTiles + sizetile / 2) - 1));
            } else Colors.printFreeSpaces(lineLength / 2 + 1);
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
            Colors.printFreeSpaces(sizeLenghtFromBord);
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

            /*
            for(int j=0;j<bookshelfView[0].length;j++){
                if(j==0){
                    index = (i == 0 && j == 0) ? 0 : (i < bookshelfView.length ) ? 3 : 6;
                }
                if(j>0 && j< bookshelfView[0].length){
                    index = (i == 0) ? 1 : (i < bookshelfView.length) ? 4 : 6;

                }
                if(j==bookshelfView[0].length-1){
                    index = (i == 0) ? 2 : (i < bookshelfView.length) ? 5 : 8;
                }
                System.out.print(Colors.BEIGE_CODE+ lineRepresentations[index] + "\u001B[0m");
            }

             */


            if (i != bookshelfView.length) {
                System.out.println();
                Colors.printFreeSpaces(sizeLenghtFromBord);
                for (int j = 0; j < bookshelfView[0].length; j++) {
                    Colors.colorize(Colors.BEIGE_CODE, "┃");
                    Colors.printFreeSpaces(spaceBetweenTiles);
                    if (bookshelfView[i][j].getTileID() != -1) {
                        System.out.print(Colors.printTiles(bookshelfView[i][j].getTypeView(), sizetile));

                    } else System.out.print(lineRepresentations[6]);
                    Colors.printFreeSpaces(spaceBetweenTiles);
                    //Colors.colorize(Colors.WHITE_CODE,"║");
                    //printFreeSpaces(spaceBetweenTiles);
                }
                Colors.colorize(Colors.BEIGE_CODE, "┃");

            }
            System.out.println();

        }

        Colors.printFreeSpaces(sizeLenghtFromBord - charForSide);
        Colors.printCharacter("▓", size + 2 * charForSide, Colors.BEIGE_CODE);

        System.out.println();
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

         */
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
}
