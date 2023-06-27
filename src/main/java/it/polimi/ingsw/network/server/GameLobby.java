package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.InfoAndEndGameListener;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.server.persistence.GameLobbyInfo;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class GameLobby is the class that represents the game lobby of a single game.
 * It contains all the players that are in the game lobby: who created the game and who want to join the game,
 * when it reaches the number of players wanted, it starts the game and creates a new game controller and a new model view for the game itself.
 */
public class GameLobby implements Serializable {
    private final int idGameLobby;
    private final int wantedPlayers;
    private GameController gameController;

    private InfoAndEndGameListener infoAndEndGameListener;
    private Message messageEndGame;
    private ConcurrentHashMap<String, Connection> players; //maps of all the active players of the game
    private CopyOnWriteArrayList<String> playersDisconnected; //maps of all the disconnected players of the game

    private GameLobbyInfo gameLobbyInfo;

    /**
     * Constructor of the class GameLobby that creates a new game lobby with the generated given id and the given number of players wanted
     * @param idGameLobby the id of the game lobby
     * @param wantedPlayers the number of players wanted in the game lobby
     */
    public GameLobby(int idGameLobby, int wantedPlayers, GlobalLobby globalLobby){
        this.idGameLobby = idGameLobby;
        this.wantedPlayers = wantedPlayers;
        players = new ConcurrentHashMap<>();
        infoAndEndGameListener = new InfoAndEndGameListener(this,globalLobby);
        playersDisconnected = new CopyOnWriteArrayList<>();
        gameLobbyInfo = new GameLobbyInfo(idGameLobby,wantedPlayers);
        messageEndGame = null;
    }

