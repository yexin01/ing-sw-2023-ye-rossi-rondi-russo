package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
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
         ClientView cli=new ClientView();
         CLI cl3=new CLI();

         cl3.askLobbyDecision();

         //cli.setThreadRunning(false);
         try {
          Thread.sleep(5000);
         } catch (InterruptedException e) {
          e.printStackTrace();
         }
         System.out.print("USCITOOOOOOOO");

         ArrayList<String> playerNames = new ArrayList<>(List.of("TIZIO", "CAIO", "SEMPRONIO", "PIPPO"));
         ModelView mo=new ModelView(4,new GameRules());
         PlayerPointsView[] pl=new PlayerPointsView[4];
         pl[0]=new PlayerPointsView(new int[]{0,1},0,"TIZIO1");
         pl[0].getPoints();
         pl[1]=new PlayerPointsView(new int[]{2,1},3,"TIZIO2");
         pl[1].getPoints();
         pl[2]=new PlayerPointsView(new int[]{0,1},4,"TIZIO3");
         pl[2].getPoints();
         pl[3]=new PlayerPointsView(new int[]{5,1},5,"TIZIO4");
         mo.setPlayersPoints(pl);
         mo.checkWinner();
         int[] personal=new int[]{1,2,3,4};
         mo.setPersonalPoints(personal);
         mo.winnerEndGame();




            GameLobby gameLobby = new GameLobby(3, 3,null);

            GameController gameController = new GameController();
            //gameLobby.handleErrorFromClient(new Message(new MessageHeader(MessageType.ERROR,"TIZIO"),null));
           MessageHeader header=new MessageHeader(MessageType.DATA, gameController.getTurnNickname());
            int[] selectionBoard=new int[]{7,3,7,4};
            MessagePayload messagePayload=new MessagePayload(TurnPhase.SELECT_FROM_BOARD);
            messagePayload.put(Data.VALUE_CLIENT,selectionBoard);
            Message m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
           // gameController.checkAndInsertBoardBox(m);
            int[] orderTiles=new int[]{1,0};

            messagePayload =new MessagePayload(TurnPhase.SELECT_ORDER_TILES);
            messagePayload.put(Data.VALUE_CLIENT,orderTiles);
            m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
            //gameController.permutePlayerTiles(m);
            messagePayload=new MessagePayload(TurnPhase.SELECT_COLUMN);
            messagePayload.put(Data.VALUE_CLIENT,0);
            m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
            header=new MessageHeader(MessageType.DATA,"TIZIO");
            selectionBoard=new int[]{6,3,6,4};
            messagePayload=new MessagePayload(TurnPhase.SELECT_FROM_BOARD);
            messagePayload.put(Data.VALUE_CLIENT,selectionBoard);
            m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
            // gameController.checkAndInsertBoardBox(m);
            orderTiles=new int[]{1,0};

            messagePayload =new MessagePayload(TurnPhase.SELECT_ORDER_TILES);
            messagePayload.put(Data.VALUE_CLIENT,orderTiles);
            m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
            //gameController.permutePlayerTiles(m);
            messagePayload=new MessagePayload(TurnPhase.SELECT_COLUMN);
            messagePayload.put(Data.VALUE_CLIENT,0);
            m=new Message(header,messagePayload);
            gameController.receiveMessageFromClient(m);
            //gameController.selectingColumn(m);
//gameController.getModel().getBoard().resetBoard();


            CLI cl=new CLI();

            cl.setClientView(new ClientView());
            cl.getClientView().setBoardView(gameController.getModel().getModelView().getBoardView());
            cl.getClientView().setBookshelfView(gameController.getModel().getModelView().getBookshelfView("TIZIO"));
            cl.getClientView().setPlayerPersonalGoal(gameController.getModel().getModelView().getPlayerPersonalGoal("TIZIO"));
            cl.askCoordinates();
            PrinterBoard puu=new PrinterBoard();
            puu.printMatrixBoard(cl.getClientView().getBoardView(),new ArrayList<>(List.of(7, 3, 7, 4,7, 5)));
            puu.printMatrixBoard(cl.getClientView().getBoardView(),new ArrayList<>(List.of(7, 3,6,3)));
            puu.printMatrixBoard(cl.getClientView().getBoardView(),null);
            cl.getClientView().setCoordinatesSelected( new ArrayList<>(List.of(7, 3, 7, 4,7, 5)));
           // puu.printMatrixBoard(cl.getClientView());
            //cl.askLobbyDecision();

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
