package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.view.ClientView;
/**
 * The `PrinterBookshelfAndPersonal` class provides methods for printing bookshelf and personalGoal.
 */
public class PrinterBookshelfAndPersonal {

    private int sizeLengthFromBord;
    private int sizetile;
    private int spaceBetweenTiles;
    private int spaceBetweenPersonal;
    private int lineLength;
    /**
     * Prints the arrow indicating the choice column in the bookshelf view.
     *
     * @param bookshelfView the 2D array of ItemTileView representing the bookshelf view
     */
    public synchronized void printArrowChoiceColumn(ItemTileView[][] bookshelfView){
        Colors.printFreeSpaces(sizeLengthFromBord);
        for (int i = 0; i < bookshelfView.length-1; i++) {
            Colors.printFreeSpaces((lineLength+2)/2);
            Colors.colorize(Colors.ERROR_MESSAGE, String.valueOf(i));
            Colors.printFreeSpaces((lineLength+2-(lineLength+2)/2)-2);
        }
        System.out.println();
        Colors.printFreeSpaces(sizeLengthFromBord);
        for (int i = 0; i < bookshelfView.length-1; i++) {
            Colors.printFreeSpaces((lineLength+2)/2);
            Colors.colorize(Colors.ERROR_MESSAGE,"v");
            Colors.printFreeSpaces((lineLength+2-(lineLength+2)/2)-2);
        }

    }
    /**
     * Prints the matrix representation of the bookshelf.
     *
     * @param clientView the client view containing the bookshelf view
     * @param sizeTile the size of each tile
     * @param spaceBetweenTiles the space between each tile
     * @param sizeLengthFromBord the size of the length from the border
     * @param personalGoal indicates whether to print the personal goal or not
     * @param arrow indicates whether to print the arrow or not
     * @param spaceBetweenPersonal the space between personal goals
     */

    public synchronized void printMatrixBookshelf(ClientView clientView,int sizeTile,int spaceBetweenTiles,int sizeLengthFromBord,boolean personalGoal,boolean arrow, int spaceBetweenPersonal){
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        this.spaceBetweenTiles=spaceBetweenTiles;
        this.sizetile=sizeTile;
        this.sizeLengthFromBord = sizeLengthFromBord;
        this.spaceBetweenPersonal=spaceBetweenPersonal;
        this.lineLength=sizeTile+2*spaceBetweenTiles;
        String characterBetweenPersonalAndBookshelf;
        if(personalGoal){
            characterBetweenPersonalAndBookshelf="«";
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
                "┬" + "─".repeat(lineLength),
                "┬" + "─".repeat(lineLength) + "┬",
                "├" + "─".repeat(lineLength),
                "┼" + "─".repeat(lineLength),
                "┼" + "─".repeat(lineLength) + "┤",
                " ".repeat(lineLength - 2 * spaceBetweenTiles),
        };

        System.out.println();
        int index = 0;
        for (int i = 0; i < bookshelfView.length; i++) {
            Colors.printFreeSpaces(this.sizeLengthFromBord);
            Colors.printCharacter(" ",characterBetweenPersonalAndBookshelf.length(),Colors.WHITE_CODE);
            for (int j = 0; j < bookshelfView[0].length; j++) {
                if (j == 0) {
                    index = (i == 0 && j == 0) ? 0 : 2;
                }
                if (j > 0 && j < bookshelfView[0].length - 1) {
                    index = (i == 0) ? 0 : 3;

                }
                if (j == bookshelfView[0].length - 1) {
                    index = (i == 0) ? 1 : 4;
                }

                System.out.print(Colors.WHITE_CODE + lineRepresentations[index] + "\033[0m");
            }

            if (i != bookshelfView.length) {
                System.out.println();
                personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenPersonal,false, this.sizeLengthFromBord, this.sizeLengthFromBord -bookshelfView[0].length*2*spaceBetweenPersonal-bookshelfView[0].length-1-10);
                Colors.printCharacter(characterBetweenPersonalAndBookshelf,characterBetweenPersonalAndBookshelf.length(),Colors.WHITE_CODE);
                for (int j = 0; j < bookshelfView[0].length; j++) {
                    Colors.colorize(Colors.WHITE_CODE, "│");
                    Colors.printFreeSpaces(spaceBetweenTiles);
                    if (bookshelfView[i][j].getTileID() != -1) {
                        System.out.print(Colors.printTiles(bookshelfView[i][j].getTypeView(), sizeTile));

                    } else System.out.print(lineRepresentations[5]);
                    Colors.printFreeSpaces(spaceBetweenTiles);
                }
                Colors.colorize(Colors.WHITE_CODE, "│");

            }
            System.out.println();

        }

        Colors.printFreeSpaces(this.sizeLengthFromBord - charForSide+characterBetweenPersonalAndBookshelf.length());
        Colors.printCharacter("█", size + 2 * charForSide, Colors.WHITE_CODE);

