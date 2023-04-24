package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.EndTurnListener;
import it.polimi.ingsw.listeners.FinishSelectionListener;
import it.polimi.ingsw.listeners.TokenListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;
import java.util.Map;

public class App{
        public static void main(String[] args) throws Exception {
            GameRules gameRules=new GameRules();

            ServerView serverView=new ServerView();
            GameController gameController=new GameController();
            gameController.setServerView(serverView);
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            HashMap<String, Integer> playersId = new HashMap<String, Integer>();
            String[] playerNames = {"TIZIO", "CAIO", "SEMPRONIO"};
            for (int i = 0; i < playerNames.length; i++) {
                String playerName = playerNames[i];
                ClientView clientView = new ClientView(playerName, gameController, serverView, new Client(playerName));
                playerMap.put(playerName, clientView);
                playersId.put(playerName, i);
            }

            serverView.setPlayerMap(playerMap);
            ModelView modelView=new ModelView(playersId, gameRules);
            modelView.addListener(EventType.TILES_SELECTED,new FinishSelectionListener(serverView));
            modelView.addListener(EventType.END_TURN,new EndTurnListener(serverView));
            modelView.addListener(EventType.WIN_TOKEN,new TokenListener(serverView));
            serverView.setModelView(modelView);

            //ServerView serverView =new ServerView(playerMap,gameRules, playerMap.keySet().size());
            Game game=new Game(gameRules,modelView);
            gameController.setGame(game);


            for(Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
                String key = entry.getKey();
                ClientView value = entry.getValue();
                game.addPlayer(key,modelView);
            }

            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(playerMap.keySet().size(), gameRules);
            game.createCommonGoalCard(gameRules,modelView);
            game.createPersonalGoalCard(gameRules);



            //gameController.setServerView(serverView);

            serverView.sendInfo("TIZIO");


            ClientView player = playerMap.get("TIZIO");
            while(true){
                player.askClient();
            }

            //serverView.sendInfo("TIZIO");


            /*
            game.addPlayer("TIZIO", serverView);
            game.addPlayer("CAIO", serverView);
            game.addPlayer("SEMPRONIO", serverView);

            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname(),0);
            game.setNextPlayer();
            game.setNextPlayer();
            game.getCommonGoalCards().get(1).removeToken(game.getTurnPlayer().getNickname(),1);
            game.setNextPlayer();
            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname(),0);


             */

            //game.updateAllPoints();


            /*
            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname(),0);
            game.setNextPlayer();
            game.setNextPlayer();
            game.getCommonGoalCards().get(1).removeToken(game.getTurnPlayer().getNickname(),1);
            game.setNextPlayer();
            game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname(),0);

             */



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
