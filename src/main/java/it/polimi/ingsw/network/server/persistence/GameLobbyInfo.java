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

/**
 * This class is used to store the current state of a gameLobby.
 */
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

    /**
     * Stores the current state of the gameLobby passed as a parameter in the attributes of this class.
     * This method is called at the end of each turn to update the gameLobbyInfo right before they're saved
     * to disk by the SaveGame class.
     * @param gameLobby The GameLobby object from which information are to be extracted and saved.
     */
    public void setGameLobbyState(GameLobby gameLobby){
        this.messageEndGame = gameLobby.getMessageEndGame();
        this.playersDisconnected = gameLobby.getPlayersDisconnected();
        for(PlayerPointsView p : modelView.getPlayerPoints()){
            if (!playersDisconnected.contains(p.getNickname())) playersDisconnected.add(p.getNickname());
        }
    }

    /**
     * Restores the gameLobby whose infos where saved in this instance of GameLobbyInfo.
     * @param globalLobby The GlobalLobby object to which the restored gameLobby will be added.
     * @param gameRules The GameRules object containing the rules of the game.
     * @return The restored GameLobby object.
     * @throws Exception If an error occurs while restoring the gameLobby.
     */
    public  GameLobby restoreGameLobby(GlobalLobby globalLobby, GameRules gameRules) throws Exception {
        GameLobby gamelobby = new GameLobby(idGameLobby, wantedPlayers, globalLobby);
        gamelobby.setGameLobbyInfo(this);
        gamelobby.setPlayersDisconnected(playersDisconnected);
        gamelobby.setGameController(restoreController(restoreModel(gameRules)));
        gamelobby.getGameController().addListeners(gamelobby);
        return gamelobby;
    }


    /**
     * restores the model of the game by restoring the players, the common goal cards, the board, the bookshelves,
     * the personal goal cards and the player points.
     * @param gamerules the gamerules of the game
     * @return the restored game
     * @throws Exception if an error occurs while restoring the game
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

        return game;
    }

    /**
     * Associates the restored game to a new controller
     * @param game the restored game
     * @return the new controller associated to the game
     */
    private GameController restoreController(final Game game) {
        GameController gameController = new GameController();
        gameController.setGame(game);
        return gameController;
    }

    /**
     * ModelView getter
     * @return the modelView
     */
    public ModelView getModelView() {
        return modelView;
    }

   /**
    * ModelView setter
    * @param modelView the modelView to set
    */
    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    /**
     * IdGameLobby getter
     * @return the idGameLobby
     */
    public int getIdGameLobby() {
        return idGameLobby;
    }

    /**
     * WantedPlayers getter
     * @return the wantedPlayers
     */
    public int getWantedPlayers() {
        return wantedPlayers;
    }

    /**
     * MessageEndGame getter
     * @return the messageEndGame
     */
    public Message getMessageEndGame() {
        return messageEndGame;
    }

    /**
     * PlayersDisconnected getter
     * @return the playersDisconnected
     */
    public CopyOnWriteArrayList<String> getPlayersDisconnected() {
        return playersDisconnected;
    }

    /**
     * MessageEndGame setter
     * @param messageEndGame the messageEndGame to set
     */
    public void setMessageEndGame(Message messageEndGame) {
        this.messageEndGame = messageEndGame;
    }

    /**
     * PlayersDisconnected setter
     * @param playersDisconnected the playersDisconnected to set
     */
    public void setPlayersDisconnected(CopyOnWriteArrayList<String> playersDisconnected) {
        this.playersDisconnected = playersDisconnected;
    }

}
