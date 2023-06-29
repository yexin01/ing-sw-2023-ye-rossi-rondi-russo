package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.io.IOException;

/**
 * The ItemTileViewAdapter class is a Gson TypeAdapter used for serializing and deserializing
 * ItemTileView objects. It converts an ItemTileView object to JSON and vice versa.
 */
public class ItemTileViewAdapter extends TypeAdapter<ItemTileView> {

    /**
     * Writes the JSON representation of an ItemTileView object.
     * @param out The JsonWriter object to write the JSON to.
     * @param itemTileView The ItemTileView object to be converted to JSON.
     * @throws IOException If an I/O error occurs during writing.
     */
    @Override
    public void write(JsonWriter out, ItemTileView itemTileView) throws IOException {
        if (itemTileView == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("type").value(itemTileView.getTypeView().toString());
        out.name("tileID").value(itemTileView.getTileID());
        out.endObject();
    }

    /**
     * Reads a JSON representation and converts it into an ItemTileView object.
     * @param in The JsonReader object to read the JSON from.
     * @return The ItemTileView object created from the JSON representation.
     * @throws IOException If an I/O error occurs during reading.
     */
    @Override
    public ItemTileView read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginObject();
        Type type = null;
        int tileID = 0;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("type")) {
                type = Type.valueOf(in.nextString());
            } else if (name.equals("tileID")) {
                tileID = in.nextInt();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return new ItemTileView(type, tileID);
    }
}

