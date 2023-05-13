package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.ModelView;

import java.io.IOException;
import java.util.ArrayList;
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
    public synchronized void createGame() throws Exception {
        ArrayList<String> playersGame=new ArrayList<>();
        for (String player : players.keySet()) {
            playersGame.add(player);
        }
        this.gameController=new GameController(this,playersGame);
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

    //TODO: quando è isFull è vera allora crea il Game e tutto
    public boolean isFull(){
        return players.size() == wantedPlayers;
    }

    public boolean isPlayerActiveInThisGame(String nickname){
        return players.containsKey(nickname);
    }

    public boolean containsPlayerDisconnectedInThisGame(String nickname) {
        return playersDisconnected.contains(nickname);
    }
    public synchronized void handleTurn(Message message){
        if(message.getHeader().getMessageType().equals(MessageType.DATA)){
            gameController.receiveMessageFromClient(message);
        }

    }

    public synchronized void handleErrorFromClient(Message message){
        if(message.getHeader().getMessageType().equals(MessageType.ERROR)){
            gameController.receiveMessageFromClient(message);
        }
        //TODO: rimandare tipo l'ultimo messaggio che ha mandato il giocatore o azione ecc
    }

    public synchronized void changePlayerInActive(String nickname, Connection connection) throws IOException {
        players.put(nickname,connection);

        MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
        MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
        String content = "Player "+nickname+" reconnected to Game Lobby "+ idGameLobby + "!";
        payload.put(Data.CONTENT,content);
        Message message = new Message(header,payload);
        sendMessageToAllPlayersExceptOne(message, nickname);

        //TODO questo si potrebbe anche cancellare, al game controller interessa solo la disconessione
        //sse il gameController punta alle liste del gameLobby, deve solo stampare il broadcast e non fare altro
        gameController.reconnectionPlayer(nickname);

        //TODO PER RESILIENZA: bisogna mandarli tutti i dati del game in corso a cui si sta ricollegando

        playersDisconnected.remove(nickname);

        System.out.println("Sono la GameLobby ho cambiato il giocatore "+nickname+" in attivo");
    }

    public synchronized void changePlayerInDisconnected(String nickname) throws IOException {
        playersDisconnected.add(nickname);
        players.remove(nickname);
        if(gameController!=null){
            gameController.disconnectionPlayer(nickname);
        }
        System.out.println("Sono la GameLobby ho cambiato il giocatore "+nickname+" in disconnesso");
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

    public synchronized void sendMessageToAllPlayers(Message message) throws IOException {
        for (Connection connection : players.values()) {
            connection.sendMessageToClient(message);
        }
    }

    public synchronized void sendMessageToAllPlayersExceptOne(Message message, String nickname) throws IOException {
        for (String player : players.keySet()) {
            if (!player.equals(nickname)) {
                players.get(player).sendMessageToClient(message);
            }
        }
    }

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

    public synchronized void sendMessageToSpecificPlayer(Message message, String nickname) throws IOException {
        players.get(nickname).sendMessageToClient(message);
    }

}
