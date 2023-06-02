package it.polimi.ingsw.network.server;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that represents the Global Lobby of the server that contains all the games in progress or waiting to be created
 * and the players that have just successfully connected (with unique username) and have not yet decided what to do.
 * The player can create a new game, join a game with a specific id (known by the client himself), join the first free spot in a random game
 * or quit the server for when he doesn't want to play any other game in this server application.
 *
 * The server can also reconnect a player to a game lobby if the player has disconnected from the server but not from the game, and the game is still in progress.
 * if so, the server will reconnect the player to the game lobby and the game will continue from where it is,
 * it won't ask but is automatically reconnected to the game lobby when the player reconnects to the server with the same username.
 */
public class GlobalLobby {
    private ConcurrentHashMap<Integer, GameLobby> gameLobbies; //maps all the games in progress or waiting to be created, GameLobby refers to 1 game in progress
    private ConcurrentHashMap<String, Connection> waitingPlayersWithNoGame; //map of all the players waiting to be added to a game lobby


    /**
     * Constructor of the class GlobalLobby that creates a new Global Lobby for the server
     */
    public GlobalLobby() {
        this.gameLobbies = new ConcurrentHashMap<>();
        this.waitingPlayersWithNoGame = new ConcurrentHashMap<>();
    }


    /**
     * Method that adds a player to the waiting list of the global lobby of the server
     * @param nickname the nickname of the player that wants to be added to the waiting list of the global lobby of the server
     * @param connection the connection of the player that wants to be added to the waiting list of the global lobby of the server
     * @throws IOException if there are problems with the connection
     */
    public synchronized void addPlayerToWaiting(String nickname, Connection connection,boolean afterEndGame) throws IOException {
        waitingPlayersWithNoGame.put(nickname,connection);
        System.out.println("Player "+nickname+" added to the waiting list in server's global lobby!");
        if(!afterEndGame){
            MessageHeader header = new MessageHeader(MessageType.LOBBY, nickname);
            MessagePayload payload = new MessagePayload(KeyLobbyPayload.GLOBAL_LOBBY_DECISION);
            connection.sendMessageToClient(new Message(header,payload));
        }
    }

