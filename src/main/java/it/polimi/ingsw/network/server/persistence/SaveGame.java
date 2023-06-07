package it.polimi.ingsw.network.server.persistence;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import it.polimi.ingsw.network.server.Server;

//import org.apache.maven.settings.Server;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveGame {
    private static final String jarPathString;
    private static final GsonBuilder gsonBuilder; //not used

    static {
        File jarPath;
        try {
            jarPath = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        jarPathString = jarPath.getParentFile().getAbsolutePath();
        new File(jarPathString + "/persistence").mkdir();

        gsonBuilder = new GsonBuilder(); //not used
        //register adapter...
        //gsonBuilder.registerTypeAdapter(ModelView.class, new ModelViewAdapter());
    }

    public static void serializeGameLobby(GameLobbyInfo gameLobbyInfo) throws IOException {
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameLobbyInfo.getIdGameLobby();
        String tempFileName = fileName + ".tmp";
        try (FileOutputStream fileOut = new FileOutputStream(tempFileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(gameLobbyInfo);
        }
        File file = new File(fileName);
        File tempFile = new File(tempFileName);
        boolean renamed = tempFile.renameTo(file);
        if (!renamed) {
            throw new IOException("Failed to rename temp file to original file");
        }
    }

    public static GameLobbyInfo deserializeGameLobby(int gameId) throws IOException, ClassNotFoundException {
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameId;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (GameLobbyInfo) objectIn.readObject();
        }
    }

    public static void deleteGameLobbyFile(GameLobbyInfo gameLobbyInfo) {
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameLobbyInfo.getIdGameLobby();
        File file = new File(fileName);
        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("File " + fileName + " deleted successfully.");
        } else {
            System.out.println("Failed to delete file " + fileName);
        }
    }

    //TODO: prima ricostruisci la gamelobby dalla gamelobbyinfo e poi aggiungila alla globallobby
    public static void loadGameLobbies(GlobalLobby globalLobby, GameRules gamerules) {
        File persistenceFolder = new File(jarPathString + "/persistence");
        File[] files = persistenceFolder.listFiles();
        List<GameLobby> gameLobbies = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("Game_With_ID_")) {
                    try {
                        int id = extractIdFromFile(file.getName());
                        GameLobby gameLobby = deserializeGameLobby(id).restoreGameLobby(globalLobby, gamerules);
                        gameLobbies.add(gameLobby);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for (GameLobby gl : gameLobbies) {
                globalLobby.getGameLobbies().put(gl.getIdGameLobby(), gl);
            }
        }
    }

    private static int extractIdFromFile(String filename) {
        // Rimuovi la parte iniziale "gamelobby_with_id"
        String idString = filename.substring("Game_With_ID_".length());
        idString = idString.replace(".tmp", "");
        // Parsa l'id come intero
        return Integer.parseInt(idString);
    }


    /**
     * Saves the game lobby information to a JSON file. The data is serialized using Gson library and stored in a temporary
     * file before renaming it to the final file name. This approach provides a higher level of atomicity during the saving
     * process, ensuring that the file is either fully written or not created at all in case of failure.
     *
     * @param gameLobbyInfo the game lobby information to be saved
     * @throws IOException if there was an error in creating or writing to the file
     */
    public static void saveGame(GameLobbyInfo gameLobbyInfo) throws IOException {
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameLobbyInfo.getIdGameLobby() + ".json";
        File file = new File(fileName);
        boolean created = file.createNewFile();

        String tempFileName = fileName + ".tmp";
        File tempFile = new File(tempFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(gsonBuilder.create().toJson(gameLobbyInfo));
        } catch (Exception e) {
            System.out.println("Wasn't able to save the game " + gameLobbyInfo.getIdGameLobby() + " on disk");
        }

        if (tempFile.exists()) {
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
        }
    }


    /**
     * Returns the persistence data of the game that have the specified id. The data is obtained from a json file that is
     * named as Game_With_ID_X.json, where X is the gameId.
     *
     * @param gameId the id of the game
     * @return the persistence data of the game that have the specified id
     * @throws FileNotFoundException if it doesn't exist the file associated to the specified id
     * @throws IOException if it was not possible to close the file input stream
     */
    public static GameLobbyInfo getGameInfo(int gameId) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(jarPathString + "/persistence/Game_With_ID_" + gameId + ".json");
        InputStreamReader streamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        GameLobbyInfo gameLobbyInfo = gsonBuilder.create().fromJson(streamReader, GameLobbyInfo.class);
        streamReader.close();
        return gameLobbyInfo;
    }

    /**
     * Delete the file that is associated to the specified game id
     *
     * @param gameId the id of the game to delete
     */
    public static void deleteGameInfo(int gameId) {
        File fileToDelete = new File(jarPathString + "/persistence/Game_With_ID_" + gameId + ".json");
        try {
            if (!fileToDelete.delete()) throw new Exception();
            System.out.println("Deleted file of game " + gameId);
        } catch (Exception e) {
            System.out.println("Could not delete file of name " + fileToDelete.getName());
        }
    }

    public static void saveGlobalLobby(GlobalLobbyInfo globalLobbyInfo) throws IOException {
        String fileName = jarPathString + "/persistence/GlobalLobby" + ".json";
        File file = new File(fileName);
        boolean created = file.createNewFile();
        try {
            PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
            writer.print(gsonBuilder.create().toJson(globalLobbyInfo));
            writer.close();
        } catch (Exception e) {
            System.out.println("Wasn't able to save the GlobalLobby on disk");
        }
    }

    public static GlobalLobbyInfo getGlobalLobbyInfo() throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(jarPathString + "/persistence/GlobalLobby" + ".json");
        InputStreamReader streamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        GlobalLobbyInfo globalLobbyInfo = gsonBuilder.create().fromJson(streamReader, GlobalLobbyInfo.class);
        streamReader.close();
        return globalLobbyInfo;
    }

    public static void deleteGlobalLobbyInfo() {
        File fileToDelete = new File(jarPathString + "/persistence/GlobalLobby" + ".json");
        try {
            if (!fileToDelete.delete()) throw new Exception();
            System.out.println("Deleted file of GlobalLobby ");
        } catch (Exception e) {
            System.out.println("Could not delete file of name " + fileToDelete.getName());
        }
    }

    /**
     * Save on a file named participants.json the specified game participants
     *
     * @param gamesParticipants the participants of all games that are active or that are saved but not already restored
     * @throws IOException if it wasn't possible to write the object in the file due to a json writer error
     */
    public static void saveGameParticipants(Map<Integer, String[]> gamesParticipants) throws IOException {
        String fileName = jarPathString + "/backupGames/participants.json";
        File file = new File(fileName);
        boolean created = file.createNewFile();
        System.out.println("Saved participants on path: " + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
        writer.setIndent("    ");
        writer.beginArray();
        for (Integer gameId: gamesParticipants.keySet()) {
            writer.beginObject();
            writer.name("id").value(gameId);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < gamesParticipants.get(gameId).length - 1; i++) {
                stringBuilder.append(gamesParticipants.get(gameId)[i]).append(" ");
            }
            stringBuilder.append(gamesParticipants.get(gameId)[gamesParticipants.get(gameId).length - 1]);
            writer.name("participants").value(stringBuilder.toString());
            writer.endObject();
        }
        writer.endArray();
        writer.close();
        fileOutputStream.close();
    }

    /**
     * Returns a map of all the games that are saved in files, associated with the names of the participants
     *
     * @return a map of all the games that are saved in files, associated with the names of the participants
     * @throws IOException if it wasn't possible to write the object in the file due to a json reader error
     */
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
