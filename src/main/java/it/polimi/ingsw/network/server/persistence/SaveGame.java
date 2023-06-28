package it.polimi.ingsw.network.server.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class SaveGame {
    private static final String persistenceDirPath;
    private static final Gson gson;

    static {

        String currentWorkingDir = System.getProperty("user.dir");

        persistenceDirPath = currentWorkingDir + File.separator + "persistence";

        new File(persistenceDirPath).mkdir();


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

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
            for(GameLobby s:gameLobbies.values()){
                System.out.println("ID "+s.getIdGameLobby());
                for(String p:s.getPlayersDisconnectedInGameLobby()){
                    System.out.println(p);
                }
            }
            globalLobby.setGameLobbies(gameLobbies);

        }
    }

    private static int extractIdFromFile(String filename) {
        String idString = filename.substring("Game_With_ID_".length());
        idString = idString.replace(".json", "");
        return Integer.parseInt(idString);
    }

    public static void saveGame(GameLobbyInfo gameLobbyInfo) throws IOException {
        String fileName = persistenceDirPath + File.separator+ "Game_With_ID_" + gameLobbyInfo.getIdGameLobby() + ".json";
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(gameLobbyInfo, writer);
        } catch (IOException e) {
            System.out.println("Wasn't able to save the game " + gameLobbyInfo.getIdGameLobby() + " on disk");
            e.printStackTrace();
        }
    }


    public static GameLobbyInfo getGameInfo(int gameId) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(persistenceDirPath + File.separator + "Game_With_ID_" + gameId + ".json");
        InputStreamReader streamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        GameLobbyInfo gameLobbyInfo = gson.fromJson(streamReader, GameLobbyInfo.class);
        streamReader.close();
        return gameLobbyInfo;
    }

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
