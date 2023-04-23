package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;
import java.util.Map;

public class App{
        public static void main(String[] args) throws Exception {

            Game game=new Game();
            GameController gameController=new GameController(game);
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            playerMap.put("TIZIO", new ClientView("TIZIO",gameController,new Client("TIZIO")));
            playerMap.put("CAIO", new ClientView("CAIO",gameController,new Client("CAIO")));
            playerMap.put("SEMPRONIO", new ClientView("SEMPRONIO",gameController,new Client("SEMPRONIO")));
            GameRules gameRules=new GameRules();
            ServerView serverView =new ServerView(playerMap,gameRules, playerMap.keySet().size());
            game.setNumPlayers(playerMap.keySet().size());
            int i=0;
            for(Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
                String key = entry.getKey();
                ClientView value = entry.getValue();
                game.addPlayer(key,serverView);
                serverView.setPlayers(key,i++);
            }

            game.setServerView(serverView);
            game.createCommonGoalCard(gameRules, serverView);
            game.createPersonalGoalCard(gameRules);

            game.getBoard().setServerView(serverView);
            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(playerMap.keySet().size(), gameRules);

            gameController.setServerView(serverView);
            serverView.sendInfo("TIZIO");

            /*
            game.addPlayer("TIZIO", serverView);
            game.addPlayer("CAIO", serverView);
            game.addPlayer("SEMPRONIO", serverView);

             */

            game.updateAllPoints();
            serverView.sendInfo("TIZIO");
            //
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            //game.setNextPlayer();
            //game.setNextPlayer();
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            //game.getCommonGoalCards().get(1).removeToken(game.getTurnPlayer().getNickname());

            ClientView player = playerMap.get("TIZIO");
            player.printItemTilesSelected(null);
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
