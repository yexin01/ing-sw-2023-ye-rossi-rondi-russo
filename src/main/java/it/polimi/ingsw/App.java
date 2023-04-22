package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientUI;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.server.SendMessages;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;

public class App{
        public static void main(String[] args) throws Exception {

            Game game=new Game();
            GameController gameController=new GameController(game);

            HashMap<String, ClientUI> playerMap = new HashMap<String, ClientUI>();
            playerMap.put("TIZIO", new ClientUI("TIZIO",gameController,new Client("TIZIO")));
            playerMap.put("CAIO", new ClientUI("CAIO",gameController,new Client("CAIO")));
            playerMap.put("SEMPRONIO", new ClientUI("SEMPRONIO",gameController,new Client("SEMPRONIO")));
            SendMessages sendMessages=new SendMessages(playerMap);
            gameController.setSendMessages(sendMessages);

            game.setNumPlayers(3);
            game.addPlayers("TIZIO",sendMessages);
            game.addPlayers("CAIO",sendMessages);
            game.addPlayers("SEMPRONIO",sendMessages);

            GameRules gameRules=new GameRules();
            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(3,gameRules);
            game.createPersonalGoalCard(gameRules);
            game.createCommonGoalCard(gameRules,sendMessages);

            game.updateAllPoints();
            ClientUI player = playerMap.get("TIZIO");
            player.askClient();
            //player.ask();

/*
            //game.getPlayerByNickname("TIZIO").setPlayerPoints(32);
            //game.getPlayerByNickname("CAIO").setPlayerPoints(2);
            //game.getPlayerByNickname("SEMPRONIO").setPlayerPoints(4);
            gameController.endGame();
            game.updatePointsCommonGoals();
            //game.getCommonGoalCards().get(0).removeToken(game.getTurnPlayer().getNickname());
            game.setNextPlayer();
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
