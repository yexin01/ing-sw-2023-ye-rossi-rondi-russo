package it.polimi.ingsw.network.server.persistence;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;

import java.io.IOException;

/**
 * TypeAdapter for serializing and deserializing ModelView objects using Gson library.
 * This adapter is responsible for converting ModelView objects to JSON and vice versa.
 */
public class ModelViewAdapter extends TypeAdapter<ModelView> {
    private Gson gson;

    /**
     * Constructs a new ModelViewAdapter.
     * Initializes the Gson object and registers custom TypeAdapters for specific types.
     */
    public ModelViewAdapter(){
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ItemTileView.class, new ItemTileViewAdapter())
                .registerTypeAdapter(ItemTileView[].class, new ItemTileViewAdapter())
                .registerTypeAdapter(ItemTileView[][][].class, new ItemTileViewAdapter())
                .registerTypeAdapter(BoardBoxView[][].class, new BoardBoxViewAdapter())
                .registerTypeAdapter(PlayerPointsView[].class, new PlayerPointsViewAdapter())
                .registerTypeAdapter(PersonalGoalCard[].class, new PersonalGoalCardAdapter());


        gson = gsonBuilder.create();
    }

    /**
     * Serializes a ModelView object to JSON.
     *
     * @param out The JsonWriter object to write JSON data to.
     * @param modelView The ModelView object to be serialized.
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Override
    public void write(JsonWriter out, ModelView modelView) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("turnPlayer", modelView.getTurnPlayer());
        //jsonObject.add("activePlayers", gson.toJsonTree(modelView.getActivePlayers()));
        jsonObject.addProperty("MAX_SELECTABLE_TILES", modelView.getMaxSelectableTiles());
        jsonObject.add("commonGoalView", gson.toJsonTree(modelView.getCommonGoalView()));
        jsonObject.add("token", gson.toJsonTree(modelView.getToken()));
        jsonObject.add("personalPoints", gson.toJsonTree(modelView.getPersonalPoints()));
        jsonObject.addProperty("bookshelfFullPoints", modelView.getBookshelfFullPoints());
        jsonObject.add("boardView", gson.toJsonTree(modelView.getBoardView()));

        jsonObject.addProperty("turnPhase", modelView.getTurnPhase().toString());
        jsonObject.add("bookshelfView", gson.toJsonTree(modelView.getBookshelfView()));
        jsonObject.add("playerPoints", gson.toJsonTree(modelView.getPlayerPoints()));
        jsonObject.add("playerPersonalGoal", gson.toJsonTree(modelView.getPlayerPersonalGoal()));
        jsonObject.add("selectedItems", gson.toJsonTree(modelView.getSelectedItems()));
        out.jsonValue(jsonObject.toString());
    }


    /**
     * Deserializes a ModelView object from JSON.
     * @param in The JsonReader object to read JSON data from.
     * @return The deserialized ModelView object.
     * @throws IOException if an I/O error occurs during deserialization.
     */
    @Override
    public ModelView read(JsonReader in) throws IOException {
        System.out.println("ci entra restore model ");
        JsonElement jsonElement = JsonParser.parseReader(in);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int turnPlayer = jsonObject.get("turnPlayer").getAsInt();
        int[][] commonGoalView = gson.fromJson(jsonObject.get("commonGoalView"), int[][].class);
        int[] token = gson.fromJson(jsonObject.get("token"), int[].class);
        int[] personalPoints = gson.fromJson(jsonObject.get("personalPoints"), int[].class);
        String bookshelfFullPoints = jsonObject.get("bookshelfFullPoints").getAsString();
        BoardBoxView[][] boardView = gson.fromJson(jsonObject.get("boardView"), BoardBoxView[][].class);
        TurnPhase turnPhase = TurnPhase.valueOf(jsonObject.get("turnPhase").getAsString());
        ItemTileView[][][] bookshelfView = gson.fromJson(jsonObject.get("bookshelfView"), ItemTileView[][][].class);
        PlayerPointsView[] playerPoints = gson.fromJson(jsonObject.get("playerPoints"), PlayerPointsView[].class);
        PersonalGoalCard[] playerPersonalGoal = gson.fromJson(jsonObject.get("playerPersonalGoal"), PersonalGoalCard[].class);
        ItemTileView[] selectedItems = gson.fromJson(jsonObject.get("selectedItems"), ItemTileView[].class);

        ModelView modelView = new ModelView();
        modelView.setTurnPlayer(turnPlayer);
        modelView.setActivePlayers(new Boolean[playerPoints.length]);
        modelView.setCommonGoalView(commonGoalView);
        modelView.setToken(token);
        modelView.setPersonalPoints(personalPoints);
        modelView.setBookshelfFullPoints(bookshelfFullPoints);
        modelView.setBoardView(boardView);
        modelView.setTurnPhase(turnPhase);
        modelView.setBookshelfView(bookshelfView);
        modelView.setPlayerPoints(playerPoints);
        modelView.setPlayerPersonalGoal(playerPersonalGoal);
        modelView.setSelectedItems(selectedItems);
        return modelView;
    }
}





