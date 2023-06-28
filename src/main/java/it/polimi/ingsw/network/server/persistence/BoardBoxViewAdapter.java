package it.polimi.ingsw.network.server.persistence;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.io.IOException;

/**
 * The BoardBoxViewAdapter class is a Gson TypeAdapter used for serializing and deserializing
 * BoardBoxView objects. It converts an ItemTileView object to JSON and vice versa.
 */
public class BoardBoxViewAdapter extends TypeAdapter<BoardBoxView> {
    private Gson gson;

    /**
     * Constructs a new BoardBoxViewAdapter instance.
     * Initializes the Gson instance with the necessary TypeAdapters.
     */
    public BoardBoxViewAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ItemTileView.class, new ItemTileViewAdapter());
        gson = gsonBuilder.create();
    }


    /**
     * Writes the JSON representation of a BoardBoxView object to the specified JsonWriter.
     * @param out The JsonWriter to write the JSON representation to.
     * @param boardBoxView The BoardBoxView object to be serialized.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, BoardBoxView boardBoxView) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("occupiable", boardBoxView.isOccupiable());
        jsonObject.add("itemTileView", gson.toJsonTree(boardBoxView.getItemTileView()));
        jsonObject.addProperty("x", boardBoxView.getX());
        jsonObject.addProperty("y", boardBoxView.getY());
        jsonObject.addProperty("freeEdges", boardBoxView.getFreeEdges());
        out.jsonValue(jsonObject.toString());
    }

    /**
     * Reads a BoardBoxView object from the specified JsonReader.
     * @param in The JsonReader to read the JSON representation from.
     * @return The deserialized BoardBoxView object.
     * @throws IOException If an I/O error occurs during reading.
     */
    @Override
    public BoardBoxView read(JsonReader in) throws IOException {
        JsonElement jsonElement = JsonParser.parseReader(in);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        boolean occupiable = jsonObject.get("occupiable").getAsBoolean();
        ItemTileView itemTileView = gson.fromJson(jsonObject.get("itemTileView"), ItemTileView.class);
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        int freeEdges = jsonObject.get("freeEdges").getAsInt();
        return new BoardBoxView(occupiable, itemTileView, x, y, freeEdges);
    }
}
