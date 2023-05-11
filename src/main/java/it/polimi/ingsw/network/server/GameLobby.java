package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageHeader;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

// La gameLobby della singola partita.
// Il client viene inserito appena decide di creare questa partita
// Ci mette anche coloro che vogliono partecipare ad una partita in attesa di utenti

public class GameLobby {
    private final int idGameLobby; //id della partita
    private final int wantedPlayers;
    private GameController gameController;

    private ConcurrentHashMap<String, Connection> players; //mappa di tutti i giocatori attivi in partita
    private ConcurrentHashMap<String, Connection> playersDisconnected; //mappa di tutti i giocatori disconnessi

    //TODO: da aggiungere il controller della partita e tutti i listeners o quello che serve per gestire la partita
    //io per ora metto solo quello che mi serve per gestire la mappa
    //lavoro principalmente sui nickname dei giocatori

    public GameLobby(int idGameLobby, int wantedPlayers){


        this.idGameLobby= idGameLobby; //gli è dato in input il primo idGameLobby disponibile
        this.wantedPlayers = wantedPlayers;


        players = new ConcurrentHashMap<>();
        playersDisconnected = new ConcurrentHashMap<>();
    }
    public void createGame(){

        //this.gameController=new GameController();


    }


    public int getIdGameLobby(){
        return idGameLobby;
    }

    public int getWantedPlayers(){
        return wantedPlayers;
    }

    public ConcurrentHashMap<String, Connection> getPlayersInGameLobby(){
        return players;
    }

    public ConcurrentHashMap<String, Connection> getPlayersDisconnectedInGameLobby(){
        return playersDisconnected;
    }

    public void addPlayerToGame(String nickname, Connection connection) throws IOException {
        try{
            players.put(nickname,connection);
            ServerMessageHeader header = new ServerMessageHeader(EventType.JOINED_GAME_LOBBY, nickname);
            MessagePayload payload = new MessagePayload("\nWelcome to Game Lobby "+ idGameLobby + "! -> waiting for "+ wantedPlayers +" players...\nGame will be starting soon...");
            connection.sendMessageToClient(new MessageFromServer(header,payload));
        } catch (IOException e){
            ServerMessageHeader header = new ServerMessageHeader(EventType.ERR_JOINING_GAME_LOBBY, nickname);
            MessagePayload payload = new MessagePayload("\nERROR: Failed in joining the Game Lobby!\n");
            connection.sendMessageToClient(new MessageFromServer(header,payload));
        }
    }

    //TODO: quando è isFull è vera allora crea il Game e tutto
    public boolean isFull(){
        return players.size() == wantedPlayers;
    }

    public boolean containsPlayerInThisGame(String nickname){
        return players.containsKey(nickname);
    }

    public boolean containsPlayerDisconnectedInThisGame(String nickname){
        return playersDisconnected.containsKey(nickname);
    }

    public void changePlayerInActive(String nickname){
        players.put(nickname,playersDisconnected.get(nickname));
        playersDisconnected.remove(nickname);
    }

    public void changePlayerInDisconnected(String nickname){
        playersDisconnected.put(nickname,players.get(nickname));
        players.remove(nickname);
    }

}
