package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.CLI.Colors;
import it.polimi.ingsw.view.CLI.PrinterBoard;
import it.polimi.ingsw.view.CLI.PrinterBookshelfAndPersonal;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;
import java.util.List;

public class App{

        public static void main(String[] args) throws Exception {
            GameLobby gameLobby = new GameLobby(3, 3);
            ArrayList<String> playerNames = new ArrayList<>(List.of("TIZIO", "CAIO"/*, "SEMPRONIO", "PIPPO"*/));
            GameController gameController = new GameController(gameLobby, playerNames);
            MessageHeader header=new MessageHeader(MessageType.DATA,"TIZIO");
            int[] selectionBoard=new int[]{7,3,7,4,7,5};
            MessagePayload messagePayload=new MessagePayload(TurnPhase.SELECT_FROM_BOARD);
            messagePayload.put(Data.VALUE_CLIENT,selectionBoard);
            Message m=new Message(header,messagePayload);
            gameController.checkAndInsertBoardBox(m);
            int[] orderTiles=new int[]{1,0,2};

            messagePayload =new MessagePayload(TurnPhase.SELECT_ORDER_TILES);
            messagePayload.put(Data.VALUE_CLIENT,orderTiles);
            m=new Message(header,messagePayload);
            gameController.permutePlayerTiles(m);
            messagePayload=new MessagePayload(TurnPhase.SELECT_COLUMN);
            messagePayload.put(Data.VALUE_CLIENT,0);
            m=new Message(header,messagePayload);
            gameController.selectingColumn(m);
gameController.getModel().getBoard().resetBoard();

            CLI cl=new CLI();
            cl.setClientView(new ClientView());
            cl.getClientView().setBoardView(gameController.getModel().getModelView().getBoardView());
            PrinterBoard puu=new PrinterBoard();
            puu.printMatrixBoard(cl.getClientView());
            cl.askLobbyDecision();

            Bookshelf bo = new Bookshelf(6, 5, 3);
            bo.setTile(new ItemTile(Type.CAT, 9), 5, 0);
            bo.setTile(new ItemTile(Type.PLANT, 9), 4, 0);
            bo.setTile(new ItemTile(Type.PLANT, 9), 3, 0);
            bo.setTile(new ItemTile(Type.TROPHY, 9), 3, 1);
            bo.setTile(new ItemTile(Type.PLANT, 9), 3, 2);
            bo.setTile(new ItemTile(Type.PLANT, 9), 4, 4);
            ItemTileView[][] b;
            b = new ItemTileView[6][5];
            b = bo.cloneBookshelf();
            PrinterBookshelfAndPersonal p = new PrinterBookshelfAndPersonal();
            ClientView c = new ClientView();
            c.setIndexPersonal(2);
            c.setBookshelfView(b);

            p.printMatrixBookshelf(c, 3, 1, 60, false, false, 0);
            p.printMatrixBookshelf(c, 3, 2, 60, false, false, 1);
            p.printMatrixBookshelf(c, 3, 1, 50, true, true, 1);

            p.printMatrixBookshelf(c, 3, 2, 40, true, true, 0);
            p.printMatrixBookshelf(c, 3, 1, 80, true, true, 1);
            p.printMatrixBookshelf(c, 3, 2, 40, true, false, 0);
            p.printMatrixBookshelf(c, 3, 1, 90, true, false, 1);
            p.printMatrixBookshelf(c, 3, 2, 40, false, true, 0);
            p.printMatrixBookshelf(c, 3, 1, 50, false, true, 1);
            for (Type t : Type.values()) {
                System.out.print(Colors.printTiles(t, 3).length());
                System.out.print(Colors.printTiles(t, 3));
            }
            System.out.println();
            for (Type t : Type.values()) {
                System.out.print(Colors.printTiles(t, 3).length());
                Colors.colorizeSize(Colors.WHITE_CODE, "ooo", 3);
            }



        }}

            /*
            gameController.setServerView(serverView);
            HashMap<String, ClientView> playerMap = new HashMap<String, ClientView>();
            HashMap<String, Integer> playersId = new HashMap<String, Integer>();

             */





/*

            for (int i = 0; i < playerNames.size(); i++) {
                String playerName = play1
                12erNames.get(i);
                ClientView clientView = new ClientView(playerName, gameController, serverView, new Client(clientView, playerName));
                playerMap.put(playerName, clientView);
                playersId.put(playerName, i);
            }

 */



            //gameController.startGame(playerNames,0);
            //gameController.getModel().setNextPlayer();
            //gameController.getModel().setNextPlayer();
            //gameController.removePlayer("CAIO");
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


        }
}
*/
