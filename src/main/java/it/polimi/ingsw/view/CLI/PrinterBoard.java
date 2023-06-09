package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.BoardBoxView;

import java.util.ArrayList;
/**
 * The `PrinterBoard` class provides methods for printing board-related information.
 */
public class PrinterBoard {

    private boolean waitingForFirst;
    private int freeSpacesFromTableTypesSelected=20;
    private int sizeCoordinates=3;
    private int sizeTile =3;
    private int sizeWordType=8;
    private int spaceBetweenTiles=2;
    /**
     * Prints the matrix board with the given board view and selection.
     *
     * @param boardView the board view representing the matrix board
     * @param selectionBoard the list of selected coordinates on the board
     */
    public synchronized void printMatrixBoard(BoardBoxView[][] boardView, ArrayList<Integer> selectionBoard) {
        int lineLength= sizeTile +2*spaceBetweenTiles;
        int valuesType=0;
        if(selectionBoard==null){
            valuesType=Type.values().length+1;
        }
        Colors.printFreeSpaces(2+ sizeTile +sizeWordType+freeSpacesFromTableTypesSelected+sizeCoordinates);
        for (int i=0;i<boardView[0].length;i++){
            Colors.printFreeSpaces(spaceBetweenTiles+ sizeTile /2);
            System.out.print(i);
            if(sizeTile %2==0){
                Colors.printFreeSpaces(spaceBetweenTiles+ sizeTile /2);
            }else Colors.printFreeSpaces(spaceBetweenTiles+ sizeTile /2+1);


        }
        System.out.println();
        Colors.printFreeSpaces(2+ sizeTile +sizeWordType+freeSpacesFromTableTypesSelected+sizeCoordinates);
        finishLine=false;
        waitingForFirst=true;
        for(int i=0;i<boardView[0].length;i++){
            if(finishLine){
                break;
            }
            upperBoard(Colors.WHITE_CODE,i,boardView,lineLength);

        }
        System.out.println();

        for (int i = 0; i < boardView.length; i++) {
            finishLine=false;
            valuesType=printValues(valuesType,boardView,selectionBoard);
            System.out.print(String.format("%-"+sizeCoordinates+"s", i));
            for (int j = 0; j < boardView[i].length; j++) {

                if (boardView[i][j].getItemTileView().getTileID() != -1) {
                    boolean selected = false;
                    for (int k = 0; selectionBoard!=null && k < selectionBoard.size(); k += 2) {
                        if (selectionBoard.get(k).equals(i) && selectionBoard.get(k + 1).equals(j)) {
                            Colors.colorize(Colors.WHITE_CODE,"║");
                            Colors.printFreeSpaces(spaceBetweenTiles);
                            Colors.colorize(Colors.ERROR_MESSAGE, "█".repeat(sizeTile));
                            Colors.printFreeSpaces(spaceBetweenTiles);
                            selected = true;
                            break;
                        }
                    }
                    if (!selected) {
                        Colors.colorize(Colors.WHITE_CODE,"║");
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        System.out.print(Colors.printTiles(boardView[i][j].getItemTileView().getTypeView(), sizeTile));
                        Colors.printFreeSpaces(spaceBetweenTiles);
                    }
                    if(j==boardView[0].length-1 || !boardView[i][j+1].isOccupiable()){
                        Colors.colorize(Colors.WHITE_CODE,"║");
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        break;
                    }
                } else {

                    if (boardView[i][j].isOccupiable()) {
                        Colors.colorize(Colors.WHITE_CODE,"║");
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        Colors.printFreeSpaces(sizeTile);
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        if(j==boardView[0].length-1 || !boardView[i][j+1].isOccupiable()){
                            Colors.colorize(Colors.WHITE_CODE,"║");
                            Colors.printFreeSpaces(spaceBetweenTiles);
                            break;
                        }
                    } else Colors.colorize(Colors.RESET_CODE, " ".repeat(lineLength+1));
                }

            }
            System.out.println("");
            valuesType=printValues(valuesType,boardView,selectionBoard);
            Colors.printFreeSpaces(sizeCoordinates);
            waitingForFirst=true;
            for(int k=0;k<boardView[0].length;k++){
                if(i==boardView.length-1){
                    downBoard(Colors.WHITE_CODE,k,boardView,lineLength);
                }else  mediumBoard(Colors.WHITE_CODE,i,k,boardView,lineLength);
                if(finishLine){
                    finishLine=false;
                    break;
                }
            }
            System.out.println("");
        }
    }
    /**
     * Prints the values for a specific type on the board view, with the given selection.
     *
     * @param valuesType the type of row to print
     * @param boardView the board view representing the matrix board
     * @param selection the list of selected coordinates on the board
     * @return the number of row printed
     */
    public synchronized int printValues(int valuesType,BoardBoxView[][] boardView,ArrayList<Integer> selection){
        ArrayList<Integer> coordinatesSelected=selection;
        int numSelectedType=0;
        if(valuesType<Type.values().length){
            for (int k = 0;k < coordinatesSelected.size(); k += 2) {
                Type type=boardView[coordinatesSelected.get(k)][coordinatesSelected.get(k + 1)].getType();
                if(type!=null && type.equals(Type.values()[valuesType])){
                    numSelectedType++;
                }
            }
            Colors.printTypeWithTypeColor(Type.values()[valuesType],sizeWordType);
            System.out.printf(Colors.printTiles(Type.values()[valuesType++],sizeTile));
            Colors.colorize(Colors.ERROR_MESSAGE," "+numSelectedType);
            Colors.printFreeSpaces(freeSpacesFromTableTypesSelected);
            return valuesType;
        }else if(valuesType==Type.values().length){
            Colors.printTypeWithTypeColor("SELECTED",sizeWordType);
            System.out.printf(Colors.printTiles("SELECTED",sizeTile));
            Colors.colorize(Colors.ERROR_MESSAGE,"  ");
            valuesType++;
            Colors.printFreeSpaces(freeSpacesFromTableTypesSelected);
        }
        else Colors.printFreeSpaces(sizeWordType+freeSpacesFromTableTypesSelected+2+sizeTile);
        return valuesType;
    }
    /**
     * Prints the upper part of the board with the specified color and column, based on the board view.
     *
     * @param color the color to use for printing
     * @param column the column number to print
     * @param board the board view representing the matrix board
     * @param lineLength the length of the line for formatting purposes
     */
    public synchronized void upperBoard(String color, int column, BoardBoxView[][] board,int lineLength ) {


        String[] lineRepresentations = {
                " ".repeat(lineLength+1),
                "╔" + "═".repeat(lineLength) + "╗",
                "╔" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength) + "╗"
        };

