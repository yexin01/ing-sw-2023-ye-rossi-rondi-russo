package it.polimi.ingsw.server.persistence;

import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SaveGame {

    static {

    }

    public static void saveGame(PersistenceGameInfo gameInfo) throws IOException {

    }

    public static PersistenceGameInfo getPersistenceData(int gameId) throws FileNotFoundException, IOException {

    }

    public static void deletePersistenceData(int gameId) {

    }

    public static void saveGameParticipants(Map<Integer, String[]> gamesParticipants) throws IOException {

    }

    public static Map<Integer, String[]> getParticipants() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(jarPathString + "/backupGames/participants.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        Map<Integer, String[]> participants = new HashMap<>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            int gameId = 0;
            String participantsString = "";
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if (nextName.equals("id")) gameId = jsonReader.nextInt();
                else if (nextName.equals("participants")) participantsString = jsonReader.nextString();
                else jsonReader.skipValue();
            }
            participants.put(gameId, participantsString.split(" "));
            jsonReader.endObject();
        }
        jsonReader.endArray();
        jsonReader.close();
        fileInputStream.close();
        return participants;
    }
}