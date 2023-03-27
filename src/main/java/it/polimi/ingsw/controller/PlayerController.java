package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class PlayerController {

    private Game game;

    private ArrayList<BookshelfController> bookshelfControllers=new ArrayList<>();

    public PlayerController(Game game) {
        this.game=game;
    }

    public Game getGame() {
        return game;
    }

    public void firstPlayer() {
        game.setFirstPlayer(game.getPlayers().get(0));
        game.setTurnPlayer(game.getPlayers().get(0));
    }


   public boolean insertNickname(String nickname) {
        //IMPORTA il 4 dajason sarebbe numero di giocatori possibili meno uno
        if(true && !nickname.equals("stop")){//aggiunta la condizione che il nome deve essere diverso dgli altri
            Player player=new Player(nickname);
            game.getPlayers().add(player);
            game.setNumPlayers(game.getNumPlayers() + 1);
            bookshelfControllers.add(new BookshelfController(new Bookshelf()));
            if(game.getPlayers().size()==4)  return false;
            return true;
        }

        return false;
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

    public int maxTileOrFinish(){
        return 2;
    }



}
