package it.polimi.ingsw.model;



import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;

import java.util.ArrayList;
import java.util.Random;

public class Board{
    private ModelView modelView;

    //private GameInfo gameInfo;


    private BoardBox[][] matrix;

    public Board(ModelView modelView) {
        this.modelView=modelView;
    }

    public BoardBox[][] getMatrix() {return matrix;}
    public void setMatrix(BoardBox[][] matrix) {
        this.matrix = matrix;

    }

    public BoardBox getBoardBox(int x,int y) {
        return matrix[x][y];
    }


    private ArrayList<ItemTile> tiles;

    public ArrayList<ItemTile> getTiles() {
        return tiles;
    }

    public void tiles(ArrayList<ItemTile> tiles) {
        this.tiles = tiles;

    }

    private ArrayList<BoardBox> selectedBoard=new ArrayList<BoardBox>();

    public ArrayList<BoardBox> getSelectedBoard() {
        return selectedBoard;
    }

    public void setSelectedBoard(ArrayList<BoardBox> selectedBoard) {
        this.selectedBoard = selectedBoard;

    }
    public ErrorType checkCoordinates(int x, int y) {
        if (x < 0 || y<0 || x> matrix.length-1 || y> matrix[0].length-1 || !getBoardBox(x,y).isOccupiable() ) {
            return ErrorType.INVALID_COORDINATES;
            //throw new Error(ErrorType.INVALID_COORDINATES);
        }
        return null;
    }

    public ErrorType checkFinishChoice() {
        if (selectedBoard.size()==0) {
            return ErrorType.NOT_VALUE_SELECTED;
            //throw new Error(ErrorType.INVALID_COORDINATES);
        }
        return null;
    }



    private boolean finishPlayer;
    //TODO it will be removed when the non-deprecated version is implemented

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
    public BoardBoxView[][] cloneBoard(){
        BoardBoxView[][] boardView=new BoardBoxView[matrix.length][matrix[0].length];
        ItemTile itemTile;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                itemTile=matrix[i][j].getTile();
                if(itemTile!=null){
                    boardView[i][j]=new BoardBoxView(matrix[i][j].isOccupiable(), new ItemTileView(itemTile.getType(), itemTile.getTileID()), i, j, matrix[i][j].getFreeEdges());
                }else boardView[i][j]=new BoardBoxView(matrix[i][j].isOccupiable(), new ItemTileView(null,-1), i, j, matrix[i][j].getFreeEdges());
            }
        }
        return boardView;
    }

    public void firstFillBoard(int numPlayers, GameRules gameRules) throws Exception {
        int[][] matrix = gameRules.getMatrix(numPlayers);
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
                }
            }
        }
        modelView.setBoardView(cloneBoard());
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

    public ErrorType checkSelectable(BoardBox boardBox, int numSelectableTiles) throws Error {
        if(selectedBoard.size() > (numSelectableTiles+1)){
            return ErrorType.TOO_MANY_TILES;
        }

        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        selectedBoard.add(boardBox);
        if (selectedBoard.size() == 1) {
            return null;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedBoard.remove(selectedBoard.size() - 1);
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        return null;
    }
/*
    /**
     *
     * @return itemTiles of the arraylist selected Board

    public ArrayList<ItemTile> selected(){
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i = 0; i< selectedBoard.size(); i++ ){
            selectedItems.add(selectedBoard.get(i).getTile());
            increaseNear(selectedBoard.get(i).getX(), selectedBoard.get(i).getY());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setFreeEdges(0);

        }
        resetBoardChoice();
        return selectedItems;
    }

 */
public ArrayList<ItemTile> selected(){
    ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
    for(int i = 0; i< selectedBoard.size(); i++ ){
        selectedItems.add(selectedBoard.get(i).getTile());
        /*

        increaseNear(selectedBoard.get(i).getX(), selectedBoard.get(i).getY());
        matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
        matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setFreeEdges(0);

         */

    }
    //resetBoardChoice();
    return selectedItems;
}
    public void resetBoard(){
        for(int i = 0; i< selectedBoard.size(); i++ ){
            increaseNear(selectedBoard.get(i).getX(), selectedBoard.get(i).getY());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setFreeEdges(0);
        }
        resetBoardChoice();
        modelView.setBoardView(cloneBoard());
    }
    public void resetBoardChoice(){
        selectedBoard=new ArrayList<>();
        return;
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
        modelView.setBoardView(cloneBoard());
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