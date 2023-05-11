package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer2;
import it.polimi.ingsw.messages.MessagePayload2;
import it.polimi.ingsw.messages.ServerMessageHeader2;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

// la GlobalLobby del server che contiene tutte le partite in corso o in attesa di creare i game
// e i giocatori che si sono appena correttamente collegati (con username unico) e ancora non hanno deciso che fare

// prima passa per la waitingPlayersWithNoGame
// poi ha 3 opzioni: playerCreatesGameLobby, playerJoinsGameLobbyId, playerJoinsFirstFreeSpotInRandomGame
// o dal server nella knownPlayerLogin: reconnectPlayerToGameLobby

public class GlobalLobby {

    private ConcurrentHashMap<Integer, GameLobby> gameLobbies; //mappa di tutte le partite in corso, GameLobby è riferito ad 1 game in corso
    private ConcurrentHashMap<String, Connection> waitingPlayersWithNoGame; //mappa di tutti i giocatori in attesa di una partita

    private final static int MAX_PLAYERS = 4;
    private final static int MIN_PLAYERS = 2;

    public GlobalLobby() {
        this.gameLobbies = new ConcurrentHashMap<>();
        this.waitingPlayersWithNoGame = new ConcurrentHashMap<>();
    }

    public synchronized void addPlayerToWaiting(String nickname, Connection connection) throws IOException {
        try{
            waitingPlayersWithNoGame.put(nickname,connection);
            ServerMessageHeader2 header = new ServerMessageHeader2(EventType.JOIN_GLOBAL_LOBBY, nickname);
            MessagePayload2 payload = new MessagePayload2("Welcome to the Global Lobby!");
            connection.sendMessageToClient(new MessageFromServer2(header,payload));
            System.out.println("Player "+nickname+" added to the waiting list in global lobby!");
        } catch (IOException e){
            ServerMessageHeader2 header = new ServerMessageHeader2(EventType.ERR_JOIN_GLOBAL_LOBBY, nickname);
            MessagePayload2 payload = new MessagePayload2("ERROR: Failed in joining the Global Lobby!");
            connection.sendMessageToClient(new MessageFromServer2(header,payload));
        }
    }

    public synchronized void playerCreatesGameLobby(int wantedPlayers, String nickname,Connection connection) throws IOException {
        int gameId = getFirstFreeGameLobbyId();
        GameLobby gameLobby = new GameLobby(gameId, wantedPlayers);
        gameLobbies.put(gameId, gameLobby);

        waitingPlayersWithNoGame.remove(nickname);

        //aggiungi il giocatore alla lobby di gioco appena creata
        gameLobby.addPlayerToGame(nickname, connection);

        ServerMessageHeader2 header = new ServerMessageHeader2(EventType.CREATED_GAME_LOBBY, nickname);
        MessagePayload2 payload = new MessagePayload2("Game Lobby created!");
        connection.sendMessageToClient(new MessageFromServer2(header, payload));
    }

    public synchronized void playerJoinsGameLobbyId(int gameId, String nickname, Connection connection) throws IOException {
        GameLobby gameLobby = findGameLobbyById(gameId);
        if(!gameLobby.isFull()){
            gameLobby.addPlayerToGame(nickname,connection);

            waitingPlayersWithNoGame.remove(nickname);
        } else {
            ServerMessageHeader2 header = new ServerMessageHeader2(EventType.ERR_GAME_FULL, nickname);
            MessagePayload2 payload = new MessagePayload2("ERROR: Failed in joining this game lobby because is full!\n");
            connection.sendMessageToClient(new MessageFromServer2(header,payload));
        }
    }

    public synchronized void playerJoinsFirstFreeSpotInRandomGame(String nickname, Connection connection) throws IOException {
        boolean done = false;
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (!gameLobby.isFull()) {
                gameLobby.addPlayerToGame(nickname, connection);
                done = true;
            }
        }
        if(!done){
            ServerMessageHeader2 header = new ServerMessageHeader2(EventType.ERR_NO_FREE_SPOTS, nickname);
            MessagePayload2 payload = new MessagePayload2("ERROR: Failed in joining a random game lobby because all games are full!\nCreating a new Game Lobby for min num players...\n");
            connection.sendMessageToClient(new MessageFromServer2(header,payload));

            // Se non c'è spazio in nessuna partita esistente, crea una nuova partita con min_players
            GameLobby gameLobby = new GameLobby(getFirstFreeGameLobbyId(), MIN_PLAYERS);
            gameLobbies.put(gameLobby.getIdGameLobby(), gameLobby);
            gameLobby.addPlayerToGame(nickname, connection);
        }
        waitingPlayersWithNoGame.remove(nickname);
    }

    public synchronized void reconnectPlayerToGameLobby(String nickname, Connection connection) throws IOException {
        for (GameLobby gameLobby : gameLobbies.values()) {
            if (gameLobby.containsPlayerDisconnectedInThisGame(nickname)) {
                gameLobby.addPlayerToGame(nickname, connection);

                waitingPlayersWithNoGame.remove(nickname);

                ServerMessageHeader2 header = new ServerMessageHeader2(EventType.RECONNECT_TO_GAME_LOBBY, nickname);
                MessagePayload2 payload = new MessagePayload2("You have been reconnected to your previous game lobby!");
                connection.sendMessageToClient(new MessageFromServer2(header, payload));
                return;
            }
        }
        ServerMessageHeader2 header = new ServerMessageHeader2(EventType.ERR_RECONNECT_TO_GAME_LOBBY, nickname);
        MessagePayload2 payload = new MessagePayload2("ERROR: Failed to reconnect to previous game lobby!");
        connection.sendMessageToClient(new MessageFromServer2(header, payload));
    }

    public synchronized void disconnectPlayerFromGlobalLobby(String nickname) {
        //o lo trova nella waitingPlayersWithNoGame o lo trova in una gameLobby
        if(waitingPlayersWithNoGame.containsKey(nickname)){
            waitingPlayersWithNoGame.remove(nickname);
        } else {
            for (GameLobby gameLobby : gameLobbies.values()) {
                if (gameLobby.containsPlayerInThisGame(nickname)) {
                    gameLobby.changePlayerInDisconnected(nickname);
                    return;
                }
            }
        }
    }

    private int getFirstFreeGameLobbyId() {
        int lastGameId = 0;
        for (Integer gameId : gameLobbies.keySet()) {
            if (gameId - lastGameId > 1) {
                // c'è uno spazio tra gli ID, quindi restituisci il valore del primo posto libero
                return lastGameId + 1;
            }
            lastGameId = gameId;
        }
        // non ci sono spazi tra gli ID, quindi restituisci l'ID successivo all'ultimo GameLobby
        return lastGameId + 1;
    }

    private GameLobby findGameLobbyById(int gameId) {
        return gameLobbies.get(gameId);
    }

}