        System.out.println();
    }
    private boolean frame=true;
    /**
     * Prints the personal goal with or without a frame.
     *
     * @param personalLine the line number for the personal goal
     * @param clientView the client view containing the personal goals
     * @param spaceBetweenPersonal the space between cells of personal goal
     * @param mediumSpaces indicates whether to use medium spaces or not
     * @param fromBoardBookshelf the position from the board bookshelf
     * @param sizeLengthFromBord the size length from the border
     * @return the number of spaces printed
     */
    public synchronized int printPersonalGoalWithOrWithoutFrame(int personalLine, ClientView clientView, int spaceBetweenPersonal, boolean mediumSpaces, int fromBoardBookshelf,int sizeLengthFromBord)  {
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        PersonalGoalCard q = clientView.getPlayerPersonalGoal();
        int lineLength=1+spaceBetweenPersonal*2;
        String[] lineRepresentations = {
                "┌" + "─".repeat(lineLength),
                "┬" + "─".repeat(lineLength),
                "┬" + "─".repeat(lineLength) + "┐",
                "├" + "─".repeat(lineLength),
                "┼" + "─".repeat(lineLength),
                "┼" + "─".repeat(lineLength) + "┤",
                "└" + "─".repeat(lineLength),
                "┴" + "─".repeat(lineLength),
                "┴" + "─".repeat(lineLength) + "┘",
                " ".repeat(lineLength - 2 * spaceBetweenPersonal),
        };
        int index = 0;
        int limit;
        if(mediumSpaces){
            limit=(bookshelfView.length+1)*2;
        }else limit=bookshelfView.length;
        if(personalLine<limit){
            Colors.printFreeSpaces(sizeLengthFromBord);
            fromBoardBookshelf=fromBoardBookshelf-sizeLengthFromBord;
            if(mediumSpaces && frame){
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
                    System.out.print(Colors.WHITE_CODE + lineRepresentations[index] + "\033[0m");

                }
                fromBoardBookshelf=fromBoardBookshelf-lineRepresentations[index].length();
                frame=false;
            }else{
                if(personalLine!= bookshelfView.length){
                    for(int j=0;j<bookshelfView[0].length;j++){
                        boolean found = false;
                        Colors.colorize(Colors.WHITE_CODE,"│");
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
                            Colors.printFreeSpaces(spaceBetweenPersonal);
                            fromBoardBookshelf=fromBoardBookshelf-spaceBetweenPersonal;
                        }
                    }
                    Colors.colorize(Colors.WHITE_CODE,"│");
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
    /**
     * Prints the personal goals for the client view.
     *
     * @param clientView the client view containing the personal goals
     * @param spaceBetweenTiles the space between each tile
     * @param sizeLengthFromBord the size of the length from the border
     */

    public synchronized void printPersonal(ClientView clientView ,int spaceBetweenTiles,int sizeLengthFromBord) {
        ItemTileView[][] bookshelfView= clientView.getBookshelfView();
        PersonalGoalCard q=clientView.getPlayerPersonalGoal();
        int personalLine=0;
        for(int i=0;i<bookshelfView.length+1;i++){
            printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            System.out.println();
            personalLine= printPersonalGoalWithOrWithoutFrame(personalLine,clientView,spaceBetweenTiles,true,0,sizeLengthFromBord);
            printTypeCellsPersonal(i,q);
            System.out.println();
        }
        System.out.println();
    }
    /**
     * Prints the type cells for a specific row in the personal goal card.
     *
     * @param rowCoordinates the row coordinates of the bookshelf
     * @param p the personal goal card
     */
    public synchronized  void printTypeCellsPersonal(int rowCoordinates, PersonalGoalCard p){
        Colors.printFreeSpaces(2);
        int atLeastOne=0;
        for(PersonalGoalBox cell: p.getCells()){
            int x = cell.getX();
            if(x==rowCoordinates){
                int y = cell.getY();
                if(atLeastOne==0){
                    Colors.colorize(Colors.WHITE_CODE,"»");
                }else Colors.colorize(Colors.WHITE_CODE,"·");
                Type type=cell.getTypePersonal();
                Colors.printFreeSpaces(1);
                Colors.colorizeSize(Colors.getColor(type), String.valueOf(type),6);
                Colors.printFreeSpaces(2);
                System.out.print(Colors.printTiles(type,3));
                Colors.printFreeSpaces(1);
                Colors.colorize(Colors.getColor(type),"(" + x + ", " + y + ")  ");
                atLeastOne++;
            }
        }
        if(atLeastOne!=0){
            Colors.colorize(Colors.WHITE_CODE,":");
            Colors.printFreeSpaces(1);
            Colors.colorize(Colors.ERROR_MESSAGE, String.valueOf(atLeastOne));
        }
    }
}
