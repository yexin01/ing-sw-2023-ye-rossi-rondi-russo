package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.view.ClientView;

public class PrinterBookshelfAndPersonal {

    //private int sizetile=3;
    //private int spaceBetweenTiles=2;
    //private int lineLength=sizetile+2*spaceBetweenTiles+3;

    private int sizeLenghtFromBord;
    private int sizetile;
    private int spaceBetweenTiles;
    private int spaceBetweenPersonal;
    private int lineLength;
    public synchronized void printArrowChoiceColumn(ItemTileView[][] bookshelfView){
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

    }

    public synchronized void printMatrixBookshelf(ClientView clientView,int sizetile,int spaceBetweenTiles,int sizeLenghtFromBord,boolean personalGoal,boolean arrow, int spaceBetweenPersonal){
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        this.spaceBetweenTiles=spaceBetweenTiles;
        this.sizetile=sizetile;
        this.sizeLenghtFromBord=sizeLenghtFromBord;
        this.spaceBetweenPersonal=spaceBetweenPersonal;
        this.lineLength=sizetile+2*spaceBetweenTiles;
        String characterBetweenPersonalAndBookshelf;
        if(personalGoal){
            characterBetweenPersonalAndBookshelf="←";
        }else characterBetweenPersonalAndBookshelf=" ";
        if(arrow){
            printArrowChoiceColumn(bookshelfView);
        }
        int personalLine;
        if(!personalGoal){
            personalLine=bookshelfView.length+1;
        }else personalLine=0;
        int charForSide = 3;
        int size = (lineLength + 1) * bookshelfView[0].length + 1;

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
            //personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenPersonal,false,sizeLenghtFromBord,3);

            Colors.printFreeSpaces(sizeLenghtFromBord);
            Colors.printCharacter(" ",characterBetweenPersonalAndBookshelf.length(),Colors.GAME_INSTRUCTION);
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
                personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenPersonal,false,sizeLenghtFromBord,sizeLenghtFromBord-bookshelfView[0].length*2*spaceBetweenPersonal-bookshelfView[0].length-1-10);
                Colors.printCharacter(characterBetweenPersonalAndBookshelf,characterBetweenPersonalAndBookshelf.length(),Colors.GAME_INSTRUCTION);
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

        Colors.printFreeSpaces(sizeLenghtFromBord - charForSide+characterBetweenPersonalAndBookshelf.length());
        Colors.printCharacter("▓", size + 2 * charForSide, Colors.BEIGE_CODE);

