package it.polimi.ingsw.network.server.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.server.Connection;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import it.polimi.ingsw.network.server.persistence.GameLobbyInfo;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameLobbyInfoAdapter extends TypeAdapter<GameLobbyInfo> {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ModelView.class, new ModelViewAdapter())
            .registerTypeAdapter(Message.class, new MessageAdapter())
            .create();

    @Override
    public void write(JsonWriter out, GameLobbyInfo gameLobbyInfo) throws IOException {
        out.beginObject();
        out.name("idGameLobby").value(gameLobbyInfo.getIdGameLobby());
        out.name("wantedPlayers").value(gameLobbyInfo.getWantedPlayers());
        out.name("modelView").jsonValue(gson.toJson(gameLobbyInfo.getModelView()));
        out.name("messageEndGame").jsonValue(gson.toJson(gameLobbyInfo.getMessageEndGame()));
        out.name("playersDisconnected");
        writePlayersDisconnected(out, gameLobbyInfo.getPlayersDisconnected());
        out.endObject();
    }

    @Override
    public GameLobbyInfo read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        int idGameLobby = 0;
        int wantedPlayers = 0;
        ModelView modelView = null;
        Message messageEndGame = null;
        ConcurrentHashMap<String, Connection> players = null;
        CopyOnWriteArrayList<String> playersDisconnected = null;
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "idGameLobby":
                    idGameLobby = in.nextInt();
                    break;
                case "wantedPlayers":
                    wantedPlayers = in.nextInt();
                    break;
                case "modelView":
                    modelView = gson.fromJson(in.nextString(), ModelView.class);
                    break;
                case "messageEndGame":
                    messageEndGame = gson.fromJson(in.nextString(), Message.class);
                    break;
                case "playersDisconnected":
                    playersDisconnected = readPlayersDisconnected(in);
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        GameLobbyInfo gameLobbyInfo = new GameLobbyInfo(idGameLobby, wantedPlayers);
        gameLobbyInfo.setModelView(modelView);
        gameLobbyInfo.setMessageEndGame(messageEndGame);
        //gameLobbyInfo.setPlayers(players);
        gameLobbyInfo.setPlayersDisconnected(playersDisconnected);

        return gameLobbyInfo;
    }

    private void writePlayersDisconnected(JsonWriter out, CopyOnWriteArrayList<String> playersDisconnected) throws IOException {
        out.beginArray();
        for (String player : playersDisconnected) {
            out.value(player);
        }
        out.endArray();
    }

    private CopyOnWriteArrayList<String> readPlayersDisconnected(JsonReader in) throws IOException {
        CopyOnWriteArrayList<String> playersDisconnected = new CopyOnWriteArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            playersDisconnected.add(in.nextString());
        }
        in.endArray();
        return playersDisconnected;
    }
}
