package it.polimi.ingsw.network.server.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessagePayload;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.server.Connection;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * TypeAdapter for serializing and deserializing GameLobbyInfo objects using Gson.
 * This adapter handles the conversion between JSON representation and GameLobbyInfo instances.
 */
public class GameLobbyInfoAdapter extends TypeAdapter<GameLobbyInfo> {
    private Gson gson;

    /**
     * Constructs a new GameLobbyInfoAdapter instance.
     * Initializes the Gson instance with the necessary TypeAdapters.
     */
    public GameLobbyInfoAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ItemTileView.class, new ItemTileViewAdapter())
                .registerTypeAdapter(ItemTileView[].class, new ItemTileViewAdapter())
                .registerTypeAdapter(ItemTileView[][][].class, new ItemTileViewAdapter())
                .registerTypeAdapter(BoardBoxView[][].class, new BoardBoxViewAdapter())
                .registerTypeAdapter(PlayerPointsView[].class, new PlayerPointsViewAdapter())
                .registerTypeAdapter(PersonalGoalCard[].class, new PersonalGoalCardAdapter())
                .registerTypeAdapter(ModelView.class, new ModelViewAdapter())
                .registerTypeAdapter(MessageHeader.class, new MessageHeaderAdapter())
                .registerTypeAdapter(MessagePayload.class, new MessagePayloadAdapter())
                .registerTypeAdapter(Message.class, new MessageAdapter());
        gson = gsonBuilder.create();
    }

    /**
     * Writes the JSON representation of a GameLobbyInfo object to the specified JsonWriter.
     * @param out The JsonWriter to write the JSON representation to.
     * @param gameLobbyInfo The GameLobbyInfo object to be serialized.
     * @throws IOException If an I/O error occurs during writing.
     */
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

    /**
     * Reads a GameLobbyInfo object from the specified JsonReader.
     * @param in The JsonReader to read the JSON representation from.
     * @return The deserialized GameLobbyInfo object.
     * @throws IOException If an I/O error occurs during reading.
     */
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

    /**
     * Writes the playersDisconnected list to the specified JsonWriter.
     * @param out The JsonWriter to write the playersDisconnected list to.
     * @param playersDisconnected The playersDisconnected list to be serialized.
     * @throws IOException If an I/O error occurs during writing.
     */
    private void writePlayersDisconnected(JsonWriter out, CopyOnWriteArrayList<String> playersDisconnected) throws IOException {
        out.beginArray();
        for (String player : playersDisconnected) {
            out.value(player);
        }
        out.endArray();
    }

    /**
     * Reads the playersDisconnected list from the specified JsonReader.
     * @param in The JsonReader to read the playersDisconnected list from.
     * @return The deserialized playersDisconnected list.
     * @throws IOException If an I/O error occurs during reading.
     */
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

