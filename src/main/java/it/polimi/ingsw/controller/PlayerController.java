package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Observable;
import java.util.Observer;

public class PlayerController {

    private Game game;



    public PlayerController(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void firstPlayer() {
        game.setFirstPlayer(game.getPlayers().get(0));
        game.setTurnPlayer(game.getPlayers().get(0));
    }


   public boolean insertNickname(String nickname) {

        if(nickname.equals("stop")||game.getPlayers().size()==4){//inserire nella condizione il metodo che deve essere diverso dagli altri nomi
            return false;
        }
        if(true){//aggiunta la condizione che il nome deve essere diverso dgli altri
            Player player=new Player(nickname);
            game.getPlayers().add(player);
            game.setNumPlayers(game.getNumPlayers() + 1);
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
