package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.io.IOException;

public class ItemTileViewAdapter extends TypeAdapter<ItemTileView> {

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