    /**
     * Method that creates a new game controller for the game lobby and starts the game itself with the players in the game lobby
     * @throws Exception if something goes wrong in the creation of the game controller
     */
    public synchronized void createGame() throws Exception {
        this.gameController=new GameController();
        this.gameController.createGame(this);
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
     * @return the message end game
     */
    public Message getMessageEndGame() {
        return messageEndGame;
    }

    /**
     * Method to set the message end game
     * @param messageEndGame the message end game
     */
    public void setMessageEndGame(Message messageEndGame) {
        this.messageEndGame = messageEndGame;
    }

    /**
     * @return the players in the game lobby
     */
    public ConcurrentHashMap<String, Connection> getPlayers() {
        return players;
    }

    /**
     * Method to set the players in the game lobby
     * @param players the players in the game lobby
     */
    public void setPlayers(ConcurrentHashMap<String, Connection> players) {
        this.players = players;
    }

    /**
     * @return the players disconnected in the game lobby
     */
    public CopyOnWriteArrayList<String> getPlayersDisconnected() {
        return playersDisconnected;
    }

    /**
     * Method to set the players disconnected in the game lobby
     * @param playersDisconnected the players disconnected in the game lobby
     */
    public void setPlayersDisconnected(CopyOnWriteArrayList<String> playersDisconnected) {
        this.playersDisconnected = playersDisconnected;
    }

    /**
     * @return the game lobby info
     */
    public GameLobbyInfo getGameLobbyInfo() {
        return gameLobbyInfo;
    }

    /**
     * Method to set the game lobby info
     * @param gameLobbyInfo the game lobby info
     */
    public void setGameLobbyInfo(GameLobbyInfo gameLobbyInfo) {
        this.gameLobbyInfo = gameLobbyInfo;
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
            String content = "Welcome to Game Lobby "+ idGameLobby + "! -> waiting for "+ wantedPlayers +" players... Game will be starting soon...";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

            if(isFull()){
                createGame();
                System.out.println("The game lobby is now full, created a game with gameID: "+idGameLobby+"\n");
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
     * @return true, if the game lobby is full, false otherwise
     */
    public boolean isFull(){
        return players.size() == wantedPlayers || getGameController()!=null;
    }

    /**
     * Method to check if a player is active in the game lobby
     * @param nickname the nickname of the player to check
     * @return true, if the player is active in the game lobby, false otherwise
     */
    public boolean isPlayerActiveInThisGame(String nickname){
        return players.containsKey(nickname);
    }

    /**
     * Method to check if a player is disconnected in the game lobby
     * @param nickname the nickname of the player to check
     * @return true, if the player is disconnected in the game lobby, false otherwise
     */
    public boolean containsPlayerDisconnectedInThisGame(String nickname) {
        return playersDisconnected.contains(nickname);
    }

    /**
     * Method that sends a message to the game controller to handle the turn of the player if the game is not ended yet,
     * otherwise it sends a message to the player to notify him that the game is ended
     * @param message the message received from the player
     */
    public synchronized void handleTurn(Message message) throws IOException {
        if(messageEndGame==null){
           gameController.receiveMessageFromClient(message);
        } else {
            int index=gameController.getModel().getModelView().getIntegerValue(message.getHeader().getNickname());
            gameController.getActivePlayers()[index] = false;
            MessageHeader header = new MessageHeader(MessageType.LOBBY,message.getHeader().getNickname());
            MessagePayload payload = new MessagePayload(KeyLobbyPayload.GLOBAL_LOBBY_DECISION);
            sendMessageToSpecificPlayer(new Message(header,payload),message.getHeader().getNickname());
            boolean allFalse = true;
            for (boolean value : gameController.getActivePlayers()) {
                if (value) {
                    allFalse = false;
                    break;
                }
            }
            if(allFalse){ infoAndEndGameListener.endGame(); }
        }
    }

    /**
     * Method that sends a message to the game controller to handle the error of the player if the game is not ended yet,
     * otherwise it sends a message to the player to notify him that the game is ended
     * @param message the message received from the player
     * @throws IOException if there are problems with the connection
     */
    public synchronized void handleErrorFromClient(Message message) throws IOException {
        if(messageEndGame==null){
            if(message.getPayload().getKey().equals(KeyErrorPayload.ERROR_DATA)){
                infoAndEndGameListener.fireEvent(TurnPhase.ALL_INFO,message.getHeader().getNickname(),gameLobbyInfo.getModelView());
            }
        }else{
            sendMessageToSpecificPlayer(messageEndGame,message.getHeader().getNickname());
        }
    }

    /**
     * Method to change the player from disconnected to active in the game lobby:
     * if he is the only player in the game lobby, it sends a message to the player to notify him that he is the only player in the game lobby,
     * if he is not the only player in the game lobby, it sends a message to all the players in the game lobby to notify them that the player reconnected,
     * if the game is ended, it sends a message to the player to notify him that the game is ended
     * @param nickname the nickname of the player to change
     * @param connection the connection of the player to change
     * @throws IOException if there are problems with the connection
     */
    public synchronized void changePlayerInActive(String nickname, Connection connection) throws IOException {
        players.put(nickname,connection);
        playersDisconnected.remove(nickname);
        gameController.reconnectionPlayer(nickname);
        if(checkOnlyPlayer()){
            sendOnlyOnePlayer(nickname);
        }else if(messageEndGame==null && players.size()>2){
            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload=new MessagePayload(KeyConnectionPayload.RECONNECTION);
            String content=nickname+" reconnected to Game Lobby "+ idGameLobby + "!";
            payload.put(Data.CONTENT,content);
            payload.put(Data.WHO_CHANGE,nickname);
            Message message = new Message(header,payload);
            sendMessageToAllPlayers(message);
        }else sendMessageToSpecificPlayer(messageEndGame,nickname);

        System.out.println("GameLobby "+ idGameLobby+" changed "+nickname+" in active");
        int m=0;
        for(String s:playersDisconnected){
            System.out.println(m);
            System.out.println("DISCONNECTED"+s);
            m++;
        }
        for(String s:players.keySet()){
            System.out.println(m);
            System.out.println("RECONNECTED"+s);
            m++;
        }
    }

    /**
     * Method to change the player from active to disconnected in the game lobby
     * if he is the only player in the game lobby, it sends a message to the player to notify him that he is the only player in the game lobby,
     * if he is not the only player in the game lobby, it sends a message to all the players in the game lobby to notify them that the player disconnected,
     * if the game is ended, it sends a message to the player to notify him that the game is ended
     * @param nickname the nickname of the player to change
     * @throws IOException if there are problems with the connection
     */
    public synchronized void changePlayerInDisconnected(String nickname) throws IOException {
        playersDisconnected.add(nickname);
        players.remove(nickname);
        if(gameController!=null){
            gameController.disconnectionPlayer(nickname);
        }
        if(checkOnlyPlayer()){
            sendOnlyOnePlayer(nickname);
        }else if(messageEndGame==null){
            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            String content = "Player "+nickname+" disconnected from Game Lobby "+ idGameLobby + "!";
            payload.put(Data.CONTENT,content);
            Message message = new Message(header,payload);
            sendMessageToAllPlayers(message);
        }
    }

    /**
     * @return true, if there is only one player in the game lobby and the game is not ended, false otherwise
     */
    public boolean checkOnlyPlayer(){
        return players.size()==1 && messageEndGame==null;
    }

    /**
     * Method to send a message error when there is only one player in the game lobby
     * @param nickname the nickname of the player to send the message
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendOnlyOnePlayer(String nickname) throws IOException {
        MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
        MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
        payload.put(Data.ERROR,ErrorType.ONLY_PLAYER);
        Message message = new Message(header,payload);
        sendMessageToAllPlayers(message);
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
     * Method to send a message to a specific player in the game lobby
     * @param message the message to send
     * @param nickname the nickname of the player to send the message
     * @throws IOException if there are problems with the connection
     */
    public synchronized void sendMessageToSpecificPlayer(Message message, String nickname) throws IOException {
        if(!playersDisconnected.contains(nickname))
        System.out.println(players.get(nickname).isConnected());
            players.get(nickname).sendMessageToClient(message);
    }

}
