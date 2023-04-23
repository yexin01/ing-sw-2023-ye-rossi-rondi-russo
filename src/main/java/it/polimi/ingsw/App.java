package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.server.listener.ServerView;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;

public class App{
        public static void main(String[] args) throws Exception {

            Game game=new Game();
            GameController gameController=new GameController(game);

            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            playerMap.put("TIZIO", new ClientView("TIZIO",gameController,new Client("TIZIO")));
            playerMap.put("CAIO", new ClientView("CAIO",gameController,new Client("CAIO")));
            playerMap.put("SEMPRONIO", new ClientView("SEMPRONIO",gameController,new Client("SEMPRONIO")));
            ServerView serverView =new ServerView(playerMap);
            gameController.setServerView(serverView);

            game.setNumPlayers(3);
            game.addPlayer("TIZIO", serverView);
            game.addPlayer("CAIO", serverView);
            game.addPlayer("SEMPRONIO", serverView);

            GameRules gameRules=new GameRules();
            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(3,gameRules);
            game.createPersonalGoalCard(gameRules);
            game.createCommonGoalCard(gameRules, serverView);
            //game.updateAllPoints();
            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            game.setNextPlayer();
            game.setNextPlayer();
            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            game.getCommonGoalCards().get(1).removeToken(game.getTurnPlayer().getNickname());

            ClientView player = playerMap.get("TIZIO");
            while(true){
                player.askClient();
            }

            //player.ask();

/*
            //game.getPlayerByNickname("TIZIO").setPlayerPoints(32);
            //game.getPlayerByNickname("CAIO").setPlayerPoints(2);
            //game.getPlayerByNickname("SEMPRONIO").setPlayerPoints(4);
            gameController.endGame();
            game.updatePointsCommonGoals();
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());

            game.updatePointsCommonGoals();
            game.setNextPlayer();
            game.setNextPlayer();
            game.updatePointsCommonGoals();
            game.setNextPlayer();
            game.updatePointsCommonGoals();
            game.setNextPlayer();
            game.updatePointsCommonGoals();
            game.setNextPlayer();
            game.updatePointsCommonGoals();

 */

        }
}
