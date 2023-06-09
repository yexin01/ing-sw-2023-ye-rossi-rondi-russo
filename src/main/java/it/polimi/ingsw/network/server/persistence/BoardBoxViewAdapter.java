package it.polimi.ingsw.network.server.persistence;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;

import java.io.IOException;

public class BoardBoxViewAdapter extends TypeAdapter<BoardBoxView> {
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemTileView.class, new ItemTileViewAdapter())
            .create();

    /*
    public BoardBoxViewAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ItemTileView.class, new ItemTileViewAdapter());
        gson = gsonBuilder.create();
    }

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
