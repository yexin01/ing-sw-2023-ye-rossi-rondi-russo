package it.polimi.ingsw.network.server.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to save the game state to disk, so that execution can resume where it left off even after the server
 * crashes. To resume a game, players will need to reconnect to the server using the same nicknames once it is back up
 * and running.
 */
public class SaveGame {
    private static final String persistenceDirPath;
    private static final Gson gson;

    static {

        String currentWorkingDir = System.getProperty("user.dir");

        persistenceDirPath = currentWorkingDir + File.separator + "persistence";

        new File(persistenceDirPath).mkdir();


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameLobbyInfoAdapter.class, new GameLobbyInfoAdapter());
        gson = gsonBuilder.create();
    }

    /**
     * This method restores each gameLobby saved in the persistence folder exploiting the infos saved at the end of each turn.
     * Called in the constructor of the server to restore the state of the globalLobby after a server crash.
     * @param globalLobby is the globalLobby of the server
     * @param gamerules are the gamerules of the game
     */
    public static void loadGameLobbies(GlobalLobby globalLobby, GameRules gamerules) {
        File persistenceFolder = new File(persistenceDirPath);
        File[] files = persistenceFolder.listFiles();
        ConcurrentHashMap<Integer,GameLobby> gameLobbies=new ConcurrentHashMap<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("Game_With_ID_")) {
                    try {
                        int id = extractIdFromFile(file.getName());
                        GameLobby gameLobby = getGameInfo(id).restoreGameLobby(globalLobby, gamerules);
                        gameLobbies.put(id,gameLobby);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            globalLobby.setGameLobbies(gameLobbies);

        }
    }

    /**
     * Extracts the id of the game from the filename
     * @param filename the name of the file
     * @return the id of the game
     */
    private static int extractIdFromFile(String filename) {
        String idString = filename.substring("Game_With_ID_".length());
        idString = idString.replace(".json", "");
        return Integer.parseInt(idString);
    }

    /**
     * Saves the game on disk
     * @param gameLobbyInfo info of the gameLobby to be saved
     * @throws IOException if the file cannot be saved
     */
    public static void saveGame(GameLobbyInfo gameLobbyInfo) throws IOException {
        String fileName = persistenceDirPath + File.separator+ "Game_With_ID_" + gameLobbyInfo.getIdGameLobby() + ".json";
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(gameLobbyInfo, writer);
        } catch (IOException e) {
            System.out.println("Wasn't able to save the game " + gameLobbyInfo.getIdGameLobby() + " on disk");
            e.printStackTrace();
        }
    }

    /**
     * Loads the game info from disk
     * @param gameId the id of the game to be loaded
     * @return the info of the gameLobby
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if the file cannot be read
     */
    public static GameLobbyInfo getGameInfo(int gameId) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(persistenceDirPath + File.separator + "Game_With_ID_" + gameId + ".json");
        InputStreamReader streamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        GameLobbyInfo gameLobbyInfo = gson.fromJson(streamReader, GameLobbyInfo.class);
        streamReader.close();
        return gameLobbyInfo;
    }

    /**
     * Deletes the file with the infos of the game with the provided id.
     * Called at the end of the game to free the disk space and make the GameLobbyid available for a new game
     * @param gameId the id of the game
     */
    public static void deleteGameInfo(int gameId) {
        File fileToDelete = new File(persistenceDirPath + File.separator + "Game_With_ID_" + gameId + ".json");
        try {
            if (!fileToDelete.delete()) throw new Exception();
            System.out.println("Deleted file of game " + gameId);
        } catch (Exception e) {
            System.out.println("Could not delete file of name " + fileToDelete.getName());
        }
    }
}