        String text;

        if (column == board[0].length - 1 && waitingForFirst && !board[0][column].isOccupiable()) {
            finishLine = true;
            return;
        }

        if (board[0].length == 1 || (waitingForFirst && !board[0][column + 1].isOccupiable() && board[0][column].isOccupiable())) {
            text = lineRepresentations[1];
            finishLine = true;
        } else {
            if (!board[0][column].isOccupiable()) {
                text = lineRepresentations[0];
            } else {
                if ((column == 0 && board[0][column].isOccupiable()) || (column != 0 && !board[0][column - 1].isOccupiable())) {
                    text = lineRepresentations[2];
                    waitingForFirst = false;
                } else if (column != 0 && column != board[0].length - 1 && board[0][column - 1].isOccupiable() && board[0][column + 1].isOccupiable()) {
                    text = lineRepresentations[3];
                } else {
                    text = lineRepresentations[4];
                    finishLine = true;
                }
            }
        }
        System.out.print(color + text + "\033[0m");

    }
    /**
     * Prints the lower part of the board with the specified color and column, based on the board view.
     *
     * @param color the color to use for printing
     * @param column the column number to print
     * @param board the board view representing the matrix board
     * @param lineLength the length of the line for formatting purposes
     */

    public synchronized void downBoard(String color, int column, BoardBoxView[][] board,int lineLength ) {


        String[] lineRepresentations = {
                " ".repeat(lineLength+1),
                "╚" + "═".repeat(lineLength) + "╝",
                "╚" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength) + "╝"
        };
        String text;
        int row = board.length - 1;

        if (column == board[row].length - 1 && waitingForFirst && !board[row][column].isOccupiable()) {
            text = lineRepresentations[0];
            finishLine = true;
            return;
        }

        if (board[row].length == 1 || (waitingForFirst && !board[row][column + 1].isOccupiable() && board[row][column].isOccupiable())) {
            text = lineRepresentations[1];
            finishLine = true;
        } else {
            if (!board[row][column].isOccupiable()) {
                text = lineRepresentations[0];
            } else {
                if ((column == 0 && board[row][column].isOccupiable()) || (column != 0 && !board[row][column - 1].isOccupiable())) {
                    text = lineRepresentations[2];
                    waitingForFirst = false;
                } else if (column != 0 && column != board[row].length - 1 && board[row][column - 1].isOccupiable() && board[row][column + 1].isOccupiable()) {
                    text = lineRepresentations[3];
                } else {
                    text = lineRepresentations[4];
                    finishLine = true;
                }
            }
        }
        System.out.print(color + text + "\033[0m");

    }


    public boolean finishLine;

    /**
     * Prints the medium part of the board with the specified color, row, and column, based on the board view.
     *
     * @param color the color to use for printing
     * @param row the row number to print
     * @param column the column number to print
     * @param board the board view representing the matrix board
     * @param lineLength the length of the line for formatting purposes
     */
    public synchronized void mediumBoard(String color, int row, int column, BoardBoxView[][] board, int lineLength) {
        String text;


        String[] lineRepresentations = {
                " ".repeat(lineLength+1),
                "╔" + "═".repeat(lineLength),
                "╠" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength) + "╗",
                "╬" + "═".repeat(lineLength),
                "╬" + "═".repeat(lineLength) + "╗",
                "╬" + "═".repeat(lineLength) + "╣",
                "╩" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength) + "╝",
                "╬" + "═".repeat(lineLength) + "╝",
                "╚" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength) + "╝"

        };

        if (board[row][column].isOccupiable() == false) {
            if (board[row + 1][column].isOccupiable() == false) text = lineRepresentations[0];
            else if (waitingForFirst) {
                text = lineRepresentations[1];
                waitingForFirst=false;
            } else {
                if(column== board[0].length-1){
                    if (board[row][column - 1].isOccupiable() == true) text=lineRepresentations[6];
                    else text = lineRepresentations[4];
                    finishLine=true;
                }
                else{
                    if(board[row][column - 1].isOccupiable() == true){
                        if(board[row+1][column + 1].isOccupiable() == true )text=lineRepresentations[5];
                        else {
                            text=lineRepresentations[6];
                            finishLine=true;
                        }
                    }
                    else {
                        if(board[row+1][column + 1].isOccupiable() == true)text=lineRepresentations[3];
                        else {
                            text=lineRepresentations[4];
                            finishLine=true;
                        }

                    }

                }

            }
        }else {
            if(waitingForFirst) {
                if(board[row + 1][column].isOccupiable() == true) text=lineRepresentations[2];
                else text=lineRepresentations[11];
                waitingForFirst=false;
            }
            else {
                if(column== board[0].length-1){
                    if(board[row+1][column].isOccupiable() == true)text=lineRepresentations[7];
                    else if(board[row+1][column-1].isOccupiable() == true) text=lineRepresentations[10];
                    else text = lineRepresentations[12];
                    finishLine=true;
                }else {
                    if(board[row+1][column].isOccupiable() == true){
                        if( board[row+1][column+1].isOccupiable()==true)text=lineRepresentations[5];
                        else{
                            if( board[row][column+1].isOccupiable()==true)text=lineRepresentations[5];
                            else{
                                text=lineRepresentations[7];
                                finishLine=true;
                            }

                        }
                    }
                    else{
                        if(board[row][column+1].isOccupiable() == true){
                            if(board[row+1][column-1].isOccupiable() == true)text=lineRepresentations[5];
                            else text=lineRepresentations[8];
                        }
                        else{
                            if(board[row+1][column-1].isOccupiable() == true)text=lineRepresentations[10];
                            else text=lineRepresentations[12];
                            finishLine=true;
                        }
                    }

                }

            }
        }
        System.out.print(color + text + "\033[0m");
    }
}
