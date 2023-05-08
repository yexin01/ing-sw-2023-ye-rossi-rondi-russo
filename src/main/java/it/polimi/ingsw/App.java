package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.view.CLI.Colors;

import java.util.ArrayList;
import java.util.List;

public class App{
        public static void main(String[] args) throws Exception {

            //CLI cli=new CLI();
            //cli.printItemTilesSelected();
/*
            //String s="\u001B[38;2;197;225;247m" + "\u2588" + "\u001B[0m";
            String s=Colors.printTiles(Type.CAT);
            //System.out.println(s);
            Colors.upperBoard(Colors.OCHRE_YELLOW_CODE);
            Colors.mediumBoard(Colors.OCHRE_YELLOW_CODE);
            Colors.lowerBoard(Colors.OCHRE_YELLOW_CODE);
            Colors.colorize(Colors.OCHRE_YELLOW_CODE,"╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗");
            System.out.println("╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗\n" +
                                "║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ \n" +
                                "╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣ \n" +
                                "║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ "+s+" ║ \n" +
                                "╚══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╝");

 */



            GameLobby gameLobby =new GameLobby();
            ArrayList<String> playerNames = new ArrayList<>(List.of("TIZIO", "CAIO", "SEMPRONIO","PIPPO"));
            GameController gameController=new GameController(gameLobby, playerNames);
            /*
            gameController.setServerView(serverView);
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            HashMap<String, Integer> playersId = new HashMap<String, Integer>();

             */




/*

            for (int i = 0; i < playerNames.size(); i++) {
                String playerName = playerNames.get(i);
                ClientView clientView = new ClientView(playerName, gameController, serverView, new Client(clientView, playerName));
                playerMap.put(playerName, clientView);
                playersId.put(playerName, i);
            }

 */



            //gameController.startGame(playerNames,0);
            gameController.getModel().setNextPlayer();
            //gameController.getModel().setNextPlayer();
            gameController.removePlayer("CAIO");
/*
            while(true){
                playerMap.get(gameController.getModel().getTurnPlayer().getNickname()).askClient();

            }

 */

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
