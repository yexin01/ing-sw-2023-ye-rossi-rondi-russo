package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.InfoAndEndGameListener;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class GameLobby is the class that represents the game lobby of a single game.
 * It contains all the players that are in the game lobby: who created the game and who wants to join the game,
 * when it reaches the number of players wanted, it starts the game and creates a new game controller and a new model view for the game itself.
 */
public class GameLobby {
    private final int idGameLobby;
    private final int wantedPlayers;
    private GameController gameController;
    private ModelView modelView;
    private InfoAndEndGameListener infoAndEndGameListener;
    private Message messageEndGame=null;


    private ConcurrentHashMap<String, Connection> players; //maps of all the active players of the game
    private CopyOnWriteArrayList<String> playersDisconnected; //maps of all the disconnected players of the game

    // sto valutando cosa conviene fare per le disconnessioni di un player potrei cambiare i metodi di disconnessione

    /**
     * Constructor of the class GameLobby that creates a new game lobby with the given id and the given number of players wanted
     * @param idGameLobby the id of the game lobby
     * @param wantedPlayers the number of players wanted in the game lobby
     */
    public GameLobby(int idGameLobby, int wantedPlayers,GlobalLobby globalLobby){
        this.idGameLobby= idGameLobby;
        this.wantedPlayers = wantedPlayers;
        players = new ConcurrentHashMap<>();
        infoAndEndGameListener=new InfoAndEndGameListener(this,globalLobby);
        playersDisconnected = new CopyOnWriteArrayList<>();
        messageEndGame=null;
    }

    /**
     * Method that creates a new game controller for the game lobby and starts the game itself with the players in the game lobby
     * @throws Exception if something goes wrong in the creation of the game controller
     */
    public synchronized void createGame() throws Exception {
        ArrayList<String> playersGame=new ArrayList<>();
        for (String player : players.keySet()) {
            playersGame.add(player);
        }
        this.gameController=new GameController(this,playersGame,infoAndEndGameListener);
    }

    /**
     * @return the idGameLobby of the game lobby
     */
    public int getIdGameLobby(){
        return idGameLobby;
    }

    /**
     * @return the number of players wanted in the game lobby
     */
    public int getWantedPlayers(){
        return wantedPlayers;
    }

    /**
     * @return the players in the game lobby
     */
    public ConcurrentHashMap<String, Connection> getPlayersInGameLobby(){
        return players;
    }

    /**
     * @return the players disconnected in the game lobby
     */
    public CopyOnWriteArrayList<String> getPlayersDisconnectedInGameLobby(){
        return playersDisconnected;
    }

    /**
     * @return the info and end game listener
     */
    public InfoAndEndGameListener getStartAndEndGameListener(){
        return infoAndEndGameListener;
    }

    /**
     * Method to set the info and end game listener
     * @param infoAndEndGameListener the info and end game listener
     */
    public void setStartAndEndGameListener(InfoAndEndGameListener infoAndEndGameListener){
        this.infoAndEndGameListener = infoAndEndGameListener;
    }

    /**
     * @return the model view of the game
     */
    public ModelView getModelView() {
        return modelView;
    }

    /**
     * Method to set the model view of the game
     * @param modelView the model view of the game
     */
    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    /**
     * @return the game controller of the game
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Method to set the game controller of the game
     * @param gameController the game controller of the game
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Method that adds a player to the game lobby if it is not full yet and sends a message to the player that wants to join the game lobby as confirmation
     * @param nickname the nickname of the player that wants to join the game lobby
     * @param connection the connection of the player that wants to join the game lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void addPlayerToGame(String nickname, Connection connection) throws IOException {
        try{
            players.put(nickname,connection);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            String content = "\nWelcome to Game Lobby "+ idGameLobby + "! -> waiting for "+ wantedPlayers +" players...\nGame will be starting soon...";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

            if(isFull()){
                System.out.println("è piena la lobby. posso creare il gioco vero");
                createGame();
                System.out.println("creato un game con gameID: "+idGameLobby+"\n");
            }

        } catch (IOException e){
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_JOINING_GAME_LOBBY);
            connection.sendMessageToClient(new Message(header,payload));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true if the game lobby is full, false otherwise
     */
    public boolean isFull(){
        return players.size() == wantedPlayers;
    }

    /**
     * Method to check if a player is active in the game lobby
     * @param nickname the nickname of the player to check
     * @return true if the player is active in the game lobby, false otherwise
     */
    public boolean isPlayerActiveInThisGame(String nickname){
        return players.containsKey(nickname);
    }

    /**
     * Method to check if a player is disconnected in the game lobby
     * @param nickname the nickname of the player to check
     * @return true if the player is disconnected in the game lobby, false otherwise
     */
    public boolean containsPlayerDisconnectedInThisGame(String nickname) {
        return playersDisconnected.contains(nickname);
    }

