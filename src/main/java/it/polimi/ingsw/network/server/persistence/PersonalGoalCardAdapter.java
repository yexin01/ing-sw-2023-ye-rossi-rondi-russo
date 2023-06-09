package it.polimi.ingsw.network.server.persistence;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Type;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalGoalCardAdapter extends TypeAdapter<PersonalGoalCard> {

    @Override
    public void write(JsonWriter out, PersonalGoalCard personalGoalCard) throws IOException {
        out.beginObject();
        out.name("idPersonal").value(personalGoalCard.getIdPersonal());
        out.name("cells");
        out.beginArray();
        for (PersonalGoalBox box : personalGoalCard.getCells()) {
            out.beginObject();
            out.name("x").value(box.getX());
            out.name("y").value(box.getY());
            out.name("type").value(box.getTypePersonal().name());
            out.endObject();
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public PersonalGoalCard read(JsonReader in) throws IOException {
        int idPersonal = 0;
        ArrayList<PersonalGoalBox> cells = new ArrayList<>();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("idPersonal")) {
                idPersonal = in.nextInt();
            } else if (name.equals("cells")) {
                in.beginArray();
                while (in.hasNext()) {
                    in.beginObject();
                    int x = 0;
                    int y = 0;
                    Type type = null;
                    while (in.hasNext()) {
                        String boxName = in.nextName();
                        if (boxName.equals("x")) {
                            x = in.nextInt();
                        } else if (boxName.equals("y")) {
                            y = in.nextInt();
                        } else if (boxName.equals("type")) {
                            String typeName = in.nextString();
                            type = Type.valueOf(typeName);
                        } else {
                            in.skipValue();
                        }
                    }
                    in.endObject();
                    PersonalGoalBox box = new PersonalGoalBox(type, x, y);
                    cells.add(box);
                }
                in.endArray();
            } else {
                in.skipValue();
            }
        }
        in.endObject();

        return new PersonalGoalCard(idPersonal, cells);
    }
}

