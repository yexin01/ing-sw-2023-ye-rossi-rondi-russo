package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

public class PrinterBoard {

    private boolean waitingForFirst;
    private int freeSpacesFromTableTypesSelected=20;
    private int sizeCoordinates=3;
    private int sizeTile =3;
    private int sizeWordType=8;
    private int spaceBetweenTiles=2;
    public void printMatrixBoard(ClientView clientView) {
        System.out.println("BOARD");

        int lineLength= sizeTile +2*spaceBetweenTiles;

        BoardBoxView[][] boardView = clientView.getBoardView();
        ArrayList<Integer> coordinates = clientView.getCoordinatesSelected();
        //Colors.upperOneBoard(Colors.WHITE_CODE);
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
        //Colors.mediumBoard(Colors.OCHRE_YELLOW_CODE);
        int valuesType=0;
        for (int i = 0; i < boardView.length; i++) {
            finishLine=false;
            valuesType=printValues(valuesType,clientView);
            /*
            if(valuesType<Type.values().length){
                Colors.printTypeWithTypeColor(Type.values()[valuesType]);
                System.out.printf("  "+Colors.printTiles(Type.values()[valuesType++])+"                           -");
            }else Colors.printFreeSpaces(42);

             */

            System.out.print(String.format("%-"+sizeCoordinates+"s", i));
            for (int j = 0; j < boardView[i].length; j++) {

                if (boardView[i][j].getItemTileView().getTypeView() != null) {
                    //System.out.printf("%-6s","("+i+","+j+")");
                    boolean selected = false;
                    for (int k = 0; coordinates.size() > 0 && k < coordinates.size(); k += 2) {
                        if (coordinates.get(k).equals(i) && coordinates.get(k + 1).equals(j)) {
                            // colors.colorize(Colors.RED_CODE,"SELECTED");
                            Colors.colorize(Colors.WHITE_CODE,"║");
                            Colors.printFreeSpaces(spaceBetweenTiles);
                            Colors.colorize(Colors.RED_CODE, "█".repeat(sizeTile));
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

                        //colors.printTypeWithTypeColor(matrix[i][j].getItemTileView().getTypeView());
                    }
                    if(j==boardView[0].length-1 || !boardView[i][j+1].isOccupiable()){
                        Colors.colorize(Colors.WHITE_CODE,"║");
                        Colors.printFreeSpaces(spaceBetweenTiles);
                        break;
                    }
                } else {
                    //TODO finire
                    //printFreeSpaces(2);
                    //System.out.print("  ");
                    //Colors.colorize(Colors.WHITE_CODE,"║ ");
                    if (boardView[i][j].isOccupiable()) {
                        //System.out.printf("%-6s","("+i+","+j+")");

                        Colors.colorize(Colors.RESET_CODE, " ".repeat(lineLength+1));
                    } else Colors.colorize(Colors.RESET_CODE, " ".repeat(lineLength+1));
                    //Colors.colorize(Colors.WHITE_CODE,"║ ");
                }


            }
            //Colors.colorize(Colors.WHITE_CODE,"║ ");
            System.out.println("");
            valuesType=printValues(valuesType,clientView);

/*                if(valuesType<Type.values().length){
                    Colors.printTypeWithTypeColor(Type.values()[valuesType]);
                    System.out.printf("  "+Colors.printTiles(Type.values()[valuesType++])+"                           -");
                }else Colors.printFreeSpaces(42);

 */
            //System.out.println(i);
            Colors.printFreeSpaces(sizeCoordinates);
            //Colors.mediumBoard(Colors.WHITE_CODE);
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
        /*
        valuesType=printValues(valuesType);
        /*
        if(valuesType<Type.values().length){
            Colors.printTypeWithTypeColor(Type.values()[valuesType]);
            System.out.printf("  "+Colors.printTiles(Type.values()[valuesType++])+"       -");
        }else Colors.printFreeSpaces(44);


        System.out.print("  ");
        for(int k=0;k<boardView[0].length;k++){
            Colors.downBoard(Colors.WHITE_CODE,k,boardView);
        }
        System.out.println("  ");

        //Colors.lowerBoard(Colors.WHITE_CODE);
*/
        //askCoordinates();
    }
    public int printValues(int valuesType,ClientView clientView){
        ArrayList<Integer> coordinatesSelected=clientView.getCoordinatesSelected();
        BoardBoxView[][] boardView=clientView.getBoardView();
        int numSelectedType=0;
        if(valuesType<Type.values().length){
            for (int k = 0;k < coordinatesSelected.size(); k += 2) {
                Type type=boardView[coordinatesSelected.get(k)][coordinatesSelected.get(k + 1)].getType();
                if(type.equals(Type.values()[valuesType])){
                    numSelectedType++;
                }
            }
            Colors.printTypeWithTypeColor(Type.values()[valuesType],sizeWordType);
            System.out.printf(Colors.printTiles(Type.values()[valuesType++],sizeTile));
            Colors.colorize(Colors.RED_CODE," "+numSelectedType);
            Colors.printFreeSpaces(freeSpacesFromTableTypesSelected);
            return valuesType;
        }else if(valuesType==Type.values().length){
            Colors.printTypeWithTypeColor("SELECTED",sizeWordType);
            System.out.printf(Colors.printTiles("SELECTED",sizeTile));
            Colors.colorize(Colors.RED_CODE,"  ");
            valuesType++;
            Colors.printFreeSpaces(freeSpacesFromTableTypesSelected);
        }
        else Colors.printFreeSpaces(sizeWordType+freeSpacesFromTableTypesSelected+2+sizeTile);
        return valuesType;
    }
    public void upperBoard(String color, int column, BoardBoxView[][] board,int lineLength ) {


        String[] lineRepresentations = {
                " ".repeat(lineLength+1),
                "╔" + "═".repeat(lineLength) + "╗",
                "╔" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength),
                "╦" + "═".repeat(lineLength) + "╗"
        };

        /*
        String[] lineRepresentations = {
                "       ",
                "╔══════╗",
                "╔══════",
                "╦══════",
                "╦══════╗"
        };

         */

        String text;

        if (column == board[0].length - 1 && waitingForFirst && !board[0][column].isOccupiable()) {
            text = lineRepresentations[0];
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
        System.out.print(color + text + "\u001B[0m");

    }

    public void downBoard(String color, int column, BoardBoxView[][] board,int lineLength ) {


        String[] lineRepresentations = {
                " ".repeat(lineLength+1),
                "╚" + "═".repeat(lineLength) + "╝",
                "╚" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength),
                "╩" + "═".repeat(lineLength) + "╝"
        };

        /*
        String[] lineRepresentations = {
                "       ",
                "╚══════╝",
                "╚══════",
                "╩══════",
                "╩══════╝"
        };

         */

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
        System.out.print(color + text + "\u001B[0m");
        /*
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
   */
    }


    public boolean finishLine;


    public void mediumBoard(String color, int row, int column, BoardBoxView[][] board, int lineLength) {
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
        /*
        String[] lineRepresentations = {
                "       ",
                "╔══════",
                "╠══════",
                "╦══════",
                "╦══════╗",
                "╬══════",
                "╬══════╗",
                "╬══════╣",
                "╩══════",
                "╩══════╝",
                "╬══════╝"
        };

         */


        if (board[row][column].isOccupiable() == false) {
            if (board[row + 1][column].isOccupiable() == false) text = lineRepresentations[0];
            else if (waitingForFirst) {
                //if (board[row - 1][column].isOccupiable() == true) text = "╔══════";
                //else text = "╠══════";
                text = lineRepresentations[1];
                waitingForFirst=false;
            } else {
                if(column== board[0].length-1){
                    if (board[row][column - 1].isOccupiable() == true) text=lineRepresentations[6];
                    else text = lineRepresentations[4];
                    finishLine=true;
                    /*
                    if (board[row+1][column - 1].isOccupiable() == true) text = "╬══════╗";
                    else text = "╦══════╗";
                    finishLine=true;

                     */

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




        /*
        String text;
        if (board[row][column].isOccupiable() == false) {
            if (board[row + 1][column].isOccupiable() == false) text = "       ";
            else if (waitingForFirst) {
                //if (board[row - 1][column].isOccupiable() == true) text = "╔══════";
                //else text = "╠══════";
                text = "╔══════";
                waitingForFirst=false;
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
            if(waitingForFirst) {
                if(board[row + 1][column].isOccupiable() == true) text="╠══════";
                else text="╚══════";
                waitingForFirst=false;
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

         */

        System.out.print(color + text + "\u001B[0m");


    }
}
