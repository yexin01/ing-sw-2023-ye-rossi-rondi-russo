package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.ModelView;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// La gameLobby della singola partita.
// Il client viene inserito appena decide di creare questa partita
// Ci mette anche coloro che vogliono partecipare ad una partita in attesa di utenti

public class GameLobby {
    private final int idGameLobby; //id della partita
    private final int wantedPlayers;
    private GameController gameController;
    private ModelView modelView;

    private ConcurrentHashMap<String, Connection> players; //mappa di tutti i giocatori attivi in partita
    private CopyOnWriteArrayList<String> playersDisconnected; //mappa di tutti i giocatori disconnessi della partita

    //TODO: da aggiungere il controller della partita e tutti i listeners o quello che serve per gestire la partita
    //io per ora metto solo quello che mi serve per gestire la mappa
    //lavoro principalmente sui nickname dei giocatori

    public GameLobby(int idGameLobby, int wantedPlayers){
        this.idGameLobby= idGameLobby; //gli è dato in input il primo idGameLobby disponibile
        this.wantedPlayers = wantedPlayers;

        players = new ConcurrentHashMap<>();
        playersDisconnected = new CopyOnWriteArrayList<>();
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

    public CopyOnWriteArrayList<String> getPlayersDisconnectedInGameLobby(){
        return playersDisconnected;
    }

    public synchronized void addPlayerToGame(String nickname, Connection connection) throws IOException {
        try{
            players.put(nickname,connection);

            MessageHeader header = new MessageHeader(MessageType.LOBBY, nickname);
            MessagePayload payload = new MessagePayload(KeyLobbyPayload.JOINED_GAME_LOBBY);
            String content = "\nWelcome to Game Lobby "+ idGameLobby + "! -> waiting for "+ wantedPlayers +" players...\nGame will be starting soon...";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

        } catch (IOException e){
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_JOINING_GAME_LOBBY);
            connection.sendMessageToClient(new Message(header,payload));
        }
    }

    //TODO: quando è isFull è vera allora crea il Game e tutto
    public boolean isFull(){
        return players.size() == wantedPlayers;
    }

    public boolean containsPlayerInThisGame(String nickname){
        return players.containsKey(nickname);
    }

    public boolean containsPlayerDisconnectedInThisGame(String nickname) {
        return playersDisconnected.contains(nickname);
    }

    public synchronized void changePlayerInActive(String nickname, Connection connection){
        players.put(nickname,connection);

        //TODO PER RESILIENZA: bisogna mandarli tutti i dati del game in corso a cui si sta ricollegando

        playersDisconnected.remove(nickname);
    }

    public void changePlayerInDisconnected(String nickname){
        playersDisconnected.add(nickname);
        players.remove(nickname);
    }

    public ModelView getModelView() {
        return modelView;
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
