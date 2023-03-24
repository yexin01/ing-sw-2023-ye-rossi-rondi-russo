package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.*;


import java.util.*;

public class Controller1 implements Observer {

    public final Board board;
    private final Game game;
    public final View1 view1;
    public final View2 view2;


    public Controller1(Board board,View1 view1, View2 view2, Game game) {
        this.board = board;
        this.view1 = view1;
        this.view2 = view2;
        this.game = game;
    }
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String nickname){
            if(!insertNickname(nickname)){
                start();
                firstPlayer();
            }
        }
        if(arg instanceof Integer number){
            if(board.getPlayerChoiceX()==-1){
                board.setPlayerChoiceX(number);
                game.setFinishopposite();
            }else if (board.getPlayerChoiceY()==-1) {
                board.setPlayerChoiceY(number);
                game.setFinishopposite();
            }else {
                board.setPlayerChoicenumTile(number);
                isSelectable(board.getBoardBox(board.getPlayerChoiceX(), board.getPlayerChoiceY()), board.getPlayerChoicenumTile());
                if(board.getPlayerChoicenumTile()==maxTileOrFinish()) {
                    game.getTurnPlayer().setSelectedItems(selected());
                    //Inserisce nella bookshelf
                    //calcola punteggio...
                    //Chiama il metodo bookshelf piena
                    setNextPlayer(game.getTurnPlayer());
                    if(checkRefill()){
                        refill();
                    }

                }
            }
            board.setPlayerChoiceX(-1);
            board.setPlayerChoiceY(-1);
            board.setPlayerChoicenumTile(-1);
            game.setFinishopposite();
        }
    }

    private void firstPlayer() {
        game.setFirstPlayer(game.getPlayers().get(0));
        game.setTurnPlayer(game.getPlayers().get(0));
    }


    private boolean insertNickname(String nickname) {
        if(nickname.equals("stop")||game.getPlayers().size()==3){//inserire nella condizione il metodo che deve essere diverso dagli altri nomi
            return false;
        }
        if(true){
            Player player=new Player(nickname);
            game.getPlayers().add(player);
            game.setNumPlayers(game.getNumPlayers() + 1);
            board.setFinishPlayeropposite();

            // nickname=null;

        }
        return true;
    }
    public void setNextPlayer(Player player){
        for(int i=0;i<game.getPlayers().size();i++){
            if(game.getPlayers().get(i).getNickname().equals(player.getNickname())){
                if(i<game.getPlayers().size()-1){
                    game.setTurnPlayer(game.getPlayers().get(i+1));
                }
                else {
                    game.setTurnPlayer(game.getPlayers().get(0));
                }
            }

        }
    }
    public void start(){
        initialize();
        //IMPORTARLE DA JSON
        int matrice2gioc[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int matrice3gioc[][] ={
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };
        int matrice4gioc[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
        };

        switch (game.getNumPlayers()) {
            case 2:
                fill(matrice2gioc);
                break;
            case 3:
                fill(matrice3gioc);
                break;
            case 4:
                fill(matrice4gioc);
                break;
            default:
                fill(matrice2gioc);
                break;
        }


    }
    public void initialize() {
        //IMPORTARE IL 22
        //IMPORTARE IL 22 DA un FILE JASON
        int j=0;
        board.setTiles(new ArrayList<ItemTile>());
        for(Type t: Type.values()){
            for (int i = 0; i < 22; i++) {
                board.getTiles().add(new ItemTile(t,j++));
            }
        }

    }
    public void fill(int [][] matrice){
        //importa da jason la matrice
        int dimensione = 9;   // o qualsiasi altra dimensione desiderata
        board.setMatrix(new BoardBox[dimensione][dimensione]);
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                this.board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrice[i][j]==1){
                    randomNumber = random.nextInt(board.getTiles().size());
                    board.getMatrix()[i][j].setTile(board.getTiles().get(randomNumber));
                    board.getTiles().remove(randomNumber);
                    board.getMatrix()[i][j].setOccupiable(true);
                    board.getMatrix()[i][j].setOccupied(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    setFreeEdges(i,j);
                    // System.out.println(matrix[i][j].getEdges());
                }
            }
        }
    }
    public void setFreeEdges(int x, int y){
        if(x>0) {
            //sopra
            if (!board.getMatrix()[x - 1][y].isOccupied() || !board.getMatrix()[x - 1][y].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(x< board.getMatrix().length-1) {
            //sotto
            if (!board.getMatrix()[x + 1][y].isOccupied()|| !board.getMatrix()[x + 1][y].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(y>0) {
            //sinistra
            if (!board.getMatrix()[x][y - 1].isOccupied()|| !board.getMatrix()[x][y-1].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(y< board.getMatrix().length-1) {
            //destra
            if (!board.getMatrix()[x][y + 1].isOccupied()|| !board.getMatrix()[x][y+1].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
    }
    public void increaseNear(int x, int y){
        if(x>0) {
            //sopra
            if (board.getMatrix()[x-1][y].isOccupied())
                board.getMatrix()[x-1][y].increasefreeEdges();
        }
        if(x< board.getMatrix().length-1) {
            //sotto
            if (board.getMatrix()[x+1][y].isOccupied())
                board.getMatrix()[x+1][y].increasefreeEdges();
        }
        if(y>0) {
            //sinistra
            if (board.getMatrix()[x][y-1].isOccupied())
                board.getMatrix()[x][y-1].increasefreeEdges();
        }
        if(y< board.getMatrix().length-1) {
            //destra
            if (board.getMatrix()[x][y+1].isOccupied())
                board.getMatrix()[x][y+1].increasefreeEdges();
        }
    }
    private boolean near(BoardBox boardbox,int nTessera) {
        if
        ((board.getSelectedBoard().get(nTessera - 1).getX() - boardbox.getX() == 1 ||
                board.getSelectedBoard().get(nTessera - 1).getX() - boardbox.getX() == -1 ||
                board.getSelectedBoard().get(nTessera - 1).getY() - boardbox.getY() == 1 ||
                board.getSelectedBoard().get(nTessera - 1).getY() - boardbox.getY() == -1)
                && (board.getSelectedBoard().get(nTessera - 1).getX() == boardbox.getX() ||
                board.getSelectedBoard().get(nTessera - 1).getY() == boardbox.getY())) {
            return true;
        }
        return false;
    }
    private boolean samerowOrSamecolumn(BoardBox boardbox,int nTessera) {
        if(
                (board.getSelectedBoard().get(nTessera-1).getX()==boardbox.getX() &&
                        board.getSelectedBoard().get(nTessera-2).getX()==boardbox.getX()) ||
                        (board.getSelectedBoard().get(nTessera-1).getY()==boardbox.getY() &&
                                board.getSelectedBoard().get(nTessera-2).getY()==boardbox.getY())) {
            return true;
        }
        return false;
    }
    public boolean isSelectable(BoardBox boardbox, int nTessera){


        switch(nTessera) {
            case 0:
                if(boardbox.getFreeEdges()>0){
                    board.setSelectedBoard(new ArrayList<BoardBox>());
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            case 1:
                if(boardbox.getFreeEdges()>0 && near(boardbox,nTessera)){
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            case 2:
                if(boardbox.getFreeEdges()>0 && near(boardbox,nTessera)&& samerowOrSamecolumn(boardbox,nTessera)){
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            default:

                break;
        }
        return false;
    }

    public int maxTileOrFinish() {
        //INSERIRE il metodo max tile
        return 2;
    }

    public ArrayList<ItemTile> selected(){
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i = 0; i< board.getSelectedBoard().size(); i++ ){
            selectedItems.add(board.getSelectedBoard().get(i).getTile());
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setOccupied(false);
            increaseNear(board.getSelectedBoard().get(i).getX(), board.getSelectedBoard().get(i).getY());
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setTile(null);
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setFreeEdges(0);
        }
        for(int i=0;i<selectedItems.size();i++){
            System.out.println(selectedItems.get(i).getType());
        }
        return selectedItems;
    }
    public boolean checkRefill(){
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    if(board.getMatrix()[i][j].getFreeEdges()!=4)
                        return false;
                }

            }
        }
        return true;

    }
    public void refill(){
        //sposta le tessere isolate nel sacchetto
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    board.getTiles().add(board.getMatrix()[i][j].getTile());
                    board.getMatrix()[i][j].setTile(null);
                    board.getMatrix()[i][j].setOccupied(false);
                }
            }
        }
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupiable()){
                    randomNumber = random.nextInt(board.getTiles().size());
                    board.getMatrix()[i][j].setTile(board.getTiles().get(randomNumber));
                    board.getTiles().remove(randomNumber);
                    board.getMatrix()[i][j].setOccupied(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    board.getMatrix()[i][j].setFreeEdges(0);
                    setFreeEdges(i,j);
                }
            }
        }
    }

}