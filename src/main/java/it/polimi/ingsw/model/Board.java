package it.polimi.ingsw.model;



import it.polimi.ingsw.json.GameRules;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Board extends Observable {

    public Board() {
        playerChoiceX=-1;
        playerChoiceY=-1;
        finishPlayerChoice=-1;
        columnSelected=-1;
    }
    private BoardBox[][] matrix;

    public BoardBox[][] getMatrix() {return matrix;}
    public void setMatrix(BoardBox[][] matrix) {
        this.matrix = matrix;
        setChanged();
        notifyObservers(matrix);
    }

    public BoardBox getBoardBox(int x,int y) {
        return matrix[x][y];
    }

//PLAYER CHOICE
    private Integer playerChoiceX;

    public Integer getPlayerChoiceX() {
        return playerChoiceX;
    }

    public void setPlayerChoiceX(Integer playerChoiceX) {
        try {
            if (playerChoiceX < -1 || playerChoiceX >= matrix.length) {
                throw new IllegalArgumentException(" value must be between 0 and " + (matrix.length - 1));
            }
            this.playerChoiceX = playerChoiceX;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid row:Rewrite the row" + e.getMessage());
            setFinishPlayeropposite();
        }
    }
    private Integer playerChoiceY;
    public Integer getPlayerChoiceY() {
        return playerChoiceY;
    }

    public void setPlayerChoiceY(Integer playerChoiceY) {
        try {
            if (playerChoiceY < -1 || playerChoiceY >= matrix[0].length) {
                throw new IllegalArgumentException(" value must be between 0 and " + (matrix[0].length - 1));

            }
            this.playerChoiceY = playerChoiceY;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid column:Rewrite the column" + e.getMessage());
            setFinishPlayeropposite();
        }
    }
    private ArrayList<ItemTile> tiles;

    public ArrayList<ItemTile> getTiles() {
        return tiles;
    }

    public void tiles(ArrayList<ItemTile> tiles) {
        this.tiles = tiles;
        setChanged();
        notifyObservers(tiles);
    }

    private ArrayList<BoardBox> selectedBoard=new ArrayList<>();

    public ArrayList<BoardBox> getSelectedBoard() {
        return selectedBoard;
    }

    public void setSelectedBoard(ArrayList<BoardBox> selectedBoard) {
        this.selectedBoard = selectedBoard;
        setChanged();
        notifyObservers(selectedBoard);
    }

    private boolean finishPlayer;
//TODO it will be removed when the non-deprecated version is implemented
    public void setFinishPlayeropposite() {
        finishPlayer = !finishPlayer;
        setChanged();
        notifyObservers(finishPlayer);
    }
    public void printMatrix(){
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getTile()!=null) {
                    System.out.printf("%-10s",+j+""+matrix[i][j].getTile().getType());
                } else {
                    System.out.printf("%-10s",+j+"EMPTY");
                }
            }
            System.out.println("");
        }
    }
    //BOOKSHELF
    private int columnSelected;
    public int getColumnSelected() {
        return columnSelected;
    }
    public void setColumnSelected(Integer columnSelected) {
        //TODO import num column bookshelf
        //TODO OR THIS WILL BE IN THE BOOKSHELF
        //TODO it depends on how the controller is implemented

        int maxColumBookshelf=4;
        try {
            if (columnSelected < -1 || columnSelected > 4) {
                throw new IllegalArgumentException(" value must be between 0 and  "+maxColumBookshelf);
            }
            this.columnSelected = columnSelected;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid bookshelf column:Rewrite the column" + e.getMessage());
            setFinishPlayeropposite();
        }
    }
    private boolean endGame;//true when a player has completely filled the bookshelf
                            //the game ends when the first player has to start the turn

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    private Integer finishPlayerChoice;
    //TODO it will be removed when the non-deprecated version is implemented
    public Integer getFinishPlayerChoice() {
        return finishPlayerChoice;
    }

    public void setFinishPlayerChoice(Integer finishPlayerChoice) {
        this.finishPlayerChoice = finishPlayerChoice;
    }

    public void firstFillBoard(int numPlayers, GameRules gameRules) throws Exception {
        //fillBag(gameRules);
        int[][] matrix = gameRules.getMatrix(numPlayers);
        //TODO FIRTSFILL
        this.matrix=new BoardBox[matrix.length][matrix[0].length];
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    randomNumber = random.nextInt(tiles.size());
                    this.matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                    this.matrix[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if(this.matrix[i][j].getTile()!=null){
                    setFreeEdges(i,j);
                    // System.out.println(matrix[i][j].getEdges());
                }
            }
        }
    }
    /**
     * instantiate numTilesType for each type of tile
     *
     */

    public void fillBag(GameRules gameRules) throws Exception {
        int[] numTilesOfType = gameRules.getNumTilesPerType();
        int j = 0;
        tiles=new ArrayList<ItemTile>();
        for (Type t : Type.values()) {
            for (int i = 0; i < numTilesOfType[t.ordinal()]; i++) {
                tiles.add(new ItemTile(t, j++));
            }
        }
    }



    /**
     * calculates the number of free edges of the cell having x and y coordinates of the Board
     *
     * @param x:board.matrix row
     * @param y: board.matrix column
     */
    public void setFreeEdges(int x, int y){
        if(x>0) {
            //up
            if (matrix[x - 1][y].getTile()==null || !matrix[x - 1][y].isOccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(x< matrix.length-1) {
            //down
            if (matrix[x+1][y].getTile()==null|| !matrix[x + 1][y].isOccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(y>0) {
            //left
            if (matrix[x][y-1].getTile()==null|| !matrix[x][y-1].isOccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(y< matrix.length-1) {
            //right
            if (matrix[x][y+1].getTile()==null|| !matrix[x][y+1].isOccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
    }

    /**
     * Updates the free edges of the adjacent cells after the tile
     * in row x and column y of the board is chosen by the user
     * @param x:board.matrix row
     * @param y: board.matrix column
     */

    public void increaseNear(int x, int y){
        if(x>0) {
            //up
            if (matrix[x-1][y].getTile()!=null)
                matrix[x-1][y].increasefreeEdges();
        }
        if(x< matrix.length-1) {
            //down
            if (matrix[x+1][y].getTile()!=null)
                matrix[x+1][y].increasefreeEdges();
        }
        if(y>0) {
            //left
            if (matrix[x][y-1].getTile()!=null)
                matrix[x][y-1].increasefreeEdges();
        }
        if(y< matrix.length-1) {
            //right
            if (matrix[x][y+1].getTile()!=null)
                matrix[x][y+1].increasefreeEdges();
        }
    }

    /**
     *
     * @return check that each ItemTile of selectedBoard is adjacent to the previous one
     */
    public boolean allAdjacent(){
        for (int i = 1; i < selectedBoard.size(); i++) {
            BoardBox currentTile = selectedBoard.get(i);
            BoardBox previousTile = selectedBoard.get(i - 1);
            if (Math.abs(currentTile.getX() - previousTile.getX()) != 1 && Math.abs(currentTile.getY() - previousTile.getY()) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return check that all the ItemTiles in the selectedBoard array are on the same row or column
     */
    public boolean allSameRowOrSameColumn(){
        int firstX = selectedBoard.get(0).getX();
        int firstY = selectedBoard.get(0).getY();
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 1; i < selectedBoard.size(); i++) {
            if (selectedBoard.get(i).getX() != firstX) {
                allSameRow = false;
            }
            if (selectedBoard.get(i).getY() != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }
        return false;
    }

    /**
     * adjacent, in the same row or column and adjacent
     * @return
     */
    //TODO depends on how we implement the controller: this method checks the tile after each selection,
    // if we change the controller by checking the cards when the user has selected them all this method
    // must be changed by adding an arraylist to the board, checking at the end
    //TODO pass it the maximum of the player's selectable tile cells as a parameter
    //TODO avoid reading the json file and having to reduce the controller by one check
    public boolean checkSelectable(BoardBox boardBox, int numSelectableTiles){
        if ((boardBox.getFreeEdges() <= 0) || (selectedBoard.size() > (numSelectableTiles+1))) {
            System.err.println("You chose more than "+numSelectableTiles+" tiles write -1 to reset the choice");
            return false;
        }
        selectedBoard.add(boardBox);
        if (selectedBoard.size() == 1) {
            return true;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedBoard.remove(selectedBoard.size() - 1);
            return false;
        }
        return true;
    }


    /**
     *
     * @return itemTiles of the arraylist selected Board
     */
    public ArrayList<ItemTile> selected(){
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i = 0; i< selectedBoard.size(); i++ ){
            selectedItems.add(selectedBoard.get(i).getTile());
            increaseNear(selectedBoard.get(i).getX(), selectedBoard.get(i).getY());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setFreeEdges(0);
            selectedBoard=new ArrayList<>();
        }

        return selectedItems;
    }

    /**
     *
     * @return check that there are all isolated cells
     */

    public boolean checkRefill(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    if(matrix[i][j].getFreeEdges()!=4)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * add all the isolated tiles to the arraylist,
     * randomly extract tiles from the same arraylist,
     * insert on the board in correspondence with the occupiable cells
     */
    public void refill(){
        //add the board tiles to the arraylist Tiles
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    tiles.add(matrix[i][j].getTile());
                    matrix[i][j].setTile(null);
                }
            }
        }
        //randomly extract ItemTile from arraylist Tiles and place them on the board
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].isOccupiable()){
                    randomNumber = random.nextInt(tiles.size());
                    matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                }
            }
        }
        //calculate and set the number of free edges for each tile
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    matrix[i][j].setFreeEdges(0);
                    setFreeEdges(i,j);
                }
            }
        }
    }


}

/*
    private int numOfTile;
    public int getnumOfTile() {
        return numOfTile;
    }

    public void setNumOfTile(Integer numOfTile) {
        GameRules boardAndGame;
        try {
            boardAndGame = new GameRules();
        } catch (Exception e) {
            System.err.println("Error initializing ReadBoardAndGame: " + e.getMessage());
            return;
        }

        int maxNumOfTile = boardAndGame.getMaxSelectableTiles();

        try {
            if (numOfTile < -1 ||  numOfTile > maxNumOfTile) {
                throw new IllegalArgumentException("value must be between 0 and " + maxNumOfTile);
            }
            this.numOfTile = numOfTile;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid numOf tile: " + e.getMessage());
            setFinishPlayeropposite();
        }
    }

 */