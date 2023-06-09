package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import java.io.IOException;

public class PlayerPointsViewAdapter extends TypeAdapter<PlayerPointsView> {

    @Override
    public void write(JsonWriter out, PlayerPointsView playerPointsView) throws IOException {
        out.beginObject();
        out.name("commonGoalPoints");
        out.beginArray();
        for (int point : playerPointsView.getCommonGoalPoints()) {
            out.value(point);
        }
        out.endArray();
        out.name("adjacentPoints").value(playerPointsView.getAdjacentPoints());
        out.name("nickname").value(playerPointsView.getNickname());
        out.endObject();
    }

    @Override
    public PlayerPointsView read(JsonReader in) throws IOException {
        int[] commonGoalPoints = null;
        int adjacentPoints = 0;
        String nickname = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("commonGoalPoints")) {
                in.beginArray();
                int size = 0;
                while (in.hasNext()) {
                    in.nextInt(); // Assume integer values
                    size++;
                }
                in.endArray();

                // Reset the stream position to read the values again
                in.beginArray();
                commonGoalPoints = new int[size];
                for (int i = 0; i < size; i++) {
                    commonGoalPoints[i] = in.nextInt();
                }
                in.endArray();
            } else if (name.equals("adjacentPoints")) {
                adjacentPoints = in.nextInt();
            } else if (name.equals("nickname")) {
                nickname = in.nextString();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return new PlayerPointsView(commonGoalPoints, adjacentPoints, nickname);
    }
}

