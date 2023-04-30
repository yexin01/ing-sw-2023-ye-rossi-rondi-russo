package it.polimi.ingsw;

import it.polimi.ingsw.view.clientView.ClientView;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.server.ServerView;

import java.util.HashMap;

public class App{
        public static void main(String[] args) throws Exception {


            ServerView serverView=new ServerView();
            GameController gameController=new GameController();
            gameController.setServerView(serverView);
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            HashMap<String, Integer> playersId = new HashMap<String, Integer>();
            String[] playerNames = {"TIZIO", "CAIO", "SEMPRONIO"};
            for (int i = 0; i < playerNames.length; i++) {
                String playerName = playerNames[i];
                ClientView clientView = new ClientView(playerName, gameController, serverView, new Client(clientView, playerName));
                playerMap.put(playerName, clientView);
                playersId.put(playerName, i);
            }



            gameController.startGame(playerMap,playersId,serverView);
            gameController.getModel().setNextPlayer();
            //gameController.getModel().setNextPlayer();
            gameController.removePlayer("CAIO");

            while(true){
                playerMap.get(gameController.getModel().getTurnPlayer().getNickname()).askClient();

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
