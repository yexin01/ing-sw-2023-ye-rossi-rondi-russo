package it.polimi.ingsw.network.server.persistence;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameLobbyInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5158808756179690476L;
    private final int idGameLobby;
    private final int wantedPlayers;
    private ModelView modelView;
    private Message messageEndGame=null;
    private CopyOnWriteArrayList<String> playersDisconnected;


    public GameLobbyInfo(int idGameLobby, int wantedPlayers){
        this.idGameLobby = idGameLobby;
        this.wantedPlayers = wantedPlayers;
    }
    public int getIdGameLobby() {
        return idGameLobby;
    }

    public void setGameLobbyState(GameLobby gameLobby){
        this.messageEndGame = gameLobby.getMessageEndGame();
        this.playersDisconnected = gameLobby.getPlayersDisconnected();
        System.out.println("setGameLobbyState");
        System.out.println(modelView.getBookshelfView()[0][0][0].getTileID());
        for(PlayerPointsView p : modelView.getPlayerPoints()){
            if (!playersDisconnected.contains(p.getNickname())) playersDisconnected.add(p.getNickname());
        }

    }

    public  GameLobby restoreGameLobby(GlobalLobby globalLobby, GameRules gameRules) throws Exception {
        GameLobby gamelobby = new GameLobby(idGameLobby, wantedPlayers, globalLobby);
        gamelobby.setGameLobbyInfo(this);
        gamelobby.setPlayersDisconnected(playersDisconnected);
        gamelobby.setGameController(restoreControllers(restoreModel(gameRules)));
        gamelobby.getGameController().addListeners(gamelobby);
        return gamelobby;
    }





    /**
     * Restore the model of the previously saved game associated to this class and returns the Game instance of the game
     *
     * @return the Game instance of the restored game
     */

    private Game restoreModel(GameRules gamerules) throws Exception {
        Game game = new Game(modelView);
        //restore players...
        ArrayList<String> nicknames = new ArrayList<String>();
        nicknames.addAll(Arrays.stream(game.getModelView().getPlayerPoints()).map(PlayerPointsView::getNickname).collect(Collectors.toList()));
        game.setPlayers(new ArrayList<>());
        for(String name : nicknames){
            game.getPlayers().add(new Player(name, modelView, gamerules));
        }

        //restore CommonGoalCards...
        ArrayList<Integer> cgcNumbers = new ArrayList<Integer>();
        cgcNumbers.addAll(Arrays.asList(Arrays.stream(game.getModelView().getCommonGoalView()[0]).boxed().toArray(Integer[]::new)));
        game.createCommonGoalCard(gamerules, cgcNumbers);

        //restore Board...
        game.setBoard(game.getModelView().restoreBoard());

        //restore Bookshelves, personal goal cards and points
        int i=0;
        for(Player p: game.getPlayers()){
            p.setBookshelf(game.getModelView().restoreBookshelf(p));
            p.setPersonalGoalCard(game.getModelView().getPlayerPersonalGoal(p.getNickname()));
            p.setPersonalGoalPoints(game.getModelView().getPersonalPoint(p.getNickname()));
            p.setAdjacentPoints(game.getModelView().getPlayerPoints()[i].getAdjacentPoints());
            p.setCommonGoalPoints(game.getModelView().getPlayerPoints()[i].getCommonGoalPoints());
            i++;
        }
        Boolean[] activePlayers=new Boolean[game.getPlayers().size()];
        Arrays.fill(activePlayers, false);
        game.getModelView().setActivePlayers(activePlayers);
        try {
            modelView.setMAX_SELECTABLE_TILES((new GameRules()).getMaxSelectableTiles());
        } catch (Exception e) {
        }
        for(String s:playersDisconnected){
            System.out.println("DISCONNESSSI"+s);
        }

        return game;
    }



    /**
     * Restore controllers of the previously saved game associated to this class and returns the GameController
     * that will control the restored game
     *
     * @param game the restored game
     * @return the game controller of the restored game
     */
    private GameController restoreControllers(final Game game) {
        GameController gameController = new GameController();
        gameController.setGame(game);
        return gameController;
    }


    public int getWantedPlayers() {
        return wantedPlayers;
    }

    public ModelView getModelView() {
        return modelView;
    }

    public Message getMessageEndGame() {
        return messageEndGame;
    }


    public CopyOnWriteArrayList<String> getPlayersDisconnected() {
        return playersDisconnected;
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    public void setMessageEndGame(Message messageEndGame) {
        this.messageEndGame = messageEndGame;
    }

    public void setPlayersDisconnected(CopyOnWriteArrayList<String> playersDisconnected) {
        this.playersDisconnected = playersDisconnected;
    }
}