    /**
     * Method that sends a message to the game controller to handle the turn of the player
     * @param message the message received from the player
     */
    public synchronized void handleTurn(Message message) throws IOException {
        for(String playersD:playersDisconnected){
            System.out.println("DISCONNESSO "+playersD);
        }
        if(players.size()==1 && messageEndGame==null ){
            gameController.endGame();
        }
        else if(messageEndGame==null){
            gameController.receiveMessageFromClient(message);
        }else {
            int index=gameController.getModel().getIntByNickname(message.getHeader().getNickname());
            gameController.getActivePlayers()[index]=false;
            System.out.println(message.getHeader().getNickname()+"SETTATO "+gameController.getActivePlayers()[index]);
            boolean allFalse = true;
            for (boolean value : gameController.getActivePlayers()) {
                if (value) {
                    allFalse = false;
                    break;
                }
            }
            if(allFalse){
                infoAndEndGameListener.endGame();
            }
        }
    }

    /**
     * Method that sends a message to the game controller to handle the error of the player
     * @param message the message received from the player
     * @throws IOException if there are problems with the connection
     */
    public synchronized void handleErrorFromClient(Message message) throws IOException {
        if(messageEndGame==null){
            infoAndEndGameListener.fireEvent(TurnPhase.ALL_INFO,message.getHeader().getNickname(),modelView);
            System.out.println("SONO NELLA GAME LOBBY l'utente ha segnalato un error:"+message.getHeader().getNickname());
        }else{
            sendMessageToSpecificPlayer(messageEndGame,message.getHeader().getNickname());
            System.out.println("END GAME l'utente ha segnalato un error:"+message.getHeader().getNickname());
        }
    }

    /**
     * Method to change the player from disconnected to active in the game lobby
     * and sends a message to all the players in the game lobby to notify the reconnection of the player
     * @param nickname the nickname of the player to change
     * @param connection the connection of the player to change
     * @throws IOException if there are problems with the connection
     */
    public synchronized void changePlayerInActive(String nickname, Connection connection) throws IOException {
        players.put(nickname,connection);
        playersDisconnected.remove(nickname);

        if(messageEndGame==null){
            gameController.reconnectionPlayer(nickname);
            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload=new MessagePayload(KeyConnectionPayload.RECONNECTION);
            String content=nickname+" reconnected to Game Lobby "+ idGameLobby + "!";
            payload.put(Data.CONTENT,content);
            payload.put(Data.WHO_CHANGE,nickname);
            Message message = new Message(header,payload);
            sendMessageToAllPlayers(message);
        }else sendMessageToSpecificPlayer(messageEndGame,nickname);

        System.out.println("Sono la GameLobby "+ idGameLobby+" ho cambiato il giocatore "+nickname+" in attivo");
    }

    /**
     * Method to change the player from active to disconnected in the game lobby
     * and sends a message to all the players in the game lobby to notify the disconnection of the player
     * @param nickname the nickname of the player to change
     * @throws IOException if there are problems with the connection
     */
    public synchronized void changePlayerInDisconnected(String nickname) throws IOException {
        System.out.println("Sono la GameLobby "+ idGameLobby+" ho cambiato il giocatore "+nickname+" in disconnesso");
        playersDisconnected.add(nickname);
        if(messageEndGame==null){
            players.remove(nickname);
            gameController.disconnectionPlayer(nickname);
            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            String content = "Player "+nickname+" disconnected to Game Lobby "+ idGameLobby + "!";
            payload.put(Data.CONTENT,content);
            Message message = new Message(header,payload);
            sendMessageToAllPlayers(message);
            System.out.println(content);
        }
    }

    /**
     * Method to send a message to all the active players in the game lobby
     * @param message the message to send
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendMessageToAllPlayers(Message message) throws IOException {
        for (Connection connection : players.values()) {
            connection.sendMessageToClient(message);
        }
    }

    /**
     * Method to send a message to all the active players in the game lobby except one
     * @param message the message to send
     * @param nickname the nickname of the player to exclude
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendMessageToAllPlayersExceptOne(Message message, String nickname) throws IOException {
        for (String player : players.keySet()) {
            if (!player.equals(nickname)) {
                players.get(player).sendMessageToClient(message);
            }
        }
    }

    /**
     * Method to send a message to all the active players in the game lobby except some
     * @param message the message to send
     * @param nicknames the nicknames of the players to exclude
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendMessageToAllPlayersExceptSome(Message message, String[] nicknames) throws IOException {
        for (String player : players.keySet()) {
            boolean found = false;
            for (String nickname : nicknames) {
                if (player.equals(nickname)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                players.get(player).sendMessageToClient(message);
            }
        }
    }

    /**
     * Method to send a message to a specific player in the game lobby
     * @param message the message to send
     * @param nickname the nickname of the player to send the message
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendMessageToSpecificPlayer(Message message, String nickname) throws IOException {
        players.get(nickname).sendMessageToClient(message);
    }

    public Message getMessageEndGame() {
        return messageEndGame;
    }

    public void setMessageEndGame(Message messageEndGame) {
        this.messageEndGame = messageEndGame;
    }
}