    /**
     * Method that creates a new game lobby for the player that wants to create a new game
     * with the first free game lobby id available and adds the player to the game lobby
     * @param wantedPlayers the number of players that the player wants to play with (valid number of players)
     * @param nickname the nickname of the player that wants to create a new game lobby
     * @param connection the connection of the player that wants to create a new game lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void playerCreatesGameLobby(int wantedPlayers, String nickname,Connection connection) throws IOException {
        int gameId = getFirstFreeGameLobbyId();
        GameLobby gameLobby = new GameLobby(gameId, wantedPlayers,this);
        gameLobbies.put(gameId, gameLobby);

        waitingPlayersWithNoGame.remove(nickname);
        gameLobby.addPlayerToGame(nickname, connection);

        System.out.println("\nCreated a new game lobby with id: "+gameId+" and added player "+nickname+" to it!\n");
    }

    /**
     * Method to join a game lobby with a specific id given by the player, if the game lobby exists and it's not full/started,
     * otherwise it sends an error message to the player
     * @param gameId the id of the game lobby that the player wants to join
     * @param nickname the nickname of the player that wants to join a game lobby
     * @param connection the connection of the player that wants to join a game lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void playerJoinsGameLobbyId(int gameId, String nickname, Connection connection) throws IOException {
        GameLobby gameLobby = findGameLobbyById(gameId);

        if(gameLobby == null){
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            connection.sendMessageToClient(new Message(header,payload));

        } else if(gameLobby.isFull()){
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_FULL);
            connection.sendMessageToClient(new Message(header,payload));

        } else {
            waitingPlayersWithNoGame.remove(nickname);
            gameLobby.addPlayerToGame(nickname,connection);
        }
    }

    /**
     * Method to join a random game lobby, if there is one available with a free spot,
     * otherwise it creates a new game lobby with minimum number of players and adds the player to it waiting
     * @param nickname the nickname of the player that wants to join a random game lobby
     * @param connection the connection of the player that wants to join a random game lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void playerJoinsFirstFreeSpotInRandomGame(String nickname,int minPlayers, Connection connection) throws IOException {
        boolean done = false;
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (!gameLobby.isFull() && !done && gameLobby.getGameController()==null) {
                gameLobby.addPlayerToGame(nickname, connection);
                done = true;
            }
        }
        if(!done){ // if there is no free spot in any game lobby, create a new game lobby with minimum number of players

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            payload.put(Data.CONTENT, ErrorType.ERR_NO_FREE_SPOTS.getErrorMessage());
            connection.sendMessageToClient(new Message(header,payload));

            GameLobby gameLobby = new GameLobby(getFirstFreeGameLobbyId(), minPlayers,this);
            gameLobbies.put(gameLobby.getIdGameLobby(), gameLobby);
            gameLobby.addPlayerToGame(nickname, connection);

            System.out.println("\nCreated a new game lobby with id: "+gameLobby.getIdGameLobby()+" and added player "+nickname+" to it!\n");
        }
        waitingPlayersWithNoGame.remove(nickname);
    }

    /**
     * Method to reconnect a player to a game lobby in which he was disconnected in the previous game
     * @param nickname the nickname of the player that wants to reconnect to a game lobby
     * @param connection the connection of the player that wants to reconnect to a game lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void reconnectPlayerToGameLobby(String nickname, Connection connection) throws IOException {
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (gameLobby.containsPlayerDisconnectedInThisGame(nickname)) {
                gameLobby.changePlayerInActive(nickname, connection);
                return;
            }
        }
    }

    /**
     * Method that checks if a player is disconnected in any game lobby
     * @param nickname the nickname of the player that wants to check if he is disconnected in any game lobby
     * @return true if the player is disconnected in any game lobby, false otherwise
     */
    public synchronized boolean isPlayerDisconnectedInAnyGameLobby(String nickname) {
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (gameLobby.containsPlayerDisconnectedInThisGame(nickname)) {
                System.out.println("Found Player "+nickname+" is disconnected in game lobby "+gameLobby.getIdGameLobby());
                return true;
            }
        }
        return false;
    }

    /**
     * Method that checks if a player is active in any game lobby (not disconnected)
     * @param nickname the nickname of the player that wants to check if he is active in any game lobby
     * @return true if the player is active in any game lobby, false otherwise
     */
    public synchronized boolean isPlayerActiveInAnyGameLobby(String nickname) {
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (gameLobby.isPlayerActiveInThisGame(nickname)) {
                System.out.println("Found Player "+nickname+" is active in game lobby "+gameLobby.getIdGameLobby());
                return true;
            }
        }
        return false;
    }

    /**
     * Method that disconnects a player from the global lobby, removing him from the waitingPlayersWithNoGame map or from the game lobby he was in
     * @param nickname the nickname of the player that wants to disconnect from the global lobby
     * @throws IOException if there are problems with the connection
     */
    public synchronized void disconnectPlayerFromGlobalLobby(String nickname) throws IOException {
        if(waitingPlayersWithNoGame.containsKey(nickname)){
            waitingPlayersWithNoGame.remove(nickname);
        } else if (isPlayerActiveInAnyGameLobby(nickname)){
            for (GameLobby gameLobby : gameLobbies.values()) {
                if (gameLobby.isPlayerActiveInThisGame(nickname)) {
                    gameLobby.changePlayerInDisconnected(nickname);
                    return;
                }
            }
        } else {
            System.out.println("Player "+nickname+" is not in the global lobby!");
        }
    }

    /**
     * Method that returns the first free game lobby id (the first id that is not used by any game lobby).
     * if there is a space between the ids of the game lobbies, it returns the first free id,
     * if there are no free game lobby ids, it returns the id of the last game lobby + 1.
     * @return the first free game lobby id
     */
    private int getFirstFreeGameLobbyId() {
        int lastGameId = 0;
        for (Integer gameId : gameLobbies.keySet()) {
            if (gameId - lastGameId > 1) {
                return lastGameId + 1;
            }
            lastGameId = gameId;
        }
        return lastGameId + 1;
    }

    /**
     * Method that finds a game lobby by its id
     * @param gameId the id of the game lobby that we want to find
     * @return the game lobby with the given id
     */
    private GameLobby findGameLobbyById(int gameId) {
        return gameLobbies.get(gameId);
    }

    /**
     * Method that ends a game lobby, removing it from the global lobby and moving all the players in the waiting list of the global lobby
     * @param gameId the id of the game lobby that we want to end
     * @throws IOException if there are problems with the connection
     */
    public synchronized void endGameLobbyFromGlobalLobby(int gameId) throws IOException {
        GameLobby gameLobby = findGameLobbyById(gameId);
        ConcurrentHashMap<String, Connection> players = gameLobby.getPlayersInGameLobby();
        for (String nickname : players.keySet()) {
            addPlayerToWaiting(nickname,players.get(nickname),true);
        }
        System.out.println("All players in game lobby "+gameId+" have been moved to waitingList in the global lobby!");
        gameLobbies.remove(gameId);
        System.out.println("Game lobby "+gameId+" has ended and been removed from the global lobby!");
    }

    /**
     * Method that finds a game lobby by the nickname of a player that is in that game lobby
     * @param nickname the nickname of the player that is in the game lobby that we want to find
     * @return the game lobby with the given player in it
     */
    public GameLobby findGameLobbyByNickname(String nickname) {
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (gameLobby.isPlayerActiveInThisGame(nickname)) {
                return gameLobby;
            }
        }
        return null;
    }

}
