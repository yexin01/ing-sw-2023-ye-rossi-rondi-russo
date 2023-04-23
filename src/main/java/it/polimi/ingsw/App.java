package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;

public class App{
        public static void main(String[] args) throws Exception {

            Game game=new Game();
            GameController gameController=new GameController(game);
            GameRules gameRules=new GameRules();
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            playerMap.put("TIZIO", new ClientView("TIZIO",gameController,new Client("TIZIO")));
            playerMap.put("CAIO", new ClientView("CAIO",gameController,new Client("CAIO")));
            playerMap.put("SEMPRONIO", new ClientView("SEMPRONIO",gameController,new Client("SEMPRONIO")));
            ServerView serverView =new ServerView(playerMap);

            gameController.setServerView(serverView);
            ModelView listener=new ModelView(gameRules,3);
            serverView.setGameListener(listener);
            game.setGameListener(listener);
            game.setNumPlayers(3);
            game.addPlayer("TIZIO", serverView,listener);
            game.addPlayer("CAIO", serverView,listener);
            game.addPlayer("SEMPRONIO", serverView,listener);
            listener.setPlayers(game.getPlayers());


            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(3,gameRules);
            game.getBoard().setGameListener(listener);
            listener.setBoardView(game.getBoard().cloneBoard());
            game.createPersonalGoalCard(gameRules);
            game.createCommonGoalCard(gameRules, serverView);

            listener.setPlayers(game.getPlayers());
            game.updateAllPoints();
            serverView.sendInfo("TIZIO");
            //
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            //game.setNextPlayer();
            //game.setNextPlayer();
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            //game.getCommonGoalCards().get(1).removeToken(game.getTurnPlayer().getNickname());

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
