package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.SendMessages;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;

public class App{
        public static void main(String[] args) throws Exception {

            Game game=new Game();
            GameController gameController=new GameController(game);


            HashMap<String, Client> playerMap = new HashMap<String, Client>();
            playerMap.put("TIZIO", new Client("TIZIO",gameController));
            playerMap.put("CAIO", new Client("CAIO",gameController));
            playerMap.put("SEMPRONIO", new Client("SEMPRONIO",gameController));
            SendMessages sendMessages=new SendMessages(playerMap);
            gameController.setSendMessages(sendMessages);

            game.setNumPlayers(3);
            game.addPlayers("TIZIO",playerMap);
            game.addPlayers("CAIO",playerMap);
            game.addPlayers("SEMPRONIO",playerMap);

            GameRules gameRules=new GameRules();
            game.getBoard().fillBag(gameRules);
            game.getBoard().firstFillBoard(3,gameRules);
            game.createPersonalGoalCard(gameRules);
            game.createCommonGoalCard(gameRules);
            game.updateAllPoints();
            Client player = playerMap.get("TIZIO");
            player.ask();



        }
}