        System.out.println();
    }
    private boolean frame=true;
    public synchronized int printPersonalGoalWithOrWithoutFrame(int personalLine, ClientView clientView, int spaceBetweenPersonal, boolean mediumSpaces, int fromBoardBookshelf,int sizeLenghtFromBord)  {
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        PersonalGoalCard q = clientView.getPlayerPersonalGoal();
        int lineLength=1+spaceBetweenPersonal*2;
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
                " ".repeat(lineLength - 2 * spaceBetweenPersonal),
        };
        int index = 0;
        int limit;
        if(mediumSpaces){
            limit=(bookshelfView.length+1)*2;
        }else limit=bookshelfView.length;
        if(personalLine<limit){
            Colors.printFreeSpaces(sizeLenghtFromBord);
            fromBoardBookshelf=fromBoardBookshelf-sizeLenghtFromBord;
        //for (int i = 0; i < bookshelfView.length + 1; i++) {
            if(mediumSpaces && frame){
                //System.out.println();


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
                fromBoardBookshelf=fromBoardBookshelf-lineRepresentations[index].length();
                frame=false;
                //System.out.println();
            }else{
                if(personalLine!= bookshelfView.length){
                    for(int j=0;j<bookshelfView[0].length;j++){
                        boolean found = false;
                        Colors.colorize(Colors.BEIGE_CODE,"╎");
                        fromBoardBookshelf--;
                        Colors.printFreeSpaces(spaceBetweenPersonal);
                        fromBoardBookshelf=fromBoardBookshelf-spaceBetweenPersonal;
                        for (PersonalGoalBox p : q.getCells()) {
                            if (p.getX() == personalLine && p.getY() == j) {
                                Colors.printCharacter("■",1,Colors.getColor(p.getTypePersonal()));
                                fromBoardBookshelf--;
                                Colors.printFreeSpaces(spaceBetweenPersonal);
                                fromBoardBookshelf=fromBoardBookshelf-spaceBetweenPersonal;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Colors.printCharacter("■",1,Colors.BLACK_CODE);
                            fromBoardBookshelf--;
                            // System.out.print(lineRepresentations[9]);
                            Colors.printFreeSpaces(spaceBetweenPersonal);
                            fromBoardBookshelf=fromBoardBookshelf-spaceBetweenPersonal;
                        }
                        //printFreeSpaces(1);
                        //Colors.colorize(Colors.WHITE_CODE,"║");
                        //printFreeSpaces(spaceBetweenPersonal);
                    }
                    Colors.colorize(Colors.BEIGE_CODE,"╎");
                    fromBoardBookshelf--;
                    Colors.printFreeSpaces(spaceBetweenPersonal);
                    fromBoardBookshelf=fromBoardBookshelf-spaceBetweenPersonal;

                }
                frame=true;
            }
            Colors.printFreeSpaces(fromBoardBookshelf);
            personalLine++;
        }
        else Colors.printFreeSpaces(fromBoardBookshelf);
        return personalLine;
    }

    public synchronized void printPersonal(ClientView clientView ,int spaceBetweenTiles,int sizeLengthFromBord) {
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        PersonalGoalCard q=clientView.getPlayerPersonalGoal();
        int personalLine=0;
        int rowCoordinates=0;
        for(int i=0;i<bookshelfView.length+1;i++){
            //in base a come vogliamo che sia visualizzata
            //se si vuole stampare la personal goal senza frame cancellare le prime due righe é mettere falso al flag
            printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            //rowCoordinates= printTextRight(rowCoordinates,q);

            System.out.println();
            personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            printTextRight(i,q);
            //rowCoordinates= printTextRight(rowCoordinates,q);
            System.out.println();
        }
        System.out.println();
    }

    public synchronized  /*int*/void printTextRight(int rowCoordinates, PersonalGoalCard p){
        Colors.printFreeSpaces(2);
        int atLeastOne=0;
        for(PersonalGoalBox cell: p.getCells()){

            //for(PersonalGoalBox cell: p.getCells()){
            // Colors.colorize(Colors.GAME_INSTRUCTION,"Your current selections: ");
            //for (int i = 0; i < getClientView().getCoordinatesSelected().size(); i += 2) {
            int x = cell.getX();
            if(x==rowCoordinates){
                int y = cell.getY();
                if(atLeastOne==0){
                    Colors.colorize(Colors.GAME_INSTRUCTION,"➠");
                }else Colors.colorize(Colors.GAME_INSTRUCTION,"•");
                //Colors.colorize(Colors.BEIGE_CODE,"┃");


                Type type=cell.getTypePersonal();
                Colors.printFreeSpaces(1);
                Colors.colorizeSize(Colors.getColor(type), String.valueOf(type),6);
                Colors.printFreeSpaces(2);
                System.out.print(Colors.printTiles(type,3));
                Colors.printFreeSpaces(1);
                Colors.colorize(Colors.getColor(type),"(" + x + ", " + y + ")  ");
                atLeastOne++;

            }


            //rowCoordinates++;
        }
        if(atLeastOne!=0){
            //Colors.colorize(Colors.BEIGE_CODE,"┃");
            Colors.colorize(Colors.GAME_INSTRUCTION,":");
            Colors.printFreeSpaces(1);
            Colors.colorize(Colors.RED_CODE, String.valueOf(atLeastOne));

        }

        /*
        if(rowCoordinates<p.getCells().size()){
            PersonalGoalBox cell=p.getCells().get(rowCoordinates);
            Colors.printFreeSpaces(20);
            //for(PersonalGoalBox cell: p.getCells()){
            // Colors.colorize(Colors.GAME_INSTRUCTION,"Your current selections: ");
            //for (int i = 0; i < getClientView().getCoordinatesSelected().size(); i += 2) {
            int x = cell.getX();
            int y = cell.getY();
            Type type=cell.getTypePersonal();
            Colors.colorize(Colors.getColor(type),"(" + x + ", " + y + ")  ");
            System.out.print(Colors.printTiles(type,3));
            Colors.colorize(Colors.GAME_INSTRUCTION,"; ");
            //rowCoordinates++;
        }

         */
        //return rowCoordinates;
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
